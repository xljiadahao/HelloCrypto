package com.hellocrypto.action;

import com.hellocrypto.cache.LuckyDrawResult;
import com.hellocrypto.handler.DecryptionHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author leixu2
 */
public class PreDecryptionController extends BaseAction {

    private static final Logger logger = Logger.getLogger(PreDecryptionController.class);

    @Autowired
    private DecryptionHandler decryptionHandler;

    // pre-prepared data
    private List<String> participateNames = new ArrayList<String>();
    private List<String> secureInfo = new ArrayList<String>();

    @Override
    public String execute() {
        if (LuckyDrawResult.getLuckDrawResults() == null || LuckyDrawResult.getLuckDrawResults().isEmpty()) {
            return "failure";
        }
        // prepare encrypted data
        secureInfo.addAll(LuckyDrawResult.getLuckDrawResults());
        // prepare participate names
        participateNames.addAll(decryptionHandler.preDecryptGetParticipateName());
        return "success";
    }

    public List<String> getParticipateNames() {
        return participateNames;
    }

    public void setParticipateNames(List<String> participateNames) {
        this.participateNames = participateNames;
    }

    public List<String> getSecureInfo() {
        return secureInfo;
    }

    public void setSecureInfo(List<String> secureInfo) {
        this.secureInfo = secureInfo;
    }

}
