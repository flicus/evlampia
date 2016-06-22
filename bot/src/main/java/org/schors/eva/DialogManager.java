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
        vertx.eventBus().consumer(Constants.DM_DIALOG_HANDLER, event -> {
            JsonObject message = (JsonObject) event.body();
            System.out.println("dm:dialog handler: " + message);
            String cmd = message.getString(Constants.MAP_COMMAND);
            Long chatId = message.getLong(Constants.MAP_CHAT_ID);
            String from = message.getString(Constants.MAP_FROM);
            Map<String, String> m = dialogs.get(chatId);
            switch (cmd) {
                case Constants.CMD_OPEN_DIALOG:
                    if (m == null) {
                        m = new ConcurrentHashMap<String, String>();
                        dialogs.put(chatId, m);
                    }
                    m.put(from, message.getString(Constants.MAP_HANDLER));
                    break;
                case Constants.CMD_CLOSE_DIALOG:
                    if (m != null) {
                        m.remove(from);
                    }
                    break;
            }
        });
        vertx.eventBus().consumer(Constants.DM_COMMAND_HANDLER, event -> {
            JsonArray message = (JsonArray) event.body();
            System.out.println("dm:command handler" + message);
            Iterator i = message.iterator();
            while (i.hasNext()) {
                JsonObject obj = (JsonObject) i.next();
                commandHandlers.put(obj.getString(Constants.MAP_COMMAND), obj.getString(Constants.MAP_HANDLER));
            }
        });
        vertx.eventBus().consumer(Constants.DM_MESSAGE_HANDLER, event -> {
            JsonObject message = (JsonObject) event.body();
            System.out.println("dm:message handler: " + message);
            Long chatId = message.getLong(Constants.MAP_CHAT_ID);
            String from = message.getString(Constants.MAP_FROM);
            Map<String, String> chatDialogs = dialogs.get(chatId);
            if (chatDialogs != null && chatDialogs.get(from) != null) {
                vertx.eventBus().publish(Constants.MESSAGE_HANDLER + "/" + chatDialogs.get(from), message);
            } else {
                String commandHandler = findCommandHandler(message);
                if (commandHandler != null) {
                    vertx.eventBus().publish(Constants.MESSAGE_HANDLER + "/" + commandHandler, message);
                }
            }
        });
    }

    private String findCommandHandler(JsonObject message) {
        String text = message.getString(Constants.MAP_TEXT);
        for (Map.Entry<String, String> entry : commandHandlers.entrySet()) {
            if (text.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
