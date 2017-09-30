/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author ecuser
 * @date 2016年11月11日 下午3:39:29
 */
package com.ec.thrift.java;

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

import service.interfaces.java.CcRecordCenterApi;



/**
 * @ClassName: QurryForWeb
 * @Description: TODO
 * @author ecuser
 * @date 2016年11月11日 下午3:39:29
 */
public class AggregateForWeb extends AbstractJavaSamplerClient {

    private static CcRecordCenterApi.Client client;
    private static TTransport transport;

    // 执行测试方法
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        // 调用服务的 helloVoid 方法
        String result = "";
        String stringJson = arg0.getParameter("stringJson");
        String table = arg0.getParameter("table");
        Integer version = Integer.valueOf(arg0.getParameter("version"));

        SampleResult sr = new SampleResult();
        sr.setSampleLabel("Java请求哦");

        try {
            sr.sampleStart();// jmeter 开始统计响应时间标记

            result = client.queryForWeb(stringJson, table, version);
          
           System.out.println(result);
            
         // 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
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
            // if (transport != null) {
            // transport.close();
            // }
        }

        return sr;
    }

    // 每个线程测试前执行一次，做一些初始化工作；
    @Override
    public void setupTest(JavaSamplerContext arg0) {
        try {
            System.out.println("=========> Thrift client initialing...");
            transport = new TFramedTransport(new TSocket("192.168.1.104", 7002));
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new CcRecordCenterApi.Client(protocol);
            System.out.println("=========> Thrift client started...");
        } catch (TTransportException e) {
            System.out.println("=========> Thrift client init ERROR!!");
            e.printStackTrace();
        }
    }

    // 这个方法是用来自定义java方法入参的。
    // params.addArgument("stringJson", "");表示入参名字叫stringJson，默认值为空。
    // 设置可用参数及的默认值；
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("stringJson", "");
        params.addArgument("table", "");
        params.addArgument("version", "");
        return params;
    }
    
    //测试结束时调用；
    public void teardownTest(JavaSamplerContext arg0) {
    	transport.close();
        // System.out.println(end);
        // System.out.println("The cost is"+(end-start)/1000);
    }
   	// main只是为了调试用，最后打jar包的时候注释掉。
/*    public static void main(String[] args) 
    { // TODO Auto-generated method stub
        Arguments params = new Arguments(); 
        params.addArgument("stringJson", "{\"id\":456}");//设置参数，
        params.addArgument("table", "t_tel_record_31");//设置table参数，
        params.addArgument("version", "1");//设置version参数，并赋予默认值1 
        JavaSamplerContext arg0 = new JavaSamplerContext(params); 
        AggregateForWeb  ht = new AggregateForWeb(); 
        ht.setupTest(arg0); 
        ht.runTest(arg0);
        //test.teardownTest(arg0); 
    }
*/
}
