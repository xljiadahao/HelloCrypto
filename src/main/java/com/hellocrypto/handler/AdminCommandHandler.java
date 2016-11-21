package com.hellocrypto.handler;

import com.hellocrypto.bo.LuckyDrawBo;
import com.hellocrypto.cache.LuckyDrawResult;
import com.hellocrypto.dao.CertificateDao;
import com.hellocrypto.entity.Certificate;
import com.hellocrypto.handler.validator.AdminCommandValidator;
import com.hellocrypto.utils.ByteUtil;
import com.hellocrypto.utils.crypto.RSA;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
                List<Certificate> certificates = certificateDao.findAll();
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
