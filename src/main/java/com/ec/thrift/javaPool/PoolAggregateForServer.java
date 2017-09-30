/**
 * @Package com.ec.thrift.client
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
package com.ec.thrift.javaPool;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import service.interfaces.java.CcRecordCenterApi;
import service.interfaces.java.CcRecordCenterProtos;
import service.interfaces.java.CcRecordCenterProtos.SmsBodyInfo;

import com.ec.thrift.util.ThriftSocket;
import com.google.protobuf.InvalidProtocolBufferException;


/**
 * @ClassName: QueryForWebFromPool
 * @Description: TODO
 * @author Administrator
 * @date 2017年1月12日 下午3:52:56
 */
public class PoolAggregateForServer extends AbstractJavaSamplerClient {

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
            result = byteBufferToString(client.aggregateForServer(stringJson, table, version));


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
    
    public static String byteBufferToString(ByteBuffer buffer) {
     	
    	byte[] bt = new byte[buffer.remaining()];
		buffer.get(bt,0,bt.length);
		SmsBodyInfo info;
		String result = null;
		try {
			info = CcRecordCenterProtos.SmsBodyInfo.parseFrom(bt);
			result = "count:" + info.getCount();
			for(int i=0;i<info.getCount();i++){
//				System.out.println(info.getSmscnt(i).getFId());
//				System.out.println(info.getSmscnt(i).getFMsg());
				result = result + ",\n"+info.getSmscnt(i).getFId()+":"+info.getSmscnt(i).getFMsg();
			}
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			info = null;
		}
//		System.out.println(info);
		
		
		return info.toString() + "\n" + result;
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
	      params.addArgument("stringJson", "{"
	      		+ "\"filter\":{"
	      			+ "\"f_receiver\":15827439197,"
		      		+ "\"f_send_time\":{"
			      		+ "\"gte\":\"2017-07-11 00:00:00\","
			      		+ "\"lte\":\"2017-07-15 23:59:59\"}}}");// 设置参数，
		 params.addArgument("table", "sms");// 设置table参数， 
		 params.addArgument("version", "1"); //
      
	      //设置version参数，并赋予默认值1 
	      JavaSamplerContext arg0 = new JavaSamplerContext(params);
	      
	      PoolAggregateForServer ht = new PoolAggregateForServer(); 
	      ht.setupTest(arg0); 
	      ht.runTest(arg0); //
	      ht.teardownTest(arg0); 
      }
     

}


