package com.hellocrypto.rest;

import com.hellocrypto.bo.GroupParticipationBo;
import com.hellocrypto.cache.LuckyDrawResult;
import com.hellocrypto.constant.GeneralConstant;
import com.hellocrypto.constant.ResponseMessage;
import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.CertificateHandler;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xulei
 */
@RestController
public class ClientParticipationController {
    
    private static final Logger logger = Logger.getLogger(ClientParticipationController.class);
    
    @Autowired
    private CertificateHandler crtificateHandler;
    
    public ClientParticipationController() {}
    
    @RequestMapping(value = "joingroup", method = RequestMethod.POST)
    @ResponseBody
    public GroupParticipationBo joinGroup(HttpServletRequest servletRequest, HttpServletResponse servletResponse, 
            @RequestBody Map<String, Object> requestBody) throws BadReqException {
        logger.info("client group participation");
        try {
            return crtificateHandler.handClientParticipationReq(requestBody);
        } catch (BadReqException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * request param, groupIdentifier
     */
    @RequestMapping(value = "orgdrawresult", method = RequestMethod.GET)
    @ResponseBody
    public List<String> fetchOrgDrawResult(HttpServletRequest servletRequest, HttpServletResponse servletResponse, 
            @RequestParam Map<String, String> allRequestParams) {
        String groupIdentifier = allRequestParams.get("groupIdentifier");
        // groupIdentifier: not blank, not adhoc key
        if (StringUtils.isNotBlank(groupIdentifier) && !GeneralConstant.ADHOC_KEY.equals(groupIdentifier)) {
            return LuckyDrawResult.getLuckDrawResults(groupIdentifier);
        } else {
            return null;
        }
    }
    
}
