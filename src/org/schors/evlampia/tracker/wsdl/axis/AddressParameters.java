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
 * AddressParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.tracker.wsdl.axis;

public class AddressParameters implements java.io.Serializable {
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(AddressParameters.class, true);
    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AddressParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "DestinationAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Address"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Address"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailDirect");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MailDirect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Country"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "CountryFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Country"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryOper");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "CountryOper"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Country"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
    private Address destinationAddress;
    private Address operationAddress;
    private Country mailDirect;
    private Country countryFrom;
    private Country countryOper;
    private java.lang.Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;


    public AddressParameters() {
    }


    public AddressParameters(
            Address destinationAddress,
            Address operationAddress,
            Country mailDirect,
            Country countryFrom,
            Country countryOper) {
        this.destinationAddress = destinationAddress;
        this.operationAddress = operationAddress;
        this.mailDirect = mailDirect;
        this.countryFrom = countryFrom;
        this.countryOper = countryOper;
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
     * Gets the destinationAddress value for this AddressParameters.
     *
     * @return destinationAddress
     */
    public Address getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Sets the destinationAddress value for this AddressParameters.
     *
     * @param destinationAddress
     */
    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Gets the operationAddress value for this AddressParameters.
     *
     * @return operationAddress
     */
    public Address getOperationAddress() {
        return operationAddress;
    }

    /**
     * Sets the operationAddress value for this AddressParameters.
     *
     * @param operationAddress
     */
    public void setOperationAddress(Address operationAddress) {
        this.operationAddress = operationAddress;
    }

    /**
     * Gets the mailDirect value for this AddressParameters.
     *
     * @return mailDirect
     */
    public Country getMailDirect() {
        return mailDirect;
    }

    /**
     * Sets the mailDirect value for this AddressParameters.
     *
     * @param mailDirect
     */
    public void setMailDirect(Country mailDirect) {
        this.mailDirect = mailDirect;
    }

    /**
     * Gets the countryFrom value for this AddressParameters.
     *
     * @return countryFrom
     */
    public Country getCountryFrom() {
        return countryFrom;
    }

    /**
     * Sets the countryFrom value for this AddressParameters.
     *
     * @param countryFrom
     */
    public void setCountryFrom(Country countryFrom) {
        this.countryFrom = countryFrom;
    }

    /**
     * Gets the countryOper value for this AddressParameters.
     *
     * @return countryOper
     */
    public Country getCountryOper() {
        return countryOper;
    }

    /**
     * Sets the countryOper value for this AddressParameters.
     *
     * @param countryOper
     */
    public void setCountryOper(Country countryOper) {
        this.countryOper = countryOper;
    }

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddressParameters)) return false;
        AddressParameters other = (AddressParameters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.destinationAddress == null && other.getDestinationAddress() == null) ||
                        (this.destinationAddress != null &&
                                this.destinationAddress.equals(other.getDestinationAddress()))) &&
                ((this.operationAddress == null && other.getOperationAddress() == null) ||
                        (this.operationAddress != null &&
                                this.operationAddress.equals(other.getOperationAddress()))) &&
                ((this.mailDirect == null && other.getMailDirect() == null) ||
                        (this.mailDirect != null &&
                                this.mailDirect.equals(other.getMailDirect()))) &&
                ((this.countryFrom == null && other.getCountryFrom() == null) ||
                        (this.countryFrom != null &&
                                this.countryFrom.equals(other.getCountryFrom()))) &&
                ((this.countryOper == null && other.getCountryOper() == null) ||
                        (this.countryOper != null &&
                                this.countryOper.equals(other.getCountryOper())));
        __equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDestinationAddress() != null) {
            _hashCode += getDestinationAddress().hashCode();
        }
        if (getOperationAddress() != null) {
            _hashCode += getOperationAddress().hashCode();
        }
        if (getMailDirect() != null) {
            _hashCode += getMailDirect().hashCode();
        }
        if (getCountryFrom() != null) {
            _hashCode += getCountryFrom().hashCode();
        }
        if (getCountryOper() != null) {
            _hashCode += getCountryOper().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
