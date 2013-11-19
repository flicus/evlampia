package org.schors.evlampia.model;

public class Message {

    public static final String t_normal = "normal";
    public static final String t_system = "system";
    public static final String t_self = "self";
    public static final String t_warning = "";

    private long timestamp;
    private String sender;
    private String message;
    private String type;

    public Message(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.sender = null;
    }

    public Message(long timestamp, String sender, String message, String msgType) {
        this.timestamp = timestamp;
        this.message = message;
        this.sender = sender;
        this.type = msgType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
