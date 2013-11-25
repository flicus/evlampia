package org.schors.evlampia;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.schors.evlampia.core.CommandManager;
import org.schors.evlampia.core.CommandManagerImpl;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.dao.DAOManager;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.web.WebServer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Appender startupAppender = new ConsoleAppender(new SimpleLayout(), "System.err");
        String log4j = "log4j.properties";
        if (args.length > 1) log4j = args[1];
        PropertyConfigurator.configure(log4j);

        //System.out.close();
        //System.err.close();

        log.debug("Start org.schors.evlampia");
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = null;
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        }
        log.debug("Started jar: " + decodedPath);

        String fileToRead;
        if (args.length > 0) {
            fileToRead = args[0];
        } else fileToRead = "configuration.xml";

        ConfigurationManager.getInstance().readConfiguration(fileToRead);
//        CommandManager cmdm = new CommandManagerImpl();
//        cmdm.registerCommands(ConfigurationManager.getInstance().getConfiguration());

        Directory d = null;
        try {
            d = FSDirectory.open(new File(ConfigurationManager.getInstance().getConfiguration().getSearch().getIndexDirectory()));
        } catch (IOException ex) {
            log.error(ex, ex);
        }
        SearchManager.getInstanse().init(d);


        final WebServer server;
        final Jbot jbot = new Jbot();

        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run() {
                vbotDAOHTMLImplementation.getInstance().flush();
                jbot.shutDown();
                EvaExecutors.getInstance().getScheduler().shutdownNow();
                EvaExecutors.getInstance().getExecutor().shutdownNow();
                SearchManager.getInstanse().shutDown();
                DAOManager.getInstance().shutDown();
            }
        });

        try {
            server = new WebServer();
            EvaExecutors.getInstance().getExecutor().execute(server);
        } catch (IOException e) {
            log.error(e, e);
        }

        jbot.connect();
        jbot.joinRooms();

        URL url = null;
        try {
            url = new URL("http://ursa-tm.ru/forum/index.php?/rss/forums/1-%D1%83%D1%81%D0%B0%D0%B4%D1%8C%D0%B1%D0%B0-%D1%83%D1%80%D1%81%D1%8B/");
        } catch (MalformedURLException ex) {
            log.error(ex, ex);
        }
        jbot.startFeedReader(url);
        //jbot.startTwittor();
        jbot.run();

    }

}
