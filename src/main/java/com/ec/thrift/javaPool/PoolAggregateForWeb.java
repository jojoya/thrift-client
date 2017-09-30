/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
package com.ec.thrift.javaPool;

import java.util.Random;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import service.interfaces.java.CcRecordCenterApi;

import com.alibaba.fastjson.JSONObject;
import com.ec.thrift.util.ThriftSocket;


/**
 * @ClassName: QueryForWebFromPool
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
public class PoolAggregateForWeb extends AbstractJavaSamplerClient {

    private JavaThriftClient cr = new JavaThriftClient();

    /*
     * <p>Title: runTest</p> <p>Description: </p>
     *
     * @param arg0
     *
     * @return
     *
     * @see
     * org.apache.jmeter.protocol.java.sampler.JavaSamplerClient#runTest(org.apache.jmeter.protocol.
     * java.sampler.JavaSamplerContext)
     */
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        String result = "";
        String Host = arg0.getParameter("Host");
        Integer Port = Integer.valueOf(arg0.getParameter("Port"));
        String stringJson = arg0.getParameter("stringJson");
        String table = arg0.getParameter("table");
        Integer version = Integer.valueOf(arg0.getParameter("version"));

        SampleResult sr = new SampleResult();
//        sr.setSampleLabel("execute createForWeb method");
        ThriftSocket socket = null;
        
        sr.setSamplerData("table:"+table+"\nversion:"+version+"\nstringJson:"+stringJson+"\n");

        try {
            sr.sampleStart();

            // 从池里取得client
            JavaThriftClientUtil.config.setHost(Host);
            JavaThriftClientUtil.config.setPort(Port);
            socket = JavaThriftClientUtil.pool.borrowSocket(JavaThriftClientUtil.config);
            CcRecordCenterApi.Client client = (CcRecordCenterApi.Client) socket.getClient(cr);
            result = client.aggregateForWeb(stringJson, table, version);


            System.out.println("结果是：" + result);
            sr.setResponseData("结果是：" + result, "UTF-8");
            sr.setDataType(SampleResult.TEXT);
            sr.setSuccessful(true);

        } catch (Throwable e) {
            sr.setSuccessful(false);
            System.out.println("=========> Call thrift server accur ERROR!!");
            e.printStackTrace();
        } finally {
            sr.sampleEnd();
            // 归还client到池中
            if (null != socket) {
                try {
                	JavaThriftClientUtil.pool.returnSocket(socket, JavaThriftClientUtil.config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return sr;
    }


    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
	    params.addArgument("Host", "192.168.1.104"); 
	    params.addArgument("Port", "7121"); 
        params.addArgument("stringJson", "");
        params.addArgument("table", "");
        params.addArgument("version", "");
        return params;
    }

    
      public static void main(String[] args) { 
	      Arguments params = new Arguments();
	      params.addArgument("Host", "192.168.1.104"); 
	      params.addArgument("Port", "7121"); 
	      
	     	//{"memberList": [{"f_id": 5024944,"f_name":"【开发库】代理商管理员"}]} 
	             /* JSONObject memberListItem = new JSONObject();  
	              memberListItem.put("f_receiver", new Integer(5024944));  
	              memberListItem.put("f_receiver", "15827439197"); 
	              
	           // 返回一个JSONArray对象  
	              JSONArray memberListArray = new JSONArray();  
	              memberListArray.add(0, memberListItem); 
	              
	              JSONObject memberListObject = new JSONObject();  
	              memberListObject.put("memberList", memberListArray); 
	      	    System.out.println("jsonObject：" + memberListObject);  
 */
	      	int max=100;
	        int min=1;
	        Random random = new Random();
	        int rdm = random.nextInt(max)%(max-min+1) + min;
	        
	      JSONObject item = new JSONObject();
	      item.put("f_receiver", "15827439197"); 
	      item.put("f_send_time", "2017-07-13 17:57:36"); 
	      item.put("f_create_time", "2017-07-13 17:57:36"); 
	      item.put("f_sender", "1939153"); 
	      item.put("f_msg", "接口测试"+rdm); 
	      item.put("f_route", new Integer(100)); 
	      item.put("f_gate_server", new Integer(0)); 
	      item.put("f_result_status", new Integer(1)); 
	      item.put("f_crm_id", new Integer(0)); 
	      item.put("f_corp_id", "1939154"); 
	      item.put("f_crm_id", new Integer(0)); 
	      System.out.println("memberList：" + item.toString());  
	      
	      params.addArgument("stringJson", item.toString());// 设置参数，
	      params.addArgument("table", "tel");// 设置table参数， 
	      params.addArgument("version", "1"); 
      
	      //设置version参数，并赋予默认值1 
	      JavaSamplerContext arg0 = new JavaSamplerContext(params);
	      
	      PoolAggregateForWeb ht = new PoolAggregateForWeb(); 
	      ht.setupTest(arg0); 
	      ht.runTest(arg0); //
	      ht.teardownTest(arg0); 
      }
     

}


