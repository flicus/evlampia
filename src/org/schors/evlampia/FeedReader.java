/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schors.evlampia;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author flicus
 */
public class FeedReader {
    private static final Logger log = Logger.getLogger(FeedReader.class);
    private ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>> feeds = new ConcurrentHashMap<>();
    private Map<String, MultiUserChat> rooms = new HashMap<String, MultiUserChat>();
    private AtomicInteger idGen = new AtomicInteger();

    private String fileName;
    private boolean silent = false;

    public FeedReader(Map<String, MultiUserChat> rooms) {
        this.rooms = rooms;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<RootFeed> getFeeds() {
        return feeds.keySet();
    }

    public String addFeed(String url) {
        String res = null;
        try {
            RootFeed rootFeed = new RootFeed("", url, idGen.incrementAndGet());
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rootFeed.getLink())));
            rootFeed.setTitle(feed.getTitle());
            if (feeds.get(rootFeed) == null) {
                ConcurrentLinkedQueue<Feed> rssFeeds = new ConcurrentLinkedQueue<>();
                feeds.put(rootFeed, rssFeeds);
            }
        } catch (FeedException e) {
            res = "Фид какой то странный: " + e.getMessage();
            log.error(e, e);
        } catch (IOException e) {
            res = "Не нравится мне твоя ссылка: " + e.getMessage();
            log.error(e, e);
        }
        return res;
    }

    public String removeFeed(int id) {
        String res = null;
        RootFeed toRemove = null;
        while (feeds.keySet().iterator().hasNext()) {
            RootFeed rootFeed = feeds.keySet().iterator().next();
            if (rootFeed.getId() == id) {
                toRemove = rootFeed;
                break;
            }
        }
        if (toRemove != null)
            feeds.remove(toRemove);
        else
            res = "Нет такого";
        return res;
    }

    public boolean isSilent() {
        return silent;
    }

    public void save() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(feeds);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            log.error(e, e);
        }
    }

    public void load() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            feeds = (ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>>) ois.readObject();
        } catch (Exception e) {
            log.error(e, e);
            feeds = new ConcurrentHashMap<>();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<RootFeed, ConcurrentLinkedQueue<Feed>> entry : feeds.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append(", ");
        }
        sb.append("}");
        log.debug("### Loaded list: "+sb.toString());
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public void start() {
        log.debug("Start feed reader");
        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new FeedsReader(), 1, 20, TimeUnit.MINUTES);
    }

    public class RootFeed {
        private String title;
        private String link;
        private int id;

        private RootFeed(String title, String link, int id) {
            this.title = title;
            this.link = link;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RootFeed rootFeed = (RootFeed) o;

            if (link != null ? !link.equals(rootFeed.link) : rootFeed.link != null) return false;
            if (title != null ? !title.equals(rootFeed.title) : rootFeed.title != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (link != null ? link.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RootFeed{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }

    private class Feed {
        private String title;
        private String link;

        public Feed(String title, String link) {
            this.link = link;
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Feed feed = (Feed) o;

            if (link != null ? !link.equals(feed.link) : feed.link != null) return false;
            if (title != null ? !title.equals(feed.title) : feed.title != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (link != null ? link.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Feed{" + "title=" + title + ", link=" + link + '}';
        }


    }

    private class FeedsReader implements Runnable {

        public FeedsReader() {

        }

        @Override
        public void run() {
            log.debug("Start work unit");
            if (silent) return;
            try {
                while (feeds.entrySet().iterator().hasNext()) {
                    Map.Entry<RootFeed, ConcurrentLinkedQueue<Feed>> mapEntry = feeds.entrySet().iterator().next();
                    SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(mapEntry.getKey().getLink())));
                    int i = 0;
                    for (Object object : feed.getEntries()) {
                        if (i++ == 10) break;
                        SyndEntry entry = (SyndEntry) object;
                        Feed f = new Feed(URLDecoder.decode(entry.getTitle(), "UTF-8"), URLDecoder.decode(entry.getLink(), "UTF-8"));
                        if (!mapEntry.getValue().contains(f)) {
                            if (mapEntry.getValue().size() == 20) mapEntry.getValue().poll();
                            mapEntry.getValue().offer(f);
                            for (Map.Entry<String, MultiUserChat> room : rooms.entrySet()) {
                                log.debug("Send message to room: " + room.getKey());
                                room.getValue().sendMessage(f.getTitle() + ", " + f.getLink());
                                Thread.sleep(400);
                            }
                        }//fghbjhb77
                    }
                }
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }
}
