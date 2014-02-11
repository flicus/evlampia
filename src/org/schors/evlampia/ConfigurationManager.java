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

package org.schors.evlampia;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.joda.time.Instant;
import org.schors.evlampia.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigurationManager {

    public static final Instant startTime = new Instant();
    private static final Logger log = Logger.getLogger(ConfigurationManager.class);
    private Configuration configuration = null;

    public ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        return Singleton.instance;
    }

    public void readConfiguration(String path) {
        log.debug(String.format("Reading configuration from: %s", path));

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            log.error(ex, ex);
            System.exit(1);
        }

        XStream parser = new XStream();
        parser.alias("configuration", Configuration.class);
        parser.alias("jabber", Jabber.class);
        parser.alias("room", Room.class);
        parser.alias("search", Search.class);
        parser.alias("module", String.class);
        parser.alias("dataBaseConfig", DataBaseConfig.class);
        parser.alias("dynDns", DynDNS.class);
        parser.alias("cmd", Cmd.class);
        parser.alias("prefix", String.class);

        try {
            configuration = (Configuration) parser.fromXML(in);
        } catch (Exception e) {
            log.error(e, e);
        }
        log.debug(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private static class Singleton {
        public static ConfigurationManager instance = new ConfigurationManager();
    }
}
