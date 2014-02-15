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

package org.schors.evlampia.dao;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.schors.evlampia.model.TagItem;
import org.schors.evlampia.rss.Feed;
import org.schors.evlampia.rss.RootFeed;
import org.schors.evlampia.tracker.NamedTrackList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DAOManager {

    private static final Logger log = Logger.getLogger(DAOManager.class);
    private Jedis jedis = new Jedis("localhost");
    private Gson gson = new Gson();

    public DAOManager() {

    }

    public static DAOManager getInstance() {
        return Singleton.instance;
    }

    public void shutDown() {
//        ds.close();
    }

    public void updateTag(String tagName) {
        jedis.zincrby("tags", 1, tagName);
    }

    public List<TagItem> getTags() {
        Set<Tuple> tuples = jedis.zrevrangeWithScores("tags", 0, 50);
        List<TagItem> list = new ArrayList<>();
        for (Tuple entry : tuples) {
            list.add(new TagItem(entry.getElement(), Math.round(entry.getScore())));
        }
        return list;
    }

    public void saveTrackList(String listName, Map<String, NamedTrackList> list) {
        try {
            String json = gson.toJson(list);
            jedis.hset("tracks", listName, json);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void loadTrackList(String listName, Map<String, NamedTrackList> list) {
        list.clear();
        try {
            String json = jedis.hget("tracks", listName);
            Map<String, NamedTrackList> res = gson.fromJson(json, Map.class);
            list.putAll(res);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void saveFeeds(ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>> feeds) {
        try {
            String json = gson.toJson(feeds);
            jedis.set("feeds", json);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void loadFeeds(ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>> feeds) {
        feeds.clear();
        try {
            String json = jedis.get("feeds");
            ConcurrentHashMap<RootFeed, ConcurrentLinkedQueue<Feed>> res = gson.fromJson(json, ConcurrentHashMap.class);
            feeds.putAll(res);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void saveCount(AtomicInteger count) {
        jedis.set("feedCount", String.valueOf(count));
    }

    public AtomicInteger loadCount() {
        int resInt = 0;
        try {
            String res = jedis.get("feedCount");
            resInt = Integer.parseInt(res);
        } catch (Exception e) {
            resInt = 0;
        }
        return new AtomicInteger(resInt);
    }

    private static class Singleton {
        public static final DAOManager instance = new DAOManager();
    }
}
