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
 * FinanceParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class FinanceParameters implements java.io.Serializable {
    private java.math.BigInteger payment;

    private java.math.BigInteger value;

    private java.math.BigInteger massRate;

    private java.math.BigInteger insrRate;

    private java.math.BigInteger airRate;

    private java.math.BigInteger rate;

    public FinanceParameters() {
    }

    public FinanceParameters(
            java.math.BigInteger payment,
            java.math.BigInteger value,
            java.math.BigInteger massRate,
            java.math.BigInteger insrRate,
            java.math.BigInteger airRate,
            java.math.BigInteger rate) {
        this.payment = payment;
        this.value = value;
        this.massRate = massRate;
        this.insrRate = insrRate;
        this.airRate = airRate;
        this.rate = rate;
    }


    /**
     * Gets the payment value for this FinanceParameters.
     *
     * @return payment
     */
    public java.math.BigInteger getPayment() {
        return payment;
    }


    /**
     * Sets the payment value for this FinanceParameters.
     *
     * @param payment
     */
    public void setPayment(java.math.BigInteger payment) {
        this.payment = payment;
    }


    /**
     * Gets the value value for this FinanceParameters.
     *
     * @return value
     */
    public java.math.BigInteger getValue() {
        return value;
    }


    /**
     * Sets the value value for this FinanceParameters.
     *
     * @param value
     */
    public void setValue(java.math.BigInteger value) {
        this.value = value;
    }


    /**
     * Gets the massRate value for this FinanceParameters.
     *
     * @return massRate
     */
    public java.math.BigInteger getMassRate() {
        return massRate;
    }


    /**
     * Sets the massRate value for this FinanceParameters.
     *
     * @param massRate
     */
    public void setMassRate(java.math.BigInteger massRate) {
        this.massRate = massRate;
    }


    /**
     * Gets the insrRate value for this FinanceParameters.
     *
     * @return insrRate
     */
    public java.math.BigInteger getInsrRate() {
        return insrRate;
    }


    /**
     * Sets the insrRate value for this FinanceParameters.
     *
     * @param insrRate
     */
    public void setInsrRate(java.math.BigInteger insrRate) {
        this.insrRate = insrRate;
    }


    /**
     * Gets the airRate value for this FinanceParameters.
     *
     * @return airRate
     */
    public java.math.BigInteger getAirRate() {
        return airRate;
    }


    /**
     * Sets the airRate value for this FinanceParameters.
     *
     * @param airRate
     */
    public void setAirRate(java.math.BigInteger airRate) {
        this.airRate = airRate;
    }


    /**
     * Gets the rate value for this FinanceParameters.
     *
     * @return rate
     */
    public java.math.BigInteger getRate() {
        return rate;
    }


    /**
     * Sets the rate value for this FinanceParameters.
     *
     * @param rate
     */
    public void setRate(java.math.BigInteger rate) {
        this.rate = rate;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FinanceParameters)) return false;
        FinanceParameters other = (FinanceParameters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.payment == null && other.getPayment() == null) ||
                        (this.payment != null &&
                                this.payment.equals(other.getPayment()))) &&
                ((this.value == null && other.getValue() == null) ||
                        (this.value != null &&
                                this.value.equals(other.getValue()))) &&
                ((this.massRate == null && other.getMassRate() == null) ||
                        (this.massRate != null &&
                                this.massRate.equals(other.getMassRate()))) &&
                ((this.insrRate == null && other.getInsrRate() == null) ||
                        (this.insrRate != null &&
                                this.insrRate.equals(other.getInsrRate()))) &&
                ((this.airRate == null && other.getAirRate() == null) ||
                        (this.airRate != null &&
                                this.airRate.equals(other.getAirRate()))) &&
                ((this.rate == null && other.getRate() == null) ||
                        (this.rate != null &&
                                this.rate.equals(other.getRate())));
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
        if (getPayment() != null) {
            _hashCode += getPayment().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getMassRate() != null) {
            _hashCode += getMassRate().hashCode();
        }
        if (getInsrRate() != null) {
            _hashCode += getInsrRate().hashCode();
        }
        if (getAirRate() != null) {
            _hashCode += getAirRate().hashCode();
        }
        if (getRate() != null) {
            _hashCode += getRate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(FinanceParameters.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "FinanceParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Payment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("massRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MassRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insrRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "InsrRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AirRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
