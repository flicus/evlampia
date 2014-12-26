/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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

package org.schors.eva.commands;

import org.apache.log4j.Logger;
import org.schors.eva.CommandContext;
import org.schors.eva.annotations.Command;
import org.schors.eva.annotations.CommandExecute;
import org.schors.eva.facilities.EvaExecutors;

import java.util.Iterator;

@Command(
        dependsOn = {"executors"},
        group = "General",
        longDescription = "",
        name = "Alarm",
        prefixes = {},
        shortDescription = ""
)
public class AlarmCmd {

    private static final Logger log = Logger.getLogger(AlarmCmd.class);
    private static volatile boolean alarmGap = false;

    @CommandExecute
    public void execute(CommandContext ctx) throws Exception {
        EvaExecutors evaExecutors = (EvaExecutors) ctx.getFacility(EvaExecutors.getName());
        Connection conn = (Connection) context.getFacilities().get(Jbot.F_XMPP_CONNECTION);

        String s = null;
        if (alarmGap) s = "Иди колядуй";
        else {
            s = getRoomOccupants(conn, muc.getRoom());
            evaExecutors.getExecutor().execute(new GapCleaner());
        }
        ctx.getEndpoint().send(s);
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
}
