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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.schors.eva.Application;
import org.schors.eva.Version;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;
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
    private static final Type feedsType = new TypeToken<HashMap<String, ArrayList<Feed>>>() {
    }.getType();
    private static final Type user2feedsType = new TypeToken<HashMap<String, HashSet<RootFeed>>>() {
    }.getType();
    private Map<String, RootFeed> rootFeeds = new ConcurrentHashMap<>();
    private Map<RootFeed, Queue<Feed>> feeds = new ConcurrentHashMap<>();
    private Map<String, Set<RootFeed>> user2feeds = new ConcurrentHashMap<>();
    private Map<RootFeed, Set<String>> feed2users = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private AtomicInteger idGen = new AtomicInteger();
    private boolean silent = false;
    private Gson gson = new Gson();

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

    public void save() {
        try {
            lock.readLock().lock();
            lock.writeLock().lock();

            Jedis jedis = getFacility(Jedis.class);
            jedis.getJedis().set("feedCount", String.valueOf(idGen));
            String json = gson.toJson(feeds, feedsType);
            jedis.getJedis().set("feeds", json);
            json = gson.toJson(user2feeds, user2feedsType);
            jedis.getJedis().set("user2feeds", json);
        } finally {
            lock.readLock().unlock();
            lock.writeLock().unlock();
        }
    }

    public synchronized void load() {
        try {
            lock.readLock().lock();
            lock.writeLock().lock();

            Jedis jedis = getFacility(Jedis.class);

            int resInt = 0;
            try {
                String res = jedis.getJedis().get("feedCount");
                resInt = Integer.parseInt(res);
            } catch (Exception e) {
                resInt = 0;
            }
            idGen = new AtomicInteger(resInt);

            String json = jedis.getJedis().get("feeds");
            HashMap<String, ArrayList<Feed>> res = gson.fromJson(json, feedsType);
            feeds.clear();
            rootFeeds.clear();
            for (Map.Entry<String, ArrayList<Feed>> entry : res.entrySet()) {
                ConcurrentLinkedQueue<Feed> q = new ConcurrentLinkedQueue<>(entry.getValue());
                String aaa = entry.getKey().substring(8);
                RootFeed rf = gson.fromJson(aaa, RootFeed.class);
                feeds.put(rf, q);
                rootFeeds.put(rf.getLink(), rf);
            }

            user2feeds.clear();
            json = jedis.getJedis().get("user2feeds");
            HashMap<String, HashSet<RootFeed>> tmp = gson.fromJson(json, user2feedsType);
            for (Map.Entry<String, HashSet<RootFeed>> entry : tmp.entrySet()) {
                Set<RootFeed> uf = new CopyOnWriteArraySet<>();
                user2feeds.put(entry.getKey(), uf);
                uf.addAll(entry.getValue());
            }

            feed2users.clear();
            for (Map.Entry<String, RootFeed> entry : rootFeeds.entrySet()) {
                Set<String> uf = new CopyOnWriteArraySet<>();
                feed2users.put(entry.getValue(), uf);

                for (Map.Entry<String, Set<RootFeed>> entry1 : user2feeds.entrySet()) {
                    if (entry1.getValue().contains(entry.getValue())) {
                        uf.add(entry.getKey());
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
            lock.writeLock().unlock();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<RootFeed, Queue<Feed>> entry : feeds.entrySet()) {
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
