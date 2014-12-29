/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schors.eva.facility;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.schors.eva.Application;
import org.schors.eva.Version;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Facility(
        name = "feedReader",
        version = @Version(major = 1, minor = 0),
        dependsOn = {})
public class FeedReader extends AbstractFacility {
    private static final Logger log = Logger.getLogger(FeedReader.class);
    private Map<String, RootFeed> rootFeeds = new ConcurrentHashMap<>();
    private Map<RootFeed, Queue<Feed>> feeds = new ConcurrentHashMap<>();
    private Map<String, Set<RootFeed>> user2feeds = new ConcurrentHashMap<>();
    private Map<RootFeed, Set<String>> feed2users = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private AtomicInteger idGen = new AtomicInteger();
    private String fileName;
    private boolean silent = false;

    public FeedReader(Application application) {
        super(application);
    }

    public Set<RootFeed> getFeeds(String enduser) {
        try {
            lock.readLock().lock();
            return user2feeds.get(enduser);
        } finally {
            lock.readLock().unlock();
        }
    }

    public String addFeed(String user, String url) {
        String res = null;
        try {
            lock.writeLock().lock();
            RootFeed rootFeed = rootFeeds.get(url);
            if (rootFeed == null) {
                rootFeed = new RootFeed("", url, idGen.incrementAndGet());
                URLConnection urlc = new URL(rootFeed.getLink()).openConnection();
                urlc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.68 YaBrowser/14.2.1700.9977 Safari/537.36");
                SyndFeed feed = new SyndFeedInput().build(new XmlReader(urlc));
                rootFeed.setTitle(feed.getTitle());
                rootFeeds.put(url, rootFeed);
                Queue<Feed> roomFeeds = new ConcurrentLinkedQueue<>();
                feeds.put(rootFeed, roomFeeds);
            }

            if (!user2feeds.containsKey(user)) {
                Set<RootFeed> rf = new CopyOnWriteArraySet<>();
                user2feeds.put(user, rf);
            }
            user2feeds.get(user).add(rootFeed);

            if (!feed2users.containsKey(rootFeed)) {
                Set<String> uf = new CopyOnWriteArraySet<>();
                feed2users.put(rootFeed, uf);
            }
            feed2users.get(rootFeed).add(user);

            save();
        } catch (FeedException e) {
            res = "Фид какой то странный: " + e.getMessage();
            log.error(e, e);
        } catch (IOException e) {
            res = "Не нравится мне твоя ссылка: " + e.getMessage();
            log.error(e, e);
        } finally {
            lock.writeLock().unlock();
        }
        return res;
    }

    public String removeFeed(String user, int id) {
        try {
            lock.writeLock().lock();
            RootFeed toRemove = null;
            if (!user2feeds.containsKey(user)) return "Нет такого";

            while (user2feeds.get(user).iterator().hasNext()) {
                RootFeed rootFeed = user2feeds.get(user).iterator().next();
                if (rootFeed.getId() == id) {
                    toRemove = rootFeed;
                    break;
                }
            }

            if (toRemove != null) {
                user2feeds.get(user).remove(toRemove);
                if (user2feeds.get(user).size() == 0) {
                    user2feeds.remove(user);
                }

                feed2users.get(toRemove).remove(user);
                if (feed2users.get(toRemove).size() == 0) {
                    rootFeeds.remove(toRemove);
                    feeds.remove(toRemove);
                }

                save();
                return null;
            } else return "Нет такого";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public synchronized void save() {
        DAOManager.getInstance().saveFeeds(feeds);
        DAOManager.getInstance().saveCount(idGen);
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(new FileOutputStream(fileName));
//            oos.writeObject(feeds);
//            oos.flush();
//            oos.close();
//        } catch (IOException e) {
//            log.error(e, e);
//        }
    }

    public synchronized void load() {
        idGen = DAOManager.getInstance().loadCount();
        DAOManager.getInstance().loadFeeds(feeds);

//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(new FileInputStream(fileName));
//            feeds = (ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>>) ois.readObject();
//        } catch (Exception e) {
//            log.error(e, e);
//            feeds = new ConcurrentHashMap<>();
//        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<RootFeed, ConcurrentLinkedQueue<Feed>> entry : feeds.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append(", ");
        }
        sb.append("}");
        log.debug("### Loaded list: " + sb.toString());
    }

    @Override
    public void start() {
        log.debug("Start feed reader");
        getFacility(EvaExecutors.class).getScheduler().scheduleAtFixedRate(new FeedsUpdater(), 2, 40, TimeUnit.MINUTES);
    }

    @Override
    public void stop() {

    }

    private class FeedsUpdater implements Runnable {

        public FeedsUpdater() {

        }

        @Override
        public void run() {
            log.debug("Start work unit");
            if (silent) return;

            Iterator<Map.Entry<RootFeed, Queue<Feed>>> it = feeds.entrySet().iterator();
            while (it.hasNext()) {
                try {
                    Map.Entry<RootFeed, Queue<Feed>> mapEntry = it.next();
                    URLConnection urlc = new URL(mapEntry.getKey().getLink()).openConnection();
                    urlc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.68 YaBrowser/14.2.1700.9977 Safari/537.36");
                    SyndFeed feed = new SyndFeedInput().build(new XmlReader(urlc));
                    int i = 0;
                    for (Object object : feed.getEntries()) {
                        try {
                            SyndEntry entry = (SyndEntry) object;
                            Feed f = new Feed(URLDecoder.decode(entry.getTitle(), "UTF-8"), URLDecoder.decode(entry.getLink(), "UTF-8"));
                            if (!mapEntry.getValue().contains(f)) {
                                if (mapEntry.getValue().size() == 20) mapEntry.getValue().poll();
                                mapEntry.getValue().offer(f);

                                Set<String> users = feed2users.get(mapEntry.getKey());
                                if (users != null && users.size() > 0) {
                                    for (String item : users) {
                                        log.debug("Send message to endpoint: " + item);
                                        createDialog(item).sendMessage(f.getTitle() + ", " + f.getLink());
                                        Thread.sleep(400);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            log.error(e, e);
                        }
                        if (i++ == 10) break;
                    }
                } catch (Exception e) {
                    log.error(e, e);
                }
            }
        }
    }
}
