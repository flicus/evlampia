package org.schors.evlampia.commands;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.schors.evlampia.EvaExecutors;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

import java.util.Iterator;

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
 * Time: 18:30
 * <p/>
 * $Id:
 */

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
