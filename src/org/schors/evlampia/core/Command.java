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

    public abstract void execute(CommandContext context) throws Exception;

    public String getPrefix(String body) {
        int firstSpace = body.indexOf(" ");
        return body.substring(0, firstSpace);
    }

    public String getWithoutPrefix(String body) {
        int firstSpace = body.indexOf(" ");
        return body.substring(firstSpace);
    }

    public void setPrefixes(Set<String> prefixes) {
        this.prefixes = prefixes;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public boolean isApply(String prefix) {
        return prefixes.contains(prefix);
    }
}
