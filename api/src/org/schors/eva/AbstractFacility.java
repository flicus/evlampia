package org.schors.eva;

/**
 * Created by flic on 15.11.14.
 */
public abstract class AbstractFacility {

    protected FacilityStatus status = FacilityStatus.INITIAL;
    private FacilityManager facilityManager;

    public AbstractFacility(FacilityManager facilityManager) {
        this.facilityManager = facilityManager;
    }

    public AbstractFacility getFacility(String name) {
        return facilityManager.getFacility(name);
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public abstract void start();

    public abstract void stop();


}
