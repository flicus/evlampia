/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.schors.evlampia.model;

public class Jabber {
    private String botName;
    private String jabberServer;
    private String botNick;
    private String jabberId;
    private String jabberPassword;
    private Room[] rooms;

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotNick() {
        return botNick;
    }

    public void setBotNick(String botNick) {
        this.botNick = botNick;
    }

    public String getJabberId() {
        return jabberId;
    }

    public void setJabberId(String jabberId) {
        this.jabberId = jabberId;
    }

    public String getJabberPassword() {
        return jabberPassword;
    }

    public void setJabberPassword(String jabberPassword) {
        this.jabberPassword = jabberPassword;
    }

    public String getJabberServer() {
        return jabberServer;
    }

    public void setJabberServer(String jabberServer) {
        this.jabberServer = jabberServer;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Jabber{" + "botName=" + botName + "jabberServer=" + jabberServer + "botNick=" + botNick + "jabberId=" + jabberId + "jabberPassword=" + jabberPassword + "rooms=" + rooms + '}';
    }


}
