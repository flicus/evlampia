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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.tempmail.TempMailClient;
import io.vertx.tempmail.TempMailOptions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.TelegramApiConfiguration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TMVerticle extends AbstractVerticle {

    private static final String ID = "temp.mail";

    private TempMailClient tempMailClient;
    private Map<Long, Map<String, MailDialog>> dialogs = new ConcurrentHashMap<>();
    private Map<String, Long> mai = new ConcurrentHashMap<>();

    @Override
    public void start() throws Exception {

        tempMailClient = TempMailClient.create(vertx, new TempMailOptions().setProxy("genproxy", 8080, "http"));
        final Handler<AsyncResult<JsonObject>> emailHandler = new Handler<AsyncResult<JsonObject>>() {
            @Override
            public void handle(AsyncResult<JsonObject> event) {

            }
        };
        vertx.setTimer(30000, new MailChecker());

        JsonArray registerCommand = new JsonArray();
        JsonObject cmd = new JsonObject();
        cmd.put(Constants.MAP_COMMAND, "/mail");
        cmd.put(Constants.MAP_HANDLER, ID);
        registerCommand.add(cmd);
        vertx.eventBus().publish(Constants.DM_COMMAND_HANDLER, registerCommand);

        vertx.eventBus().consumer(Constants.MESSAGE_HANDLER + "/" + ID, event -> {
            JsonObject message = (JsonObject) event.body();
            System.out.println("tm:message handler: " + message);
            final JsonObject reply = message.copy();
            Boolean isPrivate = message.getBoolean(Constants.MAP_PRIVATE);
            if (isPrivate) {
                Long chatId = message.getLong(Constants.MAP_CHAT_ID);
                String from = message.getString(Constants.MAP_FROM);
                final MailDialog dialog = getOrCreateDialog(chatId, from);
                switch (dialog.getState()) {
                    case NEW_DIALOG:
                        JsonObject registerDialog = message.copy();
                        registerDialog.put(Constants.MAP_COMMAND, Constants.CMD_OPEN_DIALOG);
                        registerDialog.put(Constants.MAP_HANDLER, ID);
                        vertx.eventBus().publish(Constants.DM_DIALOG_HANDLER, registerDialog);
                        tempMailClient.getSupportedDomains(e -> {
                            if (e.succeeded()) {
                                List<List<String>> list = new ArrayList<>();
                                Iterator i = e.result().getJsonArray(Constants.MAP_RESULT).iterator();
                                while (i.hasNext()) {
                                    List<String> sublist = new ArrayList<>();
                                    sublist.add((String) i.next());
                                    list.add(sublist);
                                }
                                JsonArray buttons = new JsonArray(list);
                                reply.put(Constants.MAP_TEXT, "Выбери домен");
                                reply.put(Constants.MAP_BUTTONS, buttons);
                                reply.put(Constants.MAP_RESULT, Constants.RES_OK);
                                dialog.setState(MailState.SELECTING_DOMAIN);
                                vertx.eventBus().publish(Constants.RESPONSE_HANDLER, reply);
                            }
                        });
                        break;
                    case SELECTING_DOMAIN:
                        dialog.setEmail(message.getString(Constants.MAP_TEXT));
                        reply.put(Constants.MAP_TEXT, "Введи имя почтового ящика (часть е-майл адреса перед символом @)");
                        dialog.setState(MailState.SELECTING_ADDRESS);
                        reply.put(Constants.MAP_RESULT, Constants.RES_OK);
                        vertx.eventBus().publish(Constants.RESPONSE_HANDLER, reply);
                        break;
                    case SELECTING_ADDRESS:
                        dialog.setEmail(message.getString(Constants.MAP_TEXT).concat(dialog.getEmail()));
                        dialog.setState(MailState.WAITING_FOR_MAIL);
                        tempMailClient.createMailListener(dialog.getEmail(), emailHandler);
                        reply.put(Constants.MAP_TEXT, "Отслеживаю новый адрес: " + dialog.getEmail());
                        dialog.setState(MailState.WAITING_FOR_MAIL);
                        reply.put(Constants.MAP_RESULT, Constants.RES_OK);
                        JsonObject closeDialog = message.copy();
                        closeDialog.put(Constants.MAP_COMMAND, Constants.CMD_CLOSE_DIALOG);
                        vertx.eventBus().publish(Constants.DM_DIALOG_HANDLER, closeDialog);
                        vertx.eventBus().publish(Constants.RESPONSE_HANDLER, reply);
                        break;
                    case WAITING_FOR_MAIL:
                        String t = mailboxes.get(chatId).size() > 0
                                ? "На данный момент проверяю: " + getCheckedEmails(chatId, from) + helpText(chatId, from)
                                : "Пока ничего не проверяю, добавь емайл для проверки";
                        reply.put(Constants.MAP_TEXT, t);
                        reply.put(Constants.MAP_RESULT, Constants.RES_OK);
                        vertx.eventBus().publish(Constants.RESPONSE_HANDLER, reply);
                        break;
                }
            } else {
                reply.put(Constants.MAP_TEXT, "Пройди в приватную беседку");
                reply.put(Constants.MAP_RESULT, Constants.RES_OK);
                vertx.eventBus().publish(Constants.RESPONSE_HANDLER, reply);
            }
        });
    }

    private String helpText(Long chatId, String from) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nДоступные команды:");

        MailState state = MailState.NEW_DIALOG;
        Map<String, MailDialog> users = dialogs.get(chatId);
        if (users != null) {
            MailDialog mailDialog = users.get(from);
            if (mailDialog != null) {
                state = mailDialog.getState();
            }
        }

        switch (state) {
            case NEW_DIALOG:
                break;
            case SELECTING_ADDRESS:
                sb.append("\n/stop - отмена");
                break;
            case SELECTING_DOMAIN:
                sb.append("\n/stop - отмена");
                break;
            case WAITING_FOR_MAIL:
                sb.append("\n/stop - прекратить все проверки")
                        .append("\n/show - показать письма")
                        .append("\n/add  - добавить почтовый ящик")
                        .append("\n/delete - удалить почтовый ящик");
                break;
        }
        return sb.toString();
    }

    private String getCheckedEmails(Long chatId, String from) {
        StringBuilder sb = new StringBuilder("\n");
        Map<String, Set<EmailHolder>> users = mailboxes.get(chatId);
        if (users != null) {
            Set<EmailHolder> emails = users.get(from);
            if (emails != null && emails.size() > 0) {
                for (EmailHolder emailHolder : emails) {
                    sb.append(emailHolder.getEmail()).append("\n");
                }
            }
        }
        return sb.toString();
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
                                if (TelegramApiConfiguration.getInstance().getProxy() != null) {
                                    RequestConfig response = RequestConfig.custom().setProxy(TelegramApiConfiguration.getInstance().getProxy()).build();
                                    httpGet.setConfig(response);
                                }
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
                                            msg.put(Constants.MAP_CHAT_ID, chat.getKey());
                                            msg.put(Constants.MAP_RESULT, Constants.RES_OK);
                                            msg.put(Constants.MAP_TEXT, mail.getString("mail_from") + " : " + mail.getString("mail_subject"));
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
