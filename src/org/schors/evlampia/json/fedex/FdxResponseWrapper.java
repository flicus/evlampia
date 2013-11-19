package org.schors.evlampia.json.fedex;

public class FdxResponseWrapper {
    private TrackPackagesResponse TrackPackagesResponse;

    public FdxResponseWrapper() {
    }

    public TrackPackagesResponse getTrackPackagesResponse() {
        return TrackPackagesResponse;
    }

    public void setTrackPackagesResponse(TrackPackagesResponse trackPackagesResponse) {
        TrackPackagesResponse = trackPackagesResponse;
    }

    @Override
    public String toString() {
        return "FdxResponseWrapper{" +
                "TrackPackagesResponse=" + TrackPackagesResponse +
                '}';
    }
}
