/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author ecuser
 * @date 2016年11月11日 下午3:39:29
 */
package com.ec.thrift.c.group;

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

import service.interfaces.c.group.IServerGroup;
import service.interfaces.c.group.IServerGroup.Client;

/**
 * @ClassName: GetPeoplePerfTest
 * @Description: TODO
 * @author JOJOYA
 * @date 2017年3月16日09:40:06
 */
public class GroupChangeCreater extends AbstractJavaSamplerClient {

	private static Client client;
    private static TTransport transport;

    // 执行测试方法
    public SampleResult runTest(JavaSamplerContext arg0) {

    	long groupId =  Long.parseLong(arg0.getParameter("groupId"));
    	long adminId =  Long.parseLong(arg0.getParameter("adminId"));
    	long userId =  Long.parseLong(arg0.getParameter("userId"));
    	
        // 调用服务的 方法
        String result = "";

        SampleResult sr = new SampleResult();
        sr.setSamplerData("groupId："+groupId
        		+"\nadminId："+adminId
        		+"\nuserId："+userId); 
        
        try {
            sr.sampleStart();	// 开始统计响应时间标记
            result = client.changeCreater(groupId, adminId, userId);
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
            client = new IServerGroup.Client(protocol);
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
        params.addArgument("Host", "192.168.1.222");
        params.addArgument("Port", "11600");
        params.addArgument("adminId","");
        params.addArgument("groupId","");
        params.addArgument("userId","");
        
        return params;
    }
    
    
    public static void main(String [] args){
//    	(long groupId, long adminId, int memberAdd) 

    	String Host = "10.0.200.168";
    	String Port = "11600";
    	String adminId = "5024944";
    	String groupId = "1007724";
    	String userId = "5024944";//5024952
    	
        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("adminId",adminId);
        params.addArgument("groupId",groupId);
        params.addArgument("userId",userId);
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	GroupChangeCreater test = new GroupChangeCreater();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);
   
    }

}
