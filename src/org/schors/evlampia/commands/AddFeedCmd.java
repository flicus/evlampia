package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.FeedReader;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

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
 * Date: 11.02.14
 * Time: 21:08
 * <p/>
 * $Id:
 */

public class AddFeedCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat)context.getFacilities().get(Jbot.F_MUC);
        FeedReader reader = (FeedReader)context.getFacilities().get(Jbot.F_FEED_READER);
        String url = context.getParsedCommand()[0];
        String res = reader.addFeed(url);
        if (res != null) muc.sendMessage(res);
    }
}
