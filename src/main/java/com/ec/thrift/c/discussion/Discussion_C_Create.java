package com.ec.thrift.c.discussion;

import java.lang.reflect.Array;
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

import com.alibaba.fastjson.JSONArray;

import service.interfaces.c.discussion.BufferTo;
import service.interfaces.c.discussion.IServerDiscussion;
import service.interfaces.c.discussion.IServerDiscussion.Iface;


public class Discussion_C_Create extends AbstractJavaSamplerClient {
	
	//private static final JavaSamplerContext request = null;
	/**
	 * @ClassName: GetPeoplePerfTest
	 * @Description: TODO
	 * @author JOJOYA
	 * @date 2017年3月16日09:40:06
	 */

		private static Iface client;
	    private static TTransport transport;

	    // 执行测试方法
	    public SampleResult runTest(JavaSamplerContext arg0) {
	    	
	    	int adminId =  Integer.valueOf(arg0.getParameter("adminId"));
	    	
	    	String str = arg0.getParameter("userList");
	    	String temp = str.substring(1,str.length()-1);
	    	String [] arrStr = temp.split(",");
	    	List<Integer> userList= new ArrayList<Integer>();
	    	for(int i=0 ; i<arrStr.length ; i++){
	    		userList.add(Integer.valueOf(arrStr[i]));
	    	}
//	    	List<Integer> userList1 =	new ArrayList<Integer>"{12,34,45,56,78}";
	    	String theme =  arg0.getParameter("theme");
	    	//String notice =  arg0.getParameter("notice");
	    //	int userAdd = Integer.parseInt(arg0.getParameter("userAdd"));	//0只有管理员，1任何成员
	    	
	        // 调用服务的 方法
	    	String result = "";
	    	
	    //	Comparable<ByteBuffer> result=Integer.parseInt(request.getParameter("result"));

	        SampleResult sr = new SampleResult();
//	        sr.setSamplerData("adminId："+adminId
//	        		+"\nname："+theme
//	        		+"\nuserAdd："+userList1); 
	        
	        
	        try {
	            sr.sampleStart();// jmeter 开始统计响应时间标记
	            ByteBuffer bytebuffer = client.create(adminId, userList, theme);
//	            System.out.println(new String(result.get(),"UTF-8"));
	            System.out.println("------------"+bytebuffer.array());
//	            System.out.println("------------"+new String(bytebuffer.array(), "UTF-8"));
               
	            byte[] code = new byte[2];
	            bytebuffer.get(code, 0, 2);
//	  			Object bytebufferTo;
	  			short dataBody1= BufferTo.byteToShort(code);
//	  			 System.out.println("------------0--"+ dataBody1 );
	  			 
	  			byte[] msg = new byte[60];
	  			
	  			
//	            bytebuffer.get(msg , 2 , 60 );
	            			
	            bytebuffer.get(msg, 0, 60);
  				String dataBody2 = new String(msg,"UTF-8");
//  				System.out.println(new String(msg));
	            //msg to string
//	            System.out.println("------------1"+  new String(msg));
  				int dataBody3 = 999999;
	  			if(dataBody1==0){
	  				byte[] ecChatIdByte = new byte[4];
	  				bytebuffer.get(ecChatIdByte, 0, 4);
	  				dataBody3=BufferTo.byteToInt(ecChatIdByte);//  .byteToInt(ecChatIdByte);//byteToInt(ecChatIdByte);

	  			}
	  			
			
	  			System.out.println("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ndiscussId:"+String.valueOf(dataBody3));
	            sr.setResponseData("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2+"\ndiscussId:"+String.valueOf(dataBody3), "UTF-8");
	            sr.setDataType(SampleResult.TEXT);

	            sr.setSuccessful(true);

	        } catch (Throwable e) {
	            sr.setSuccessful(false);
	            System.out.println("=========> ----Call thrift server accur ERROR!!");
	            e.printStackTrace();
	        } finally {
	            sr.sampleEnd();// jmeter 结束统计响应时间标记

	            // 关闭thrift client
//	             if (transport != null) {
//	             transport.close();
//	             }
	        }

	        return sr;
	    }

	    private void byteToInt(byte[] ecChatIdByte) {
			// TODO Auto-generated method stub
			
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
//	    
	
	    
	    // 这个方法是用来自定义java方法入参的。
	    // params.addArgument("Host", "");表示入参名字叫Host，默认值为空。
	    // 设置可用参数及的默认值；
	    @Override
	    public Arguments getDefaultParameters() {
	    	

	    	
	        Arguments params = new Arguments();
	        
	        params.addArgument("Host", "10.0.200.168");
	        params.addArgument("Port", "11500");
	        params.addArgument("adminId","");
	        params.addArgument("userList","");
	        params.addArgument("theme","");
	        
	        return params;
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
    	String Host = "10.0.200.168";
    	String Port = "11500";
    	String adminId = "1931851";
    	String userList = "[1931851,1935189]";	//创建人信息，member[]的json数据[2148088,2148724]
    	String timeStr = String.valueOf(System.currentTimeMillis());
    	String theme = "name"+timeStr;
    	
        
        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("adminId",adminId);
        params.addArgument("userList",userList);	//群成员信息，member[]的json数据
        params.addArgument("theme",theme); //
       // params.addArgument("notice",notice);
       // params.addArgument("userAdd",userAdd);	//0只有管理员，1任何成员
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	Discussion_C_Create test = new Discussion_C_Create();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);

	}

}
