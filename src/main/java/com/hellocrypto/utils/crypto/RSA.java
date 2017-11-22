package com.hellocrypto.utils.crypto;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author xulei
 * 
 */
public class RSA {

    public static final int KEY_SIZE = 1024;
    
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

    public static PrivateKey loadPrivateKeyByRawBytes(byte[] encodedPrivateKey) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        return KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
    }
    
    /**
     * generate RSA key pair by Java API
     *
     * @return KeyPair
     * @throws NoSuchAlgorithmException
     * @throws java.io.IOException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
        pairgen.initialize(RSA.KEY_SIZE);
        KeyPair keyPair = pairgen.genKeyPair();
//        PublicKey pbkey = keyPair.getPublic();
//        PrivateKey prkey = keyPair.getPrivate();
        return keyPair;
    }

}
