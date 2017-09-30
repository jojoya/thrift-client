/**
 * @Package com.ec.thrift.util
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:54:15
 */
package com.ec.thrift.javaPool;

import org.apache.log4j.Logger;

import service.interfaces.java.CcRecordCenterApi;

import com.ec.thrift.util.SocketPool;
import com.ec.thrift.util.SocketPoolConfig;
import com.ec.thrift.util.ThriftSocket;


/**
 * @ClassName: ThriftClientUtil
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:54:15
 */
public class JavaThriftClientUtil {
    private static Logger log = Logger.getLogger("stdout");
    public static SocketPoolConfig config;
    public static SocketPool pool;
    static {
        try {
            config = new SocketPoolConfig();
            config.setHeartBeatInterval(3000);
            config.setMaxActive(3000);
            config.setMaxIddle(100);
            config.setMinIddle(5);
            config.setMinEvictableIdleTimeMillis(0);
            config.setTimeBetweenEvictionRunsMillis(3000);
            config.setTimeoutMillis(2000);
            config.setMaxWaitMillis(3000);
            // config.setHeartBeatClass(heartBeatClass);
            config.setLongLink(true);
//            config.setHost("192.168.1.104");//测试环境
//            config.setHost("192.168.1.104");//开发环境
//            config.setHost("10.0.201.34");//性能环境
//            config.setHost("10.0.201.35");//性能环境
//            config.setPort(7121);
            config.setTimeout(2000);
            pool = new SocketPool(config);
//            System.out.println("init the config and pool,the host:" + config.getHost() + ",the port:" + config.getPort());
        } catch (Exception e) {
            log.error("load the static throw exception", e);
            e.printStackTrace();
        }
    }

    private static ThriftSocket socket;
    public static CcRecordCenterApi.Client takeClient() {

        CcRecordCenterApi.Client client = null;
        try {
            socket = pool.borrowSocket(config);
            // client = socket.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static void returnClient() {
        try {
            pool.returnSocket(socket, config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
