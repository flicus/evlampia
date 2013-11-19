package org.schors.evlampia.json.fedex;

public class MultiStat {
    private String multiPiec;
    private String multiTm;
    private String multiDispTm;
    private String multiSta;

    public MultiStat() {
    }

    public String getMultiPiec() {
        return multiPiec;
    }

    public void setMultiPiec(String multiPiec) {
        this.multiPiec = multiPiec;
    }

    public String getMultiTm() {
        return multiTm;
    }

    public void setMultiTm(String multiTm) {
        this.multiTm = multiTm;
    }

    public String getMultiDispTm() {
        return multiDispTm;
    }

    public void setMultiDispTm(String multiDispTm) {
        this.multiDispTm = multiDispTm;
    }

    public String getMultiSta() {
        return multiSta;
    }

    public void setMultiSta(String multiSta) {
        this.multiSta = multiSta;
    }

    @Override
    public String toString() {
        return "MultiStat{" +
                "multiPiec='" + multiPiec + '\'' +
                ", multiTm='" + multiTm + '\'' +
                ", multiDispTm='" + multiDispTm + '\'' +
                ", multiSta='" + multiSta + '\'' +
                '}';
    }
}
