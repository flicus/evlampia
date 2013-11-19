/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schors.evlampia;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author flicus
 */
public class FeedReader {
    private static final Logger log = Logger.getLogger(FeedReader.class);
    private ConcurrentLinkedQueue feeds = new ConcurrentLinkedQueue<Feed>();
    private Map<String, MultiUserChat> rooms = new HashMap<String, MultiUserChat>();
    
    private boolean silent = false;
    
    public FeedReader(Map<String, MultiUserChat> rooms) {
        this.rooms = rooms;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }
    
    public void start(URL url) {
        log.debug("Start feed reader");
        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new FeedsReader(url), 1, 20, TimeUnit.MINUTES);
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
        
        private URL url;
        
        public FeedsReader(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            log.debug("Start work unit");
            try {
                SyndFeed feed = new SyndFeedInput().build(new XmlReader(url));
                int i = 0;
                for (Object object : feed.getEntries()) {
                    if (i++==10)break;
                    SyndEntry entry = (SyndEntry) object;
                    Feed f = new Feed(URLDecoder.decode(entry.getTitle(), "UTF-8"), URLDecoder.decode(entry.getLink(), "UTF-8"));
                    if (!feeds.contains(f)) {
                        if (feeds.size() == 20) feeds.poll();
                        feeds.offer(f);
                        if (!silent) {
                            for (Map.Entry<String, MultiUserChat> room : rooms.entrySet()) {
                                log.debug("Send message to room: " + room.getKey());
                                room.getValue().sendMessage(f.getTitle() + ", " + f.getLink());
                                Thread.sleep(400);
                            }
                        }
                    }//fghbjhb77
                }
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }
}
