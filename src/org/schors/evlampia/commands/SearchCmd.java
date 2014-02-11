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

package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.search.LogEntry;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.search.SearchResult;

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
