package com.ec.thrift.phpPool;

import org.apache.thrift.protocol.TProtocol;

import service.interfaces.php.EcAuth;

import com.ec.thrift.util.IThriftClientCreate;

public class PHPThriftClient implements IThriftClientCreate {
    @Override
    public Object create(TProtocol protocol) {
        return new EcAuth.Client(protocol);
    }
}