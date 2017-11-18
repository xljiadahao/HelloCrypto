package com.hellocrypto.handler.validator;

import com.hellocrypto.cache.Constant;
import com.hellocrypto.dao.GroupDao;
import com.hellocrypto.entity.Group;
import com.hellocrypto.enumeration.GroupStatus;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author leixu2
 */
public class AdminCommandValidator {
    
    private static final Logger logger = Logger.getLogger(AdminCommandValidator.class);
    
    @Autowired
    private GroupDao groupDao;
    
    public boolean validateStartLuckyDrawReq(Map<String, Object> requestParams) {
        String luckDrawNum = (String) requestParams.get("luckDrawNum");
        List<String> luckDrawText = (List<String>) requestParams.get("luckDrawText");
        String auth = (String) requestParams.get("auth");
        try {
            // auth
            if (!Constant.AUTH.equals(auth)) {
                logger.error("bad request, invalid auth, auth: " + auth);
                return false;
            }
            // check input
            Integer resultNum = Integer.parseInt(luckDrawNum);
            if (luckDrawText != null) {
                if (resultNum <= 0 || resultNum != luckDrawText.size()) {
                    logger.error("bad request, luckDrawNum and luckDrawText size mismatch");
                    return false;
                }
            } else {
                logger.error("bad request, invalid input or auth, luckDrawText = null");
                return false;
            }
            return true;
        } catch (Exception ex) {
            logger.error("exception, " + ex.getMessage());
            throw ex;
        }
    }
    
    public boolean validateGenGroupIdReq(Map<String, Object> requestParams) {
        String orgName = (String) requestParams.get("orgName");
        String activityName = (String) requestParams.get("activityName");
        if (StringUtils.isBlank(orgName) || StringUtils.isBlank(activityName)) {
            logger.error("bad request, invalid input orgName or activityName");
            return false;
        }
        return true;
    }
    
    public Group validateChangeGroupStatusReq(String groupIdentifier, String groupStatus) {
        try {
            GroupStatus.valueOf(groupStatus);
        } catch (Exception ex) {
            logger.error("bad request, invalid groupStatus, " + ex.getMessage());
            return null;
        }
        return existGroupValidate(groupIdentifier);
    }
    
    private Group existGroupValidate(String groupIdentifier) {
        if (StringUtils.isBlank(groupIdentifier)) {
            return null;
        }
        return groupDao.findByGroupId(groupIdentifier);
    }
    
}
