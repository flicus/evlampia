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

package org.schors.eva.protocol.jabber;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
public interface JabberAdapterService {

    static JabberAdapterService create(Vertx vertx) {
        return new JabberAdapterServiceImpl(vertx);
    }

    static JabberAdapterService createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(JabberAdapterService.class, vertx, address);
    }

    void newEndpoint(JsonObject cfg, Handler<AsyncResult<String>> handler);

    void newTmpEndpoint(String nick, Handler<AsyncResult<String>> handler);

    void shutDownEndpoint(String endpointId);

    void joinRoom(String endpointId, String room, boolean look4subject);

    void sendRoomMessage(String endpointId, String room, String message);

    void sendRoomMessagePrivate(String endpointId, String room, String message);

    void sendMessage(String endpointId, String jid, String message);

    void getRoomParticipants(String endpointId, String room, Handler<AsyncResult<String>> handler);
}
