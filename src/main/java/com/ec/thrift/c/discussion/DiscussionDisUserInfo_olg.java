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

public class DiscussionDisUserInfo_olg extends AbstractJavaSamplerClient {
	



	
	//获取讨论组里面成员 


		private static Iface client;
	    private static TTransport transport;

	    // 执行测试方法
	    public SampleResult runTest(JavaSamplerContext arg0) {
	    	
	    	int discussId =  Integer.valueOf(arg0.getParameter("discussId"));//删除讨论组成员
//	    	int userid =  Integer.valueOf(arg0.getParameter("userid"));
	    	

	    	
	        // 调用服务的 方法
	        String result = "";

	        SampleResult sr = new SampleResult();

	        try {
	            sr.sampleStart();	// 开始统计响应时间标记

	            ByteBuffer bytebuffer = client.disUserInfo_olg(discussId);  
	            byte[] code = new byte[2];
	            bytebuffer.get(code, 0, 2);
	  			short dataBody1= BufferTo.byteToShort(code);	
	  			
	  			 
	  			byte[] msg = new byte[60];   			
	            bytebuffer.get(msg, 0, 60);
				String dataBody2 = new String(msg,"UTF-8");
				System.out.println(dataBody1 + dataBody2);
				 System.out.println("bytebuffer.position()"+bytebuffer.position());
					StringBuffer str=new StringBuffer();
					for(int i=0;i<999;i++){
						System.out.println("aaaaaaaaaa"+bytebuffer.limit());
						        if(bytebuffer.position()==bytebuffer.limit()){
									   System.out.println("bbbbbbbbb");
									   break;
								   }else{
//						     
									   byte[] arrStr1 = new byte[4];   			//更新时间戳
								        bytebuffer.get(arrStr1, 0, 4);
								        int dataBody3 = BufferTo.byteToInt(arrStr1);	   
								
									   
						        byte[] account = new byte[32];   			
					            bytebuffer.get(account, 0, 32);
									String dataBody4 = new String(account,"UTF-8");
						        
//									byte[] name = new byte[30];   			
//						            bytebuffer.get(name, 0, 30);
//										String dataBody5 = new String(name,"UTF-8");
										
										String dataBody5 ;
										if(i==3){
											 System.out.println("bytebuffer.position()"+bytebuffer.position());
											 byte[] face = new byte[14];
									            bytebuffer.get(face, 0, 14);
									            dataBody5 = new String(face,"UTF-8");
													
										}else{
											byte[] name = new byte[32];   			
								            bytebuffer.get(name, 0, 32);
										    dataBody5 = new String(name,"UTF-8");
											
											
//											 byte[] face = new byte[50];
//									            bytebuffer.get(face, 0, 50);
//													 dataBody6 = new String(face,"UTF-8");
//													 System.out.println("bytebuffer.position()"+bytebuffer.position());
//										}
	
										str.append(dataBody3+"," + dataBody4 + dataBody5);
////							       
								}
//						}
////						System.out.println(userList);
//						System.out.println(userList.substring(0, userList.length()-1));
//			            String dataBody3=userList.substring(0, userList.length()-1);
						        
						        
						        System.out.println(str);
							}
					}
//					System.out.println(str);
//					System.out.println(str.substring(0, str.length()-1));
		            String dataBody3=str.substring(0, str.length()-1);
		        System.out.println("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ncount:"+dataBody3);
			  	sr.setResponseData("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ncount:"+dataBody3, "UTF-8");
//			  	
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
	        params.addArgument("discussId","");
//	        params.addArgument("userid","");
//	        params.addArgument("adminId","");
//	        params.addArgument("version","");
	        
	        return params;
	    }
	    
	    
	    public static void main(String [] args){
//	    	addMember(long groupId, long adminId, String memberList, String version)

	    	String Host = "10.0.200.168";
	    	String Port = "11500";
	    	String discussId = "472175";
//	    	String userid = "2148724";		//群成员信息，member[]的json数据  [2158568,2148733,2148724]
//	    	String adminId = "2148088";

	        
	        Arguments params = new Arguments();
	        params.addArgument("Host", Host);
	        params.addArgument("Port", Port);
	        params.addArgument("discussId",discussId);
//	        params.addArgument("userid",userid);
//	        params.addArgument("adminId",adminId);
//	        params.addArgument("version",version);
	        
	        JavaSamplerContext arg0 = new JavaSamplerContext(params);
	        
	        DiscussionDisUserInfo_olg test = new DiscussionDisUserInfo_olg();
	 
	    		test.setupTest(arg0);
	    		test.runTest(arg0);
	   
	    }

}
