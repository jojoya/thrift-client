package com.ec.thrift.javaPool;

import org.apache.thrift.protocol.TProtocol;

import service.interfaces.java.CcRecordCenterApi;

import com.ec.thrift.util.IThriftClientCreate;

public class JavaThriftClient implements IThriftClientCreate {
    @Override
    public Object create(TProtocol protocol) {
        return new CcRecordCenterApi.Client(protocol);
    }
}