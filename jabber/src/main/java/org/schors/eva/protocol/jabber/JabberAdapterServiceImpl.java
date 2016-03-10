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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.schors.eva.Constants;
import org.schors.eva.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JabberAdapterServiceImpl implements JabberAdapterService {

    private static final Logger log = Logger.getLogger(JabberAdapterServiceImpl.class);

    private Map<String, Endpoint> endpointMap = new HashMap<>();
    private Vertx vertx;

    public JabberAdapterServiceImpl(Vertx vertx) {
        this.vertx = vertx;
        AccountManager.sensitiveOperationOverInsecureConnectionDefault(true);
    }

    @Override
    public void newEndpoint(JsonObject cfg, Handler<AsyncResult<String>> handler) {

        SmackConfiguration.DEBUG = true;

        Endpoint endpoint = new Endpoint(UUID.randomUUID().toString(), cfg.getString(Constants.NICK));
        endpointMap.put(endpoint.getId(), endpoint);

        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
        SmackConfiguration.setDefaultPacketReplyTimeout(15000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword(cfg.getString(Constants.JID), cfg.getString(Constants.PASSWORD));
        configBuilder.setResource("taiga");
        configBuilder.setServiceName(cfg.getString(Constants.HOST));
        configBuilder.setHost(cfg.getString(Constants.HOST));
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setDebuggerEnabled(true);
//        configBuilder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.required);
//        SASLMechanism mechanism = new SASLDigestMD5Mechanism();
//        SASLAuthentication.registerSASLMechanism(mechanism);
//        SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
//        SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");

        XMPPTCPConnection connection = new XMPPTCPConnection(configBuilder.build());
        endpoint.setConnection(connection);

        try {
            connection.connect().login();
            connection.addConnectionListener(new ConnListener(endpoint));
            configure(connection, cfg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e, e);
            handler.handle(Util.makeAsyncResult(null, e, false));
            return;
        }
        handler.handle(Util.makeAsyncResult(endpoint.getId(), null, true));
    }

    @Override
    public void newTmpEndpoint(String nick, Handler<AsyncResult<String>> handler) {
        Endpoint endpoint = new Endpoint(UUID.randomUUID().toString(), nick);
        endpointMap.put(endpoint.getId(), endpoint);

        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
        SmackConfiguration.setDefaultPacketReplyTimeout(15000);

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setResource("evachat");
        configBuilder.setServiceName("sskoptsov01");
        configBuilder.setHost("sskoptsov01");
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        XMPPTCPConnection connection = new XMPPTCPConnection(configBuilder.build());
        endpoint.setConnection(connection);

        try {
            connection.connect();
            TemporaryAccountManager.Account account = TemporaryAccountManager.getInstance().getTemporaryAccount(connection);
            connection.login(account.getName(), account.getPassword());
            connection.addConnectionListener(new ConnListener(endpoint));
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Ready");
            presence.setPriority(10);
            connection.sendStanza(presence);
        } catch (Exception e) {
            log.error(e, e);
        }

        handler.handle(Util.makeAsyncResult(endpoint.getId(), null, true));
    }

    @Override
    public void shutDownEndpoint(String endpointId) {
        Endpoint endpoint = endpointMap.remove(endpointId);
        try {
            if (endpoint != null && endpoint.getConnection().isConnected()) {
                for (MultiUserChat room : endpoint.getRooms().values()) {
                    if (room.isJoined()) {
                        room.sendMessage("/me закрыл(а) браузер");
                        room.leave();
                    }
                }
                Presence p = new Presence(Presence.Type.unavailable);
                p.setStatus("Пака, бугагашечки");
                endpoint.getConnection().disconnect(p);
            }
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    @Override
    public void joinRoom(String endpointId, String room, boolean look4subject) {
        Endpoint endpoint = endpointMap.get(endpointId);
        if (endpoint != null) {
            endpoint.joinRoom(room, new RoomListener(endpoint), new RoomPresenceListener(endpoint), look4subject);
        }
    }

    @Override
    public void sendRoomMessage(String endpointId, String room, String message) {
        Endpoint endpoint = endpointMap.get(endpointId);
        if (endpoint != null) {
            try {
                endpoint.getRoom(room).sendMessage(message);
            } catch (SmackException.NotConnectedException e) {
                log.error(e, e);
            }
        }
    }

    @Override
    public void sendRoomMessagePrivate(String endpointId, String room, String message) {    //todo
        Endpoint endpoint = endpointMap.get(endpointId);
        if (endpoint != null) {
            try {
                endpoint.getRoom(room).sendMessage(message);
            } catch (SmackException.NotConnectedException e) {
                log.error(e, e);
            }
        }
    }

    @Override
    public void sendMessage(String endpointId, String jid, String message) {
        Endpoint endpoint = endpointMap.get(endpointId);
        if (endpoint != null) {
            endpoint.sendMessage(jid, message);
        }
    }

    @Override
    public void getRoomParticipants(String endpointId, String room, Handler<AsyncResult<String>> handler) {
        Endpoint endpoint = endpointMap.get(endpointId);
        if (endpoint != null) {
            List<String> occupants = endpoint.getRoom(room).getOccupants();
            handler.handle(Util.makeAsyncResult(new JsonArray(occupants).encode(), null, true));
        }
    }

    private void configure(XMPPConnection connection, JsonObject cfg) throws Exception {
        VCard vCard = new VCard();
        vCard.setFirstName(cfg.getString(Constants.FIRST_NAME));
        vCard.setLastName(cfg.getString(Constants.LAST_NAME));
        vCard.setEmailHome(cfg.getString(Constants.E_MAIL));
        vCard.setJabberId(cfg.getString(Constants.JID));
        vCard.setOrganization(cfg.getString(Constants.ORGANIZATION));
        vCard.setNickName(cfg.getString(Constants.NICK));
        VCardManager.getInstanceFor(connection).saveVCard(vCard);

        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Ready");
        presence.setPriority(10);
        connection.sendStanza(presence);
    }

    private class ConnListener implements ConnectionListener {

        private Endpoint endpoint;

        public ConnListener(Endpoint endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public void connected(XMPPConnection connection) {
            log.warn("endpoint connected: " + endpoint.getId());
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {
            log.warn("endpoint authenticated: " + endpoint.getId());
        }

        @Override
        public void connectionClosed() {
            log.warn("Connection closed: " + endpoint.getId());
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

            Thread task = new Thread(() -> {
                log.debug("Joiner thread started");
                try {
                    while (!endpoint.getConnection().isAuthenticated()) {
                        Thread.currentThread().sleep(100);
                    }
                } catch (Exception e) {
                    log.error("Interrupted: ", e);
                }
                log.debug("Reauthenticated: " + endpoint.getId());
                endpoint.joinRooms();
            });
            task.start();
        }

        @Override
        public void reconnectionFailed(Exception e) {
            log.warn("Reconnection failed: ", e);
        }
    }

    private class RoomPresenceListener implements PresenceListener {

        private Endpoint endpoint;

        public RoomPresenceListener(Endpoint endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public void processPresence(Presence presence) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("from", presence.getFrom());
            jsonObject.put("to", presence.getTo());
            jsonObject.put("type", presence.getType().name());
            jsonObject.put("command", 6);
            vertx.eventBus().publish("/jabber/" + endpoint.getId(), jsonObject);
        }
    }

    private class RoomListener implements MessageListener {

        private Endpoint endpoint;

        public RoomListener(Endpoint endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public void processMessage(Message message) {
            if (!message.getBodies().isEmpty()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.put("from", message.getFrom());
                jsonObject.put("to", message.getTo());
                jsonObject.put("subject", message.getSubject());
                jsonObject.put("body", message.getBody());
                jsonObject.put("command", 2);
                vertx.eventBus().publish("/jabber/" + endpoint.getId(), jsonObject);
            }
        }
    }

}
