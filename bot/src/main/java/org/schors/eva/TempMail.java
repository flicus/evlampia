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
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.TelegramApiConfiguration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TempMail extends AbstractVerticle {

    private static final String mailURL = "http://api.temp-mail.ru/request/mail/id/%s/format/json";

    private CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    private Map<Long, Map<String, MailDialog>> dialogs = new ConcurrentHashMap<>();
    private Map<Long, Map<String, Set<EmailHolder>>> mailboxes = new ConcurrentHashMap<>();

    @Override
    public void start() throws Exception {

        vertx.setTimer(30000, new MailChecker());

        JsonArray registerCommand = new JsonArray();
        JsonObject cmd = new JsonObject();
        cmd.put("command", "/mail");
        cmd.put("handler", "temp.mail");
        registerCommand.add(cmd);
        vertx.eventBus().publish("/dialog.manager/command.handler", registerCommand);

        vertx.eventBus().consumer("/message.handler/temp.mail", event -> {
            JsonObject message = (JsonObject) event.body();
            System.out.println("tm:message handler: " + message);
            final JsonObject reply = message.copy();
            Boolean isPrivate = message.getBoolean("private");
            if (isPrivate) {
                Long chatId = message.getLong("chatId");
                String from = message.getString("from");
                final MailDialog dialog = getOrCreateDialog(chatId, from);
                switch (dialog.getState()) {
                    case NEW:
                        JsonObject registerDialog = message.copy();
                        registerDialog.put("command", "openDialog");
                        registerDialog.put("handler", "temp.mail");
                        vertx.eventBus().publish("/dialog.manager/dialog.handler", registerDialog);

                        vertx.executeBlocking(future -> {
                            HttpGet httpGet = new HttpGet("http://api.temp-mail.ru/request/domains/format/json");
                            if (TelegramApiConfiguration.getInstance().getProxy() != null) {
                                RequestConfig response = RequestConfig.custom().setProxy(TelegramApiConfiguration.getInstance().getProxy()).build();
                                httpGet.setConfig(response);
                            }
                            try {
                                CloseableHttpResponse response = httpclient.execute(httpGet);
                                if (response.getStatusLine().getStatusCode() == 200) {
                                    HttpEntity ht = response.getEntity();
                                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                                    String responseContent = EntityUtils.toString(buf, "UTF-8");
                                    JsonArray jsonObject = new JsonArray(responseContent);
                                    List<List<String>> list = new ArrayList<>();
                                    Iterator i = jsonObject.iterator();
                                    while (i.hasNext()) {
                                        List<String> sublist = new ArrayList<>();
                                        sublist.add((String) i.next());
                                        list.add(sublist);
                                    }
                                    JsonArray buttons = new JsonArray(list);
                                    reply.put("text", "Выбери домен");
                                    reply.put("buttons", buttons);
                                    dialog.setState(MailState.SELECTING_DOMAIN);
                                    future.complete(reply);
                                }
                            } catch (Exception e) {
                                future.fail(e);
                            }
                        }, res -> {
                            if (res.succeeded()) {
                                reply.put("result", "ok");
                            } else {
                                reply.put("result", "nok");
                            }
                            vertx.eventBus().publish("/response.handler", reply);
                        });
                        break;
                    case SELECTING_DOMAIN:
                        dialog.setEmail(message.getString("text"));
                        reply.put("text", "Введи имя почтового ящика (часть е-майл адреса перед символом @)");
                        dialog.setState(MailState.SELECTING_ADDRESS);
                        reply.put("result", "ok");
                        vertx.eventBus().publish("/response.handler", reply);
                        break;
                    case SELECTING_ADDRESS:
                        dialog.setEmail(message.getString("text").concat(dialog.getEmail()));
                        dialog.setState(MailState.WAITING_FOR_MAIL);
                        Map<String, Set<EmailHolder>> userList = mailboxes.get(chatId);
                        if (userList == null) {
                            userList = new ConcurrentHashMap<>();
                            mailboxes.put(chatId, userList);
                        }
                        Set<EmailHolder> emailList = userList.get(from);
                        if (emailList == null) {
                            emailList = new HashSet<>();
                            userList.put(from, emailList);
                        }

                        emailList.add(new EmailHolder(dialog.getEmail()));
                        reply.put("text", "Отслеживаю новый адрес: " + dialog.getEmail());
                        dialog.setState(MailState.WAITING_FOR_MAIL);
                        reply.put("result", "ok");
                        JsonObject closeDialog = message.copy();
                        closeDialog.put("command", "closeDialog");
                        vertx.eventBus().publish("/dialog.manager/dialog.handler", closeDialog);
                        vertx.eventBus().publish("/response.handler", reply);
                        break;
                    case WAITING_FOR_MAIL:
                        String t = mailboxes.get(chatId).size() > 0 ? "На данный момент проверяю " : "Пока ничего не проверяю, добавь емайл для мониторинга";
                        reply.put("text", t);
                        reply.put("result", "ok");
                        vertx.eventBus().publish("/response.handler", reply);
                        break;
                }
            } else {
                reply.put("text", "Пройди в приватную беседку");
                reply.put("result", "ok");
                vertx.eventBus().publish("/response.handler", reply);
            }
        });
    }

    private MailDialog getOrCreateDialog(Long chatId, String from) {
        Map<String, MailDialog> userDialogs = dialogs.get(chatId);
        if (userDialogs == null) {
            userDialogs = new ConcurrentHashMap<>();
            dialogs.put(chatId, userDialogs);
        }
        MailDialog dialog = userDialogs.get(from);
        if (dialog == null) {
            dialog = new MailDialog();
            userDialogs.put(from, dialog);
        }
        return dialog;
    }

    private class MailChecker implements Handler<Long> {
        @Override
        public void handle(Long event) {

            System.out.println("mch: handler");
            vertx.executeBlocking(future -> {
                for (Map.Entry<Long, Map<String, Set<EmailHolder>>> chat : mailboxes.entrySet()) {
                    for (Map.Entry<String, Set<EmailHolder>> user : chat.getValue().entrySet()) {
                        for (EmailHolder email : user.getValue()) {
                            try {
                                HttpGet httpGet = new HttpGet(String.format(mailURL, DigestUtils.md5Hex(email.getEmail())));
                                CloseableHttpResponse response = httpclient.execute(httpGet);
                                if (response.getStatusLine().getStatusCode() == 200) {
                                    HttpEntity ht = response.getEntity();
                                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                                    String responseContent = EntityUtils.toString(buf, "UTF-8");
                                    JsonArray jsonObject = new JsonArray(responseContent);
                                    System.out.println(jsonObject);
                                    if (jsonObject != null) {
                                        if (jsonObject.size() > email.count) {
                                            JsonObject mail = jsonObject.getJsonObject(jsonObject.size() - 1);
                                            System.out.println("new email");
                                            JsonObject msg = new JsonObject();
                                            msg.put("chatId", chat.getKey());
                                            msg.put("result", "ok");
                                            msg.put("text", mail.getString("mail_from") + " : " + mail.getString("mail_subject"));
                                            vertx.eventBus().publish("/response.handler", msg);
                                        }
                                        email.setCount(jsonObject.size());
                                    }
                                }
                            } catch (Exception e) {
                                //do  nth
                                e.printStackTrace();
                            }
                            future.complete();
                        }
                    }
                }
            }, res -> {
                vertx.setTimer(30000, new MailChecker());
            });
        }
    }

    private class EmailHolder {
        private String email;
        private int count;

        public EmailHolder(String email) {
            this.email = email;
            count = 0;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "EmailHolder{" +
                    "email='" + email + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
