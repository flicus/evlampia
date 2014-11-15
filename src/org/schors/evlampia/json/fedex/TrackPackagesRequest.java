/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
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
