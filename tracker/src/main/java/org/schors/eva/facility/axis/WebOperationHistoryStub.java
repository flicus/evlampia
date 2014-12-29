/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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
 * WebOperationHistoryStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.schors.eva.facility.axis;

public class WebOperationHistoryStub extends org.apache.axis.client.Stub implements OperationHistoryInterface {
    static org.apache.axis.description.OperationDesc[] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[2];
        _initOperationDesc1();
    }

    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    public WebOperationHistoryStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    public WebOperationHistoryStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public WebOperationHistoryStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
        Class cls;
        javax.xml.namespace.QName qName;
        javax.xml.namespace.QName qName2;
        Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">AuthorizationHeader");
        cachedSerQNames.add(qName);
        cls = AuthorizationHeader.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryData");
        cachedSerQNames.add(qName);
        cls = OperationHistoryRecord[].class;
        cachedSerClasses.add(cls);
        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRecord");
        qName2 = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "historyRecord");
        cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryRequest");
        cachedSerQNames.add(qName);
        cls = OperationHistoryRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Address");
        cachedSerQNames.add(qName);
        cls = Address.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AddressParameters");
        cachedSerQNames.add(qName);
        cls = AddressParameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Barcode");
        cachedSerQNames.add(qName);
        cls = String.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
        cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Country");
        cachedSerQNames.add(qName);
        cls = Country.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "FinanceParameters");
        cachedSerQNames.add(qName);
        cls = FinanceParameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "ItemParameters");
        cachedSerQNames.add(qName);
        cls = ItemParameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRecord");
        cachedSerQNames.add(qName);
        cls = OperationHistoryRecord.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationParameters");
        cachedSerQNames.add(qName);
        cls = OperationParameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "RequestType");
        cachedSerQNames.add(qName);
        cls = RequestType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "Rtm02Parameter");
        cachedSerQNames.add(qName);
        cls = Rtm02Parameter.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UpdateOperationRequest");
        cachedSerQNames.add(qName);
        cls = UpdateOperationRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UserParameters");
        cachedSerQNames.add(qName);
        cls = UserParameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", ">mustUnderstand");
        cachedSerQNames.add(qName);
        cls = boolean.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
        cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }

    private static void _initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetOperationHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryRequest"), OperationHistoryRequest.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AuthorizationHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">AuthorizationHeader"), AuthorizationHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryData"));
        oper.setReturnClass(OperationHistoryRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryData"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "historyRecord"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryFaultReason"),
                "org.evlampia.post.wsdl.axis.OperationHistoryFault",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AuthorizationFaultReason"),
                "org.evlampia.post.wsdl.axis.AuthorizationFault",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateOperationData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UpdateOperationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "UpdateOperationRequest"), UpdateOperationRequest.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AuthorizationHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">AuthorizationHeader"), AuthorizationHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", ">OperationHistoryData"));
        oper.setReturnClass(OperationHistoryRecord[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryData"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "historyRecord"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "OperationHistoryFaultReason"),
                "org.evlampia.post.wsdl.axis.OperationHistoryFault",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://russianpost.org/operationhistory/data", "AuthorizationFaultReason"),
                "org.evlampia.post.wsdl.axis.AuthorizationFault",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        _operations[1] = oper;

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class)
                                    cachedSerFactories.get(i);
                            Class df = (Class)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                    cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        } catch (Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public OperationHistoryRecord[] getOperationHistory(OperationHistoryRequest historyRequest, AuthorizationHeader authorizationHeader) throws java.rmi.RemoteException, AuthorizationFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "GetOperationHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{historyRequest, authorizationHeader});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (OperationHistoryRecord[]) _resp;
                } catch (Exception _exception) {
                    return (OperationHistoryRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, OperationHistoryRecord[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof OperationHistoryFault) {
                    throw (OperationHistoryFault) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AuthorizationFault) {
                    throw (AuthorizationFault) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public OperationHistoryRecord[] updateOperationData(UpdateOperationRequest updateRequest, AuthorizationHeader authorizationHeader) throws java.rmi.RemoteException, AuthorizationFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "UpdateOperationData"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{updateRequest, authorizationHeader});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (OperationHistoryRecord[]) _resp;
                } catch (Exception _exception) {
                    return (OperationHistoryRecord[]) org.apache.axis.utils.JavaUtils.convert(_resp, OperationHistoryRecord[].class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof OperationHistoryFault) {
                    throw (OperationHistoryFault) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof AuthorizationFault) {
                    throw (AuthorizationFault) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

}
