package org.schors.evlampia.json.fedex;

public class CDOInfo {
    private String spclInstructDesc;
    private String delivOptn;
    private String delivOptnStatus;
    private String reqApptWdw;
    private String reqApptDesc;
    private String rerouteTRKNbr;
    private String beginTm;
    private String endTm;

    public CDOInfo() {
    }

    public String getSpclInstructDesc() {
        return spclInstructDesc;
    }

    public void setSpclInstructDesc(String spclInstructDesc) {
        this.spclInstructDesc = spclInstructDesc;
    }

    public String getDelivOptn() {
        return delivOptn;
    }

    public void setDelivOptn(String delivOptn) {
        this.delivOptn = delivOptn;
    }

    public String getDelivOptnStatus() {
        return delivOptnStatus;
    }

    public void setDelivOptnStatus(String delivOptnStatus) {
        this.delivOptnStatus = delivOptnStatus;
    }

    public String getReqApptWdw() {
        return reqApptWdw;
    }

    public void setReqApptWdw(String reqApptWdw) {
        this.reqApptWdw = reqApptWdw;
    }

    public String getReqApptDesc() {
        return reqApptDesc;
    }

    public void setReqApptDesc(String reqApptDesc) {
        this.reqApptDesc = reqApptDesc;
    }

    public String getRerouteTRKNbr() {
        return rerouteTRKNbr;
    }

    public void setRerouteTRKNbr(String rerouteTRKNbr) {
        this.rerouteTRKNbr = rerouteTRKNbr;
    }

    public String getBeginTm() {
        return beginTm;
    }

    public void setBeginTm(String beginTm) {
        this.beginTm = beginTm;
    }

    public String getEndTm() {
        return endTm;
    }

    public void setEndTm(String endTm) {
        this.endTm = endTm;
    }

    @Override
    public String toString() {
        return "CDOInfo{" +
                "spclInstructDesc='" + spclInstructDesc + '\'' +
                ", delivOptn='" + delivOptn + '\'' +
                ", delivOptnStatus='" + delivOptnStatus + '\'' +
                ", reqApptWdw='" + reqApptWdw + '\'' +
                ", reqApptDesc='" + reqApptDesc + '\'' +
                ", rerouteTRKNbr='" + rerouteTRKNbr + '\'' +
                ", beginTm='" + beginTm + '\'' +
                ", endTm='" + endTm + '\'' +
                '}';
    }
}
