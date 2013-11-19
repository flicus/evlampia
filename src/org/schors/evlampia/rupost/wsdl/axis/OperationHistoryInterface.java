/**
 * OperationHistoryInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public interface OperationHistoryInterface extends java.rmi.Remote {
    public OperationHistoryRecord[] getOperationHistory(OperationHistoryRequest historyRequest, AuthorizationHeader authorizationHeader) throws java.rmi.RemoteException, OperationHistoryFault, AuthorizationFault;
    public OperationHistoryRecord[] updateOperationData(UpdateOperationRequest updateRequest, AuthorizationHeader authorizationHeader) throws java.rmi.RemoteException, OperationHistoryFault, AuthorizationFault;
}
