package org.schors.evlampia.ask;

public class Answer {
    private String id;
    private String who;

    public Answer() {
    }

    public Answer(String id, String who) {
        this.id = id;
        this.who = who;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", who='" + who + '\'' +
                '}';
    }
}
