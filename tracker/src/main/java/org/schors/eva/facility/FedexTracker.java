/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.facility;

import com.google.gson.Gson;
import org.schors.eva.facility.fedex.*;

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

    private static class Singleton {
        public static final FedexTracker instanse = new FedexTracker();
    }

}
