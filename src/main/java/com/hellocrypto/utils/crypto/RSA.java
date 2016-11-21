package com.hellocrypto.utils.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author xulei
 * 
 */
public class RSA {

    public static byte[] encrypt(String content, Key pbk) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        byte[] encryptedData = cipher.doFinal(content.getBytes());
        return encryptedData;
    }
    
    public static byte[] encrypt(byte[] content, Key pbk) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        byte[] encryptedData = cipher.doFinal(content);
        return encryptedData;
    }

    public static byte[] decrypt(byte[] encryptedData, Key prk)  
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, prk);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return decryptedData;
    }
    
    public static PublicKey getPubKeyByRawBytes(byte[] pubkeyEncoded) 
            throws NoSuchAlgorithmException, InvalidKeySpecException{
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubkeyEncoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

}
