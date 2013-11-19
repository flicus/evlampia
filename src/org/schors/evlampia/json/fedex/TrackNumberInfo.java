package org.schors.evlampia.json.fedex;

public class TrackNumberInfo {
    private String trackingNumber;
    private String trackingQualifier;
    private String trackingCarrier;

    public TrackNumberInfo() {
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingQualifier() {
        return trackingQualifier;
    }

    public void setTrackingQualifier(String trackingQualifier) {
        this.trackingQualifier = trackingQualifier;
    }

    public String getTrackingCarrier() {
        return trackingCarrier;
    }

    public void setTrackingCarrier(String trackingCarrier) {
        this.trackingCarrier = trackingCarrier;
    }

    @Override
    public String toString() {
        return "TrackNumberInfo{" +
                "trackingNumber='" + trackingNumber + '\'' +
                ", trackingQualifier='" + trackingQualifier + '\'' +
                ", trackingCarrier='" + trackingCarrier + '\'' +
                '}';
    }
}
