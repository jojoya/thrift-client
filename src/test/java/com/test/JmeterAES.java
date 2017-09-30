package com.test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class JmeterAES {
	
	public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.encodeBase64String(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cSrc = "{\"appId\":\"P201707280\",\"applyNo\":\"20170802101737582178\",\"idNo\":\"jmeter 高级技术群：572445436\",\"userName\":\"jmeter 高级技术群：572445436\",\"phone\":\"jmeter 高级技术群：572445436\",\"zhima\":700,\"applyTime\":\"2017-08-02 10:17:37\",\"amount\":9800}";
        //密码，长度要是8的倍数
       String cKey = "d4e9acc6a0ff505b";
       // 需要加密的字串
       System.out.println(cSrc);
       // 加密
       try {
		String enString = Encrypt(cSrc, cKey);
		System.out.println(1939154%8);
		System.out.println(enString);
	} catch (Exception e) {
		e.printStackTrace();
	}    
//       vars.put("enString",enString);
//       return enString;
	}

}
