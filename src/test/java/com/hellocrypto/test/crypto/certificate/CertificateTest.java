package com.hellocrypto.test.crypto.certificate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import sun.misc.BASE64Encoder;

/**
 *
 * @author leixu2
 */
public class CertificateTest {

    private static final String CERTIFICATE_PATH = "src/test/resources/testrsa.crt";
    
    public static void main(String[] args) throws CertificateException, FileNotFoundException {
        
        System.out.println("--------------------Certificate-----------------------");
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        FileInputStream bais = new FileInputStream(CERTIFICATE_PATH);
        X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
        System.out.println("Certificate Info: alg-" + cert.getSigAlgName() + ", type-" + cert.getType() 
                + ", issuer-" + cert.getIssuerDN().getName());
        PublicKey puk = cert.getPublicKey();
        RSAPublicKey rsaPubKey = (RSAPublicKey) puk;
        System.out.println("RSA Public Key Modules: " + rsaPubKey.getModulus());
        System.out.println("RSA Public Key Public Exponent: " + rsaPubKey.getPublicExponent());
        BASE64Encoder bse = new BASE64Encoder();
        System.out.println("pk:" + bse.encode(puk.getEncoded()));
        
    }

}
