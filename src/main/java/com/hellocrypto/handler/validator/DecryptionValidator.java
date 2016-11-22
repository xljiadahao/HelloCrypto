package com.hellocrypto.handler.validator;

import com.hellocrypto.bo.KeystoreBo;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author leixu2
 */
public class DecryptionValidator {
    
    private static final Logger logger = Logger.getLogger(DecryptionValidator.class);
    
    public boolean validate(KeystoreBo keystoreBo) {
        if (keystoreBo.getFile() == null 
                || keystoreBo.getEncrypt() == null || "".equals(keystoreBo.getEncrypt()) 
                || keystoreBo.getAlias() == null || "".equals(keystoreBo.getAlias())
                || keystoreBo.getStorepass() == null || "".equals(keystoreBo.getStorepass())
                || keystoreBo.getKeypass()== null || "".equals(keystoreBo.getKeypass())) {
            logger.error("bad request, encrypt: " + keystoreBo.getEncrypt() + ", alias: " + keystoreBo.getAlias()
                    + ", file: " + (keystoreBo.getFile() == null ? "null" : keystoreBo.getFile().getAbsolutePath()));
            return false;
        }
        return true;
    }
    
}
