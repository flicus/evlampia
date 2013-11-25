/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schors.evlampia;


import org.schors.evlampia.model.*;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author flic
 */
public class Configuration implements Serializable {

    private Jabber jabber;
    private Search search;
    private String logsPath;
    private DataBaseConfig dataBaseConfig;
    private String hostAddress;
    private DynDNS dynDns;
    private Cmd[] cmds;

    public String getHostAddress() {
        return hostAddress;
    }

    public Cmd[] getCmds() {
        return cmds;
    }

    public void setCmds(Cmd[] cmds) {
        this.cmds = cmds;
    }

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
                ", cmds=" + Arrays.toString(cmds) +
                '}';
    }
}
