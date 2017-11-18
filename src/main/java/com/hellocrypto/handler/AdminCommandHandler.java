package com.hellocrypto.handler;

import com.hellocrypto.bo.LuckyDrawBo;
import com.hellocrypto.cache.LuckyDrawResult;
import com.hellocrypto.dao.CertificateDao;
import com.hellocrypto.dao.GroupDao;
import com.hellocrypto.entity.Certificate;
import com.hellocrypto.entity.Group;
import com.hellocrypto.enumeration.ClientType;
import com.hellocrypto.enumeration.GroupStatus;
import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.validator.AdminCommandValidator;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.MD5;
import com.hellocrypto.utils.crypto.RSA;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author leixu2
 */
public class AdminCommandHandler {
    
    private static final Logger logger = Logger.getLogger(AdminCommandHandler.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private AdminCommandValidator adminCommandValidator;
    @Autowired
    private CertificateDao certificateDao;
    @Autowired
    private GroupDao groupDao;
    
    public AdminCommandHandler() {}
    
    public AdminCommandHandler(AdminCommandValidator adminCommandValidator, CertificateDao certificateDao) {
        this.adminCommandValidator = adminCommandValidator;
        this.certificateDao = certificateDao;
    }
    
    public LuckyDrawBo handleLuckyDrawReq(Map<String, Object> requestParams) {
        LuckyDrawBo luckyDrawBo = new LuckyDrawBo();
        try {
            if (adminCommandValidator.validateStartLuckyDrawReq(requestParams)) {
                Integer resultSize = Integer.parseInt((String)requestParams.get("luckDrawNum"));
                List<String> luckDrawText = (List<String>) requestParams.get("luckDrawText");
                logger.info("lucky draw result size: " + resultSize);
                luckyDrawBo.setLuckydrawNum(resultSize);
                for (String txt : luckDrawText) {
                    logger.info("lucky draw text: " + txt);
                }
                // lucky draw for ad-hoc individual user
                List<Certificate> certificates = certificateDao.findCertificatesByType(ClientType.INDIVIDUAL);
                if (certificates != null && (certificates.size() >= resultSize)) {
                    List<Certificate> drawCers = new ArrayList<Certificate>();
                    int[] index = getRandomIndex(certificates.size(), resultSize);
                    for (int n = 0; n < index.length; n++) {
                        Certificate cer = certificates.get(index[n]);
                        logger.info("luckdraw index: " + index[n] + ", certificate: " + cer.getName());
                        drawCers.add(cer);
                    }
                    // generate encryption result
                    Map<String, String> encryptionResults = generateEncryptionResults(drawCers, luckDrawText);
                    List<String> names = new ArrayList<String>();
                    List<String> encyptContents = new ArrayList<String>();
                    for (String luckyName : encryptionResults.keySet()) {
                        names.add(luckyName);
                        encyptContents.add(encryptionResults.get(luckyName));
                    }
                    // populate result in cache
                    LuckyDrawResult.setLuckDrawResults(encyptContents);
                    // populate the response
                    luckyDrawBo.setIsSuccess(Boolean.TRUE);
                    luckyDrawBo.setNames(names);
                    luckyDrawBo.setResultList(encyptContents);
                    luckyDrawBo.setDescription("lucky draw triggered successfully");
                } else {
                    luckyDrawBo.setIsSuccess(Boolean.FALSE);
                    luckyDrawBo.setDescription("bad request, invalid lucky draw size, total certificate number: " 
                            + (certificates == null ? "null" : certificates.size()));
                }
            } else {
                luckyDrawBo.setIsSuccess(Boolean.FALSE);
                luckyDrawBo.setDescription("bad request, invalid input or auth");
            }
        } catch (Exception ex) {
            logger.error("unexpected exception, " + ex.getMessage());
            luckyDrawBo.setIsSuccess(Boolean.FALSE);
            luckyDrawBo.setDescription("unexpected error, " + ex.getMessage());
        }
        luckyDrawBo.setTimestamp(sdf.format(new Date()));
        return luckyDrawBo;
    }
    
    public String generateGroupIdentifier(Map<String, Object> requestParams) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException, BadReqException {
        if (adminCommandValidator.validateGenGroupIdReq(requestParams)) {
            String orgName = (String) requestParams.get("orgName");
            String activityName = (String) requestParams.get("activityName");
            Integer maxCount = null;
            try{
                String maxCountStr = (String) requestParams.get("maxCount");
                if (StringUtils.isNumeric(maxCountStr)) {
                    maxCount = Integer.parseInt(maxCountStr);
                }
            } catch (Exception ex) {
                logger.error("unexpected exception, " + ex.getMessage());
            }
            // generate 5 characters Group Identifier   
            String groupIdentifier = null;
            // maximum re-try is 3 times
            int reGenTimes = 0;
            while (true) {
                groupIdentifier = groupIdentifierGenerator(orgName, activityName);
                if (checkIsValid(groupIdentifier)) {
                    logger.info("groupIdentifier successfully generated, id: " + groupIdentifier);
                    break;
                } else {
                    reGenTimes++;
                }
                if (reGenTimes >= 3) {
                    throw new RuntimeException("invalid groupIdentifier");
                }
            }
            // add Group record
            Group newRegisteredGroup = constructGroupEntity(groupIdentifier, orgName, activityName, maxCount);
            try{
                groupDao.registerGroup(newRegisteredGroup);
                return newRegisteredGroup.getIdentifier();
            } catch (Exception ex) {
                logger.error("group persistence error, " + ex.getMessage());
                throw new RuntimeException("group persistence error, " + ex.getMessage());
            }
        } else {
            throw new BadReqException("orgName and activityName validate failed");
        }
    }
    
    public Boolean changeGroupChannelStatus(Map<String, Object> requestBody) {
        String groupIdentifier = (String) requestBody.get("groupIdentifier");
        String groupStatus = (String) requestBody.get("groupStatus");
        Group group = adminCommandValidator.validateChangeGroupStatusReq(groupIdentifier, groupStatus);
        if (group != null) {
            group.setIsActivated(GroupStatus.valueOf(groupStatus).getPersistFlag());
            groupDao.updateGroup(group);
            return true;
        } else {
            return false;
        }
    }
    
    
    private Group constructGroupEntity(String groupIdentifier, String orgName, String activityName, Integer maxCount) {
        Group group = new Group();
        group.setIdentifier(groupIdentifier);
        group.setOrgName(orgName);
        group.setActivityName(activityName);
        if (maxCount != null) {
           group.setMaxCount(maxCount);
        }
        group.setIsActivated(Boolean.TRUE);
        group.setTimestamp(new Timestamp(new Date().getTime()));
        return group;
    }
    
    private boolean checkIsValid(String groupIdentifier) {
        boolean isValid = true;
        // 1. basic validation
        if (StringUtils.isBlank(groupIdentifier) || groupIdentifier.length() != 5) {
            logger.error("invalid groupIdentifier");
            isValid = false;
        }
        // 2. duplicated validation
        if (isValid) {
            Group group = groupDao.findByGroupId(groupIdentifier);
            if (group != null) {
                logger.error("duplicated groupIdentifier");
                isValid = false;
            }
        }
        return isValid;
    }
    
    /**
     * the algorithm to generate the Group Identifier 
     * orgName+activityName+timestamp, hash, then get the first 5 characters
     */
    private String groupIdentifierGenerator(String orgName, String activityName) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder hashContent = new StringBuilder();
        hashContent.append(orgName);
        hashContent.append(activityName);
        hashContent.append(new Date().getTime());
        String digest = MD5.md5Base64(hashContent.toString());
        if (StringUtils.isNotBlank(digest) && digest.length() >= 5) {
            return digest.substring(0, 5);
        } else {
            return null;
        }
    }
    
    // <name, encrypted text>
    private Map<String, String> generateEncryptionResults(List<Certificate> drawCers, List<String> luckDrawText) 
            throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        Map<String, String> encryptionResults = new HashMap<String, String>();
        for (int i = 0; i < drawCers.size(); i++) {
            String encryptClearText = luckDrawText.get(i);
            byte[] encryptPubKey = drawCers.get(i).getCertificateBinary();
            String encryptText = ByteUtil.parseByte2HexStr(
                    RSA.encrypt(encryptClearText, RSA.getPubKeyByRawBytes(encryptPubKey)));
            encryptionResults.put(drawCers.get(i).getName(), encryptText);
        }
        return encryptionResults;
    }
    
    private int[] getRandomIndex(int totalNum, int resultSize) {
        // get the size of resultSize random index
        int[] index = new int[resultSize];
        for (int n = 0; n < index.length; n++) {
            index[n] = -1;
        }
        Random random = new Random();
        // randomlization
        // index[0] = random.nextInt(totalNum);
        for (int i = 0; i < index.length; i++) {
            boolean isReGenRequired = true;
            while (isReGenRequired) {
                index[i] = random.nextInt(totalNum);
                isReGenRequired = false;
                // re-gen duplicated item
                for (int j = 0; j < i; j++) {
                    if (index[i] == index[j]) {
                        logger.debug("re-gen duplicated item required, index[" + i + "]=" + index[i] 
                                + ", index[" + j + "]=" + index[j]);
                        isReGenRequired = true;
                        break;
                    }
                }
            }
        }
        return index;
    }
    
}
