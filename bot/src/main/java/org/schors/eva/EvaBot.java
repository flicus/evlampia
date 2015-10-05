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

package org.schors.eva;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.schors.eva.protocol.JabberAdapterService;

public class EvaBot extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        final JabberAdapterService jabber = JabberAdapterService.createProxy(vertx, Constants.SERVICE_JABBER);

        JsonObject cfg = new JsonObject();
        cfg.put(Constants.NICK, "lampa");
        cfg.put(Constants.ORGANIZATION, "kolhoz");
        cfg.put(Constants.JID, "eva");
        cfg.put(Constants.E_MAIL, "aaa.bbb.cc");
        cfg.put(Constants.FIRST_NAME, "lampa");
        cfg.put(Constants.HOST, "sskoptsov01");
        cfg.put(Constants.LAST_NAME, "snow");
        cfg.put(Constants.PASSWORD, "eva");

        jabber.newEndpoint(cfg, connectionEvent -> {
            if (connectionEvent.succeeded()) {
                String id = connectionEvent.result();
                jabber.joinRoom(id, "r1@conference.sskoptsov01", true);

                vertx.eventBus().consumer("/jabber/" + id, messageEvent -> {
                    JsonObject message = (JsonObject) messageEvent.body();
                    String[] tmp = message.getString("from").split("/");
                    if (tmp.length > 0 && !tmp[tmp.length - 1].startsWith("lampa")) {
                        jabber.sendRoomMessage(id, "r1@conference.sskoptsov01", "echo: " + message.getString("body"));
                    }
                });
            } else {
                throw new RuntimeException("Unable to login: " + connectionEvent.cause());
            }
        });
    }
}
