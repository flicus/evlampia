package org.schors.evlampia.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class Unit {
    private Set<Command> commands = new HashSet<>();
    private Set<Facility> facilities = new HashSet<>();

    public abstract void register(UnitManager unitManager);
}
