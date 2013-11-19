package org.schors.evlampia.core;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 08.07.13
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class ExternalIPResolver {

    private static final Logger log = Logger.getLogger(ExternalIPResolver.class);
    private static String ip = "";

    public static String resolve(boolean useCached) {
        if (!useCached) {
            BufferedReader in = null;
            try {
                URL aws = new URL("http://checkip.amazonaws.com/");
                in = new BufferedReader(new InputStreamReader(aws.openStream()));
                ip = in.readLine();
            } catch (MalformedURLException e) {
                log.error(e, e);
            } catch (IOException e) {
                log.error(e, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.error(e, e);
                    }
                }
            }
        }
        return ip;
    }
}
