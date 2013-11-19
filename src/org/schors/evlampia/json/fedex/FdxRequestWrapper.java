package org.schors.evlampia.json.fedex;

public class FdxRequestWrapper {
    private TrackPackagesRequest TrackPackagesRequest;

    public FdxRequestWrapper() {
    }

    public TrackPackagesRequest getTrackPackagesRequest() {
        return TrackPackagesRequest;
    }

    public void setTrackPackagesRequest(TrackPackagesRequest trackPackagesRequest) {
        this.TrackPackagesRequest = trackPackagesRequest;
    }

    @Override
    public String toString() {
        return "FdxRequestWrapper{" +
                "TrackPackagesRequest=" + TrackPackagesRequest +
                '}';
    }
}
