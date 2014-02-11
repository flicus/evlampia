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
import org.schors.evlampia.FeedReader;
import org.schors.evlampia.RootFeed;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

import java.util.Set;

public class GetFeedsCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);
        FeedReader reader = (FeedReader) context.getFacilities().get(Jbot.F_FEED_READER);
        Set<RootFeed> feeds = reader.getFeeds();
        StringBuilder sb = new StringBuilder();
        sb.append(Jbot.newline).append("Слежу за ними:").append(Jbot.newline);
        for (RootFeed feed : feeds) {
            sb.append(feed.getId()).append(" : ").append(feed.getTitle()).append(", ").append(feed.getLink()).append(Jbot.newline);
        }
        muc.sendMessage(sb.toString());
    }
}
