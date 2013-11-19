/**
 * OperationParameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class OperationParameters  implements java.io.Serializable {
    private Rtm02Parameter operType;

    private Rtm02Parameter operAttr;

    private java.util.Calendar operDate;

    public OperationParameters() {
    }

    public OperationParameters(
           Rtm02Parameter operType,
           Rtm02Parameter operAttr,
           java.util.Calendar operDate) {
           this.operType = operType;
           this.operAttr = operAttr;
           this.operDate = operDate;
    }


    /**
     * Gets the operType value for this OperationParameters.
     * 
     * @return operType
     */
    public Rtm02Parameter getOperType() {
        return operType;
    }


    /**
     * Sets the operType value for this OperationParameters.
     * 
     * @param operType
     */
    public void setOperType(Rtm02Parameter operType) {
        this.operType = operType;
    }


    /**
     * Gets the operAttr value for this OperationParameters.
     * 
     * @return operAttr
     */
    public Rtm02Parameter getOperAttr() {
        return operAttr;
    }


    /**
     * Sets the operAttr value for this OperationParameters.
     * 
     * @param operAttr
     */
    public void setOperAttr(Rtm02Parameter operAttr) {
        this.operAttr = operAttr;
    }


    /**
     * Gets the operDate value for this OperationParameters.
     * 
     * @return operDate
     */
    public java.util.Calendar getOperDate() {
        return operDate;
    }


    /**
     * Sets the operDate value for this OperationParameters.
     * 
     * @param operDate
     */
    public void setOperDate(java.util.Calendar operDate) {
        this.operDate = operDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperationParameters)) return false;
        OperationParameters other = (OperationParameters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.operType==null && other.getOperType()==null) ||
             (this.operType!=null &&
              this.operType.equals(other.getOperType()))) &&
            ((this.operAttr==null && other.getOperAttr()==null) ||
             (this.operAttr!=null &&
              this.operAttr.equals(other.getOperAttr()))) &&
            ((this.operDate==null && other.getOperDate()==null) ||
             (this.operDate!=null &&
              this.operDate.equals(other.getOperDate())));
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
        if (getOperType() != null) {
            _hashCode += getOperType().hashCode();
        }
        if (getOperAttr() != null) {
            _hashCode += getOperAttr().hashCode();
        }
        if (getOperDate() != null) {
            _hashCode += getOperDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperationParameters.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationParameters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operAttr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperAttr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
