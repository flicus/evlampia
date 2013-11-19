package org.schors.evlampia.core;


import org.schors.evlampia.EvaExecutors;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TokenManager {

    private Map<String, String> tokens = new HashMap<>();
    //private BASE64Encoder encoder = new BASE64Encoder();
    //private BASE64Decoder decoder = new BASE64Decoder();
    //private Random random = new Random(System.currentTimeMillis());

    public TokenManager() {

        EvaExecutors.getInstance().getScheduler().scheduleAtFixedRate(new ClearTokensTask(), 1, 2, TimeUnit.HOURS);

    }

    public String makeNewToken(String username) {
        String token = Long.toHexString(System.currentTimeMillis());
        tokens.put(token, username);
        return token;
    }

    public String checkToken(String token) {
        return tokens.get(token);
    }

    public class ClearTokensTask implements Runnable {

        @Override
        public void run() {
            try {
                List<String> toRemove = new ArrayList<>();

                long now = System.currentTimeMillis();
                for (Map.Entry<String, String> entry : tokens.entrySet()) {
                    long itemTime = Long.decode("#".concat(entry.getKey()));
                    if ((now - itemTime) > 1000 * 60 * 60 * 24) toRemove.add(entry.getKey());    //24 hours
                }
                for (String key : toRemove) {
                    tokens.remove(key);
                }
            } catch (Exception e) {

            }
        }
    }

    private static class Singleton {
        public static final TokenManager instance = new TokenManager();
    }

    public static TokenManager getInstance() {
        return Singleton.instance;
    }
}

