package com.hellocrypto.test.crypto.md5;

import com.hellocrypto.utils.ByteUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author leixu2
 */
public class MD5Test {

    private static final String TEST_CLEAR_DATA ="HELLO Crypto, Whoami";
    
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        System.out.println("--------------------RSA-----------------------");
        System.out.println("Clear Text: " + TEST_CLEAR_DATA 
                + ", target MD5 base64 value: RwGNuvLc+7aqiPaRY4C6wg==" 
                + ", target MD5 hex: 47018DBAF2DCFBB6AA88F6916380BAC2");
        System.out.println("MD5 base64 encoding: " + encoderByMd5(TEST_CLEAR_DATA));
        System.out.println("MD5 Hex String: " + ByteUtil.parseByte2HexStr(md5(TEST_CLEAR_DATA)));
        
    }
    
    public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
        
    }
    
    public static byte[] md5(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(content.getBytes());
    }

}
