package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.search.LogEntry;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.search.SearchResult;

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
 * Time: 18:16
 * <p/>
 * $Id:
 */

public class SearchCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        String[] commands = context.getParsedCommand();
        if (commands.length >= 2) {
            SearchResult<LogEntry> result = SearchManager.getInstanse().search(getWithoutPrefix(context.getBody()), 5, 0);
            if (result != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Найдено: ").append(result.hits).append(Jbot.newline);
                if (result.getItems() != null) {
                    for (LogEntry entry : result.getItems()) {
                        sb.append(entry.getAuthor()).append(" : ").append(entry.getMessage()).append(Jbot.newline).append(entry.getUrl()).append(Jbot.newline);
                    }
                }
                sb.append(Jbot.newline).append("Веб поиск - http://0xffff.net/eva/");
                muc.sendMessage(sb.toString());
            }
        }
    }
}
