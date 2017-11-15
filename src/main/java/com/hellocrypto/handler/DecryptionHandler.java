package com.hellocrypto.handler;

import com.hellocrypto.bo.KeystoreBo;
import com.hellocrypto.bo.SecureInfoBo;
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
import java.security.PrivateKey;
import java.util.List;
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
    
}
