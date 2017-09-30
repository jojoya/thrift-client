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
public class GroupModify extends AbstractJavaSamplerClient {

	private static Client client;
    private static TTransport transport;

    // 执行测试方法
    public SampleResult runTest(JavaSamplerContext arg0) {
    	
    	long groupId =  Long.parseLong(arg0.getParameter("groupId"));
    	long adminId =  Long.parseLong(arg0.getParameter("adminId"));
    	String name =  arg0.getParameter("name");
    	String notice =  arg0.getParameter("notice");
    	int memberAdd = Integer.parseInt(arg0.getParameter("memberAdd"));	//0只有管理员，1任何成员
    	
        // 调用服务的 方法
        String result = "";

        SampleResult sr = new SampleResult();
        sr.setSamplerData("groupId："+groupId
        		+"\nadminId："+adminId
        		+"\nname："+name
        		+"\nnotice："+notice
        		+"\nmemberAdd："+memberAdd);
        
        try {
            sr.sampleStart();// jmeter 开始统计响应时间标记
            result = client.modifyGroup(groupId, adminId, name, notice, memberAdd);
            System.out.println(result);

            sr.setResponseData("结果是：" + result, "UTF-8");
            sr.setDataType(SampleResult.TEXT);

            sr.setSuccessful(true);

        } catch (Throwable e) {
            sr.setSuccessful(false);
            System.out.println("=========> Call thrift server accur ERROR!!");
            e.printStackTrace();
        } finally {
            sr.sampleEnd();// jmeter 结束统计响应时间标记

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
        
        params.addArgument("Host", "10.0.200.168");
        params.addArgument("Port", "11600");
        params.addArgument("groupId","1007656");//1007718、1007656
        params.addArgument("adminId","5024944");
        params.addArgument("name","");
        params.addArgument("notice","");
        params.addArgument("memberAdd","0");
        
        return params;
    }
    
    
    public static void main(String [] args){

    	String Host = "10.0.200.168";
    	String Port = "11600";
    	String groupId = "1007656";
    	String adminId = "5024944";
    	String timeStr = String.valueOf(System.currentTimeMillis());
    	String name = "name"+timeStr;
    	String notice = "notice"+timeStr;
    	String memberAdd = "0";	//0只有管理员，1任何成员
    	
        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("groupId",groupId);
        params.addArgument("adminId",adminId);
        params.addArgument("name",name);
        params.addArgument("notice",notice);
        params.addArgument("memberAdd",memberAdd);	//0只有管理员，1任何成员
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	GroupModify test = new GroupModify();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);
   
    }

}
