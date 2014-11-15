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
 * UpdateOperationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.tracker.wsdl.axis;

public class UpdateOperationRequest implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(UpdateOperationRequest.class, true);
    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UpdateOperationRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "RequestType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "RequestType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceOperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "SourceOperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRecord"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetOperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "TargetOperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRecord"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ReasonDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initiatorDepartment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "InitiatorDepartment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executorIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ExecutorIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
    private RequestType requestType;
    private OperationHistoryRecord sourceOperation;
    private OperationHistoryRecord targetOperation;
    private java.lang.String reasonDescription;
    private java.math.BigInteger initiatorDepartment;
    private java.lang.String executorIP;
    private java.lang.Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;


    public UpdateOperationRequest() {
    }


    public UpdateOperationRequest(
            RequestType requestType,
            OperationHistoryRecord sourceOperation,
            OperationHistoryRecord targetOperation,
            java.lang.String reasonDescription,
            java.math.BigInteger initiatorDepartment,
            java.lang.String executorIP) {
        this.requestType = requestType;
        this.sourceOperation = sourceOperation;
        this.targetOperation = targetOperation;
        this.reasonDescription = reasonDescription;
        this.initiatorDepartment = initiatorDepartment;
        this.executorIP = executorIP;
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
     * Gets the requestType value for this UpdateOperationRequest.
     *
     * @return requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the requestType value for this UpdateOperationRequest.
     *
     * @param requestType
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * Gets the sourceOperation value for this UpdateOperationRequest.
     *
     * @return sourceOperation
     */
    public OperationHistoryRecord getSourceOperation() {
        return sourceOperation;
    }

    /**
     * Sets the sourceOperation value for this UpdateOperationRequest.
     *
     * @param sourceOperation
     */
    public void setSourceOperation(OperationHistoryRecord sourceOperation) {
        this.sourceOperation = sourceOperation;
    }

    /**
     * Gets the targetOperation value for this UpdateOperationRequest.
     *
     * @return targetOperation
     */
    public OperationHistoryRecord getTargetOperation() {
        return targetOperation;
    }

    /**
     * Sets the targetOperation value for this UpdateOperationRequest.
     *
     * @param targetOperation
     */
    public void setTargetOperation(OperationHistoryRecord targetOperation) {
        this.targetOperation = targetOperation;
    }

    /**
     * Gets the reasonDescription value for this UpdateOperationRequest.
     *
     * @return reasonDescription
     */
    public java.lang.String getReasonDescription() {
        return reasonDescription;
    }

    /**
     * Sets the reasonDescription value for this UpdateOperationRequest.
     *
     * @param reasonDescription
     */
    public void setReasonDescription(java.lang.String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    /**
     * Gets the initiatorDepartment value for this UpdateOperationRequest.
     *
     * @return initiatorDepartment
     */
    public java.math.BigInteger getInitiatorDepartment() {
        return initiatorDepartment;
    }

    /**
     * Sets the initiatorDepartment value for this UpdateOperationRequest.
     *
     * @param initiatorDepartment
     */
    public void setInitiatorDepartment(java.math.BigInteger initiatorDepartment) {
        this.initiatorDepartment = initiatorDepartment;
    }

    /**
     * Gets the executorIP value for this UpdateOperationRequest.
     *
     * @return executorIP
     */
    public java.lang.String getExecutorIP() {
        return executorIP;
    }

    /**
     * Sets the executorIP value for this UpdateOperationRequest.
     *
     * @param executorIP
     */
    public void setExecutorIP(java.lang.String executorIP) {
        this.executorIP = executorIP;
    }

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateOperationRequest)) return false;
        UpdateOperationRequest other = (UpdateOperationRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.requestType == null && other.getRequestType() == null) ||
                        (this.requestType != null &&
                                this.requestType.equals(other.getRequestType()))) &&
                ((this.sourceOperation == null && other.getSourceOperation() == null) ||
                        (this.sourceOperation != null &&
                                this.sourceOperation.equals(other.getSourceOperation()))) &&
                ((this.targetOperation == null && other.getTargetOperation() == null) ||
                        (this.targetOperation != null &&
                                this.targetOperation.equals(other.getTargetOperation()))) &&
                ((this.reasonDescription == null && other.getReasonDescription() == null) ||
                        (this.reasonDescription != null &&
                                this.reasonDescription.equals(other.getReasonDescription()))) &&
                ((this.initiatorDepartment == null && other.getInitiatorDepartment() == null) ||
                        (this.initiatorDepartment != null &&
                                this.initiatorDepartment.equals(other.getInitiatorDepartment()))) &&
                ((this.executorIP == null && other.getExecutorIP() == null) ||
                        (this.executorIP != null &&
                                this.executorIP.equals(other.getExecutorIP())));
        __equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRequestType() != null) {
            _hashCode += getRequestType().hashCode();
        }
        if (getSourceOperation() != null) {
            _hashCode += getSourceOperation().hashCode();
        }
        if (getTargetOperation() != null) {
            _hashCode += getTargetOperation().hashCode();
        }
        if (getReasonDescription() != null) {
            _hashCode += getReasonDescription().hashCode();
        }
        if (getInitiatorDepartment() != null) {
            _hashCode += getInitiatorDepartment().hashCode();
        }
        if (getExecutorIP() != null) {
            _hashCode += getExecutorIP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
