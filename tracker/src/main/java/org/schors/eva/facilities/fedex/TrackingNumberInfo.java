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

package org.schors.eva.facilities.fedex;

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
