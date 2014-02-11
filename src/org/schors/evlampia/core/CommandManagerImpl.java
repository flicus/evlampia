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

package org.schors.evlampia.core;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.model.Cmd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandManagerImpl implements CommandManager {

    private final static Logger log = Logger.getLogger(CommandManagerImpl.class);

    private Set<Command> commandMap = new HashSet<>();
    private Map<String, Command> commandCache = new HashMap<>();


    @Override
    public void registerCommands(Configuration cfg) {
        for (Cmd cmd : cfg.getCmds()) {
            try {
                Class cmdClass = Class.forName(cmd.getCmdClass());
                if (Command.class.isAssignableFrom(cmdClass)) {
                    Command command = (Command) cmdClass.newInstance();
                    command.setLongDescription(cmd.getLongDescription());
                    command.setShortDescription(cmd.getShortDescription());
                    Set<String> pfs = new HashSet<>();
                    for (String s : cmd.getPrefixes()) {
                        pfs.add(s);
                    }
                    command.setPrefixes(pfs);
                    registerCommand(command);
                }

            } catch (ClassNotFoundException e) {
                log.error(e);
            } catch (InstantiationException e) {
                log.error(e);
            } catch (IllegalAccessException e) {
                log.error(e);
            }
        }
    }

    @Override
    public void registerCommand(Command command) {
        commandMap.add(command);
    }

    @Override
    public void proceed(final CommandContext context) {
        String[] commands = context.getParsedCommand();
        Command command = null;
        if (commands.length > 0) {
            if (commandCache.containsKey(commands[0])) {
                command = commandCache.get(commands[0]);
            } else {
                for (Command cmd : commandMap) {
                    if (cmd.isApply(commands[0])) {
                        command = cmd;
                        commandCache.put(commands[0], command);
                        break;
                    }
                }
            }
            if (command != null) {
                final Command cmd = command;
                EvaExecutors.getInstance().getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cmd.execute(context);
                        } catch (Exception e) {
                            MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);
                            try {
                                muc.sendMessage(e.getMessage());
                                log.error("Error on command: ", e);
                            } catch (XMPPException e1) {
                                log.error(e1);
                            }
                        }
                    }
                });
            }
        } else {
            //context.getSender().send(context.getFrom(), "Неизвестная команда");
        }
    }

    @Override
    public Set<Command> getCommandList() {
        return commandMap;
    }
}
