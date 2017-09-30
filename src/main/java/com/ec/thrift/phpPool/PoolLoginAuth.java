/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
package com.ec.thrift.phpPool;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import service.interfaces.php.EcAuth;

import com.ec.thrift.util.ThriftSocket;

/**
 * @ClassName: QueryForWebFromPool
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
public class PoolLoginAuth extends AbstractJavaSamplerClient {

    private PHPThriftClient ptc = new PHPThriftClient();

    /*
     * <p>Title: runTest</p> <p>Description: </p>
     *
     * @param arg0
     *
     * @return
     *
     * @see
     * org.apache.jmeter.protocol.java.sampler.JavaSamplerClient#runTest(org.apache.jmeter.protocol.
     * java.sampler.JavaSamplerContext)
     */
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
    	long userId =  Long.parseLong(arg0.getParameter("userId"));
    	long corpId =  Long.parseLong(arg0.getParameter("corpId"));
    	
    	ThriftSocket socket = null;
        boolean result = false;
        SampleResult sr = new SampleResult();
        sr.setSamplerData("userId:"+userId+"\ncorpId:"+corpId);

        try {
            sr.sampleStart();
            // 从池里取得client
            socket = PHPThriftClientUtil.pool.borrowSocket(PHPThriftClientUtil.config);
            EcAuth.Client client = (EcAuth.Client) socket.getClient(ptc);
            
            result = client.loginAuth(userId, corpId);
            System.out.println(result);
            sr.setResponseData("结果是：" + result, "UTF-8");
            sr.setDataType(SampleResult.TEXT);
            sr.setSuccessful(true);

        } catch (Throwable e) {
            sr.setSuccessful(false);
            System.out.println("=========> Call thrift server accur ERROR!!");
            e.printStackTrace();
        } finally {
            sr.sampleEnd();
            // 归还client到池中
            if (null != socket) {
                try {
                	PHPThriftClientUtil.pool.returnSocket(socket, PHPThriftClientUtil.config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return sr;
    }


    @Override
    public void teardownTest(JavaSamplerContext arg0){  
    	super.teardownTest(arg0);  
    }  
    
    // 这个方法是用来自定义java方法入参的。
    // params.addArgument("Host", "");表示入参名字叫Host，默认值为空。
    // 设置可用参数及的默认值；
    @Override
    public Arguments getDefaultParameters() {

        Arguments params = new Arguments();
//        params.addArgument("Host", "10.0.201.197");
//        params.addArgument("Port", "3605");
        params.addArgument("userId","");
        params.addArgument("corpId","");
        
        return params;
    }
    
    
    public static void main(String [] args){
//    	loginAuth(long userId, long corpId)

//    	String Host = "10.0.201.197";
//    	String Host = "192.168.1.141";	//开发环境
//    	String Port = "3605";
//    	String userId = "2148088";
//    	String corpId = "2148089";
    	String userId = "2118298";
    	String corpId = "2118299";

        Arguments params = new Arguments();
//        params.addArgument("Host", Host);
//        params.addArgument("Port", Port);
        params.addArgument("userId",userId);
        params.addArgument("corpId",corpId);
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	PoolLoginAuth test = new PoolLoginAuth();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);
   
    }
     

}


