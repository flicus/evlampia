package org.schors.evlampia.json.fedex;

import java.util.List;

public class TrackPackagesRequest {
    private String appType;
    private String uniqueKey;
    private ProcessingParameters processingParameters;
    private List<TrackNumberInfoWrapper> trackingInfoList;

    public TrackPackagesRequest() {
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public ProcessingParameters getProcessingParameters() {
        return processingParameters;
    }

    public void setProcessingParameters(ProcessingParameters processingParameters) {
        this.processingParameters = processingParameters;
    }

    public List<TrackNumberInfoWrapper> getTrackingInfoList() {
        return trackingInfoList;
    }

    public void setTrackingInfoList(List<TrackNumberInfoWrapper> trackingInfoList) {
        this.trackingInfoList = trackingInfoList;
    }

    @Override
    public String toString() {
        return "TrackPackagesRequest{" +
                "appType='" + appType + '\'' +
                ", uniqueKey='" + uniqueKey + '\'' +
                ", processingParameters=" + processingParameters +
                ", trackingInfoList=" + trackingInfoList +
                '}';
    }
}
