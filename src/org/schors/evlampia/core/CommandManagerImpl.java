package org.schors.evlampia.core;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.Configuration;
import org.schors.evlampia.model.Cmd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
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
    public void proceed(CommandContext context) {
        String[] commands = context.getBody().split(" ");
        Command command = null;
        if (commands.length > 0) {
            if (commandCache.containsKey(commands[0])) {
                command = commandCache.get(commands[0]);
            } else {
                for (Command cmd : commandMap) {
                    if (cmd.isApply(commands[0])) {
                        command = cmd;
                        commandCache.put(commands[0], command);
                    }
                }
            }
            if (command != null)
                try {
                    command.execute(context);
                } catch (Exception e) {
                    MultiUserChat muc = (MultiUserChat)context.getFacilities().get(Jbot.F_MUC);
                    try {
                        muc.sendMessage(e.getMessage());
                        log.error("Error on command: ", e);
                    } catch (XMPPException e1) {
                        log.error(e1);
                    }
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
