/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.tracker.TracksManager;

import java.util.List;

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
