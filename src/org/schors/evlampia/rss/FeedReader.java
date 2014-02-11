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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schors.evlampia.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.EvaExecutors;

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

public class FeedReader implements Serializable {
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
                save();
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
        if (toRemove != null) {
            feeds.remove(toRemove);
            save();
        } else
            res = "Нет такого";
        return res;
    }

    public boolean isSilent() {
        return silent;
    }

    public synchronized void save() {
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

    public synchronized void load() {
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
        log.debug("### Loaded list: " + sb.toString());
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public void start() {
        log.debug("Start feed reader");
        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new FeedsReader(), 1, 20, TimeUnit.MINUTES);
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
