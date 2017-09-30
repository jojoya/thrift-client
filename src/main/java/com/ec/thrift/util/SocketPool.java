package com.ec.thrift.util;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 *
 * SocketPool.java
 *
 * @author Chen Jun 2015年4月28日
 */
public final class SocketPool {
    private GenericKeyedObjectPool socketPool;
    private boolean longLink;
    private SocketPoolConfig config;
    private static Logger log = Logger.getLogger(SocketPool.class);

    public SocketPool(SocketPoolConfig config) {

        if (config == null) {
            return;
        }
        this.longLink = true;
        this.config = config;
        if (longLink) {
            socketPool = new GenericKeyedObjectPool(new SocketPoolObjectFactory());
            socketPool.setMaxActive(config.getMaxActive());
            socketPool.setMaxIdle(config.getMaxIddle());
            socketPool.setMinIdle(config.getMinIddle());
            socketPool.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
            socketPool.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
            socketPool.setTestWhileIdle(true);
            socketPool.setMaxWait(config.getMaxWaitMillis());
            socketPool.setTestOnBorrow(true);
            socketPool.setTestOnReturn(true);
            socketPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        }
    }

    public ThriftSocket borrowSocket(SocketPoolConfig config) throws Exception {
        ThriftSocket mtTangSocket = null;
        // log.info("borrowSocket");
        if (longLink) {
            mtTangSocket = (ThriftSocket) socketPool.borrowObject(config);

            if (!mtTangSocket.isOpen()) {
                log.info("Socket not ok be invalidate");
                socketPool.invalidateObject(config, mtTangSocket);
                return borrowSocket(config);
            }
        } else {
            mtTangSocket = new ThriftSocket(config);
        }
        return mtTangSocket;
    }

    public void invalidateObject(SocketPoolConfig config, ThriftSocket mtTangSocket) throws Exception {
        socketPool.invalidateObject(config, mtTangSocket);
    }

    public void returnSocket(ThriftSocket mtTangSocket, SocketPoolConfig config) throws Exception {
        if (mtTangSocket != null && mtTangSocket.isOpen()) {
            if (longLink) {
                socketPool.returnObject(config, mtTangSocket);
            } else {
                mtTangSocket.close();
            }
        }
    }

    public void closeConnections() {
        if (longLink) {
            socketPool.clear();
        }
    }

    public int getActiveConnections() {
        return longLink ? socketPool.getNumActive() : 0;
    }

    public int getIdleConnections() {
        return longLink ? socketPool.getNumIdle() : 0;
    }
}
