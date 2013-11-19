package org.schors.evlampia.model;

public class Jabber {
    private String botName;
    private String jabberServer;
    private String botNick;
    private String jabberId;
    private String jabberPassword;
    private Room[] rooms;

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotNick() {
        return botNick;
    }

    public void setBotNick(String botNick) {
        this.botNick = botNick;
    }

    public String getJabberId() {
        return jabberId;
    }

    public void setJabberId(String jabberId) {
        this.jabberId = jabberId;
    }

    public String getJabberPassword() {
        return jabberPassword;
    }

    public void setJabberPassword(String jabberPassword) {
        this.jabberPassword = jabberPassword;
    }

    public String getJabberServer() {
        return jabberServer;
    }

    public void setJabberServer(String jabberServer) {
        this.jabberServer = jabberServer;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Jabber{" + "botName=" + botName + "jabberServer=" + jabberServer + "botNick=" + botNick + "jabberId=" + jabberId + "jabberPassword=" + jabberPassword + "rooms=" + rooms + '}';
    }

    

}
