/**
 * @Package com.ec.thrift.util
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:54:15
 */
package com.ec.thrift.phpPool;

import org.apache.log4j.Logger;

import service.interfaces.php.EcAuth;

import com.ec.thrift.util.SocketPool;
import com.ec.thrift.util.SocketPoolConfig;
import com.ec.thrift.util.ThriftSocket;


/**
 * @ClassName: ThriftClientUtil
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午2:54:15
 */
public class PHPThriftClientUtil {
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
            config.setHost("10.0.201.105");//性能环境
            config.setPort(3605);
            config.setTimeout(5000);
            pool = new SocketPool(config);
//            System.out.println("init the config and pool,the host:" + config.getHost() + ",the port:" + config.getPort());
        } catch (Exception e) {
            log.error("load the static throw exception", e);
            e.printStackTrace();
        }
    }

    private static ThriftSocket socket;
    public static EcAuth.Client takeClient() {

        EcAuth.Client client = null;
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
