package org.schors.evlampia.json.fedex;

public class ScanEvent {
    private String date;
    private String time;
    private String gmtOffset;
    private String status;
    private String statusCD;
    private String scanLocation;
    private String scanDetails;
    private String scanDetailsHtml;
    private String rtrnShprTrkNbr;
    private boolean isDelException;
    private boolean isClearanceDelay;
    private boolean isException;
    private boolean isDelivered;

    public ScanEvent() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCD() {
        return statusCD;
    }

    public void setStatusCD(String statusCD) {
        this.statusCD = statusCD;
    }

    public String getScanLocation() {
        return scanLocation;
    }

    public void setScanLocation(String scanLocation) {
        this.scanLocation = scanLocation;
    }

    public String getScanDetails() {
        return scanDetails;
    }

    public void setScanDetails(String scanDetails) {
        this.scanDetails = scanDetails;
    }

    public String getScanDetailsHtml() {
        return scanDetailsHtml;
    }

    public void setScanDetailsHtml(String scanDetailsHtml) {
        this.scanDetailsHtml = scanDetailsHtml;
    }

    public String getRtrnShprTrkNbr() {
        return rtrnShprTrkNbr;
    }

    public void setRtrnShprTrkNbr(String rtrnShprTrkNbr) {
        this.rtrnShprTrkNbr = rtrnShprTrkNbr;
    }

    public boolean isDelException() {
        return isDelException;
    }

    public void setDelException(boolean delException) {
        isDelException = delException;
    }

    public boolean isClearanceDelay() {
        return isClearanceDelay;
    }

    public void setClearanceDelay(boolean clearanceDelay) {
        isClearanceDelay = clearanceDelay;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    @Override
    public String toString() {
        return "ScanEvent{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", gmtOffset='" + gmtOffset + '\'' +
                ", status='" + status + '\'' +
                ", statusCD='" + statusCD + '\'' +
                ", scanLocation='" + scanLocation + '\'' +
                ", scanDetails='" + scanDetails + '\'' +
                ", scanDetailsHtml='" + scanDetailsHtml + '\'' +
                ", rtrnShprTrkNbr='" + rtrnShprTrkNbr + '\'' +
                ", isDelException=" + isDelException +
                ", isClearanceDelay=" + isClearanceDelay +
                ", isException=" + isException +
                ", isDelivered=" + isDelivered +
                '}';
    }
}
