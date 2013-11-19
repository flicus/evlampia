package org.schors.evlampia.core;

public interface CommandManager {
    public void registerCommand(Command command);
    public void proceed(CommandContext context);
}
