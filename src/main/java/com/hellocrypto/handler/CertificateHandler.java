package com.hellocrypto.handler;

import com.hellocrypto.bo.GroupParticipationBo;
import com.hellocrypto.dao.CertificateDao;
import com.hellocrypto.entity.Certificate;
import com.hellocrypto.entity.Group;
import com.hellocrypto.enumeration.ClientType;
import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.validator.CertificateValidator;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.CertificateUtil;
import com.hellocrypto.utils.crypto.MD5;
import com.hellocrypto.utils.crypto.RSA;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import javax.persistence.RollbackException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

/**
 *
 * @author leixu2
 */
public class CertificateHandler {
    
    private static final Logger logger = Logger.getLogger(CertificateHandler.class);
    
    @Autowired
    private CertificateValidator certificateValidator;
    @Autowired
    private CertificateDao certificateDao;
    
    public void handCertificateReq(String name, File file) throws Exception {
        if (certificateValidator.validate(name, file)) {
            InputStream is = new FileInputStream(file);
            // String newCerName = "/certificatetmp/" + name + "-" + (new Date().getTime()) + ".crt";
            // OutputStream os = new FileOutputStream(new File(newCerName));
            // byte[] buffer = new byte[1024];
            // int length = 0;
            // while(-1 != (length = is.read(buffer, 0, buffer.length))) {
            //     os.write(buffer);
            // }
            // try {
            //    os.close();
            // } catch (IOException ex) {
            //     logger.warn("close stream IOException, " + ex.getMessage());
            // }
            // InputStream isNewCer = new FileInputStream(new File(newCerName));
            try {
                // 1. populate public key singature
                String pubkRawHex = CertificateUtil.getPublicKeyHex(is);
                String pubkFingerprint = MD5.md5Base64(pubkRawHex);
                byte[] cerRawBinary = ByteUtil.parseHexStr2Byte(pubkRawHex);
                // byte[] cerRawBinary = IOUtils.toByteArray(is);
                Certificate certificate = new Certificate();
                certificate.setName(name);
                certificate.setCertificateBinary(cerRawBinary);
                certificate.setPubKeyFingerprint(pubkFingerprint);
                certificate.setType(ClientType.INDIVIDUAL.getPersistClientType());
                certificate.setTimestamp(new Timestamp(new Date().getTime()));
                certificateDao.addCertificate(certificate);
            } catch (BadReqException ex) {
                throw ex;
            } catch (RollbackException ex) {
                logger.error("RollbackException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (TransactionSystemException ex) {
                logger.error("TransactionSystemException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (CertificateException ex) {
                logger.error("CertificateException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (FileNotFoundException ex) {
                logger.error("FileNotFoundException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (NoSuchAlgorithmException ex) {
                logger.error("NoSuchAlgorithmException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (UnsupportedEncodingException ex) {
                logger.error("UnsupportedEncodingException, " + ex.getMessage());
                throw new BadReqException(ex.getMessage());
            } catch (Exception ex) {
                logger.error("unknown server error, " + ex.getMessage());
                throw ex;
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.warn("close stream IOException, " + ex.getMessage());
                }
            }
        } else {
            throw new BadReqException("name or certificate is empty.");
        }
    }
    
    public GroupParticipationBo handClientParticipationReq(Map<String, Object> requestBody) 
            throws BadReqException, NoSuchAlgorithmException, IOException, Exception {
        String name = (String) requestBody.get("name");
        String groupIdentifier = (String) requestBody.get("groupIdentifier");
        Group group = certificateValidator.clientParticipateValidate(name, groupIdentifier);
        if (group != null) {
            // 1. generate the RSA keypair file
            KeyPair rsaKeyPair = RSA.generateKeyPair();
            PublicKey pbkey = rsaKeyPair.getPublic();
            // 2. prepare public key and its md5 digest
            String pubkRawHex = ByteUtil.parseByte2HexStr(pbkey.getEncoded());
            String pubkFingerprint = MD5.md5Base64(pubkRawHex);
            byte[] cerRawBinary = ByteUtil.parseHexStr2Byte(pubkRawHex);
            Certificate certificate = new Certificate();
            certificate.setName(name);
            certificate.setCertificateBinary(cerRawBinary);
            certificate.setPubKeyFingerprint(pubkFingerprint);
            certificate.setType(ClientType.GROUP.getPersistClientType());
            certificate.setGroup(group);
            certificate.setTimestamp(new Timestamp(new Date().getTime()));
            certificateDao.addCertificate(certificate);
            // 3. populate secure key for response
            PrivateKey prkey = rsaKeyPair.getPrivate();
            String prvkRawHex = ByteUtil.parseByte2HexStr(prkey.getEncoded());
            logger.info("secure key raw hex: " + prvkRawHex);
            // 4. populate the response
            GroupParticipationBo groupParticipationBo = new GroupParticipationBo();
            groupParticipationBo.setName(name);
            groupParticipationBo.setSecureKey(prvkRawHex);
            groupParticipationBo.setGroupIdentifier(group.getIdentifier());
            groupParticipationBo.setOrgName(group.getOrgName());
            groupParticipationBo.setActivityName(group.getActivityName());
            return groupParticipationBo;
        } else {
            throw new BadReqException("group not exist or inactive");
        }
    }
    
}
