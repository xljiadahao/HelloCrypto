package com.hellocrypto.utils.crypto;

import com.hellocrypto.utils.ByteUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author leixu2
 */
public class MD5 {
    
    public static byte[] md5(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(content.getBytes());
    }
    
    public static String md5Base64(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5(content));
    }
    
    public static String md5Hex(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return ByteUtil.parseByte2HexStr(md5(content));
    }
    
}
