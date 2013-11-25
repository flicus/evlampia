package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.core.PacketSender;
import org.schors.evlampia.vbotDAOInterface;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class FlushLogCmd extends Command {
    @Override
    public void execute(CommandContext context) throws Exception {
        vbotDAOInterface dao = (vbotDAOInterface)context.getFacilities().get(Jbot.F_DAO);
        dao.flush();
        MultiUserChat muc = (MultiUserChat)context.getFacilities().get(Jbot.F_MUC);
        if (muc != null) {
            muc.sendMessage("Логи чата: http://0xffff.net/logs");
        } else {
            PacketSender ps = (PacketSender)context.getFacilities().get("xmpp");
            ps.send("Логи чата: http://0xffff.net/logs");
        }
    }
}
