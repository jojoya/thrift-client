package com.ec.thrift.c.discussion;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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

public class DiscussionAllDisInfo  extends AbstractJavaSamplerClient  {


class test{
	private Integer lastModiTime;
	private short  nums;
	private Integer discussId;
	private byte lspush;
	
	public Integer getLastModiTime() {
		return lastModiTime;
	}
	public void setLastModiTime(Integer lastModiTime) {
		this.lastModiTime = lastModiTime;
	}
	public short getNums() {
		return nums;
	}
	public void setNums(short nums) {
		this.nums = nums;
	}
	public Integer getDiscussId() {
		return discussId;
	}
	public void setDiscussId(Integer discussId) {
		this.discussId = discussId;
	}
	public byte getLspush() {
		return lspush;
	}
	public void setLspush(byte lspush) {
		this.lspush = lspush;
	}

	
}

	
	//获取讨论组里面成员 


		private static Iface client;
	    private static TTransport transport;

	    // 执行测试方法
	    public SampleResult runTest(JavaSamplerContext arg0) {
	    	
	    	int userId =  Integer.valueOf(arg0.getParameter("userId"));//删除讨论组成员
	    	int batch =  Integer.valueOf(arg0.getParameter("batch"));
	    	

	    	
	        // 调用服务的 方法
	        String result = "";

	        SampleResult sr = new SampleResult();

	        try {
	            sr.sampleStart();	// 开始统计响应时间标记

//	            ByteBuffer bytebuffer = client.disMembers(discussId); 
	            
	            ByteBuffer bytebuffer = client.allDisInfo(userId,batch);
	            
	            
//	            System.out.println("------------"+bytebuffer.array());
	      
	            byte[] code = new byte[2];
	            bytebuffer.get(code, 0, 2);
	  			short dataBody1= BufferTo.byteToShort(code);
	  			 System.out.println("------------0--"+ dataBody1 );
	  			 
	  			byte[] msg = new byte[60];   			
	            bytebuffer.get(msg, 0, 60);
					String dataBody2 = new String(msg,"UTF-8");
					System.out.println("------------0--"+ dataBody2 );
					
					List list = new ArrayList();
					
					
					StringBuffer userList=new StringBuffer();
					for(int i=0;i<100;i++){
						
						byte[] arrStr1 = new byte[4];   			//更新时间戳
				        bytebuffer.get(arrStr1, 0, 4);
				        int lastModiTime = BufferTo.byteToInt(arrStr1);
				        System.out.println("lastModiTime:"+lastModiTime);
				        
						   if(lastModiTime==0){
				    		    break;
							}else{
//								byte[] arrStr1 = new byte[4];   			//更新时间戳
//						        bytebuffer.get(arrStr1, 0, 4);
//						        int lastModiTime = BufferTo.byteToInt(arrStr1);
//						        System.out.println("lastModiTime:"+lastModiTime);
						        
						        byte[] arrStr2 = new byte[2];   			//更新时间戳
						        bytebuffer.get(arrStr2, 0, 2);
						        short nums = BufferTo.byteToShort(arrStr2);
						        System.out.println("nums:"+nums);
						        
						        byte[] arrStr3 = new byte[4];   			//更新时间戳
						        bytebuffer.get(arrStr3, 0, 4);
						        int discussId = BufferTo.byteToInt(arrStr3);
						        System.out.println("discussId:"+discussId);
						        
						        byte[] arrStr5 = new byte[256];   			
					            bytebuffer.get(arrStr5, 0, 256);
									String theme = new String(arrStr5,"UTF-8");
									System.out.println(String.valueOf(theme));
									
						        byte[] arrStr4 = new byte[1];   			//更新时间戳
						        bytebuffer.get(arrStr4, 0, 1);
						        Byte lspush = arrStr4[0];
						        System.out.println("lspush:"+lspush);
						        
						        userList.append(lastModiTime+"," + nums+"," + discussId+"," + theme +"," + lspush +",");
							}
					}
					System.out.println(userList);
//					System.out.println(userList.substring(0, userList.length()-1));
					
		            String dataBody3=userList.substring(0, userList.length()-1);
					
				
				System.out.println("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\nDiscussionInfo:"+dataBody3);
		        sr.setResponseData("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\nDiscussionInfo:"+dataBody3, "UTF-8");
					
			
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
	    	String userId = "2148088";
	    	String batch = "4";		//群成员信息，member[]的json数据  [2158568,2148733,2148724]
//	    	String adminId = "2148088";  2148724

	        
	        Arguments params = new Arguments();
	        params.addArgument("Host", Host);
	        params.addArgument("Port", Port);
	        params.addArgument("userId",userId);
	        params.addArgument("batch",batch);
//	        params.addArgument("adminId",adminId);
//	        params.addArgument("version",version);
	        
	        JavaSamplerContext arg0 = new JavaSamplerContext(params);       
	    	DiscussionAllDisInfo test = new DiscussionAllDisInfo();
	 	    		test.setupTest(arg0);
	    		test.runTest(arg0);
	    }
}
