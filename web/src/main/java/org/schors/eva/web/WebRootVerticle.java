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

        @Override
        public void handle(final ServerWebSocket webSocket) {

            log("ws handshake");

            //check path
            log(webSocket.path());
            if (!"/ws/api".equals(webSocket.path())) {
                webSocket.reject();
                return;
            }

            //check name
            String query = webSocket.query();
            log(query);
            String parts[] = query.split("&");
            String name = null;
            for (String part : parts) {
                String pairs[] = part.split("=");
                if (pairs != null && pairs.length == 2) {
                    if ("name".equals(pairs[0])) {
                        name = pairs[1];
                        break;
                    }
                }
            }
            log(name);
            if (name == null) {
                webSocket.writeFinalTextFrame(createError(101, "No name").encode());
                webSocket.close();
                return;
            }

            //check if its already exist
            if (connections.containsKey(name)) {
                webSocket.writeFinalTextFrame(createError(102, "Already exist").encode());
                webSocket.close();
                return;
            }

            //all is ok, keep this connection
            connections.put(name, webSocket);

            final String finalName = name;
            final JabberAdapterService jabber = JabberAdapterService.createProxy(vertx, Constants.SERVICE_JABBER);
            jabber.newTmpEndpoint(name, event1 -> {
//                    final String id = event1.result();
                jabber.joinRoom(finalName, "r1@conference.sskoptsov01", false);

                vertx.eventBus().consumer("/jabber/" + finalName, event -> {
                    webSocket.writeFinalTextFrame(event.body().toString());
                });

                webSocket.frameHandler(message -> {
                    jabber.sendRoomMessage(finalName, "r1@conference.sskoptsov01", message.textData());
//                    webSocket.writeFinalTextFrame("Hello");
                });

                webSocket.closeHandler(new Handler<Void>() {
                    @Override
                    public void handle(Void event) {
                        jabber.shutDownEndpoint(finalName);
                        connections.remove(finalName);
                    }
                });
            });
        }
    }

}
