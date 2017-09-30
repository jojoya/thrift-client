package com.test;
import java.security.MessageDigest;

public class liteKey {

		 public static String stringMD5(String inStr) {
		        // 初始化MessageDigest对象
		        MessageDigest md5 = null;
		        try {
		            // 初始化MD5对象，MessageDigest是protected只能通过getInstance初始化
		            md5 = MessageDigest.getInstance("MD5");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        // 将inStr转化为字符串数组
		        char[] charArray = inStr.toCharArray();
		        // 转化为字节数组
		        byte[] byteArray = new byte[charArray.length];
		        for (int i = 0; i < charArray.length; i++)
		            byteArray[i] = (byte) charArray[i];
		        byte[] md5Bytes = md5.digest(byteArray);
		        StringBuffer hexValue = new StringBuffer();
		        for (int i = 0; i < md5Bytes.length; i++) {
		            int val = ((int) md5Bytes[i]) & 0xff;
		            if (val < 16)
		                hexValue.append("0");
		            hexValue.append(Integer.toHexString(val));
		        }
		        System.out.println("&key=" + hexValue.toString() );
		        return hexValue.toString();
		    }

		
	public static void main(String[] args) {

		long time =System.currentTimeMillis();
		System.out.println(time);
		String account="14422222222";
		System.out.println(account);
		String password = "a888888";
		System.out.println(password);
		
		
		String passWordMD5=stringMD5(password);
		System.out.println(passWordMD5); 
		String str=account+passWordMD5+time;
		String key= stringMD5(str);
		System.out.println(key);
		
		
	}

}
