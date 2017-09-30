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
//import service.interfaces.c.group.IServerGroup;
//import service.interfaces.c.group.IServerGroup.Client;


//import com.ec.thrift.c.group.GroupAddMember;

public class Discussion_C_AddMember  extends AbstractJavaSamplerClient {


	private static Iface client;
    private static TTransport transport;

    // 执行测试方法
    public SampleResult runTest(JavaSamplerContext arg0) {
    	
    	int discussId =  Integer.valueOf(arg0.getParameter("discussId"));
    	
    	String str = arg0.getParameter("userList");
    	String temp = str.substring(1,str.length()-1);
    	String [] arrStr = temp.split(",");
    	List<Integer> userList= new ArrayList<Integer>();
    	for(int i=0 ; i<arrStr.length ; i++){
    		userList.add(Integer.valueOf(arrStr[i]));
    	}
    	int adminId =  Integer.valueOf(arg0.getParameter("adminId"));
    	

//    	long groupId =  Long.parseLong(arg0.getParameter("groupId"));
//    	long adminId =  Long.parseLong(arg0.getParameter("adminId"));
//    	String memberList =  arg0.getParameter("memberList");
//    	String version =  arg0.getParameter("version");
    	
        // 调用服务的 方法
        String result = "";

        SampleResult sr = new SampleResult();
//        sr.setSamplerData("groupId："+groupId
//        		+"\nadminId："+adminId
//        		+"\nmemberList："+memberList
//        		+"\nversion："+version); 
//        
        try {
            sr.sampleStart();	// 开始统计响应时间标记
//            ByteBuffer byteBuffer = client.create( );
//            System.out.println(bytebuffer.array());

            ByteBuffer bytebuffer = client.addMember(discussId, userList, adminId);  
//            System.out.println(new String(result.get(),"UTF-8"));
            System.out.println("------------"+bytebuffer.array());
            
            byte[] code = new byte[2];
            bytebuffer.get(code, 0, 2);
//  			Object bytebufferTo;
  			short dataBody1= BufferTo.byteToShort(code);
//  			 System.out.println("------------0--"+ dataBody1 );
  			 
  			byte[] msg = new byte[60];
  			
  			
//            bytebuffer.get(msg , 2 , 60 );
            			
            bytebuffer.get(msg, 0, 60);
				String dataBody2 = new String(msg,"UTF-8");
//				System.out.println("------------0--"+ dataBody2 );
				System.out.println("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2);	  			 
	            sr.setResponseData("结果是：\ncode:" + String.valueOf(dataBody1)+"\nmsg:"+dataBody2, "UTF-8");
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
        params.addArgument("userList","");
        params.addArgument("adminId","");
//        params.addArgument("version","");
        
        return params;
    }
    
    
    public static void main(String [] args){
//    	addMember(long groupId, long adminId, String memberList, String version)

    	String Host = "10.0.200.168";
    	String Port = "11500";
    	String discussId = "472141";//471982
//    	String userList = "[2158568,2148733,2148724]";		//群成员信息，member[]的json数据
    	String userList = "[1930398,343773,1916030,1935602,1936733,1975077,2145609,2163373,2167258,100733,120387,204119,423322,917536,979045,1188990,1501076,1589881,1637166,1647611,1662979,1810696,1810885,1827577,1827586,1915629,2148715,2163367,893398,63576,305466,1132818,757415,1205690,1443297,1489334,1718069,220701,332372,368972,425471,442660,480961,539510,1482558,204116,276848,848932,856630,1050302,1375494,1450312,1801177,225111,797130,963876,1198251,1411109,1560611,1832890,1071240,1368638,1444544,880088,893299,917532,938773,960892,970696,997233,1213156,1642037]";	
    	String adminId = "2148088";//2148088

        
        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("discussId",discussId);
        params.addArgument("userList",userList);
        params.addArgument("adminId",adminId);
//        params.addArgument("version",version);
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
    	Discussion_C_AddMember test = new Discussion_C_AddMember();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);  
    }
	
}
