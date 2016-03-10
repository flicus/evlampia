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

import org.apache.log4j.Logger;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PresenceListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Endpoint {

    private static final Logger log = Logger.getLogger(Endpoint.class);

    private String id;
    private String nick;
    private XMPPTCPConnection connection;
    private Map<String, MultiUserChat> rooms = new ConcurrentHashMap<>();

    public Endpoint(String id, String nick) {
        this.id = id;
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public XMPPTCPConnection getConnection() {
        return connection;
    }

    public void setConnection(XMPPTCPConnection connection) {
        this.connection = connection;
    }

    public Map<String, MultiUserChat> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, MultiUserChat> rooms) {
        this.rooms = rooms;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void joinRoom(String room, MessageListener listener, PresenceListener presenceListener, boolean look4subject) {
        if (!rooms.containsKey(room)) {
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            MultiUserChat muc = manager.getMultiUserChat(room);
            try {
                muc.join(nick);
                muc.addMessageListener(listener);
                muc.addParticipantListener(presenceListener);
                if (look4subject) {
                    muc.addSubjectUpdatedListener((subject, from) -> {
                        if (!subject.contains("http://0xffff.net/logs")) {
                            try {
                                muc.changeSubject(subject + " | Логи чата: http://0xffff.net/logs");
                            } catch (Exception ex) {
                                log.error(ex, ex);
                            }
                        }
                    });
                }
                rooms.put(room, muc);
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    public void joinRooms() {
        for (MultiUserChat room : rooms.values()) {
            try {
                if (!room.isJoined()) {
                    room.join(nick);
                }
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    public MultiUserChat getRoom(String room) {
        return rooms.get(room);
    }

    public void sendMessage(String jid, String message) {
        try {
            connection.sendStanza(new Message(jid, message));
        } catch (SmackException.NotConnectedException e) {
            log.error(e, e);
        }
    }

}
