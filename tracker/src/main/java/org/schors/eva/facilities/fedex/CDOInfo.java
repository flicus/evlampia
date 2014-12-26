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
