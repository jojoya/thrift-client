/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
package com.ec.thrift.javaPool;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import service.interfaces.java.CcRecordCenterApi;

import com.ec.thrift.util.ThriftSocket;

/**
 * @ClassName: QueryForWebFromPool
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
public class PoolQueryForWeb extends AbstractJavaSamplerClient {

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
            result = client.queryForWeb(stringJson, table, version);


//            System.out.println(result);
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
	      params.addArgument
	      ("stringJson", 
	    		  "{f_corp_id:1939154,"
	      		+ "f_is_mate:0,"
	      		+ "f_send_flag:1,"
	      		+ "f_result_status:1,"
	      		+ "f_send_time: \"2016-12-25 08:33:53\"}");// 设置参数，
	      params.addArgument("table", "tel");// 设置table参数， 
	      params.addArgument("version", "1"); //
      
	      //设置version参数，并赋予默认值1 
	      JavaSamplerContext arg0 = new JavaSamplerContext(params);
	      
	      PoolQueryForWeb ht = new PoolQueryForWeb(); 
	      ht.setupTest(arg0); 
	      ht.runTest(arg0); //
	      ht.teardownTest(arg0); 
      }
     

}


