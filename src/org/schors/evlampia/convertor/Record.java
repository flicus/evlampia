/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
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

package org.schors.evlampia.convertor;

import org.jdom2.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record implements Convertable {
    private static final Pattern pattern = Pattern.compile("<a\\s+target=\"_blank\"\\s+href='(.*?)'>.*?</a>", Pattern.MULTILINE);

    private String id;
    private String time;
    private String nick;
    private String type;
    private String message;

    public Record() {
    }

    public Record(String id, String time, String type, String nick, String message) {
        this.id = id;
        this.time = time;
        this.nick = nick;
        this.type = type;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", nick='" + nick + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public Element toElement() {

        Element a = new Element("a");
        a.setAttribute("id", id);
        a.setAttribute("href", "#" + id);
        a.addContent("[" + time + "]");

        Element timestamp = new Element("span");
        timestamp.setAttribute("class", "timestamp");
        timestamp.addContent(a);

        Element nick = new Element("span");
        nick.setAttribute("class", "normal".equals(type) ? "lampa" : "nickname");
        nick.addContent(this.nick.replace("&lt;", "<").replace("&gt;", ">"));

        Element p = new Element("p");
        p.addContent(timestamp);
        p.addContent(nick);

        Matcher m = pattern.matcher(message);

        if (m.find()) {
            int index = 0;
            do {
                p.addContent(message.substring(index, m.start()));
                p.addContent(new Link(m.group(1)).toElement());
                index = m.end();
            } while (m.find());
            if (index < message.length() - 1) {
                p.addContent(message.substring(index, message.length()));
            }
        } else {
            p.addContent(message);
        }

        return p;
    }
}
