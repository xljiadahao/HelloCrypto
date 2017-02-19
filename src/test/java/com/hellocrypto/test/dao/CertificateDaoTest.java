package com.hellocrypto.test.dao;

import com.hellocrypto.dao.CertificateDaoImpl;
import com.hellocrypto.entity.Certificate;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.CertificateUtil;
import com.hellocrypto.utils.crypto.MD5;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * Mockito with JUnit test
 * 
 * @author xulei
 */
public class CertificateDaoTest {
    
    private static final String CERTIFICATE_PATH = "src/test/resources/testrsa.crt";
    
    private static CertificateDaoImpl mockCertificateDao;
    private static Certificate certificate1;
    private static Certificate certificate2;
    private static Certificate certificate3;
    
    @BeforeClass
    public static void setUp() throws Exception {
        // use the test certificate raw bytes as the mock data for certificate raw data
        FileInputStream bais = new FileInputStream(CERTIFICATE_PATH);
        String pubkRawHex = CertificateUtil.getPublicKeyHex(bais);
        byte[] cerRawBinary = ByteUtil.parseHexStr2Byte(pubkRawHex);
        
        mockCertificateDao = mock(CertificateDaoImpl.class);
        
        certificate1 = new Certificate();
        certificate1.setId(1);
        certificate1.setName("mock cer 1");
        String cer1Fingerprint = MD5.md5Base64(ByteUtil.parseByte2HexStr(ByteUtil.int2byte(1)));
        certificate1.setCertificateBinary(cerRawBinary);
        certificate1.setPubKeyFingerprint(cer1Fingerprint);
        certificate1.setTimestamp(new Timestamp(new Date().getTime()));
        
        certificate2 = new Certificate();
        certificate2.setId(2);
        certificate2.setName("mock cer 2");
        String cer2Fingerprint = MD5.md5Base64(ByteUtil.parseByte2HexStr(ByteUtil.int2byte(2)));
        certificate2.setCertificateBinary(cerRawBinary);
        certificate2.setPubKeyFingerprint(cer2Fingerprint);
        certificate2.setTimestamp(new Timestamp(new Date().getTime()));
        
        certificate3 = new Certificate();
        certificate3.setId(3);
        certificate3.setName("mock cer 3");
        String cer3Fingerprint = MD5.md5Base64(ByteUtil.parseByte2HexStr(ByteUtil.int2byte(3)));
        certificate3.setCertificateBinary(cerRawBinary);
        certificate3.setPubKeyFingerprint(cer3Fingerprint);
        certificate3.setTimestamp(new Timestamp(new Date().getTime()));
        
        List<Certificate> certificates = new ArrayList<Certificate>();
        certificates.add(certificate1);
        certificates.add(certificate2);
        certificates.add(certificate3);
        when(mockCertificateDao.findAll()).thenReturn(certificates);
        List<String> names = new ArrayList<String>();
        names.add(certificate1.getName());
        names.add(certificate2.getName());
        names.add(certificate3.getName());
        when(mockCertificateDao.findNames()).thenReturn(names);
    }
    
    @Test
    public void testFindAll() throws Exception {
        List<Certificate> certificates = mockCertificateDao.findAll();
        assertEquals(3, certificates.size());
        for (int i = 0; i < certificates.size(); i++) {
            Certificate cer = certificates.get(i);
            System.out.println("certificate: " + cer.getId() + "-" + cer.getName() + "-" + cer.getPubKeyFingerprint());
            assertEquals(new Integer(i+1), cer.getId());
            assertEquals("mock cer " + (i+1), cer.getName());
            assertEquals(MD5.md5Base64(ByteUtil.parseByte2HexStr(ByteUtil.int2byte(i+1))), 
                    cer.getPubKeyFingerprint());
        }
    }
    
    @Test
    public void testFindNames() throws Exception {
        List<String> names = mockCertificateDao.findNames();
        for (int i = 0; i < names.size(); i++) {
            assertEquals("mock cer " + (i+1), names.get(i));
        }
    }
    
}
