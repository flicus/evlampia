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

package org.schors.evlampia.dao;

import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;
import org.schors.evlampia.ConfigurationManager;
import org.schors.evlampia.model.DataBaseConfig;
import org.schors.evlampia.model.TagItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOManager {

    private static final Logger log = Logger.getLogger(DAOManager.class);

    private PGPoolingDataSource ds;

    public DAOManager() {

        DataBaseConfig cfg = ConfigurationManager.getInstance().getConfiguration().getDataBaseConfig();
        try {
            Class.forName(cfg.getDriver());
        } catch (ClassNotFoundException e) {
            log.error(e, e);
        }

        ds = new PGPoolingDataSource();
        ds.setServerName(cfg.getHost());
        ds.setDatabaseName(cfg.getDatabase());
        ds.setUser(cfg.getUser());
        ds.setPassword(cfg.getPassword());
        ds.setMaxConnections(cfg.getMaxConnections());
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void shutDown() {
        ds.close();
    }

    public void updateTag(String tagName) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            Savepoint sp1 = conn.setSavepoint("sp1");
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO cloud VALUES(?, 1);")) {
                stmt.setString(1, tagName);
                int inserted = stmt.executeUpdate();
            } catch (Exception e) {
                log.error(e, e);
                conn.rollback(sp1);
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE cloud SET count = count + 1 WHERE tag = ?;")) {
                    stmt2.setString(1, tagName);
                    int updated = stmt2.executeUpdate();
                } catch (Exception ee) {
                    log.error(ee, ee);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            log.error(e, e);
        }
    }

    public List<TagItem> getTags() {

        List<TagItem> result = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select tag, count from cloud order by count desc limit 50;")) {

            while (rs.next()) {
                result.add(new TagItem(rs.getString(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            log.error(e, e);
        }
        return result;
    }

    public long getLastId() {
        long result = 0;
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select twLastId from settings where id=0");) {

            if (rs.next()) {
                result = rs.getLong(1);
            }

        } catch (SQLException e) {
            log.error(e, e);
        }
        return result;
    }

    public void updateLastId(long lastId) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("update settings set twLastId = ? where id=0");) {
            statement.setLong(1, lastId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error(e, e);
        }
    }


    private static class Singleton {
        public static final DAOManager instance = new DAOManager();
    }

    public static DAOManager getInstance() {
        return Singleton.instance;
    }
}
