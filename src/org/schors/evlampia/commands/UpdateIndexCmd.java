package org.schors.evlampia.commands;

import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.search.SearchManager;
import org.schors.evlampia.vbotDAOInterface;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 27.11.13
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class UpdateIndexCmd extends Command {
    private final static Logger log = Logger.getLogger(UpdateIndexCmd.class);

    @Override
    public void execute(final CommandContext context) throws Exception {
        final vbotDAOInterface dao = (vbotDAOInterface) context.getFacilities().get(Jbot.F_DAO);
        final MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        synchronized (UpdateIndexCmd.class) {
            dao.flush();
            boolean done = false;
            try {
                SearchManager.getInstanse().updateIndex(true);
                done = true;
            } catch (IOException e) {
                log.error(e, e);
            }
            if (!context.getFacilities().containsKey("silentIndex")) {
                try {
                    if (done)
                        muc.sendMessage("Индекс обновлен");
                    else
                        muc.sendMessage("Ошибка обновления индекса");
                } catch (Exception e) {
                    log.error(e, e);
                }
            }
        }
    }
}
