package org.schors.evlampia;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.joda.time.Instant;
import org.schors.evlampia.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigurationManager {

    private static final Logger log = Logger.getLogger(ConfigurationManager.class);
    private Configuration configuration = null;

    public static final Instant startTime = new Instant();

    public ConfigurationManager() {

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

    public static ConfigurationManager getInstance() {
        return Singleton.instance;
    }
}
