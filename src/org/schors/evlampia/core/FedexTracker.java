package org.schors.evlampia.core;

import com.google.gson.Gson;
import org.schors.evlampia.json.fedex.*;

import java.util.ArrayList;
import java.util.List;

public class FedexTracker {

    FdxRequestWrapper fdxRequestWrapper = new FdxRequestWrapper();
    TrackNumberInfoWrapper trackNumberInfoWrapper = new TrackNumberInfoWrapper();
    ProcessingParameters processingParameters = new ProcessingParameters();
    TrackPackagesRequest trackPackagesRequest = new TrackPackagesRequest();
    TrackNumberInfo trackNumberInfo = new TrackNumberInfo();
    List<TrackNumberInfoWrapper> trackNumberInfoList = new ArrayList<>();
    Gson gson = new Gson();

    public FedexTracker() {

        trackNumberInfoList.add(trackNumberInfoWrapper);
        fdxRequestWrapper.setTrackPackagesRequest(trackPackagesRequest);
        trackPackagesRequest.setAppType("wtrk");
        trackPackagesRequest.setUniqueKey("");
        trackPackagesRequest.setProcessingParameters(processingParameters);
        trackPackagesRequest.setTrackingInfoList(trackNumberInfoList);

        processingParameters.setAnonymousTransaction(true);
        processingParameters.setClientId("WTRK");
        processingParameters.setReturnDetailedErrors(true);
        processingParameters.setReturnLocalizedDateTime(false);

        trackNumberInfoWrapper.setTrackNumberInfo(trackNumberInfo);

//        trackNumberInfo.setTrackingNumber("799408958110");
        trackNumberInfo.setTrackingCarrier("");
        trackNumberInfo.setTrackingQualifier("");
     }

    private static class Singleton {
        public static final FedexTracker instanse = new FedexTracker();
    }

    public static FedexTracker getInstanse() {
        return Singleton.instanse;
    }

    public String makeRequest(String id) {
        trackNumberInfo.setTrackingNumber(id);
        return gson.toJson(fdxRequestWrapper);
    }

    public FdxResponseWrapper parseResponse(String in) {
        return gson.fromJson(in, FdxResponseWrapper.class);
    }

}
