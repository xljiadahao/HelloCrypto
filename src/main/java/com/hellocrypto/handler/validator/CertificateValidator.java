package com.hellocrypto.handler.validator;

import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author leixu2
 */
public class CertificateValidator {
    
    private static final Logger logger = Logger.getLogger(CertificateValidator.class);

    public boolean validate(String name, File file) {
        if (name == null || "".equals(name.trim()) 
                || file == null || "".equals(file.getAbsolutePath())) {
            logger.error("bad request, name: " + name + ", file: "   
                    + (file == null ? "null" : (", " + file.getAbsolutePath())));
            return false;
        }
        return true;
    }
 
}
