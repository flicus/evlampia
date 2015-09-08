/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import org.schors.eva.Constants;
import org.schors.eva.protocol.JabberAdapterService;

import java.util.HashMap;
import java.util.Map;

public class WebRootVerticle extends AbstractVerticle {

    private Map<String, ServerWebSocket> connections = new HashMap<>();
    private HttpServer server;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServerOptions options = new HttpServerOptions()
                .setMaxWebsocketFrameSize(10000);
        server = vertx.createHttpServer(options);
        server.websocketHandler(new WebSocketHandler());
        server.requestHandler(new HttpRequestHandler());
        server.listen(8080);
        startFuture.succeeded();
    }

    private JsonObject createError(int code, String message) {
        JsonObject ret = new JsonObject()
                .put("result", code)
                .put("error", message);
        return ret;
    }

    private JsonObject createMessage(String from, String message) {
        JsonObject ret = new JsonObject()
                .put("result", 0)
                .put("from", from)
                .put("message", message);
        return ret;
    }

    private void log(String message) {
        System.out.println(message);
    }

    private class HttpRequestHandler implements Handler<HttpServerRequest> {

        @Override
        public void handle(HttpServerRequest request) {
            String file = "";
            log(request.path());
            if (request.path().equals("/")) {
                file = "index.html";
            } else if (!request.path().contains("..")) {
                file = request.path();
            }
            request.response().sendFile("web/" + file);
        }
    }

    private class WebSocketHandler implements Handler<ServerWebSocket> {

        private JabberAdapterService jabber;
        private String name;
        private String id;
        private boolean connected = false;

        @Override
        public void handle(final ServerWebSocket webSocket) {
            log("ws handshake");
            if (!"/ws/api".equals(webSocket.path())) {
                webSocket.reject();
                return;
            }

            webSocket.frameHandler(message -> {
                log(message.textData());
                JsonObject jsonObject = new JsonObject(message.textData());
                int cmd = jsonObject.getInteger(Constants.JSON_COMMAND_CODE);
                switch (cmd) {

                    case 1: {   //new connection
                        String name = jsonObject.getString(Constants.JSON_NAME);
                        if (name != null) {
                            this.name = name;
                            jabber = JabberAdapterService.createProxy(vertx, Constants.SERVICE_JABBER);
                            jabber.newTmpEndpoint(name, event -> {
                                if (event.succeeded()) {
                                    this.id = event.result();
                                    jabber.joinRoom(this.id, "r1@conference.sskoptsov01", false);

                                    vertx.eventBus().consumer("/jabber/" + this.id, event2 -> {
                                        JsonObject msg = (JsonObject) event2.body();
                                        webSocket.writeFinalTextFrame(msg.encode());
                                    });

                                    connected = true;
                                } else {
                                    webSocket.writeFinalTextFrame(
                                            createError(103, "Unable to connect: " + event.cause().getMessage()).encode());
                                }
                            });
                        } else {
                            webSocket.writeFinalTextFrame(createError(102, "Name field is absent").encode());
                        }
                        break;
                    }

                    case 2: {   //generic message
                        if (!connected) {
                            webSocket.writeFinalTextFrame(createError(104, "Not connected").encode());
                        } else {
                            jabber.sendRoomMessage(id, "r1@conference.sskoptsov01", jsonObject.getString("message"));
                        }
                        break;
                    }

                    case 3: {   //drop connection
                        connected = false;
                        if (jabber != null) {
                            jabber.shutDownEndpoint(id);
                        }
                        break;
                    }

                    default: {  //unknown command
                        webSocket.writeFinalTextFrame(createError(101, "Unknown command").encode());
                    }
                }
            });

            webSocket.closeHandler(event -> {
                connected = false;
                if (jabber != null) {
                    jabber.shutDownEndpoint(id);
                }
            });
        }
    }
}
