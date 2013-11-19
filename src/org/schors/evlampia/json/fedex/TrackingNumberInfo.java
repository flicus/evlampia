package org.schors.evlampia.json.fedex;

public class TrackingNumberInfo {
    private String trackingNumber;
    private String trackingQualifier;
    private String trackingCarrier;
    private ProcessingParameters processingParameters;

    public TrackingNumberInfo() {
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

    public ProcessingParameters getProcessingParameters() {
        return processingParameters;
    }

    public void setProcessingParameters(ProcessingParameters processingParameters) {
        this.processingParameters = processingParameters;
    }

    @Override
    public String toString() {
        return "TrackingNumberInfo{" +
                "trackingNumber='" + trackingNumber + '\'' +
                ", trackingQualifier='" + trackingQualifier + '\'' +
                ", trackingCarrier='" + trackingCarrier + '\'' +
                ", processingParameters=" + processingParameters +
                '}';
    }
}
