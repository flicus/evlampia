/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.schors.evlampia.core;

import java.util.HashSet;
import java.util.Set;

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
        return firstSpace > 0 ? body.substring(firstSpace) : null;
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
