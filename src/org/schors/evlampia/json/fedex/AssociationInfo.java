package org.schors.evlampia.json.fedex;


public class AssociationInfo {
    private TrackingNumberInfo trackingNumberInfo;
    private String associatedType;

    public AssociationInfo() {
    }

    public TrackingNumberInfo getTrackingNumberInfo() {
        return trackingNumberInfo;
    }

    public void setTrackingNumberInfo(TrackingNumberInfo trackingNumberInfo) {
        this.trackingNumberInfo = trackingNumberInfo;
    }

    public String getAssociatedType() {
        return associatedType;
    }

    public void setAssociatedType(String associatedType) {
        this.associatedType = associatedType;
    }

    @Override
    public String toString() {
        return "AssociationInfo{" +
                "trackingNumberInfo=" + trackingNumberInfo +
                ", associatedType='" + associatedType + '\'' +
                '}';
    }
}
