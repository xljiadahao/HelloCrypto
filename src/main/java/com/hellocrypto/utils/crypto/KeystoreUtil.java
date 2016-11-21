package com.hellocrypto.utils.crypto;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author xulei
 * 
 */
public class KeystoreUtil {

    private static final String KEY_STORE = "JKS";

    public static PrivateKey getPrivateKey(InputStream is, String alias,
            String storePass, String keyPass) throws Exception {
        KeyStore ks = getKeyStore(is, storePass);
        PrivateKey key = (PrivateKey) ks.getKey(alias, keyPass.toCharArray());
        return key;
    }

    public static PrivateKey getPrivateKey(String keyStorePath, String alias,
            String storePass, String keyPass) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, storePass);
        PrivateKey key = (PrivateKey) ks.getKey(alias, keyPass.toCharArray());
        return key;
    }

    public static PublicKey getPublicKey(String keyStorePath, String alias,
            String storePass) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, storePass);
        PublicKey key = ks.getCertificate(alias).getPublicKey();
        return key;
    }

    public static KeyStore getKeyStore(InputStream is, String password)
            throws Exception {
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }
    
    public static KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
        FileInputStream is = new FileInputStream(keyStorePath);
        return getKeyStore(is, password);
    }

}
