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

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;
import org.schors.eva.Constants;

public class TelegramAdapter extends AbstractVerticle {

    private TelegramAdapterService telegramAdapterService;

    @Override
    public void start() throws Exception {
        telegramAdapterService = new TelegramAdapterServiceImpl(vertx);
        ProxyHelper.registerService(TelegramAdapterService.class, vertx, telegramAdapterService, Constants.SERVICE_TELEGRAM);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
