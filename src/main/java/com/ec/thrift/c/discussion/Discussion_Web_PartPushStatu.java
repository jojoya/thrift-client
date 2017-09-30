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

public class Discussion_Web_PartPushStatu extends AbstractJavaSamplerClient {

	



	private static Iface client;
    private static TTransport transport;

    // 执行测试方法
    public SampleResult runTest(JavaSamplerContext arg0) {
    	
//    	String str = arg0.getParameter("memList");
//    	String temp = str.substring(1,str.length()-1);
//    	String [] arrStr = temp.split(",");
//    	List<Integer> memList= new ArrayList<Integer>();
//    	for(int i=0 ; i<arrStr.length ; i++){
//    		memList.add(Integer.valueOf(arrStr[i]));
//    	}
    	String memList =  arg0.getParameter("memList");
    	int discussId =  Integer.valueOf(arg0.getParameter("discussId"));//删除讨论组成员

    	
        // 调用服务的 方法
        String result = "";

        SampleResult sr = new SampleResult();
        sr.setSamplerData("memList："+memList
        		+"\ndiscussId："+discussId); 
        try {
            sr.sampleStart();	// 开始统计响应时间标记

            result = client.partPushStatu_web(discussId,memList);  
            System.out.println("------------"+result);
      


            sr.setResponseData("结果是：" + result, "UTF-8");
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
        params.addArgument("memList","");
//        params.addArgument("adminId","");
//        params.addArgument("version","");
        
        return params;
    }
    
    
    public static void main(String [] args){
//    	addMember(long groupId, long adminId, String memberList, String version)

    	String Host = "10.0.200.168";
    	String Port = "11500";
    	String discussId = "123123";
//    	String timeStr = String.valueOf(System.currentTimeMillis());
//    	String theme = "name"+timeStr;;		//群成员信息，member[]的json数据  [2158568,2148733,2148724]
    	String memList = "10623,251941,442596,641078,899815,1574293,1845665,1915976,1919619,1930398,1935606,1948923,2052303,2055213,2065848,2072763,2072775,2076421,2076429,2076455,2079723,2112378,2112395,2112501,2113041,2139448,2141091,2141191,2141195,2141199,2141207,2141211,2144135,2144318,2144945,2148601,2151182,2151485,2153639,2155517,2158377,2158762,2158766,2160433,2162527,2162531,2162540,2162863,2163016,2163060,2165674,2165785,2165879,2166737,2166853,2167283,2167291,2167295,2167303,2167307,2167478,2168249,2170836,2171023,2171394,2171472,2171845,2171924,2172473,191504,300502,1918923,1934620,2148305,2151229,2162523,2167279,702778,611144,794799,914306,979037,1031209,1160462,1614818,1913550,508335,1411045,1461166,1589904,2068046,2078153,2108740,2112815,2141095,2141151,2141159,300504,300506,338340,893310,920129,929283,1089714,1308866,1916298,2160530,258201,343773,783875,1923223,2076406,2144941,2162519,2163340,10260,107508,946327,979059,1286139,1488975,1623176,57794,405896,421151,893404,929264,970705,1500460,1746894,1894079,1916030,1935602,1936733,1975077,2145609,2163373,2167258,100733,120387,204119,423322,917536,979045,1188990,1501076,1589881,1637166,1647611,1662979,1810696,1810885,1827577,1827586,1915629,2148715,2163367,893398,63576,305466,1132818,757415,1205690,1443297,1489334,1718069,220701,332372,368972,425471,442660,480961,539510,1482558,204116,276848,848932,856630,1050302,1375494,1450312,1801177,225111,797130,963876,1198251,1411109,1560611,1832890,1071240,1368638,1444544,880088,893299,917532,938773,960892,970696,997233,1213156,1642037";

        
        Arguments params = new Arguments();
        params.addArgument("Host", Host);
        params.addArgument("Port", Port);
        params.addArgument("discussId",discussId);
//        params.addArgument("theme",theme);
        params.addArgument("memList",memList);
//        params.addArgument("version",version);
        
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        
        Discussion_Web_PartPushStatu test = new Discussion_Web_PartPushStatu();
 
    		test.setupTest(arg0);
    		test.runTest(arg0);
   
    }
	


	}
