package org.schors.evlampia.commands;

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
 * Date: 25.11.13
 * Time: 18:12
 * <p/>
 * $Id:
 */

public class RssShutUpCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        FeedReader feedReader = (FeedReader) context.getFacilities().get(Jbot.F_FEED_READER);
        feedReader.setSilent(!feedReader.isSilent());
    }
}
