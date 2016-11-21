package com.hellocrypto.utils.crypto;

import com.hellocrypto.utils.ByteUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import sun.misc.BASE64Encoder;

/**
 *
 * @author leixu2
 */
public class CertificateUtil {
    
    public static PublicKey getPublicKey(InputStream is) throws CertificateException, FileNotFoundException {
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(is);
        return cert.getPublicKey();
    }
    
    public static PublicKey getPublicKey(String certificatePath) throws CertificateException, FileNotFoundException {
        // CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        // X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
        FileInputStream bais = new FileInputStream(certificatePath);
        return getPublicKey(bais);
    }
    
    public static String getPublicKeyHex(InputStream is) throws CertificateException, FileNotFoundException {
        return ByteUtil.parseByte2HexStr(getPublicKey(is).getEncoded());
    }

    public static String getPublicKeyHex(String certificatePath) throws CertificateException, FileNotFoundException {
        return ByteUtil.parseByte2HexStr(getPublicKey(certificatePath).getEncoded());
    }
    
    public static String getPublicKeyBase64(String certificatePath) throws CertificateException, FileNotFoundException {
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(getPublicKey(certificatePath).getEncoded());
    }
    
}
