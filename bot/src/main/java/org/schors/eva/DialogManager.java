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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DialogManager extends AbstractVerticle {

    private Map<Long, Map<String, String>> dialogs = new ConcurrentHashMap<>();
    private Map<String, String> commandHandlers = new ConcurrentHashMap<>();

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("/dialog.manager/dialog.handler", event -> {
            JsonObject message = (JsonObject) event.body();
            String cmd = message.getString("command");
            Long chatId = message.getLong("chatId");
            String from = message.getString("from");
            Map<String, String> m = dialogs.get(chatId);
            switch (cmd) {
                case "openDialog":
                    if (m == null) {
                        m = new ConcurrentHashMap<String, String>();
                        dialogs.put(chatId, m);
                    }
                    m.put(from, message.getString("handler"));
                    break;
                case "closeDialog":
                    if (m != null) {
                        m.remove(from);
                    }
                    break;
            }
        });
        vertx.eventBus().consumer("/dialog.manager/command.handler", event -> {
            JsonArray message = (JsonArray) event.body();
            Iterator i = message.iterator();
            while (i.hasNext()) {
                JsonObject obj = (JsonObject) i.next();
                commandHandlers.put(obj.getString("command"), obj.getString("handler"));
            }
        });
        vertx.eventBus().consumer("/dialog.manager/message.handler", event -> {
            JsonObject message = (JsonObject) event.body();
            Long chatId = message.getLong("chatId");
            String from = message.getString("from");
            Map<String, String> chatDialogs = dialogs.get(chatId);
            if (chatDialogs != null && chatDialogs.get(from) != null) {
                vertx.eventBus().publish("/message.handler/" + chatDialogs.get(from), message);
            } else {
                String commandHandler = findCommandHandler(message);
                if (commandHandler != null) {
                    vertx.eventBus().publish("/message.handler/" + commandHandler, message);
                }
            }
        });
    }

    private String findCommandHandler(JsonObject message) {
        String text = message.getString("text");
        for (Map.Entry<String, String> entry : commandHandlers.entrySet()) {
            if (entry.getKey().startsWith(text)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
