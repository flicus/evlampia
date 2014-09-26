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

package org.schors.evlampia.core;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;
import org.jivesoftware.smackx.packet.VCard;
import org.schors.evlampia.commands.HelpCmd;
import org.schors.evlampia.dao.vbotDAOHTMLImplementation;
import org.schors.evlampia.dao.vbotDAOInterface;
import org.schors.evlampia.model.Room;
import org.schors.evlampia.rss.FeedReader;
import org.schors.evlampia.tracker.TracksManager;

import java.io.File;
import java.util.*;

//import org.schors.evlampia.Twittor;

public class Jbot implements PacketListener, ConnectionListener {

    public static final String F_DAO = "dao";
    public static final String F_TRACKER = "tracker";
    public static final String F_FEED_READER = "feedReader";
    public static final String F_XMPP_CONNECTION = "xmppConnection";
    public static final String F_RANDOM = "random";
    public static final String F_MUC = "muc";
    public static final String F_TWIT = "twittor";
    public static final String F_CALENDAR = "calendar";


    public static String newline = System.getProperty("line.separator");
    public static String fileSeparator = System.getProperty("file.separator");
    private static final Logger log = Logger.getLogger(Jbot.class);
    private Connection conn;
    private Map<String, MultiUserChat> rooms = new HashMap<String, MultiUserChat>();
    private vbotDAOInterface dao;
    private FeedReader feedReader;
    private TracksManager privateTrackManager;
    //    private Twittor twittor = new Twittor();
    private File hostFile;
    private Random random = new Random(System.currentTimeMillis());
    private CommandManager commandManager = new CommandManagerImpl();

    private static Map<String, Object> facilities = new HashMap<>();


    public Jbot() {
        String pth = Jbot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        hostFile = new File(pth);
        log.debug("### Host file: " + hostFile.getAbsolutePath());
        dao = vbotDAOHTMLImplementation.getInstance();
        privateTrackManager = new TracksManager(hostFile.getParent() + fileSeparator + "private.list", new TracksManager.Sender() {
            public void sendMessage(String message, String to) {
                if (conn != null && conn.isConnected()) {
                    Message msg = new Message(to, Message.Type.chat);
                    msg.setBody(message);
                    conn.sendPacket(msg);
                }
            }
        });
        privateTrackManager.load();
//        twittor.init(rooms);
//        twittor.start();
        feedReader = new FeedReader(rooms);
        feedReader.setFileName("feeds.list");
        feedReader.load();

        facilities.put(F_DAO, dao);
        facilities.put(F_TRACKER, privateTrackManager);
        facilities.put(F_FEED_READER, feedReader);
        facilities.put(F_XMPP_CONNECTION, conn);
        facilities.put(F_RANDOM, random);
//        facilities.put(F_TWIT, twittor);
        facilities.put(F_CALENDAR, Calendar.getInstance(TimeZone.getDefault()));

        commandManager.registerCommands(ConfigurationManager.getInstance().getConfiguration());
        commandManager.registerCommand(new HelpCmd(commandManager));
    }

    public void startFeedReader() {

        feedReader.start();
    }

    public void connect() {
        try {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            SmackConfiguration.setKeepAliveInterval(60000 * 5);  // 5 mins
            SmackConfiguration.setPacketReplyTimeout(15000);      // 10 secs

            Configuration configuration = ConfigurationManager.getInstance().getConfiguration();

            conn = new XMPPConnection(configuration.getJabber().getJabberServer());
            facilities.put(F_XMPP_CONNECTION, conn);
            conn.connect();
            conn.addConnectionListener(this);
            //conn.login("vasya@ubuntu", "ljujybvtyzrbhgbx");
            conn.login(configuration.getJabber().getJabberId(), configuration.getJabber().getJabberPassword(), "home");
            VCard vCard = new VCard();
            vCard.setFirstName("Евлампия");
            vCard.setLastName("Шандараховна");
            vCard.setEmailHome("foo@fee.bar");
            vCard.setJabberId(configuration.getJabber().getJabberId());
            vCard.setOrganization("Совхоз \"Светлый Путь Слоупока\"");
            vCard.setNickName("eva@ubuntu");

            vCard.setField("TITLE", "Ms");
            vCard.setAddressFieldHome("STREET", "Some street");
            vCard.setAddressFieldWork("CTRY", "RU");
            vCard.setPhoneWork("FAX", "3443233");
            vCard.save(conn);

            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Ready");
            conn.sendPacket(presence);
            presence.setPriority(10);

            PacketFilter filter = new OrFilter(new PacketTypeFilter(Message.class), new PacketTypeFilter(Presence.class));

            PacketListener listener = new PacketListener() {
                @Override
                public void processPacket(Packet packet) {
                    if (packet instanceof Message) {
                        processMessage((Message) packet);
                    } else if (packet instanceof Presence) {
                        processPresence((Presence) packet);
                    }

                }
            };

            conn.addPacketListener(listener, filter);

            ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(conn);
            sdm.addFeature("jabber:iq:version");

        } catch (XMPPException ex) {
            log.error(ex, ex);
        }
    }

    private void processPresence(Presence presence) {
        Roster roster = conn.getRoster();
        if (presence.getType().equals(Presence.Type.subscribe) && !roster.contains(presence.getFrom())) {
            Message msg = new Message("mrazik@jabber.ru", Message.Type.normal);
            msg.setBody("Request for roster addition from: " + StringUtils.parseBareAddress(presence.getFrom()));
            conn.sendPacket(msg);
        } else if (presence.getType().equals(Presence.Type.unsubscribe)) {
            RosterEntry entry = roster.getEntry(StringUtils.parseBareAddress(presence.getFrom()));
            if (entry != null) {
                try {
                    roster.removeEntry(entry);
                    Presence unsubscribed = new Presence(Presence.Type.unsubscribed);
                    unsubscribed.setTo(presence.getFrom());
                    conn.sendPacket(unsubscribed);
                    privateTrackManager.deleteList(StringUtils.parseBareAddress(presence.getFrom()));

                    Message msg = new Message("mrazik@jabber.ru", Message.Type.normal);
                    msg.setBody("Removed from roster: " + StringUtils.parseBareAddress(presence.getFrom()));
                    conn.sendPacket(msg);
                } catch (XMPPException e) {
                    log.error(e, e);
                }
            }
        }
    }

    private void processMessage(Message message) {
        Roster roster = conn.getRoster();
        if ("mrazik@jabber.ru".equals(StringUtils.parseBareAddress(message.getFrom())) || "vanon123@jabber.ru".equals(StringUtils.parseBareAddress(message.getFrom()))) {
            String[] commands = message.getBody().split(" ");
            if (commands.length > 0) {
                if (commands[0].equals("!aaa") && commands.length > 1) {
                    try {
                        roster.createEntry(commands[1], commands[1], null);
                        Presence subscribed = new Presence(Presence.Type.subscribed);
                        subscribed.setTo(commands[1]);
                        conn.sendPacket(subscribed);
                    } catch (XMPPException e) {
                        log.error(e, e);
                    }
                } else if (commands[0].startsWith("!r")) {
                    joinRooms();
                } else if (commands[0].startsWith("!t")) {
                    String toSay = message.getBody().replace("!t", "");
                    for (MultiUserChat tmpMuc : rooms.values()) {
                        try {
                            tmpMuc.sendMessage(toSay);
                        } catch (XMPPException e) {
                            log.error(e, e);
                        }
                    }
                } else if (commands[0].startsWith("!k") && commands.length > 1) {
                    for (MultiUserChat tmpMuc : rooms.values()) {
                        try {
                            tmpMuc.kickParticipant(commands[1], "Убирайся, гадкий либераст!");
                        } catch (XMPPException e) {
                            log.error(e, e);
                        }
                    }
                }
            }
        }
        if (roster.contains(StringUtils.parseBareAddress(message.getFrom()))) {
            String body = message.getBody();
            if (body.startsWith("!а")) {
                String[] items = body.split(" ");
                if (items.length >= 3) {
                    privateTrackManager.addTrack(StringUtils.parseBareAddress(message.getFrom()), items[1], items[2]);
                }
            } else if (body.startsWith("!д")) {
                String[] items = body.split(" ");
                if (items.length >= 2) {
                    privateTrackManager.deleteTrack(StringUtils.parseBareAddress(message.getFrom()), items[1]);
                }
            } else if (body.startsWith("!с")) {
                List<String> list = privateTrackManager.getStatus(StringUtils.parseBareAddress(message.getFrom()));
                if (list != null && list.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : list) {
                        sb.append(s).append(newline);
                    }
                    Message msg = new Message(message.getFrom(), Message.Type.chat);
                    msg.setBody(sb.toString());
                    conn.sendPacket(msg);
                }
            }
        }
    }

    public void joinRooms() {

        Configuration configuration = ConfigurationManager.getInstance().getConfiguration();

        for (Room room : configuration.getJabber().getRooms()) {
            MultiUserChat tmpMuc = rooms.get(room.getName());

            final MultiUserChat muc = tmpMuc == null ? new MultiUserChat(conn, room.getName()) : tmpMuc;

            try {
                muc.join(configuration.getJabber().getBotNick());
            } catch (XMPPException ex) {
                log.error(ex, ex);
            }
            if (tmpMuc == null) {
                muc.addMessageListener(new RoomListener(muc));
                muc.addSubjectUpdatedListener(new SubjectUpdatedListener() {

                    @Override
                    public void subjectUpdated(String string, String string1) {
                        if (!string.contains("http://0xffff.net/logs")) {
                            try {
                                muc.changeSubject(string + " | Логи чата: http://0xffff.net/logs");
                            } catch (XMPPException ex) {
                                log.error(ex, ex);
                            }

                        }
                    }
                });
                synchronized (rooms) {
                    rooms.put(room.getName(), muc);
                }
            }
        }
    }

    public void run() {
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                log.info(e, e);
            }
        }
    }

    public void processPacket(Packet packet) {
    }

    public void connectionClosed() {
        log.warn("Connection closed");
    }

    public void connectionClosedOnError(Exception excptn) {
        log.warn("Connection closed on error: " + excptn);
    }

    public void reconnectingIn(int i) {
        log.warn("Reconnect in " + i);
    }

    public void reconnectionSuccessful() {
        log.warn("Reconnect successfull");

        Thread task = new Thread(new Runnable() {

            @Override
            public void run() {
                log.debug("Joiner thread started");
                try {
                    while (!conn.isAuthenticated()) {
                        Thread.currentThread().sleep(100);
                    }
                } catch (Exception e) {
                    log.error("Interrupted: " + e, e);
                }
                log.debug("Reauthenticated");
//                try {
//                    conn.login(cfg.getJabber().getJabberId(), cfg.getJabber().getJabberPassword());
//                } catch (XMPPException ex) {
//                    log.warn("Error on relogin: "+ex);
//                }
                joinRooms();
            }
        });
        task.start();

    }

    public void reconnectionFailed(Exception excptn) {
        log.warn("Reconnection failed: " + excptn);
    }

    public class RoomListener implements PacketListener {

        private MultiUserChat muc;
        private TracksManager tracksManager;
        private Map<String, Object> roomFacilities = new HashMap<>();

        public RoomListener(MultiUserChat mc) {
            this.muc = mc;
            this.tracksManager = new TracksManager(hostFile.getParent() + "/" + muc.getRoom(), new TracksManager.Sender() {


                public void sendMessage(String message, String to) {
                    try {
                        muc.sendMessage(message);
                        //dao.store(System.currentTimeMillis(), muc.getRoom(), "Евлампия", message, org.schors.evlampia.model.Message.t_self);
                    } catch (XMPPException ex1) {
                        log.error(ex1, ex1);
                    }
                }
            });
            this.tracksManager.load();
            roomFacilities.putAll(facilities);
            roomFacilities.put(F_MUC, this.muc);
            roomFacilities.put(F_TRACKER, this.tracksManager);
        }

        @Override
        public void processPacket(Packet packet) {
            String from = packet.getFrom();
            //"vnations@conference.ubuntu/schors"
            String[] tmp = from.split("/");
            String body = ((Message) packet).getBody();
            String[] commands = body.split(" ");

            CommandContext commandContext = new CommandContext(from, body, roomFacilities, commands);

            commandManager.proceed(commandContext);

            if (body.startsWith(". ")) {
                body = " <совершенно секретно>";
            }

            String mType = org.schors.evlampia.model.Message.t_normal;
            if (!from.equals(muc.getRoom() + "/" + muc.getNickname())) mType = org.schors.evlampia.model.Message.t_self;
            dao.store(System.currentTimeMillis(), tmp[0], tmp[1], body, mType);
        }
    }

    public void shutDown() {
        if (conn.isConnected()) {
            for (Map.Entry<String, MultiUserChat> entry : rooms.entrySet()) {
                if (entry.getValue().isJoined()) {
                    try {
                        entry.getValue().sendMessage("/me ушла на обед");
                    } catch (XMPPException e) {
                        //booo
                    }
                    entry.getValue().leave();
                }
            }
            Presence p = new Presence(Presence.Type.unavailable);
            p.setStatus("Пака, бугагашечки");
            conn.disconnect(p);
        }
    }
}
