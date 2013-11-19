/**
 * Country.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class Country  implements java.io.Serializable {
    private int id;

    private java.lang.String code2A;

    private java.lang.String code3A;

    private java.lang.String nameRU;

    private java.lang.String nameEN;

    public Country() {
    }

    public Country(
           int id,
           java.lang.String code2A,
           java.lang.String code3A,
           java.lang.String nameRU,
           java.lang.String nameEN) {
           this.id = id;
           this.code2A = code2A;
           this.code3A = code3A;
           this.nameRU = nameRU;
           this.nameEN = nameEN;
    }


    /**
     * Gets the id value for this Country.
     *
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Country.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the code2A value for this Country.
     *
     * @return code2A
     */
    public java.lang.String getCode2A() {
        return code2A;
    }


    /**
     * Sets the code2A value for this Country.
     *
     * @param code2A
     */
    public void setCode2A(java.lang.String code2A) {
        this.code2A = code2A;
    }


    /**
     * Gets the code3A value for this Country.
     *
     * @return code3A
     */
    public java.lang.String getCode3A() {
        return code3A;
    }


    /**
     * Sets the code3A value for this Country.
     *
     * @param code3A
     */
    public void setCode3A(java.lang.String code3A) {
        this.code3A = code3A;
    }


    /**
     * Gets the nameRU value for this Country.
     *
     * @return nameRU
     */
    public java.lang.String getNameRU() {
        return nameRU;
    }


    /**
     * Sets the nameRU value for this Country.
     *
     * @param nameRU
     */
    public void setNameRU(java.lang.String nameRU) {
        this.nameRU = nameRU;
    }


    /**
     * Gets the nameEN value for this Country.
     *
     * @return nameEN
     */
    public java.lang.String getNameEN() {
        return nameEN;
    }


    /**
     * Sets the nameEN value for this Country.
     *
     * @param nameEN
     */
    public void setNameEN(java.lang.String nameEN) {
        this.nameEN = nameEN;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Country)) return false;
        Country other = (Country) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            this.id == other.getId() &&
            ((this.code2A==null && other.getCode2A()==null) ||
             (this.code2A!=null &&
              this.code2A.equals(other.getCode2A()))) &&
            ((this.code3A==null && other.getCode3A()==null) ||
             (this.code3A!=null &&
              this.code3A.equals(other.getCode3A()))) &&
            ((this.nameRU==null && other.getNameRU()==null) ||
             (this.nameRU!=null &&
              this.nameRU.equals(other.getNameRU()))) &&
            ((this.nameEN==null && other.getNameEN()==null) ||
             (this.nameEN!=null &&
              this.nameEN.equals(other.getNameEN())));
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
        _hashCode += getId();
        if (getCode2A() != null) {
            _hashCode += getCode2A().hashCode();
        }
        if (getCode3A() != null) {
            _hashCode += getCode3A().hashCode();
        }
        if (getNameRU() != null) {
            _hashCode += getNameRU().hashCode();
        }
        if (getNameEN() != null) {
            _hashCode += getNameEN().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Country.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Country"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code2A");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Code2A"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code3A");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Code3A"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameRU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "NameRU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "NameEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
          new  org.apache.axis.encoding.ser.BeanSerializer(
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
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
