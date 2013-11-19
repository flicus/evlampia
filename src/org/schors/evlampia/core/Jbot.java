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
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.packet.VCard;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.schors.evlampia.*;
import org.schors.evlampia.model.Room;
import org.schors.evlampia.rupost.TracksManager;
import org.schors.evlampia.search.LogEntry;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.search.SearchResult;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.*;

/**
 * @author flic
 */
public class Jbot implements PacketListener, ConnectionListener {

    public static String newline = System.getProperty("line.separator");
    public static String fileSeparator = System.getProperty("file.separator");
    private static final Logger log = Logger.getLogger(Jbot.class);
    private Connection conn;
    private PacketCollector collector;
    private Map<String, MultiUserChat> rooms = new HashMap<String, MultiUserChat>();
    private vbotDAOInterface dao;
    private Random rnd = new Random(System.currentTimeMillis());
    private FeedReader feedReader;
    private Twittor twittor;
    private TracksManager privateTrackManager;
    private File hostFile;
    private static volatile boolean alarmGap = false;
    private static Random random = new Random(System.currentTimeMillis());
    private static final PeriodFormatter pf = new PeriodFormatterBuilder().printZeroRarelyLast().appendYears().appendSuffix(" year", " years").appendSeparator(", ")//.printZeroRarelyLast()
            .appendMonths().appendSuffix(" month", " months").appendSeparator(", ")
            .appendDays().appendSuffix(" day", " days").appendSeparator(", ")
            .appendHours().appendSuffix(" hour", " hours").appendSeparator(", ")
            .appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(", ")
            .appendSecondsWithOptionalMillis().appendSuffix(" second", " seconds").toFormatter();

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
    }

    public void startTwittor() {
        twittor = Twittor.getInstance();
        twittor.init(rooms);
        twittor.start();
    }

    public void startFeedReader(URL url) {
        feedReader = new FeedReader(rooms);
        feedReader.start(url);
    }

    public void connect() {
        try {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            SmackConfiguration.setKeepAliveInterval(60000 * 5);  // 5 mins
            SmackConfiguration.setPacketReplyTimeout(15000);      // 10 secs

            Configuration configuration = ConfigurationManager.getInstance().getConfiguration();

            conn = new XMPPConnection(configuration.getJabber().getJabberServer());
            conn.connect();
            conn.addConnectionListener(this);
            //conn.login("vasya@ubuntu", "ljujybvtyzrbhgbx");
            conn.login(configuration.getJabber().getJabberId(), configuration.getJabber().getJabberPassword(), "porosek");
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

                //Тема: Щорс невозбранно сломал тему с логами. Так делать не хорошо. Больше так не делай. 3335^84=8,71064697904005E295 inb4: молчачат скрытых игрунов в ерепаблики | http://z0r.de/4034 | Лоли чата: http://schors.zapto.org/logs
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

    private String shuffle(String substring) {
        char[] item = substring.toCharArray();
        if (item.length == 4) {
            char tmp = item[1];
            item[1] = item[2];
            item[2] = tmp;
        } else {
            for (int i = 1; i < substring.length() - 1; i++) {
                int moveTo = rnd.nextInt(substring.length() - 2) + 1;
                char tmp = item[moveTo];
                item[moveTo] = item[i];
                item[i] = tmp;
            }
        }
        return String.valueOf(item);
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

    public String getRoomOccupants(Connection conn, String roomName) throws XMPPException {
        StringBuffer sb = new StringBuffer();
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(conn);
        DiscoverItems items = discoManager.discoverItems(roomName);
        for (Iterator it = items.getItems(); it.hasNext(); ) {
            if (it.hasNext()) {
                DiscoverItems.Item item = (DiscoverItems.Item) it.next();
                String occupant = StringUtils.parseResource(item.getEntityID());
                sb.append(occupant + " ");
            }
        }
        if (sb.length() < 1) {
            sb.append("Никого нет дома");
        }
        return sb.toString();
    }

    public class RoomListener implements PacketListener {

        private MultiUserChat muc;
        private TracksManager tracksManager;

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
        }

        @Override
        public void processPacket(Packet packet) {
            String from = packet.getFrom();
            //"vnations@conference.ubuntu/schors"
            String[] tmp = from.split("/");
            String body = ((Message) packet).getBody();

            if (body.startsWith("!лог") || body.startsWith("!л")) {
                dao.flush();
                try {
                    muc.sendMessage("Логи чата: http://0xffff.net/logs");
                } catch (XMPPException ex) {
                    log.error(ex, ex);
                }
            } else if (body.startsWith("!н")) {
                String[] commands = body.split(" ");
                if (commands.length == 2) {
                    try {
                        muc.changeNickname(commands[1]);
                    } catch (XMPPException ex) {
                        log.error(ex, ex);
                        try {
                            muc.sendMessage(ex.getMessage());
                        } catch (XMPPException ex1) {
                            log.error(ex1, ex1);
                        }
                    }
                }
            } else if (body.startsWith("!ш")) {
                rnd.setSeed(System.currentTimeMillis());
                String words[] = body.replace("!ш", "").split(" ");
                StringBuilder sb = new StringBuilder();
                for (String item : words) {
                    if (item.length() < 4) {
                        sb.append(item).append(" ");
                        continue;
                    }
                    sb.append(shuffle(item)).append(" ");
                }
                try {
                    muc.sendMessage(sb.toString());
                } catch (XMPPException ex1) {
                    log.error(ex1, ex1);
                }
            } else if (body.startsWith("!з")) {
                feedReader.setSilent(!feedReader.isSilent());
            } else if (body.startsWith("!п")) {

                String[] commands = body.split(" ");
                if (commands.length >= 2) {
                    SearchResult<LogEntry> result = SearchManager.getInstanse().search(body.replace("!п", ""), 5, 0);
                    if (result != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Найдено: ").append(result.hits).append(newline);
                        if (result.getItems() != null) {
                            for (LogEntry entry : result.getItems()) {
                                sb.append(entry.getAuthor()).append(" : ").append(entry.getMessage()).append(newline).append(entry.getUrl()).append(newline);
                            }
                        }
                        sb.append(newline).append("Веб поиск - http://0xffff.net/eva/");
                        try {
                            muc.sendMessage(sb.toString());
                        } catch (XMPPException ex1) {
                            log.error(ex1, ex1);
                        }
                    }
                }

            } else if (body.startsWith("!а")) {
                String[] items = body.split(" ");
                if (items.length >= 4) {
                    tracksManager.addTrack(items[1], items[2], items[3]);
                }
            } else if (body.startsWith("!д")) {
                String[] items = body.split(" ");
                if (items.length >= 3) {
                    tracksManager.deleteTrack(items[1], items[2]);
                }
            } else if (body.startsWith("!с")) {
                String[] items = body.split(" ");
                if (items.length >= 2) {
                    List<String> list = tracksManager.getStatus(items[1]);
                    if (list != null && list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : list) {
                            sb.append(s).append(newline);
                        }
                        try {
                            muc.sendMessage(sb.toString());
                        } catch (XMPPException ex1) {
                            log.error(ex1, ex1);
                        }
                    }
                }
//            }else if (body.startsWith("!!!")) {
//                try {
//                    Collection<Affiliate> m = muc.getMembers();
//                    StringBuilder sb = new StringBuilder();
//                    for (Affiliate a : m) {
//                        sb.append(a.getNick()).append(" ");
//                    }
//                    muc.sendMessage(sb.toString());
//                } catch (XMPPException e) {
//                    log.error(e,e);
//                }
            } else if (body.startsWith("!т")) {
                String token = TokenManager.getInstance().makeNewToken(from);
                try {
                    muc.sendMessage("http://0xffff.net/eva/?evaid=" + token);
                } catch (XMPPException e) {
                    log.error(e, e);
                }
            } else if (body.startsWith("!!")) {
                try {
//                    Iterator<String> o = muc.getOccupants();
//                    StringBuilder sb = new StringBuilder();
//                    while (o.hasNext()) {
//                        sb.append(StringUtils.parseResource(o.next())).append(" ");
//                    }
                    String s = null;
                    if (alarmGap) s = "Иди колядуй";
                    else {
                        s = getRoomOccupants(conn, muc.getRoom());
                        EvaExecutors.getInstance().getExecutor().execute(new GapCleaner());
                    }
                    muc.sendMessage(s);
                } catch (XMPPException e) {
                    log.error(e, e);
                }
            } else if (body.startsWith("!и")) {
                StringBuilder info = new StringBuilder();
                info.append(newline).append("OS: ").append(ManagementFactory.getOperatingSystemMXBean().getName()).append(" ").append(ManagementFactory.getOperatingSystemMXBean().getVersion()).append(" ").append(ManagementFactory.getOperatingSystemMXBean().getArch()).append(newline)
                        .append("Memory: ").append(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()).append("/").append(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()).append(newline)
                        .append("CPU: ").append(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage()).append(newline)
                        .append("External IP: ").append(ExternalIPResolver.resolve(true)).append(newline)
                        .append("Uptime: ").append(new Period(ConfigurationManager.startTime, new Instant()).toString(pf));
                try {
                    muc.sendMessage(info.toString());
                } catch (XMPPException e) {
                    log.error(e, e);
                }

            } else if (body.startsWith("!ddns")) {
                try {
                    muc.sendMessage(DynDNSManager.update(ConfigurationManager.getInstance().getConfiguration()));
                } catch (XMPPException e) {
                    log.error(e, e);
                }
            } else {
                if (body.startsWith(". ")) {
                    body = " <совершенно секретно>";
                }
            }
            String mType = org.schors.evlampia.model.Message.t_normal;
            if (!from.equals(muc.getRoom() + "/" + muc.getNickname())) mType = org.schors.evlampia.model.Message.t_self;
            dao.store(System.currentTimeMillis(), tmp[0], tmp[1], body, mType);
        }
    }

    private class GapCleaner implements Runnable {

        @Override
        public void run() {
            alarmGap = true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.warn(e, e);
            }
            alarmGap = false;
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
