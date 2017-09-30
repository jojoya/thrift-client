/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
package com.ec.thrift.javaPool;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import service.interfaces.java.CcRecordCenterApi;

import com.ec.thrift.util.SocketPoolConfig;
import com.ec.thrift.util.ThriftSocket;

/**
 * @ClassName: QueryForWebFromPool
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
public class PoolUpdateForServer extends AbstractJavaSamplerClient {

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
            result = String.valueOf(client.updateForServer(stringJson, table, version));


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

    public static String byteBufferToString(ByteBuffer buffer) {
    	CharBuffer charBuffer = null;
    	try {
    	Charset charset = Charset.forName("UTF-8");
    	CharsetDecoder decoder = charset.newDecoder();
    	charBuffer = decoder.decode(buffer);
    	buffer.flip();
    	return charBuffer.toString();
    	} catch (Exception ex) {
    	ex.printStackTrace();
    	return null;
    	}
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
	      params.addArgument("Host", "192.168.1.104"); //
	      params.addArgument("Port", "7121"); //
	      params.addArgument("stringJson", "{\"filter\":{\"f_receiver\":15827439197}}");// 设置参数，
	      params.addArgument("table", "sms");// 设置table参数， 
	      params.addArgument("version", "1"); //

      
	      //设置version参数，并赋予默认值1 
	      JavaSamplerContext arg0 = new JavaSamplerContext(params);
	      
	      PoolUpdateForServer ht = new PoolUpdateForServer(); 
	      ht.setupTest(arg0); 
	      ht.runTest(arg0); //
	      ht.teardownTest(arg0); 
      }
     

}


