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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;
import org.schors.eva.Util;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.TelegramApiConfiguration;
import org.telegram.telegrambots.api.objects.Message;

public class TelegramAdapterServiceImpl implements TelegramAdapterService, MessageListener {

    private static final Logger log = Logger.getLogger(TelegramAdapterServiceImpl.class);

    private TelegramBotsApi telegram = new TelegramBotsApi();
    private TelegramHandler telegramHandler;
    private Vertx vertx;

    public TelegramAdapterServiceImpl(Vertx vertx) {
        this.vertx = vertx;
        TelegramApiConfiguration.getInstance().setProxy("genproxy", 8080, "http");
    }

    @Override
    public void newEndpoint(JsonObject cfg, Handler<AsyncResult<String>> handler) {
        this.telegramHandler = new TelegramHandler(cfg, this);
        try {
            this.telegram.registerBot(this.telegramHandler);
        } catch (TelegramApiException e) {
            log.error(e, e);
            handler.handle(Util.makeAsyncResult((String) null, e, false));
        }
        handler.handle(Util.makeAsyncResult(this.telegramHandler.getBotUsername(), null, true));
    }

    @Override
    public void sendMessage(JsonObject message) {
        this.telegramHandler.sendMessageInt(
                message.getLong("chatId"),
                message.getInteger("messageId"),
                message.getString("text"),
                message.getJsonArray("buttons"));
    }

    @Override
    public void onMessage(Message message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("from", message.getFrom().getUserName());
        jsonObject.put("chatId", message.getChatId());
        jsonObject.put("messageId", message.getMessageId());
        jsonObject.put("text", message.getText());
        vertx.eventBus().publish("/telegram/" + this.telegramHandler.getBotUsername(), jsonObject);
    }

}
