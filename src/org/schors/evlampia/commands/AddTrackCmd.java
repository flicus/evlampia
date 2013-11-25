package org.schors.evlampia.commands;

import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.rupost.TracksManager;

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
 * Time: 19:03
 * <p/>
 * $Id:
 */

public class AddTrackCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        TracksManager tracksManager = (TracksManager) context.getFacilities().get(Jbot.F_TRACKER);
        String[] items = context.getParsedCommand();
        if (items.length >= 4) {
            tracksManager.addTrack(items[1], items[2], items[3]);
        }
    }
}
