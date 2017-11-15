package com.hellocrypto.test.handler;

import com.hellocrypto.bo.LuckyDrawBo;
import com.hellocrypto.dao.CertificateDaoImpl;
import com.hellocrypto.entity.Certificate;
import com.hellocrypto.enumeration.ClientType;
import com.hellocrypto.handler.AdminCommandHandler;
import com.hellocrypto.handler.validator.AdminCommandValidator;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.CertificateUtil;
import com.hellocrypto.utils.crypto.MD5;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

/**
 * Mockito with JUnit test
 * 
 * @author xulei
 */
public class AdminCommandHandlerTest {
    
    private static final String CERTIFICATE_PATH = "src/test/resources/testrsa.crt";
    
    private static AdminCommandHandler adminCommandHandler;
    
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
        when(mockCertificateDao.findCertificatesByType(Mockito.any(ClientType.class))).thenReturn(certificates);
        
        adminCommandHandler = new AdminCommandHandler(new AdminCommandValidator(), mockCertificateDao);
    }
    
    @Test
    public void testHandleLuckyDrawReq() throws Exception {
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("luckDrawNum", "2");
        List<String> luckDrawText = new ArrayList<String>();
        luckDrawText.add("Luck Draw by Mockito");
        luckDrawText.add("Luck Draw by JUnit");
        requestParams.put("luckDrawText", luckDrawText);
        requestParams.put("auth", "xulei");
        LuckyDrawBo luckyDrawBo = adminCommandHandler.handleLuckyDrawReq(requestParams);
        System.out.println("Is Success: " + luckyDrawBo.getIsSuccess() + " - Lucky Draw Num: " + luckyDrawBo.getLuckydrawNum() 
                + " - Description: " + luckyDrawBo.getDescription() + " - Timestamp: " + luckyDrawBo.getTimestamp() 
                + " - Name Size: " + luckyDrawBo.getNames().size() + " - Result Size: " + luckyDrawBo.getResultList().size());
        assertTrue(luckyDrawBo.getIsSuccess());
        assertEquals(new Integer(2), luckyDrawBo.getLuckydrawNum());
        assertEquals(2, luckyDrawBo.getNames().size());
        assertEquals(2, luckyDrawBo.getResultList().size());
    }
    
    @Test
    public void testHandleLuckyDrawReq_illegal() throws Exception {
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("luckDrawNum", "2");
        List<String> luckDrawText = new ArrayList<String>();
        luckDrawText.add("Luck Draw by Mockito");
        requestParams.put("luckDrawText", luckDrawText);
        requestParams.put("auth", "xulei");
        LuckyDrawBo luckyDrawBo = adminCommandHandler.handleLuckyDrawReq(requestParams);
        System.out.println("Is Success: " + luckyDrawBo.getIsSuccess() + " - Lucky Draw Num: " + luckyDrawBo.getLuckydrawNum() 
                + " - Description: " + luckyDrawBo.getDescription() + " - Timestamp: " + luckyDrawBo.getTimestamp());
        assertFalse(luckyDrawBo.getIsSuccess());
    }

}
