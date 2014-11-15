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
import org.schors.evlampia.core.CommandManager;
import org.schors.evlampia.core.Jbot;

import java.util.HashSet;
import java.util.Set;

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
