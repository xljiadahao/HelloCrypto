package com.hellocrypto.handler.validator;

import com.hellocrypto.dao.GroupDao;
import com.hellocrypto.entity.Group;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author xulei
 */
public class CertificateValidator {
    
    private static final Logger logger = Logger.getLogger(CertificateValidator.class);

    @Autowired
    private GroupDao groupDao;
    
    public boolean validate(String name, File file) {
        if (name == null || "".equals(name.trim()) 
                || file == null || "".equals(file.getAbsolutePath())) {
            logger.error("bad request, name: " + name + ", file: "   
                    + (file == null ? "null" : (", " + file.getAbsolutePath())));
            return false;
        }
        return true;
    }

    public Group clientParticipateValidate(String name, String groupIdentifier) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(groupIdentifier)) {
            return null;
        }
        Group group = groupDao.findByGroupId(groupIdentifier, false);
        // group should be activated
        if (group == null || !group.getIsActivated()) {
            return null;
        }
        return group;
    }

}
