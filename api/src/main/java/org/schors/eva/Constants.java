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

public interface Constants {

    String HOST = "host";
    String JID = "jid";
    String PASSWORD = "password";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String E_MAIL = "email";
    String ORGANIZATION = "organization";
    String NICK = "nick";

    String SERVICE_JABBER = "adapter.jabber";
    String SERVICE_TELEGRAM = "adapter.telegram";
    String MSG_JABBER_READY = "jabber.ready";
    String MSG_JABBER_SHUTDOWN = "jabber.shutdown";

    String MAP_TOKEN = "token";
    String MAP_NAME = "name";
    String MAP_COMMAND = "command";
    String MAP_CHAT_ID = "chatId";
    String MAP_FROM = "from";
    String MAP_HANDLER = "handler";
    String MAP_TEXT = "text";
    String MAP_PRIVATE = "private";
    String MAP_RESULT = "result";
    String MAP_BUTTONS = "buttons";

    String CMD_OPEN_DIALOG = "openDialog";
    String CMD_CLOSE_DIALOG = "closeDialog";

    String RESPONSE_HANDLER = "/response.handler";
    String MESSAGE_HANDLER = "/message.handler";

    String DM_MESSAGE_HANDLER = "/dialog.manager/message.handler";
    String DM_DIALOG_HANDLER = "/dialog.manager/dialog.handler";
    String DM_COMMAND_HANDLER = "/dialog.manager/command.handler";

    String RES_OK = "ok";




}
