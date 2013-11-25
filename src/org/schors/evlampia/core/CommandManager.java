package org.schors.evlampia.core;

import org.schors.evlampia.Configuration;

import java.util.Set;

public interface CommandManager {
    public void registerCommands(Configuration cfg);
    public void registerCommand(Command command);
    public void proceed(CommandContext context);
    public Set<Command> getCommandList();
}
