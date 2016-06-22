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

package org.schors.eva.protocol.telegram;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.schors.eva.Constants;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelegramHandler extends TelegramLongPollingBot {

    private String token;
    private String botName;
    private MessageListener listener;

    public TelegramHandler(JsonObject cfg, MessageListener listener) {
        token = cfg.getString(Constants.MAP_TOKEN);
        botName = cfg.getString(Constants.MAP_NAME);
        this.listener = listener;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage()) {
            listener.onMessage(update.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMessageInt(Long chatId, Integer messageId, String message, JsonArray buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        if (buttons != null) {
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            markup.setResizeKeyboard(true);
            markup.setOneTimeKeyboad(true);
            markup.setSelective(true);
            sendMessage.setReplayToMessageId(messageId);
            List<List<String>> list = new ArrayList<>();
            Iterator i = buttons.iterator();
            while (i.hasNext()) {
                List<String> sublist = new ArrayList<>();
                sublist.add(((JsonArray) i.next()).getString(0));
                list.add(sublist);
            }
            markup.setKeyboard(list);
            sendMessage.setReplayMarkup(markup);
        } else {
            ReplyKeyboardHide keyboardHide = new ReplyKeyboardHide();
            keyboardHide.setHideKeyboard(true);
            sendMessage.setReplayMarkup(keyboardHide);
        }
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
