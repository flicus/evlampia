package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.rupost.TracksManager;

import java.util.List;

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
 * Time: 19:08
 * <p/>
 * $Id:
 */

public class CheckTrackCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        TracksManager tracksManager = (TracksManager) context.getFacilities().get(Jbot.F_TRACKER);
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);
        String[] items = context.getParsedCommand();

        if (items.length >= 2) {
            List<String> list = tracksManager.getStatus(items[1]);
            if (list != null && list.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String s : list) {
                    sb.append(s).append(Jbot.newline);
                }
                muc.sendMessage(sb.toString());
            }
        }
    }
}
