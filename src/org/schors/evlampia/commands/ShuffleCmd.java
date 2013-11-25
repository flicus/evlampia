package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

import java.util.Random;

/**
 * Copyright (c) 2013 Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Amdocs jNetX. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs jNetX.
 * <p/>
 * User: Sergey Skoptsov (sskoptsov@amdocs.com)
 * Date: 25.11.13
 * Time: 17:55
 * <p/>
 * $Id:
 */

public class ShuffleCmd extends Command {

    private Random random;

    @Override
    public void execute(CommandContext context) throws Exception {
        Random random = (Random) context.getFacilities().get(Jbot.F_RANDOM);
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        random.setSeed(System.currentTimeMillis());
        String[] words  = getWithoutPrefix(context.getBody()).split(" ");
        StringBuilder sb = new StringBuilder();
        for (String item : words) {
            if (item.length() < 4) {
                sb.append(item).append(" ");
                continue;
            }
            sb.append(shuffle(item)).append(" ");
        }
        muc.sendMessage(sb.toString());
    }

    private String shuffle(String substring) {
        char[] item = substring.toCharArray();
        if (item.length == 4) {
            char tmp = item[1];
            item[1] = item[2];
            item[2] = tmp;
        } else {
            for (int i = 1; i < substring.length() - 1; i++) {
                int moveTo = random.nextInt(substring.length() - 2) + 1;
                char tmp = item[moveTo];
                item[moveTo] = item[i];
                item[i] = tmp;
            }
        }
        return String.valueOf(item);
    }
}
