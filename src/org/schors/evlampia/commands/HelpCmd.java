package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.CommandManager;
import org.schors.evlampia.core.Jbot;

import java.util.HashSet;
import java.util.Set;

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
 * Time: 19:17
 * <p/>
 * $Id:
 */

public class HelpCmd extends Command {

    private CommandManager commandManager;

    public HelpCmd(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.setShortDescription("!? [команда] : получить этот список команд, либо подробную помошь по команде [команда]");
        this.setLongDescription("!? [команда] : получить этот список команд, либо подробную помошь по команде [команда]");
        Set<String> prfs = new HashSet<>();
        prfs.add("!?");
        this.setPrefixes(prfs);
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        StringBuilder sb = new StringBuilder();
        if (context.getParsedCommand().length > 1) {
            for (Command cmd : commandManager.getCommandList()) {
                if (cmd.isApply(context.getParsedCommand()[1])) {
                    sb.append(cmd.getLongDescription()).append(Jbot.newline);
                    break;
                }
            }
        } else {
            sb.append(Jbot.newline).append("Доступные команды:").append(Jbot.newline);
            for (Command command : commandManager.getCommandList()) {
                sb.append(command.getShortDescription()).append(Jbot.newline);
            }
        }
        muc.sendMessage(sb.toString());
    }
}
