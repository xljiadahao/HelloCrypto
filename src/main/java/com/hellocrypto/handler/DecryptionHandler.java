package com.hellocrypto.handler;

import com.hellocrypto.bo.KeystoreBo;
import com.hellocrypto.bo.SecureInfoBo;
import com.hellocrypto.constant.ResponseMessage;
import com.hellocrypto.dao.CertificateDao;
import com.hellocrypto.enumeration.ClientType;
import com.hellocrypto.handler.validator.DecryptionValidator;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.KeystoreUtil;
import com.hellocrypto.utils.crypto.RSA;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author leixu2
 */
public class DecryptionHandler {
    
    private static final Logger logger = Logger.getLogger(DecryptionHandler.class);
    
    @Autowired
    private CertificateDao certificateDao;
    @Autowired
    private DecryptionValidator decryptionValidator;
    
    public List<String> preDecryptGetParticipateName() {
        return certificateDao.findNames(ClientType.INDIVIDUAL);
    }
    
    public SecureInfoBo getSecureInfo(KeystoreBo keystoreBo) {
        SecureInfoBo secureInfoBo = new SecureInfoBo();
        String secureInfo = null;
        Boolean isDecryptSuccess = false;
        if (decryptionValidator.validate(keystoreBo)) {
            InputStream is = null;
            try {
                // 1. retrive private key from keystore file
                is = new FileInputStream(keystoreBo.getFile());
                PrivateKey prk = KeystoreUtil.getPrivateKey(is, keystoreBo.getAlias(), 
                        keystoreBo.getStorepass(), keystoreBo.getKeypass());
                // 2. decrypt
                secureInfo = new String(RSA.decrypt(
                        ByteUtil.parseHexStr2Byte(keystoreBo.getEncrypt()), prk));
                isDecryptSuccess = true;
                logger.info("got secure info successfully: " + secureInfo);
            } catch (FileNotFoundException ex) {
                logger.error("decrypt error, " + ex.getMessage());
                secureInfo = "decrypt error, " + ex.getMessage();
                isDecryptSuccess = false;
            } catch (Exception ex) {
                logger.error("decrypt error, " + ex.getMessage());
                secureInfo = "decrypt error, " + ex.getMessage();
                isDecryptSuccess = false;
            } finally {
                try {
                    if(is != null) {
                        is.close();
                    }
                } catch (IOException ex) {
                    logger.error("close io exception, " + ex.getMessage());
                }
            }
        } else {
            secureInfo = "Oops, sorry, invalid input";
            isDecryptSuccess = false;
        }
        secureInfoBo.setDecryptedSecureInfo(secureInfo);
        secureInfoBo.setDecryptSuccess(isDecryptSuccess);
        return secureInfoBo;
    }
    
    public String getSecureInfo(Map<String, Object> requestBody) {
        String privateKey = (String) requestBody.get("secureKey");
        String encryptMessage = (String) requestBody.get("encryptMessage");
        if (decryptionValidator.validate(privateKey, encryptMessage)) {
            try {
                PrivateKey key = RSA.loadPrivateKeyByRawBytes(ByteUtil.parseHexStr2Byte(privateKey));
                String secureInfo = new String(RSA.decrypt(
                        ByteUtil.parseHexStr2Byte(encryptMessage), key), "UTF-8");
                logger.info("good decryption, secureInfo: " + secureInfo);
                return secureInfo;
            } catch (NoSuchAlgorithmException ex) {
                logger.error("org lucky draw, getSecureInfo NoSuchAlgorithmException, " + ex.getMessage());
                return ResponseMessage.BAD_DECRYPTION;
            } catch (InvalidKeySpecException ex) {
                logger.error("org lucky draw, getSecureInfo InvalidKeySpecException, " + ex.getMessage());
                return ResponseMessage.BAD_DECRYPTION;
            } catch (Exception ex) {
                logger.warn("bad decryption, " + ex.getMessage());
                return ResponseMessage.BAD_DECRYPTION;
            }
        } else {
            logger.error("org lucky draw, bad request");
            return ResponseMessage.BAD_DECRYPTION;
        }
    }

}
