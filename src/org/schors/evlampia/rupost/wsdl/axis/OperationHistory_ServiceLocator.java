/**
 * OperationHistory_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.evlampia.rupost.wsdl.axis;

public class OperationHistory_ServiceLocator extends org.apache.axis.client.Service implements OperationHistory_Service {

    public OperationHistory_ServiceLocator() {
    }


    public OperationHistory_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OperationHistory_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OperationHistory
    private java.lang.String OperationHistory_address = "http://voh.russianpost.ru:8080/niips-operationhistory-web/OperationHistory";

    public java.lang.String getOperationHistoryAddress() {
        return OperationHistory_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OperationHistoryWSDDServiceName = "OperationHistory";

    public java.lang.String getOperationHistoryWSDDServiceName() {
        return OperationHistoryWSDDServiceName;
    }

    public void setOperationHistoryWSDDServiceName(java.lang.String name) {
        OperationHistoryWSDDServiceName = name;
    }

    public OperationHistoryInterface getOperationHistory() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OperationHistory_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOperationHistory(endpoint);
    }

    public OperationHistoryInterface getOperationHistory(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WebOperationHistoryStub _stub = new WebOperationHistoryStub(portAddress, this);
            _stub.setPortName(getOperationHistoryWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOperationHistoryEndpointAddress(java.lang.String address) {
        OperationHistory_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (OperationHistoryInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                WebOperationHistoryStub _stub = new WebOperationHistoryStub(new java.net.URL(OperationHistory_address), this);
                _stub.setPortName(getOperationHistoryWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("OperationHistory".equals(inputPortName)) {
            return getOperationHistory();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://russianpost.org/operationhistory", "OperationHistory");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://russianpost.org/operationhistory", "OperationHistory"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("OperationHistory".equals(portName)) {
            setOperationHistoryEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
