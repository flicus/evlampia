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
 * ItemParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class ItemParameters implements java.io.Serializable {
    private java.lang.String barcode;

    private java.lang.String internum;

    private boolean validRuType;

    private boolean validEnType;

    private java.lang.String complexItemName;

    private Rtm02Parameter mailRank;

    private Rtm02Parameter postMark;

    private Rtm02Parameter mailType;

    private Rtm02Parameter mailCtg;

    private java.math.BigInteger mass;

    private java.math.BigInteger maxMassRU;

    private java.math.BigInteger maxMassEN;

    public ItemParameters() {
    }

    public ItemParameters(
            java.lang.String barcode,
            java.lang.String internum,
            boolean validRuType,
            boolean validEnType,
            java.lang.String complexItemName,
            Rtm02Parameter mailRank,
            Rtm02Parameter postMark,
            Rtm02Parameter mailType,
            Rtm02Parameter mailCtg,
            java.math.BigInteger mass,
            java.math.BigInteger maxMassRU,
            java.math.BigInteger maxMassEN) {
        this.barcode = barcode;
        this.internum = internum;
        this.validRuType = validRuType;
        this.validEnType = validEnType;
        this.complexItemName = complexItemName;
        this.mailRank = mailRank;
        this.postMark = postMark;
        this.mailType = mailType;
        this.mailCtg = mailCtg;
        this.mass = mass;
        this.maxMassRU = maxMassRU;
        this.maxMassEN = maxMassEN;
    }


    /**
     * Gets the barcode value for this ItemParameters.
     *
     * @return barcode
     */
    public java.lang.String getBarcode() {
        return barcode;
    }


    /**
     * Sets the barcode value for this ItemParameters.
     *
     * @param barcode
     */
    public void setBarcode(java.lang.String barcode) {
        this.barcode = barcode;
    }


    /**
     * Gets the internum value for this ItemParameters.
     *
     * @return internum
     */
    public java.lang.String getInternum() {
        return internum;
    }


    /**
     * Sets the internum value for this ItemParameters.
     *
     * @param internum
     */
    public void setInternum(java.lang.String internum) {
        this.internum = internum;
    }


    /**
     * Gets the validRuType value for this ItemParameters.
     *
     * @return validRuType
     */
    public boolean isValidRuType() {
        return validRuType;
    }


    /**
     * Sets the validRuType value for this ItemParameters.
     *
     * @param validRuType
     */
    public void setValidRuType(boolean validRuType) {
        this.validRuType = validRuType;
    }


    /**
     * Gets the validEnType value for this ItemParameters.
     *
     * @return validEnType
     */
    public boolean isValidEnType() {
        return validEnType;
    }


    /**
     * Sets the validEnType value for this ItemParameters.
     *
     * @param validEnType
     */
    public void setValidEnType(boolean validEnType) {
        this.validEnType = validEnType;
    }


    /**
     * Gets the complexItemName value for this ItemParameters.
     *
     * @return complexItemName
     */
    public java.lang.String getComplexItemName() {
        return complexItemName;
    }


    /**
     * Sets the complexItemName value for this ItemParameters.
     *
     * @param complexItemName
     */
    public void setComplexItemName(java.lang.String complexItemName) {
        this.complexItemName = complexItemName;
    }


    /**
     * Gets the mailRank value for this ItemParameters.
     *
     * @return mailRank
     */
    public Rtm02Parameter getMailRank() {
        return mailRank;
    }


    /**
     * Sets the mailRank value for this ItemParameters.
     *
     * @param mailRank
     */
    public void setMailRank(Rtm02Parameter mailRank) {
        this.mailRank = mailRank;
    }


    /**
     * Gets the postMark value for this ItemParameters.
     *
     * @return postMark
     */
    public Rtm02Parameter getPostMark() {
        return postMark;
    }


    /**
     * Sets the postMark value for this ItemParameters.
     *
     * @param postMark
     */
    public void setPostMark(Rtm02Parameter postMark) {
        this.postMark = postMark;
    }


    /**
     * Gets the mailType value for this ItemParameters.
     *
     * @return mailType
     */
    public Rtm02Parameter getMailType() {
        return mailType;
    }


    /**
     * Sets the mailType value for this ItemParameters.
     *
     * @param mailType
     */
    public void setMailType(Rtm02Parameter mailType) {
        this.mailType = mailType;
    }


    /**
     * Gets the mailCtg value for this ItemParameters.
     *
     * @return mailCtg
     */
    public Rtm02Parameter getMailCtg() {
        return mailCtg;
    }


    /**
     * Sets the mailCtg value for this ItemParameters.
     *
     * @param mailCtg
     */
    public void setMailCtg(Rtm02Parameter mailCtg) {
        this.mailCtg = mailCtg;
    }


    /**
     * Gets the mass value for this ItemParameters.
     *
     * @return mass
     */
    public java.math.BigInteger getMass() {
        return mass;
    }


    /**
     * Sets the mass value for this ItemParameters.
     *
     * @param mass
     */
    public void setMass(java.math.BigInteger mass) {
        this.mass = mass;
    }


    /**
     * Gets the maxMassRU value for this ItemParameters.
     *
     * @return maxMassRU
     */
    public java.math.BigInteger getMaxMassRU() {
        return maxMassRU;
    }


    /**
     * Sets the maxMassRU value for this ItemParameters.
     *
     * @param maxMassRU
     */
    public void setMaxMassRU(java.math.BigInteger maxMassRU) {
        this.maxMassRU = maxMassRU;
    }


    /**
     * Gets the maxMassEN value for this ItemParameters.
     *
     * @return maxMassEN
     */
    public java.math.BigInteger getMaxMassEN() {
        return maxMassEN;
    }


    /**
     * Sets the maxMassEN value for this ItemParameters.
     *
     * @param maxMassEN
     */
    public void setMaxMassEN(java.math.BigInteger maxMassEN) {
        this.maxMassEN = maxMassEN;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemParameters)) return false;
        ItemParameters other = (ItemParameters) obj;
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
                ((this.internum == null && other.getInternum() == null) ||
                        (this.internum != null &&
                                this.internum.equals(other.getInternum()))) &&
                this.validRuType == other.isValidRuType() &&
                this.validEnType == other.isValidEnType() &&
                ((this.complexItemName == null && other.getComplexItemName() == null) ||
                        (this.complexItemName != null &&
                                this.complexItemName.equals(other.getComplexItemName()))) &&
                ((this.mailRank == null && other.getMailRank() == null) ||
                        (this.mailRank != null &&
                                this.mailRank.equals(other.getMailRank()))) &&
                ((this.postMark == null && other.getPostMark() == null) ||
                        (this.postMark != null &&
                                this.postMark.equals(other.getPostMark()))) &&
                ((this.mailType == null && other.getMailType() == null) ||
                        (this.mailType != null &&
                                this.mailType.equals(other.getMailType()))) &&
                ((this.mailCtg == null && other.getMailCtg() == null) ||
                        (this.mailCtg != null &&
                                this.mailCtg.equals(other.getMailCtg()))) &&
                ((this.mass == null && other.getMass() == null) ||
                        (this.mass != null &&
                                this.mass.equals(other.getMass()))) &&
                ((this.maxMassRU == null && other.getMaxMassRU() == null) ||
                        (this.maxMassRU != null &&
                                this.maxMassRU.equals(other.getMaxMassRU()))) &&
                ((this.maxMassEN == null && other.getMaxMassEN() == null) ||
                        (this.maxMassEN != null &&
                                this.maxMassEN.equals(other.getMaxMassEN())));
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
        if (getInternum() != null) {
            _hashCode += getInternum().hashCode();
        }
        _hashCode += (isValidRuType() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isValidEnType() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getComplexItemName() != null) {
            _hashCode += getComplexItemName().hashCode();
        }
        if (getMailRank() != null) {
            _hashCode += getMailRank().hashCode();
        }
        if (getPostMark() != null) {
            _hashCode += getPostMark().hashCode();
        }
        if (getMailType() != null) {
            _hashCode += getMailType().hashCode();
        }
        if (getMailCtg() != null) {
            _hashCode += getMailCtg().hashCode();
        }
        if (getMass() != null) {
            _hashCode += getMass().hashCode();
        }
        if (getMaxMassRU() != null) {
            _hashCode += getMaxMassRU().hashCode();
        }
        if (getMaxMassEN() != null) {
            _hashCode += getMaxMassEN().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ItemParameters.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ItemParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Barcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Internum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validRuType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ValidRuType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validEnType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ValidEnType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complexItemName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ComplexItemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailRank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MailRank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postMark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "PostMark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MailType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailCtg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MailCtg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Mass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxMassRU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MaxMassRU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxMassEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "MaxMassEN"));
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
