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
