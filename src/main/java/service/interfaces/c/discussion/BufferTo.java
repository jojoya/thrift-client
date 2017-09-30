package service.interfaces.c.discussion;

import org.apache.jmeter.samplers.SampleResult;



public class BufferTo {
	 /** 
    * 注释：字节数组到short的转换！ 
    * 
    * @param b 
    * @return 
    */ 
   public static short byteToShort(byte[] b) { 
       short s = 0; 
       short s0 = (short) (b[0] & 0xff);// 最低位 
       short s1 = (short) (b[1] & 0xff); 
       s1 <<= 8; 
       s = (short) (s0 | s1); 
       return s; 
   }
   /** 
    * 注释：字节数组到int的转换！ 
    * 
    * @param b 
    * @return 
    */ 
   public static int byteToInt(byte[] b) { 
       int s = 0; 
       int s0 = b[0] & 0xff;// 最低位 
       int s1 = b[1] & 0xff; 
       int s2 = b[2] & 0xff; 
       int s3 = b[3] & 0xff; 
       s3 <<= 24; 
       s2 <<= 16; 
       s1 <<= 8; 
       s = s0 | s1 | s2 | s3; 
       return s; 
   } 
}