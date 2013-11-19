package org.schors.evlampia.core;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 09.10.13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public interface UnitManager {
    public void registerUnit(Unit unit);
    public void runFacility(String facilityName, String method);
}
