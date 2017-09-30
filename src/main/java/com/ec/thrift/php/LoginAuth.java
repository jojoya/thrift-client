/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author ecuser
 * @date 2016年11月11日 下午3:39:29
 */
package com.ec.thrift.php;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import service.interfaces.php.EcAuth;
import service.interfaces.php.EcAuth.Client;

/**
 * @ClassName: GetPeoplePerfTest
 * @Description: TODO
 * @author JOJOYA
 * @date 2017年3月16日09:40:06
 */
public class LoginAuth extends AbstractJavaSamplerClient {

	private static Client client;
    private static TTransport transport;

    // 执行测试方法
    public SampleResult runTest(JavaSamplerContext arg0) {

    	long userId =  Long.parseLong(arg0.getParameter("userId"));
    	long corpId =  Long.parseLong(arg0.getParameter("corpId"));
    	
        // 调用服务的 方法
        boolean result = false;

        SampleResult sr = new SampleResult();
        sr.setSamplerData("userId："+userId
        		+"\ncorpId："+corpId); 

        try {
            sr.sampleStart();	// 开始统计响应时间标记
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
            sr.sampleEnd();	//结束统计响应时间标记

            // 关闭thrift client
//             if (transport != null) {
//             transport.close();
//             }
        }

        return sr;
    }

    // 每个线程测试前执行一次，做一些初始化工作；
    @Override
    public void setupTest(JavaSamplerContext arg0) {
    	
        String Host = arg0.getParameter("Host");
        String StrPort = arg0.getParameter("Port");
        int Port = Integer.parseInt(StrPort);
        
        try {
            System.out.println("=========> Thrift client GetPeopleServiceClient initialing...");
            transport = new TFramedTransport(new TSocket(Host, Port));
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new EcAuth.Client(protocol);
            System.out.println("=========> Thrift client GetPeopleServiceClient started...");
        } catch (TTransportException e) {
            System.out.println("=========> Thrift client init ERROR!!");
            e.printStackTrace();
        }
        
    }
    
    public void teardownTest(JavaSamplerContext arg0){  
    	super.teardownTest(arg0);  
    }  
    
    // 这个方法是用来自定义java方法入参的。
    // params.addArgument("Host", "");表示入参名字叫Host，默认值为空。
    // 设置可用参数及的默认值；
    @Override
    public Arguments getDefaultParameters() {

        Arguments params = new Arguments();
        params.addArgument("Host", "10.0.201.174");
        params.addArgument("Port", "3605");
        params.addArgument("userId","");
        params.addArgument("corpId","");
        
        return params;
    }
    
    
    public static void main(String [] args){
//    	loginAuth(long userId, long corpId)

    	String Host = "10.0.201.105";
//    	String Host = "192.168.1.141";	//开发环境
    	String Port = "3605";
//    	String userId = "2148088";
//    	String corpId = "2148089";
    	String userId = "2118298";
    	String corpId = "2118299";

        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("userId",userId);
        params.addArgument("corpId",corpId);
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	LoginAuth test = new LoginAuth();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);
   
    }

}
