/**
 * OperationHistory_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public interface OperationHistory_Service extends javax.xml.rpc.Service {
    public java.lang.String getOperationHistoryAddress();

    public OperationHistoryInterface getOperationHistory() throws javax.xml.rpc.ServiceException;

    public OperationHistoryInterface getOperationHistory(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
