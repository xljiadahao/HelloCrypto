package com.hellocrypto.test.crypto.rsa;


import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.KeystoreUtil;
import com.hellocrypto.utils.crypto.RSA;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * @author xulei
 * 
 */
public class RSATest {
    
    private static final String ENCRYPTED_DATA = "1212352E6692BE156D480A0AAE5A651B0ED11A975606F99ABBA"
            + "3509967387DBFFEEEEF11FDDA7F1232F554FB650ACF17AB6159B89A23C29684F78C70D9C1"
            + "DF63A46337DB830AE63839DBEF9D80430C9DB4E2062CEB87B8529D8082584CB56495D839E"
            + "0E549576C6AA3C91972AC0C43D0D1DD8468F3B880D7F8ED6DC113473979";
//    private static final String ENCRYPTED_DATA = "05CF837C99DD979438F1C5AAC6A9E010DE3F823251F323A04D9218"
//            + "94083169632730B6D9824124CA305E1CED91473427DFCDFC20A19D477EFB101644280522FC1CDDE224994216AF"
//            + "B456876CD07BBB3F1D2931E431CBF941A38D7291B51F52B42FE85D8EACD36282C02BD7C804E3139F594F1758E9"
//            + "702A63AF9BA2A49E506B99";
    private static final String KEYSTORE_PATH = "src/test/resources/testrsa.keystore";
    private static final String KEYSTORE_NEG_PATH = "src/test/resources/negtestrsa.keystore";
    private static final String TEST_CLEAR_DATA ="HELLO Crypto, Whoami";

    public static void main(String[] args) throws Exception {

        System.out.println("--------------------RSA-----------------------");
        PrivateKey prk = KeystoreUtil.getPrivateKey(KEYSTORE_PATH, "pypl", "password", "password");
        PublicKey puk = KeystoreUtil.getPublicKey(KEYSTORE_PATH, "pypl", "password");
        RSAPublicKey rsaPubKey = (RSAPublicKey) puk;
        System.out.println("RSA Public Key Modules: " + rsaPubKey.getModulus());
        System.out.println("RSA Public Key Encoded: " + ByteUtil.parseByte2HexStr(rsaPubKey.getEncoded()));
        System.out.println("RSA Public Key Public Exponent: " + rsaPubKey.getPublicExponent());
        System.out.println();
        
        System.out.println("-----------------RSA Raw Bytes Test--------------------");
        byte[] publicKeyRawBytes = rsaPubKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyRawBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        
        String en = ByteUtil.parseByte2HexStr(RSA.encrypt(TEST_CLEAR_DATA, publicKey));
        // String en = ByteUtil.parseByte2HexStr(RSA.encrypt(TEST_CLEAR_DATA, puk));
        System.out.println("Encypted data of the TEST_CLEAR_DATA with Hex: " + en);
        System.out.println("Decypted data for Encypted data of the TEST_CLEAR_DATA: " + 
                new String(RSA.decrypt(ByteUtil.parseHexStr2Byte(en), prk)));
        System.out.println("Decypted data for initial encypted data: " + 
                new String(RSA.decrypt(ByteUtil.parseHexStr2Byte(ENCRYPTED_DATA), prk)));
        System.out.println();

        System.out.println("-----------------RSA Neg Test--------------------");
        PrivateKey prkNeg = KeystoreUtil.getPrivateKey(KEYSTORE_NEG_PATH, "alias", "password", "password");
        System.out.println("Decypted data for initial encypted data: " + 
                new String(RSA.decrypt(ByteUtil.parseHexStr2Byte(ENCRYPTED_DATA), prkNeg)));

    }

}
