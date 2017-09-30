package com.test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class demo01 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

		char [] ucSignLen = new char[1];			//用户签名长度	len =1 szSignKey的长度
		char [] szSignKey = new char[1024];		
	
		System.out.println("szSignKey:"+szSignKey.length);
		System.out.println("szSignKey:"+String.valueOf(szSignKey));
		szSignKey= "AAAAAOVOBQDo8zJXAQAxMjM0NTU2NjcAAAAAAAAA".toCharArray();			//用户签名,签名中包含用户ID、时间戳、SessionKey等信忿 len=1024		AAAAABoZJwCRDG9YAQAxMjM0NTU2NjcAAAAAAAAA
		ucSignLen[0] = (char) szSignKey.length;
		System.out.println("szSignKey:"+szSignKey.length);
		String str = String.valueOf(szSignKey);
		System.out.println("strlength:"+str.length());
		
		
		byte[] data1 = new byte[5];
		data1[0] = 1;
		byte[] data2 = new byte[5];
		data2[0] = 2;
//		byte[] data = data1 + data2;

		byte[] data3 = new byte[data1.length+data2.length];
		  System.arraycopy(data1,0,data3,0,data1.length);
		  System.arraycopy(data2,0,data3,data1.length,data2.length);
		  
		  for(int i=0;i<data3.length;i++){
		  System.out.print(data3[i]);
		  }
		  System.out.println();

		  byte[] data4 = new byte[5];
		  data4[0] = 4;
		  for(int i=0;i<data4.length;i++){
		  System.out.print(data4[i]);
		  }
		  System.out.println();
		  
		  ByteBuffer buf = ByteBuffer.wrap(data4);
		  System.out.print("buf:");
		  for(int i=0;i<buf.capacity();i++){
		  System.out.print(buf.getChar(i));
		  }
		  
		  data4 = new byte[buf.remaining()];
		  for(int i=0;i<data4.length;i++){
		  System.out.print(data4[i]);
		  }
		
	}
	
	
	public ByteBuffer itohl(int length,int param){
		
		 ByteBuffer buffer = null;
		 ByteBuffer bufferRes = null;
		 short paramShort = 0;
		 String str = null;
		 
		  buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);//从jvm中分配缓冲字节大小，并设置字节序为小字节序
		  
		   buffer.putInt(param);//处理参数
		   buffer.putShort(paramShort);//处理参数
		   ByteUtil.putString(buffer, str, length);

/*		           if (buffer.hasArray()) {
		            byte[] bytes = buffer.array();
		            byte[] res = ZLibUtils.compress(bytes);
		            bufferRes = ByteBuffer.allocate(res.length).order(ByteOrder.LITTLE_ENDIAN);
		            bufferRes.put(res);
		        }*/
		  
		  bufferRes.flip();
		
		return bufferRes;
	} 

}
