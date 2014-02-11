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

package org.schors.evlampia.core;


import org.schors.evlampia.EvaExecutors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

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

