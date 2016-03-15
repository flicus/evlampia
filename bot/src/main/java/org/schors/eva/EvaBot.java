/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 schors
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

package org.schors.eva;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.schors.eva.protocol.telegram.TelegramAdapterService;

import java.io.IOException;


public class EvaBot extends AbstractVerticle {

    @Override
    public void start() {

        final TelegramAdapterService telegram = TelegramAdapterService.createProxy(vertx, Constants.SERVICE_TELEGRAM);

        JsonObject cfg = new JsonObject();
        cfg.put("token", "219739200:AAHXCuDWJPoRhUAjFBXFmljVJhR2uVXdmwc");
        cfg.put("name", "evlampia_bot");

        telegram.newEndpoint(cfg, event -> {
            if (event.succeeded()) {
                String id = event.result();
                vertx.eventBus().consumer("/telegram/" + id, messageEvent -> {
                    JsonObject message = (JsonObject) messageEvent.body();
                    System.out.println(message);
                    vertx.eventBus().publish("/dialog.manager/message.handler", message);
                });
            } else {
                throw new RuntimeException("Unable to create telegram bot: " + event.cause());
            }
        });

        vertx.eventBus().consumer("/response.handler", response -> {
            JsonObject message = (JsonObject) response.body();
            if ("ok".equals(message.getString("result"))) {
                telegram.sendMessage(message);
            }
        });
    }
//
//    private void getAdvice(Handler<AsyncResult<String>> handler) {
//
//        httpClient.request(HttpMethod.GET, "fucking-great-advice.ru", "/api/random", response -> {
//            if (response.statusCode() == 200) {
//                response.bodyHandler(buffer -> {
//                    JSONObject jsonObject = new JSONObject(buffer.toString("UTF-8"));
//                    String advice = jsonObject.getString("text");
//                    if (advice != null) advice = advice.replaceAll("\u00a0", " ").replaceAll("&nbsp;", " ");
//                    handler.handle(Util.makeAsyncResult(advice, null, true));
//                });
//            }
//        })
//                .exceptionHandler(event -> {
//                    event.printStackTrace();
//                    handler.handle(Util.makeAsyncResult(null, event, false));
//
//                })
//                .end();
//
//    }

    private void getAdvice(Handler<AsyncResult<String>> handler) {
        vertx.executeBlocking(future -> {
            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            HttpGet httpGet = new HttpGet("http://fucking-great-advice.ru/api/random");
            try {
                CloseableHttpResponse response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity ht = response.getEntity();
                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                    String responseContent = EntityUtils.toString(buf, "UTF-8");
                    JSONObject jsonObject = new JSONObject(responseContent);
                    String advice = jsonObject.getString("text");
                    if (advice != null) advice = advice.replaceAll("\u00a0", " ").replaceAll("&nbsp;", " ");
                    future.complete(advice);
                } else {
                    future.fail(String.format(
                            "Error response from the server: %s (%d)",
                            response.getStatusLine().getReasonPhrase(),
                            response.getStatusLine().getStatusCode()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                future.fail(e);
            }
        }, res -> {
            if (res.succeeded()) {
                handler.handle(Util.makeAsyncResult((String) res.result(), null, true));
            } else {
                handler.handle(Util.makeAsyncResult((String) null, res.cause(), false));
            }
        });

//        HttpClientOptions options = new HttpClientOptions();
//        HttpClient client = vertx.createHttpClient();
//        client.request(HttpMethod.GET, "", response -> {
//            if (response.statusCode() == 200) {
//                response.bodyHandler(buffer -> {
//                    buffer.toString("UTF-8");
//                });
//            } else {
//                handler.handle(Util.makeAsyncResult(String.format("Error response from the server: %s (%d)", response.statusMessage(), response.statusCode()), null, false));
//            }
//        }).exceptionHandler(e -> {
//            handler.handle(Util.makeAsyncResult(null, e, false));
//        }).end();
    }
}
