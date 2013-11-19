package org.schors.evlampia.model;

public class DataBaseConfig {

    private String driver;
    private String host;
    private String database;
    private String user;
    private String password;
    private int maxConnections;

    public DataBaseConfig() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @Override
    public String toString() {
        return "DataBaseConfig{" +
                "driver='" + driver + '\'' +
                ", host='" + host + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", maxConnections=" + maxConnections +
                '}';
    }
}
