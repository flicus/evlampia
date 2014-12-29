/*
 * The MIT License (MIT)
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
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

package org.schors.eva.protocol;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.schors.eva.Application;
import org.schors.eva.Version;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ProtocolAdapter(
        name = "jabber",
        version = @Version(major = 1, minor = 0))
public class JabberAdapter extends AbstractProtocolAdapter {
    private static final Logger log = Logger.getLogger(JabberAdapter.class);

    private XMPPConnection connection;
    private Map<String, MultiUserChat> rooms = new ConcurrentHashMap<>();

    public JabberAdapter(Application application) {
        super(application);
    }

    @Override
    public void start() {
        JabberConfiguration cfg = getConfiguration(JabberConfiguration.class);
        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
        SmackConfiguration.setDefaultPacketReplyTimeout(15000);

        connection = new XMPPTCPConnection(cfg.getHost());
        try {
            connection.connect();
            connection.addConnectionListener(new ConnListener());
            connection.login(cfg.getJabberId(), cfg.getPassword());
            configure();
            joinRooms();
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    @Override
    public void stop() {
        shutDown();
        rooms.clear();
        connection = null;
    }

    @Override
    public void onConfigurationChange() {
        stop();
        start();
    }

    private void configure() throws Exception {
        JabberConfiguration cfg = getConfiguration(JabberConfiguration.class);
        VCard vCard = new VCard();
        vCard.setFirstName(cfg.getFirstName());
        vCard.setLastName(cfg.getLastName());
        vCard.setEmailHome(cfg.getEmail());
        vCard.setJabberId(cfg.getJabberId());
        vCard.setOrganization(cfg.getOrganization());
        vCard.setNickName(cfg.getNick());
        vCard.save(connection);

        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Ready");
        connection.sendPacket(presence);
        presence.setPriority(10);
    }

    private void joinRooms() {
        JabberConfiguration cfg = getConfiguration(JabberConfiguration.class);
        if (cfg.getRooms().size() > 0) {
            for (String room : cfg.getRooms()) {
                MultiUserChat tmpMuc = rooms.get(room);
                final MultiUserChat muc = tmpMuc == null ? new MultiUserChat(connection, room) : tmpMuc;

                try {
                    muc.join(cfg.getNick());
                } catch (Exception e) {
                    log.error(e, e);
                }

                if (tmpMuc == null) {
                    muc.addMessageListener(new RoomListener());
                    muc.addSubjectUpdatedListener(new SubjectUpdatedListener() {
                        @Override
                        public void subjectUpdated(String subject, String from) {
                            if (!subject.contains("http://0xffff.net/logs")) {
                                try {
                                    muc.changeSubject(subject + " | Логи чата: http://0xffff.net/logs");
                                } catch (Exception ex) {
                                    log.error(ex, ex);
                                }
                            }
                        }
                    });
                    rooms.put(room, muc);
                }
            }
        }
    }

    private void shutDown() {
        try {
            if (connection.isConnected()) {
                for (Map.Entry<String, MultiUserChat> entry : rooms.entrySet()) {
                    if (entry.getValue().isJoined()) {
                        entry.getValue().sendMessage("/me ушла на обед");
                        entry.getValue().leave();
                    }
                }
                Presence p = new Presence(Presence.Type.unavailable);
                p.setStatus("Пака, бугагашечки");
                connection.disconnect(p);
            }
        } catch (Exception e) {
            log.warn("Error on shutdown", e);
        }
    }

    private class ConnListener implements ConnectionListener {

        @Override
        public void connected(XMPPConnection connection) {

        }

        @Override
        public void authenticated(XMPPConnection connection) {

        }

        @Override
        public void connectionClosed() {
            log.warn("Connection closed");
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            log.warn("Connection closed on error: ", e);
        }

        @Override
        public void reconnectingIn(int seconds) {
            log.warn("Reconnect in " + seconds);
        }

        @Override
        public void reconnectionSuccessful() {
            log.warn("Reconnect successfull");

            Thread task = new Thread(new Runnable() {

                @Override
                public void run() {
                    log.debug("Joiner thread started");
                    try {
                        while (!connection.isAuthenticated()) {
                            Thread.currentThread().sleep(100);
                        }
                    } catch (Exception e) {
                        log.error("Interrupted: ", e);
                    }
                    log.debug("Reauthenticated");
                    joinRooms();
                }
            });
            task.start();
        }

        @Override
        public void reconnectionFailed(Exception e) {
            log.warn("Reconnection failed: ", e);
        }
    }

    private class RoomListener implements PacketListener {

        @Override
        public void processPacket(Packet packet) throws SmackException.NotConnectedException {

        }
    }

}
