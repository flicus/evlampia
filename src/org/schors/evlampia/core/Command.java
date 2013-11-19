package org.schors.evlampia.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
public abstract class Command {

    private Set<String> prefixes = new HashSet<>();
    private String shortDescription;
    private String longDescription;

    public abstract void execute(CommandContext context);

    public boolean isApply(String line) {
        return prefixes.contains(line);
    };


}
