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

package org.schors.evlampia;

//import org.apache.log4j.Logger;
//import org.jivesoftware.smackx.muc.MultiUserChat;
//import org.schors.evlampia.core.Jbot;
//import org.schors.evlampia.dao.DAOManager;
//import twitter4j.*;
//import twitter4j.auth.AccessToken;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;

public class Twittor {

//    private static final Logger log = Logger.getLogger(Twittor.class);
//
//    private TwitterFactory factory = new TwitterFactory();
//    private AccessToken accessToken = new AccessToken("45521474-3HGkvRTWGOFxhT5yRrKsul8OaUXV9IS1huy0ieYc", "SJtQAykhOlOGI0teg7ki3vdTwhPekHQDqYJnDafWCO0");
//    private Twitter twitter = null;
//    private Map<String, MultiUserChat> rooms = new HashMap<String, MultiUserChat>();
//    private long lastId;
//
//    public Twittor() {
//
//        twitter = factory.getInstance();
//        twitter.setOAuthConsumer("0syaD6Kl6VS88FwyfoENng", "cZznKvV5cUmYFnIUkoQQDFOCSHNAn5IBZFCMhi3g9s");
//        twitter.setOAuthAccessToken(accessToken);
//
//        lastId = DAOManager.getInstance().getLastId();
//    }
//
//    public void init(Map<String, MultiUserChat> rooms) {
//        this.rooms = rooms;
//    }
//
//    public void start() {
//        log.debug("Start tweet reader");
//        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new Runner(), 1, 20, TimeUnit.MINUTES);
//    }
//
//    public String getTwitter() {
//        Paging p = new Paging(1, 10, lastId);
//
//        ResponseList<Status> res = null;
//        try {
//            res = twitter.getUserTimeline("trubashatal1", p);
//        } catch (TwitterException e) {
//            log.error(e, e);
//        }
//
//        StringBuilder sb = new StringBuilder();
//        if (res != null && res.size() > 0) {
//            lastId = res.get(0).getId();
//            DAOManager.getInstance().updateLastId(lastId);
//            for (Status s : res) {
//                sb.append(s.getUser().getName()).append(" : ").append(s.getText()).append(Jbot.newline);
//            }
//        }
//        return sb.toString();
//    }
//
//    private class Runner implements Runnable {
//
//        @Override
//        public void run() {
//            log.debug("Start work unit");
//            try {
//                String r = getTwitter();
//                if (r != null && r.length() > 0) {
//                    for (Map.Entry<String, MultiUserChat> room : rooms.entrySet()) {
//                        log.debug("Send message to room: " + room.getKey());
//                        room.getValue().sendMessage(r);
//                        Thread.sleep(400);
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e, e);
//            }
//        }
//    }
//
//    private static class Singleton {
//        public static final Twittor instance = new Twittor();
//    }
//
//    public static Twittor getInstance() {
//        return Singleton.instance;
//    }
}
