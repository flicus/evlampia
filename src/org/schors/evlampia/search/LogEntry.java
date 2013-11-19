package org.schors.evlampia.search;

public class LogEntry {
    
    private String author;
    private String message;
    private String date;
    private String url;

    private float score;

    public LogEntry() {
    }

    public LogEntry(String author, String message, String date, String url, float score) {
        this.author = author;
        this.message = message;
        this.date = date;
        this.url = url;
        this.score = score;
    }

    public LogEntry(String author, String message, String date, String url) {
        this(author, message, date, url, 0);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntry logEntry = (LogEntry) o;

        if (Float.compare(logEntry.score, score) != 0) return false;
        if (author != null ? !author.equals(logEntry.author) : logEntry.author != null) return false;
        if (date != null ? !date.equals(logEntry.date) : logEntry.date != null) return false;
        if (message != null ? !message.equals(logEntry.message) : logEntry.message != null) return false;
        if (url != null ? !url.equals(logEntry.url) : logEntry.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", score=" + score +
                '}';
    }
}


