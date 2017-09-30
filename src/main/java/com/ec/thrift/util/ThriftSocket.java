/**
 * @Package com.ec.thrift.util
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:46:29
 */
package com.ec.thrift.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @ClassName: ThriftSocket
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:46:29
 */
// public class ThriftSocket {
//
// private CcRecordCenterApi.Client client;
// private TTransport transport = null;
// private TProtocol protocol;
// private Logger log = LogManager.getLogger(ThriftSocket.class);
//
// public ThriftSocket(SocketPoolConfig config) throws TTransportException {
// transport = new TSocket(config.getHost(), config.getPort(), config.getTimeout());
// // 协议要和服务端一致
// TFramedTransport tf = new TFramedTransport(transport);
// // 协议要和服务端一致
// this.protocol = new TBinaryProtocol(tf);
// transport.open();
// log.info("======>创建ThriftSocket成功...");
// }
//
// /*
// * public ThriftSocket(String host, int port, int timeout) throws TTransportException {
// * transport = new TSocket(host, port, timeout); // 协议要和服务端一致 TFramedTransport tf = new
// * TFramedTransport(transport); // 协议要和服务端一致 this.protocol = new TBinaryProtocol(tf);
// * transport.open(); log.info("======>创建ThriftSocket成功..."); }
// */
// public boolean isOpen() {
// return transport.isOpen();
// }
//
// public void close() {
// transport.close();
// }
//
// public CcRecordCenterApi.Client getClient() {
// return new CcRecordCenterApi.Client(this.protocol);
// }
//
// }

public class ThriftSocket {

    private Object client;
    private TTransport transport = null;
    private TProtocol protocol;
    private Logger log = LogManager.getLogger(ThriftSocket.class);

    public ThriftSocket(SocketPoolConfig config) throws TTransportException {
        transport = new TSocket(config.getHost(), config.getPort(), config.getTimeout());
        // 协议要和服务端一致
        TFramedTransport tf = new TFramedTransport(transport);
        // 协议要和服务端一致
        this.protocol = new TBinaryProtocol(tf);
        transport.open();
//        log.info("======>创建ThriftSocket成功...");
    }

    public boolean isOpen() {
        return transport.isOpen();
    }

    public void close() {
        transport.close();
    }

    public Object getClient(IThriftClientCreate factory) {
        if (null == client) {
            client = factory.create(this.protocol);
        }
        return client;
    }

}