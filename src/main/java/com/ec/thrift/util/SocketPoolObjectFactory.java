package com.ec.thrift.util;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.log4j.Logger;

/**
 *
 * SocketPoolObjectFactory.java
 *
 * @author Chen Jun 2015年4月28日
 */
public class SocketPoolObjectFactory extends BaseKeyedPoolableObjectFactory {
    private Logger logger = Logger.getLogger(SocketPoolObjectFactory.class);

    @Override
    public Object makeObject(Object key) throws Exception {
        SocketPoolConfig config = (SocketPoolConfig) key;
        ThriftSocket socket = new ThriftSocket(config);
        logger.debug("Open Socket to " + config.getHost() + ":" + config.getPort());
        return socket;
    }

    @Override
    public void destroyObject(Object key, Object arg0) throws Exception {
        SocketPoolConfig config = (SocketPoolConfig) key;
        ThriftSocket socket = (ThriftSocket) arg0;
        try {
            socket.close();
            logger.debug("Closed socket to " + config.getHost() + ":" + config.getPort());
        } catch (Exception e) {
            logger.error("close socket failed!", e);
        }
        super.destroyObject(key, arg0);
    }

    @Override
    public boolean validateObject(Object key, Object arg0) {
        // logger.info("validateObject");
        ThriftSocket socket = (ThriftSocket) arg0;

        if (!socket.isOpen()) {
            logger.info("validate isClosed return false");
            return false;
        }
        return true;
    }

    @Override
    public void activateObject(Object key, Object arg0) throws Exception {
        // logger.info("activateObject");

        /*
         * 因为socket可能关闭，这个地方设置TimeOut不安全 MtTangSocket socket =
         * (MtTangSocket)arg0; if(!socket.isClosed()){
         * socket.setTimeout(config.getTimeoutMillis()); }
         */
        // super.activateObject(key, arg0);
    }

    @Override
    public void passivateObject(Object key, Object arg0) throws Exception {
        super.passivateObject(key, arg0);
    }
}
