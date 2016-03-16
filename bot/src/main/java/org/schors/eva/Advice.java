package org.schors.eva;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
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

import java.io.IOException;

public class Advice extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        JsonArray registerCommand = new JsonArray();
        JsonObject cmd = new JsonObject();
        cmd.put("command", "/sovet");
        cmd.put("handler", "advice");
        registerCommand.add(cmd);
        vertx.eventBus().publish("/dialog.manager/command.handler", registerCommand);

        vertx.eventBus().consumer("/message.handler/advice", event -> {
            JsonObject message = (JsonObject) event.body();
            System.out.println("advice:message handler: " + message);
            final JsonObject reply = message.copy();

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
                    reply.put("text", res.result());
                    reply.put("result", "ok");
                    vertx.eventBus().publish("/response.handler", reply);
                }
            });
        });
    }
}
