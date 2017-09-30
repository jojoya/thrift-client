package com.ec.thrift.c.discussion;

import java.nio.ByteBuffer;

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

import service.interfaces.c.discussion.BufferTo;
import service.interfaces.c.discussion.IServerDiscussion;
import service.interfaces.c.discussion.IServerDiscussion.Iface;

public class DiscussionAllDis  extends AbstractJavaSamplerClient {



	
	//获取讨论组里面成员 


		private static Iface client;
	    private static TTransport transport;

	    // 执行测试方法
	    public SampleResult runTest(JavaSamplerContext arg0) {
	    	
	    	int userId =  Integer.valueOf(arg0.getParameter("userId"));//删除讨论组成员
//	    	int user =  Integer.valueOf(arg0.getParameter("user"));
	    	

	    	
	        // 调用服务的 方法
	        String result = "";

	        SampleResult sr = new SampleResult();

	        try {
	            sr.sampleStart();	// 开始统计响应时间标记

//	            ByteBuffer bytebuffer = client.disMembers(discussId); 
	            
	            ByteBuffer bytebuffer = client.allDis(userId);
	            
	            
//	            System.out.println("------------"+bytebuffer.array());
	      
	            byte[] code = new byte[2];
	            bytebuffer.get(code, 0, 2);
	  			short dataBody1= BufferTo.byteToShort(code);
	  			 System.out.println("------------0--"+ dataBody1 );
	  			 
	  			byte[] msg = new byte[60];   			
	            bytebuffer.get(msg, 0, 60);
					String dataBody2 = new String(msg,"UTF-8");
					System.out.println("------------0--"+ dataBody2 );
					
   			    byte[] discussId = new byte[4];   			//更新时间戳
		        bytebuffer.get(discussId, 0, 4);
				int dataBody3 = BufferTo.byteToInt(discussId);
				
				byte[] nums = new byte[2];   			//更新时间戳
		        bytebuffer.get(nums, 0, 2);
				Short dataBody4 = BufferTo.byteToShort(nums);
				
				byte[] theme = new byte[256];   			
	            bytebuffer.get(theme, 0, 256);
					String dataBody5 = new String(theme,"UTF-8");
				//String str = arg0.getParameter("userList");
		    	//String temp = str.substring(1,str.length()-1);
		    	//String [] arrStr = temp.split(",");
					
					
			
		        System.out.println("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ndiscussId:"+String.valueOf(dataBody3)+"\nnums:"+String.valueOf(dataBody4)+"\ntheme:"+String.valueOf(dataBody5));
			  	sr.setResponseData("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ndiscussId:"+String.valueOf(dataBody3)+"\nnums:"+String.valueOf(dataBody4)+"\ntheme:"+String.valueOf(dataBody5), "UTF-8");
			  	
//	            sr.setResponseData("结果是：" + result, "UTF-8");
	            sr.setDataType(SampleResult.TEXT);

	            sr.setSuccessful(true);

	        } catch (Throwable e) {
	            sr.setSuccessful(false);
	            System.out.println("=========> Call thrift server accur ERROR!!");
	            e.printStackTrace();
	        } finally {
	            sr.sampleEnd();	//结束统计响应时间标记

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
	            client = new IServerDiscussion.Client(protocol);
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
	        params.addArgument("Port", "11500");
//	        params.addArgument("discussId","");
	        params.addArgument("userId","");
//	        params.addArgument("adminId","");
//	        params.addArgument("version","");
	        
	        return params;
	    }
	    
	    
	    public static void main(String [] args){
//	    	addMember(long groupId, long adminId, String memberList, String version)

	    	String Host = "10.0.200.168";
	    	String Port = "11500";
	    	String userId = "2148724";
//	    	String user = "2148724";		//群成员信息，member[]的json数据  [2158568,2148733,2148724]
//	    	String adminId = "2148088";2148724   2158568

	        
	        Arguments params = new Arguments();
	        params.addArgument("Host", Host);
	        params.addArgument("Port", Port);
	        params.addArgument("userId",userId);
//	        params.addArgument("user",user);
//	        params.addArgument("adminId",adminId);
//	        params.addArgument("version",version);
	        
	        JavaSamplerContext arg0 = new JavaSamplerContext(params);
	        
	    	DiscussionAllDis test = new DiscussionAllDis();
	 
	    		test.setupTest(arg0);
	    		test.runTest(arg0);
	   
	    }


}
