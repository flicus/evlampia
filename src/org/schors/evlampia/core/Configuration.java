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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schors.evlampia.core;


import org.schors.evlampia.model.*;

import java.io.Serializable;
import java.util.Arrays;

/**
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
