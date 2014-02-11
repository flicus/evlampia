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

package org.schors.evlampia.dao;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.schors.evlampia.core.ConfigurationManager;
import org.schors.evlampia.model.Message;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class vbotDAOHTMLImplementation implements vbotDAOInterface {
    public static final Logger log = Logger.getLogger(vbotDAOHTMLImplementation.class);
    private final static Pattern pattern = Pattern.compile("\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.])(?:[^\\s()<>]+|\\([^\\s()<>]+\\))+(?:\\([^\\s()<>]+\\)|[^`!()\\[\\]{};:'\".,<>?«»“”‘’\\s]))");

    private static final String header =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dt\">"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"
                    + "<head><title>#roomName - #today</title>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
                    + "<style type=\"text/css\">"
                    + "<!--.userjoin {color: #009900; font-style: italic; font-weight: bold}"
                    + " .userleave {color: #dc143c; font-style: italic; font-weight: bold}"
                    + " .statuschange {color: #a52a2a; font-weight: bold}"
                    + " .rachange {color: #0000FF; font-weight: bold}"
                    + " .userkick {color: #FF7F50; font-weight: bold}"
                    + " .userban {color: #DAA520; font-weight: bold}"
                    + " .nickchange {color: #FF69B4; font-style: italic; font-weight: bold}"
                    + " .timestamp {color: #aaa;}"
                    + " .timestamp a {color: #aaa; text-decoration: none;}"
                    + " .system {color: #090; font-weight: bold;}"
                    + " .emote {color: #800080;}"
                    + " .self {color: #0000AA;}"
                    + ".selfmoder {color: #DC143C;}"
                    + " .normal {color: #483d8b;}"
                    + " #mark { color: #aaa; text-align: right; font-family: monospace; letter-spacing: 3px }"
                    + " h1 { color: #369; font-family: sans-serif; border-bottom: #246 solid 3pt; letter-spacing: 3px; margin-left: 20pt;}"
                    + " h2 { color: #639; font-family: sans-serif; letter-spacing: 2px; text-align: center }"
                    + " a.h1 {text-decoration: none;color: #369;}"
                    + "#//-->"
                    + "</style>"
                    + "</head> "
                    + "<body> "
                    + "<div id=\"mark\">exTalisman log</div> "
                    + "<h1><a class=\"h1\" href=\"#roomName?join\" title=\"Join room\">#roomName</a></h1> "
                    + "<h2>#today</h2> "
                    + "<div> "
                    + "<tt>";

    private static final String line = "<span class=\"timestamp\"><a id=\"t#longTime\" href=\"#t#longTime\">[#shortTime]</a></span> <span class=\"#class\">&lt;#sender&gt;</span> #message<br/> ";

    private static final String footer = "</tt></div></body></html>";

    private static final String padding = "0";


    private static final int roomCacheLimit = 100;

    private Map<String, File> files = new ConcurrentHashMap<String, File>();
    private Map<String, Queue<Message>> cache = new ConcurrentHashMap<String, Queue<Message>>();
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    private final ReentrantLock lock = new ReentrantLock();

    private ThreadLocal<Calendar> calendar = new ThreadLocal<Calendar>() {

        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance(TimeZone.getDefault());
        }

    };

    private vbotDAOHTMLImplementation() {

    }

    private static class Singleton {
        public static vbotDAOHTMLImplementation instance = new vbotDAOHTMLImplementation();
    }

    public static vbotDAOInterface getInstance() {
        return Singleton.instance;
    }

    @Override
    public boolean store(final long timestamp, final String channel, final String sender, final String message, final String msgType) {

        Queue roomCache = null;
        if (cache.containsKey(channel)) {
            roomCache = cache.get(channel);
        } else {
            roomCache = new LinkedList();
            cache.put(channel, roomCache);
        }
        if (roomCache == null) return false;
        Runnable task;
        if (roomCache.size() < roomCacheLimit) {
            task = new Runnable() {

                @Override
                public void run() {
                    cache.get(channel).offer(new Message(timestamp, sender, message, msgType));
                }
            };
        } else {
            task = new Runnable() {

                @Override
                public void run() {
                    try {
                        lock.lock();
                        cache.get(channel).offer(new Message(timestamp, sender, message, msgType));
                        storeRoomCache(channel);
                    } finally {
                        lock.unlock();
                    }
                }

            };
        }
        pool.submit(task);
        return true;
    }

    private void storeRoomCache(String channel) {
        Queue<Message> roomCache = cache.get(channel);
        File file = files.get(channel);
        if (file == null) {
            Message message = roomCache.peek();
            file = updateFile(channel, message);
            files.put(channel, file);
        }
        RandomAccessFile out = null;
        try {
            out = new RandomAccessFile(file, "rw");
            out.seek(out.length());
            while (roomCache.size() > 0) {
                Message message = roomCache.poll();
                calendar.get().setTimeInMillis(message.getTimestamp());
                int day = calendar.get().get(Calendar.DAY_OF_MONTH);
                String d = day < 10 ? padding + String.valueOf(day) : String.valueOf(day);
                if (!file.getName().equals(d + ".html")) {
                    out.write(footer.getBytes("UTF-8"));
                    out.close();
                    file = updateFile(channel, message);
                    out = new RandomAccessFile(file, "rw");
                    out.seek(out.length());
                    files.put(channel, file);
                }
                int hour = calendar.get().get(Calendar.HOUR_OF_DAY);
                int min = calendar.get().get(Calendar.MINUTE);
                int sec = calendar.get().get(Calendar.SECOND);
                String shortTime = "".concat(hour < 10 ? padding + String.valueOf(hour) : String.valueOf(hour)).concat(":")
                        .concat(min < 10 ? padding + String.valueOf(min) : String.valueOf(min)).concat(":")
                        .concat(sec < 10 ? padding + String.valueOf(sec) : String.valueOf(sec));

                String linkedMessage = insertLinks(message.getMessage());
                String longTime = shortTime + "." + String.valueOf(calendar.get().get(Calendar.MILLISECOND));
                out.write(line.replaceAll("#longTime", longTime).replace("#shortTime", shortTime).replace("#class", message.getType()).replace("#sender", message.getSender()).replace("#message", linkedMessage).getBytes("UTF-8"));
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
                ;
            }
        }
    }

    //todo weblinks regexp ((http|https|ftp)://)?(([a-zA-Z0-9])+\.)+[a-zA-Z0-9]{1,3}\S*
    //\b((?:[a-z][\w-]+:(?:\/{1,3}|[a-z0-9%])|www\d{0,3}[.])(?:[^\s()<>]+|\([^\s()<>]+\))+(?:\([^\s()<>]+\)|[^`!()\[\]{};:'".,<>?«»“”‘’\s]))

    private File updateFile(String channel, Message message) {
        calendar.get().setTimeInMillis(message.getTimestamp());
        int year = calendar.get().get(Calendar.YEAR);
        int month = calendar.get().get(Calendar.MONTH) + 1;
        int day = calendar.get().get(Calendar.DAY_OF_MONTH);
        String m = month < 10 ? padding + String.valueOf(month) : String.valueOf(month);
        String d = day < 10 ? padding + String.valueOf(day) : String.valueOf(day);

        String path = ConfigurationManager.getInstance().getConfiguration().getLogsPath() + File.separator + channel + File.separator + String.valueOf(year) + File.separator + m + File.separator + d + ".html";
        File file = new File(path);
        if (!file.exists()) {
            RandomAccessFile out = null;
            try {
                FileUtils.forceMkdir(file.getParentFile());
                file.createNewFile();
                out = new RandomAccessFile(file, "rw");
                Locale locale = new Locale("RU");
                String date = String.valueOf(day) + " " + calendar.get().getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + String.valueOf(year) + ", " +
                        calendar.get().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
                out.write(header.replaceAll("#roomName", channel).replaceAll("#today", date).getBytes("UTF-8"));
                updateDirectory(channel, String.valueOf(year), m);
            } catch (Exception ex) {
                log.error(ex, ex);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                    }
                    ;
                }
            }
        }
        return file;
    }


    public void flush() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                try {
                    lock.lock();
                    for (String channel : cache.keySet()) {
                        storeRoomCache(channel);
                    }
                } catch (Exception e) {
                    log.error(e, e);
                } finally {
                    lock.unlock();
                }
            }
        };
        pool.submit(task);
    }

    private void updateDirectory(String channel, String year, String month) {
        String path = ConfigurationManager.getInstance().getConfiguration().getLogsPath();
        RandomAccessFile out = null;
        try {
            File f = new File(path);
            String[] list = f.list();//FileUtils.listFiles(new File(path), null, false);
            Arrays.sort(list);

            out = new RandomAccessFile(path + File.separator + "index.html", "rw");
            out.setLength(0);
            out.write("<html><body><br>".getBytes("UTF-8"));
            for (String file : list) {
                if (!"index.html".equals(file)) {
                    out.write(("<a href=\"" + file.replaceAll("#", "%23") + "/index.html" + "\">" + file + "</a><br>").getBytes("UTF-8"));
                }
            }
            out.write("</body></html>".getBytes("UTF-8"));
            out.close();

            path = path + File.separator + channel;
            f = new File(path);
            list = f.list();//FileUtils.listFiles(new File(path), null, false);
            Arrays.sort(list);
            out = new RandomAccessFile(path + File.separator + "index.html", "rw");
            out.setLength(0);
            out.write("<html><body><br>".getBytes("UTF-8"));
            out.write(("<a href=\"" + "../index.html" + "\">" + ". ." + "</a><br>").getBytes("UTF-8"));
            for (String file : list) {
                if (!"index.html".equals(file)) {
                    out.write(("<a href=\"" + file + "/index.html" + "\">" + file + "</a><br>").getBytes("UTF-8"));
                }
            }
            out.write("</body></html>".getBytes("UTF-8"));
            out.close();

            path = path + File.separator + year;
            f = new File(path);
            list = f.list();//FileUtils.listFiles(new File(path), null, false);
            Arrays.sort(list);
            out = new RandomAccessFile(path + File.separator + "index.html", "rw");
            out.setLength(0);
            out.write("<html><body><br>".getBytes("UTF-8"));
            out.write(("<a href=\"" + "../index.html" + "\">" + ". ." + "</a><br>").getBytes("UTF-8"));
            for (String file : list) {
                if (!"index.html".equals(file)) {
                    out.write(("<a href=\"" + file + "/index.html" + "\">" + file + "</a><br>").getBytes("UTF-8"));
                }
            }
            out.write("</body></html>".getBytes("UTF-8"));
            out.close();

            path = path + File.separator + month;
            f = new File(path);
            list = f.list();//FileUtils.listFiles(new File(path), null, false);
            Arrays.sort(list);
            out = new RandomAccessFile(path + File.separator + "index.html", "rw");
            out.setLength(0);
            out.write("<html><body><br>".getBytes("UTF-8"));
            out.write(("<a href=\"" + "../index.html" + "\">" + ". ." + "</a><br>").getBytes("UTF-8"));
            for (String file : list) {
                if (!"index.html".equals(file)) {
                    out.write(("<a href=\"" + file + "\">" + file + "</a><br>").getBytes("UTF-8"));
                }
            }
            out.write("</body></html>".getBytes("UTF-8"));
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
                ;
            }
        }


    }

    private String insertLinks(String message) {
        Matcher m = pattern.matcher(message);
        String result = new String(message);

        while (m.find()) {
            String link = m.group(1);
            result = result.replace(link, String.format("<a target=\"_blank\" href='%s'>%s</a>", link, link));
        }

        return result;
    }


}
