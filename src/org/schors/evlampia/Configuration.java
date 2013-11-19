/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schors.evlampia;


import org.schors.evlampia.model.DataBaseConfig;
import org.schors.evlampia.model.DynDNS;
import org.schors.evlampia.model.Jabber;
import org.schors.evlampia.model.Search;

import java.io.Serializable;

/**
 *
 * @author flic
 */
public class Configuration implements Serializable {

    private Jabber jabber;
    private Search search;
    private String logsPath;
    private DataBaseConfig dataBaseConfig;
    private DynDNS dynDns;

    public DynDNS getDynDns() {
        return dynDns;
    }

    public void setDynDns(DynDNS dynDns) {
        this.dynDns = dynDns;
    }

    public String getLogsPath() {
        return logsPath;
    }

    public void setLogsPath(String logsPath) {
        this.logsPath = logsPath;
    }

    public Jabber getJabber() {
        return jabber;
    }

    public void setJabber(Jabber jabber) {
        this.jabber = jabber;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public DataBaseConfig getDataBaseConfig() {
        return dataBaseConfig;
    }

    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "jabber=" + jabber +
                ", search=" + search +
                ", logsPath='" + logsPath + '\'' +
                ", dataBaseConfig=" + dataBaseConfig +
                ", dynDns=" + dynDns +
                '}';
    }
}
