package org.schors.eva;

import io.vertx.core.json.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TempMail {
    CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    private Map<String, MailDialog> dialogs = new ConcurrentHashMap<>();

    public TempMail() {
    }

    public SendMessage processCommand(JsonObject message) {

        String from = message.getString("from");
        MailDialog dialog = dialogs.get(from);
        if (dialog == null) {
            dialog = new MailDialog();
            dialogs.put(from, dialog);
        }

        switch (dialog.getState()) {
            case NEW:
                HttpGet httpGet = new HttpGet("http://api.temp-mail.ru/request/domains/format/json");
                try {
                    CloseableHttpResponse response = httpclient.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity ht = response.getEntity();
                        BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                        String responseContent = EntityUtils.toString(buf, "UTF-8");
                        JSONArray jsonObject = new JSONArray(responseContent);
                        List<List<String>> list = new ArrayList<>();
                        Iterator i = jsonObject.iterator();
                        while (i.hasNext()) {
                            List<String> sublist = new ArrayList<>();
                            sublist.add((String) i.next());
                            list.add(sublist);
                        }

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Выбери домен");
                        sendMessage.setChatId(message.getLong("chatId").toString());
                        sendMessage.setReplayToMessageId(message.getInteger("messageId"));

                        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
                        markup.setKeyboard(list);

                        sendMessage.setReplayMarkup(markup);
                        return sendMessage;
                    }
                } catch (Exception e) {

                }
                break;
            case SELECTING_DOMAIN:
                break;
            case SELECTING_ADDRESS:
                break;
            case WAITING_FOR_MAIL:
                break;
        }

        return null;

    }
}
