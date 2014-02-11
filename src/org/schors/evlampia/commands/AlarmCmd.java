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

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.EvaExecutors;
import org.schors.evlampia.core.Jbot;

import java.util.Iterator;

public class AlarmCmd extends Command {

    private static final Logger log = Logger.getLogger(AlarmCmd.class);
    private static volatile boolean alarmGap = false;

    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);
        Connection conn = (Connection) context.getFacilities().get(Jbot.F_XMPP_CONNECTION);

        String s = null;
        if (alarmGap) s = "Иди колядуй";
        else {
            s = getRoomOccupants(conn, muc.getRoom());
            EvaExecutors.getInstance().getExecutor().execute(new GapCleaner());
        }
        muc.sendMessage(s);

    }

    private class GapCleaner implements Runnable {

        @Override
        public void run() {
            alarmGap = true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.warn(e, e);
            }
            alarmGap = false;
        }
    }

    public String getRoomOccupants(Connection conn, String roomName) throws XMPPException {
        StringBuffer sb = new StringBuffer();
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(conn);
        DiscoverItems items = discoManager.discoverItems(roomName);
        for (Iterator it = items.getItems(); it.hasNext(); ) {
            if (it.hasNext()) {
                DiscoverItems.Item item = (DiscoverItems.Item) it.next();
                String occupant = StringUtils.parseResource(item.getEntityID());
                sb.append(occupant + " ");
            }
        }
        if (sb.length() < 1) {
            sb.append("Никого нет дома");
        }
        return sb.toString();
    }
}
