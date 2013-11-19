/**
 * AuthorizationFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class AuthorizationFault extends org.apache.axis.AxisFault {
    public java.lang.String reason;
    public java.lang.String getReason() {
        return this.reason;
    }

    public AuthorizationFault() {
    }

    public AuthorizationFault(java.lang.Exception target) {
        super(target);
    }

    public AuthorizationFault(java.lang.String message, java.lang.Throwable t) {
        super(message, t);
    }

      public AuthorizationFault(java.lang.String reason) {
        this.reason = reason;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, reason);
    }
}
