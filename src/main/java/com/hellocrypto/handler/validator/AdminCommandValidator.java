package com.hellocrypto.handler.validator;

import com.hellocrypto.constant.GeneralConstant;
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
    
    public boolean validateStartLuckyDrawReq(Map<String, Object> requestParams, String securityContext) {
        String groupIdentifier = (String) requestParams.get("groupIdentifier");
        String luckDrawNum = (String) requestParams.get("luckDrawNum");
        List<String> luckDrawText = (List<String>) requestParams.get("luckDrawText");
        // String auth = (String) requestParams.get("auth");
        try {
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
            // check group id
            Group group = null;
            if (StringUtils.isNotBlank(groupIdentifier)) {
                group = existGroupValidate(groupIdentifier);
                if (group == null) {
                    logger.error("bad request, invalid group identifier");
                    return false;
                }
            }
            // auth
            if (group != null) {
                if (!adminAuthentication(group, securityContext)) {
                    logger.error("bad request, invalid auth, securityContext: " + securityContext);
                    return false;
                }
            } else {
                if (!GeneralConstant.AUTH.equals(securityContext)) {
                    logger.error("bad request, invalid auth, securityContext: " + securityContext);
                    return false;
                }
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
    
    public Group validateChangeGroupStatusReq(String groupIdentifier, String groupStatus, String securityContext) {
        try {
            GroupStatus.valueOf(groupStatus);
        } catch (Exception ex) {
            logger.error("bad request, invalid groupStatus, " + ex.getMessage());
            return null;
        }
        Group group = existGroupValidate(groupIdentifier);
        if (adminAuthentication(group, securityContext)) {
            return group;
        } else {
            logger.error("bad request, change groupStatus, invalid auth");
            return null;
        }
    }
    
    private Group existGroupValidate(String groupIdentifier) {
        if (StringUtils.isBlank(groupIdentifier)) {
            return null;
        }
        return groupDao.findByGroupId(groupIdentifier, false);
    }
    
    private boolean adminAuthentication(Group group, String securityContex) {
        try {
            long createTime = Long.parseLong(securityContex);
            createTime = createTime/1000*1000;
            if (createTime == group.getTimestamp().getTime()) {
                return true;
            } else {
                return false;
            }
        } catch(Exception ex) {
            logger.error("bad request, invalid securityContex, " + ex.getMessage());
            return false;
        }
    }
    
}
