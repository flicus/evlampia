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
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import org.schors.eva.protocol.telegram.TelegramAdapterService;


public class EvaBot extends AbstractVerticle {

    @Override
    public void start() {

        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(1);
        vertx.deployVerticle(new DialogManager(), deploymentOptions, event -> {
            vertx.deployVerticle(new TMVerticle(), deploymentOptions);
            vertx.deployVerticle(new Advice(), deploymentOptions);
        });

        final TelegramAdapterService telegram = TelegramAdapterService.createProxy(vertx, Constants.SERVICE_TELEGRAM);

        vertx.eventBus().consumer(Constants.RESPONSE_HANDLER, response -> {
            JsonObject message = (JsonObject) response.body();
            System.out.println("eb:response handler: " + message);
            if ("ok".equals(message.getString("result"))) {
                telegram.sendMessage(message);
            }
        });

        JsonObject cfg = new JsonObject();
        cfg.put(Constants.MAP_TOKEN, "219739200:AAHXCuDWJPoRhUAjFBXFmljVJhR2uVXdmwc");
        cfg.put(Constants.MAP_NAME, "evlampia_bot");

        telegram.newEndpoint(cfg, event -> {
            if (event.succeeded()) {
                String id = event.result();
                vertx.eventBus().consumer("/telegram/" + id, messageEvent -> {
                    JsonObject message = (JsonObject) messageEvent.body();
                    System.out.println("eb: incoming: " + message);
                    vertx.eventBus().publish(Constants.DM_MESSAGE_HANDLER, message);
                });
            } else {
                throw new RuntimeException("Unable to create telegram bot: " + event.cause());
            }
        });


    }
}
