package org.schors.evlampia.core;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.util.Base64;
import org.schors.evlampia.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 08.07.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class DynDNSManager {

    private static final Logger log = Logger.getLogger(DynDNSManager.class);

    private final static String charset = "UTF-8";
    private final static String userAgent = "Evlampia XMPP bot/2.3 flicus@gmail.com";

    public final static String RC_IP_ADDRESS_ERROR = "Unable to get external IP address";
    public final static String RC_URL_ENCODING_ERROR = "DynDNS URL error";
    public final static String RC_IO_ERROR = "IO error";
    public final static String RC_UNKNOWN_ERROR = "Unknown error";

    public static String update(Configuration cfg) {

        String result = null;
        String ip = ExternalIPResolver.resolve(false);
        BufferedReader reader = null;
        if (ip == null) {
            return RC_IP_ADDRESS_ERROR;
        }

        try {
            String query = String.format("http://dynupdate.no-ip.com/nic/update?hostname=%s&myip=%s",
//                    URLEncoder.encode(cfg.getDynDNS().getUserName(), charset),
//                    URLEncoder.encode(cfg.getDynDNS().getPassword(), charset),
                    URLEncoder.encode(cfg.getDynDns().getDomainName(), charset),
                    URLEncoder.encode(ip, charset));
            HttpURLConnection conn = (HttpURLConnection) new URL(query).openConnection();
            conn.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            conn.setRequestProperty("Authorization", "Basic ".concat(Base64.encodeBytes(sb.append(cfg.getDynDns().getUserName()).append(":").append(cfg.getDynDns().getPassword()).toString().getBytes(charset))));

            conn.setRequestProperty("User-Agent", userAgent);
            conn.setRequestProperty("Accept-Charset", charset);

            int rc = conn.getResponseCode();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            sb = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line);
            }
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            result = RC_URL_ENCODING_ERROR;
        } catch (MalformedURLException e) {
            result = RC_URL_ENCODING_ERROR;
        } catch (IOException e) {
            result = RC_IO_ERROR;
        } catch (Exception e) {
            result = RC_UNKNOWN_ERROR;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
        }

        return result;
    }
}
