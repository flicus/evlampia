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
 * OperationHistoryRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class OperationHistoryRecord implements java.io.Serializable {
    private AddressParameters addressParameters;

    private FinanceParameters financeParameters;

    private ItemParameters itemParameters;

    private OperationParameters operationParameters;

    private UserParameters userParameters;

    public OperationHistoryRecord() {
    }

    public OperationHistoryRecord(
            AddressParameters addressParameters,
            FinanceParameters financeParameters,
            ItemParameters itemParameters,
            OperationParameters operationParameters,
            UserParameters userParameters) {
        this.addressParameters = addressParameters;
        this.financeParameters = financeParameters;
        this.itemParameters = itemParameters;
        this.operationParameters = operationParameters;
        this.userParameters = userParameters;
    }


    /**
     * Gets the addressParameters value for this OperationHistoryRecord.
     *
     * @return addressParameters
     */
    public AddressParameters getAddressParameters() {
        return addressParameters;
    }


    /**
     * Sets the addressParameters value for this OperationHistoryRecord.
     *
     * @param addressParameters
     */
    public void setAddressParameters(AddressParameters addressParameters) {
        this.addressParameters = addressParameters;
    }


    /**
     * Gets the financeParameters value for this OperationHistoryRecord.
     *
     * @return financeParameters
     */
    public FinanceParameters getFinanceParameters() {
        return financeParameters;
    }


    /**
     * Sets the financeParameters value for this OperationHistoryRecord.
     *
     * @param financeParameters
     */
    public void setFinanceParameters(FinanceParameters financeParameters) {
        this.financeParameters = financeParameters;
    }


    /**
     * Gets the itemParameters value for this OperationHistoryRecord.
     *
     * @return itemParameters
     */
    public ItemParameters getItemParameters() {
        return itemParameters;
    }


    /**
     * Sets the itemParameters value for this OperationHistoryRecord.
     *
     * @param itemParameters
     */
    public void setItemParameters(ItemParameters itemParameters) {
        this.itemParameters = itemParameters;
    }


    /**
     * Gets the operationParameters value for this OperationHistoryRecord.
     *
     * @return operationParameters
     */
    public OperationParameters getOperationParameters() {
        return operationParameters;
    }


    /**
     * Sets the operationParameters value for this OperationHistoryRecord.
     *
     * @param operationParameters
     */
    public void setOperationParameters(OperationParameters operationParameters) {
        this.operationParameters = operationParameters;
    }


    /**
     * Gets the userParameters value for this OperationHistoryRecord.
     *
     * @return userParameters
     */
    public UserParameters getUserParameters() {
        return userParameters;
    }


    /**
     * Sets the userParameters value for this OperationHistoryRecord.
     *
     * @param userParameters
     */
    public void setUserParameters(UserParameters userParameters) {
        this.userParameters = userParameters;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperationHistoryRecord)) return false;
        OperationHistoryRecord other = (OperationHistoryRecord) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.addressParameters == null && other.getAddressParameters() == null) ||
                        (this.addressParameters != null &&
                                this.addressParameters.equals(other.getAddressParameters()))) &&
                ((this.financeParameters == null && other.getFinanceParameters() == null) ||
                        (this.financeParameters != null &&
                                this.financeParameters.equals(other.getFinanceParameters()))) &&
                ((this.itemParameters == null && other.getItemParameters() == null) ||
                        (this.itemParameters != null &&
                                this.itemParameters.equals(other.getItemParameters()))) &&
                ((this.operationParameters == null && other.getOperationParameters() == null) ||
                        (this.operationParameters != null &&
                                this.operationParameters.equals(other.getOperationParameters()))) &&
                ((this.userParameters == null && other.getUserParameters() == null) ||
                        (this.userParameters != null &&
                                this.userParameters.equals(other.getUserParameters())));
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
        if (getAddressParameters() != null) {
            _hashCode += getAddressParameters().hashCode();
        }
        if (getFinanceParameters() != null) {
            _hashCode += getFinanceParameters().hashCode();
        }
        if (getItemParameters() != null) {
            _hashCode += getItemParameters().hashCode();
        }
        if (getOperationParameters() != null) {
            _hashCode += getOperationParameters().hashCode();
        }
        if (getUserParameters() != null) {
            _hashCode += getUserParameters().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(OperationHistoryRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AddressParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AddressParameters"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("financeParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "FinanceParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "FinanceParameters"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ItemParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ItemParameters"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationParameters"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UserParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UserParameters"));
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

    /*@Override
    public String toString() {
        return this.
    }*/
}
