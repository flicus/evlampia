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

/**
 * UserParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.tracker.wsdl.axis;

public class UserParameters implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(UserParameters.class, true);
    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UserParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendCtg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "SendCtg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sndr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Sndr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rcpn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rcpn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
    private Rtm02Parameter sendCtg;
    private java.lang.String sndr;
    private java.lang.String rcpn;
    private java.lang.Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;


    public UserParameters() {
    }


    public UserParameters(
            Rtm02Parameter sendCtg,
            java.lang.String sndr,
            java.lang.String rcpn) {
        this.sendCtg = sendCtg;
        this.sndr = sndr;
        this.rcpn = rcpn;
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Gets the sendCtg value for this UserParameters.
     *
     * @return sendCtg
     */
    public Rtm02Parameter getSendCtg() {
        return sendCtg;
    }

    /**
     * Sets the sendCtg value for this UserParameters.
     *
     * @param sendCtg
     */
    public void setSendCtg(Rtm02Parameter sendCtg) {
        this.sendCtg = sendCtg;
    }

    /**
     * Gets the sndr value for this UserParameters.
     *
     * @return sndr
     */
    public java.lang.String getSndr() {
        return sndr;
    }

    /**
     * Sets the sndr value for this UserParameters.
     *
     * @param sndr
     */
    public void setSndr(java.lang.String sndr) {
        this.sndr = sndr;
    }

    /**
     * Gets the rcpn value for this UserParameters.
     *
     * @return rcpn
     */
    public java.lang.String getRcpn() {
        return rcpn;
    }

    /**
     * Sets the rcpn value for this UserParameters.
     *
     * @param rcpn
     */
    public void setRcpn(java.lang.String rcpn) {
        this.rcpn = rcpn;
    }

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserParameters)) return false;
        UserParameters other = (UserParameters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.sendCtg == null && other.getSendCtg() == null) ||
                        (this.sendCtg != null &&
                                this.sendCtg.equals(other.getSendCtg()))) &&
                ((this.sndr == null && other.getSndr() == null) ||
                        (this.sndr != null &&
                                this.sndr.equals(other.getSndr()))) &&
                ((this.rcpn == null && other.getRcpn() == null) ||
                        (this.rcpn != null &&
                                this.rcpn.equals(other.getRcpn())));
        __equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSendCtg() != null) {
            _hashCode += getSendCtg().hashCode();
        }
        if (getSndr() != null) {
            _hashCode += getSndr().hashCode();
        }
        if (getRcpn() != null) {
            _hashCode += getRcpn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
