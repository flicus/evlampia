/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * OperationHistoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class OperationHistoryRequest implements java.io.Serializable {
    private java.lang.String barcode;

    private int messageType;

    public OperationHistoryRequest() {
    }

    public OperationHistoryRequest(
            java.lang.String barcode,
            int messageType) {
        this.barcode = barcode;
        this.messageType = messageType;
    }


    /**
     * Gets the barcode value for this OperationHistoryRequest.
     *
     * @return barcode
     */
    public java.lang.String getBarcode() {
        return barcode;
    }


    /**
     * Sets the barcode value for this OperationHistoryRequest.
     *
     * @param barcode
     */
    public void setBarcode(java.lang.String barcode) {
        this.barcode = barcode;
    }


    /**
     * Gets the messageType value for this OperationHistoryRequest.
     *
     * @return messageType
     */
    public int getMessageType() {
        return messageType;
    }


    /**
     * Sets the messageType value for this OperationHistoryRequest.
     *
     * @param messageType
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperationHistoryRequest)) return false;
        OperationHistoryRequest other = (OperationHistoryRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.barcode == null && other.getBarcode() == null) ||
                        (this.barcode != null &&
                                this.barcode.equals(other.getBarcode()))) &&
                this.messageType == other.getMessageType();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBarcode() != null) {
            _hashCode += getBarcode().hashCode();
        }
        _hashCode += getMessageType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(OperationHistoryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Barcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MessageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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

}
