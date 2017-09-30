package com.test;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	 /*生成Key*/
    public static byte[] initKey() throws NoSuchAlgorithmException{
          KeyGenerator keyGen = KeyGenerator.getInstance("AES");
           keyGen.init(128); //192 256
          SecretKey secretKey = keyGen.generateKey();
           return secretKey.getEncoded();
    }
    
    /*AES加密*/
    public static byte[] encrypt( byte[] data, byte[] key) throws Exception{
          SecretKey secretKey = new SecretKeySpec( key, "AES");
          
          Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
           cipher.init(Cipher. ENCRYPT_MODE, secretKey);
           byte[] aesBytes = cipher.doFinal( data);
           return aesBytes;
    }
    
    /*AES解密*/
    public static byte[] decrypt( byte[] data, byte[] key) throws Exception{
          SecretKey secretKey = new SecretKeySpec( key, "AES");
          
          Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
           cipher.init(Cipher. DECRYPT_MODE, secretKey);
           byte[] aesResult = cipher.doFinal( data);
           return aesResult;
    }
    
    public static void main(String[] args) throws Exception {
       
        /*Test AES*/
        byte[] aesKey = AESUtil. initKey();
   /*   System. out.println( "---AESKey---"+BytesToHex.fromBytesToHex(aesKey));
        byte[] aesResult = AESUtil. encrypt(DATA.getBytes(), aesKey);
       System. out.println( DATA+ "---AES加密---"+BytesToHex.fromBytesToHex( aesResult));
        byte[] aesPlain = AESUtil. decrypt(aesResult, aesKey);
       System. out.println( DATA+ "---AES解密---"+new String(aesPlain));*/
    }


}
