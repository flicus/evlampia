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
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import org.json.JSONObject;
import org.schors.eva.protocol.telegram.TelegramAdapterService;


public class EvaBot extends AbstractVerticle {

    private HttpClient httpClient;
    private TempMail tempMail = new TempMail();

    @Override
    public void start() {

        httpClient = vertx.createHttpClient();

        final TelegramAdapterService telegram = TelegramAdapterService.createProxy(vertx, Constants.SERVICE_TELEGRAM);
//        final JabberAdapterService jabber = JabberAdapterService.createProxy(vertx, Constants.SERVICE_JABBER);

        JsonObject cfg = new JsonObject();
//        cfg.put(Constants.NICK, "lampa");
//        cfg.put(Constants.ORGANIZATION, "kolhoz");
//        cfg.put(Constants.JID, "eva");
//        cfg.put(Constants.E_MAIL, "aaa.bbb.cc");
//        cfg.put(Constants.FIRST_NAME, "lampa");
//        cfg.put(Constants.HOST, "sskoptsov01");
//        cfg.put(Constants.LAST_NAME, "snow");
//        cfg.put(Constants.PASSWORD, "eva");
        cfg.put("token", "219739200:AAHXCuDWJPoRhUAjFBXFmljVJhR2uVXdmwc");
        cfg.put("name", "evlampia_bot");

        telegram.newEndpoint(cfg, event -> {
            if (event.succeeded()) {
                String id = event.result();
                vertx.eventBus().consumer("/telegram/" + id, messageEvent -> {
                    JsonObject message = (JsonObject) messageEvent.body();
                    System.out.println(message);
                    String text = message.getString("message");
                    if (text != null) {
                        if (text.startsWith("/help")) {
                            telegram.sendMessage(message.getLong("chatId"), message.getInteger("messageId"), "Потом как нибудь помогу");
                        } else if (text.startsWith("/sovet")) {
                            getAdvice(e -> {
                                if (e.succeeded()) {
                                    telegram.sendMessage(message.getLong("chatId"), message.getInteger("messageId"), e.result());
                                }
                            });
                        } else if (text.startsWith("/mail")) {
                            telegram.sendMessage(tempMail.processCommand(null));


                        }
                    }
                });
            } else {
                throw new RuntimeException("Unable to create telegram bot: " + event.cause());
            }
        });

//        jabber.newEndpoint(cfg, connectionEvent -> {
//            if (connectionEvent.succeeded()) {
//                String id = connectionEvent.result();
//                jabber.joinRoom(id, "r1@conference.sskoptsov01", true);
//
//                vertx.eventBus().consumer("/jabber/" + id, messageEvent -> {
//                    JsonObject message = (JsonObject) messageEvent.body();
//                    String[] tmp = message.getString("from").split("/");
//                    if (tmp.length > 0 && !tmp[tmp.length - 1].startsWith("lampa")) {
//                        jabber.sendRoomMessage(id, "r1@conference.sskoptsov01", "echo: " + message.getString("body"));
//                    }
//                });
//            } else {
//                throw new RuntimeException("Unable to login: " + connectionEvent.cause());
//            }
//        });
    }

    private void getAdvice(Handler<AsyncResult<String>> handler) {

        httpClient.request(HttpMethod.GET, "fucking-great-advice.ru", "/api/random", response -> {
            if (response.statusCode() == 200) {
                response.bodyHandler(buffer -> {
                    JSONObject jsonObject = new JSONObject(buffer.toString("UTF-8"));
                    String advice = jsonObject.getString("text");
                    if (advice != null) advice = advice.replaceAll("\u00a0", " ").replaceAll("&nbsp;", " ");
                    handler.handle(Util.makeAsyncResult(advice, null, true));
                });
            }
        })
                .exceptionHandler(event -> {
                    event.printStackTrace();
                    handler.handle(Util.makeAsyncResult(null, event, false));

                })
                .end();
            /*CloseableHttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            HttpGet request = new HttpGet("http://fucking-great-advice.ru/api/random");

            CloseableHttpResponse response = client.execute(request);
            HttpEntity ht = response.getEntity();

            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            String responseString = EntityUtils.toString(buf, "UTF-8");

            JSONObject jsonObject = new JSONObject(responseString);
            advice = jsonObject.getString("text");
            if (advice != null) advice = advice.replaceAll("\u00a0", " ").replaceAll("&nbsp;", " ");
            System.out.println(advice);*/

    }
}
