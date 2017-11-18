package com.hellocrypto.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hellocrypto.bo.LuckyDrawBo;
import com.hellocrypto.constant.ResponseMessage;
import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.AdminCommandHandler;

/**
 *
 * @author leixu2
 */
@RestController
public class AdminCommandController {
    
    private static final Logger logger = Logger.getLogger(AdminCommandController.class);
    
    @Autowired
    private AdminCommandHandler adminCommandHandler;
    
    public AdminCommandController() {}
    
    @RequestMapping(value = "securedraw", method = RequestMethod.PUT)
    @ResponseBody
    public LuckyDrawBo triggerLuckyDraw(HttpServletRequest servletRequest, HttpServletResponse servletResponse, 
            @RequestBody Map<String, Object> requestBody) {
        logger.info("triggering ad-hoc lucky draw");
        // List<String> text = (List<String>)requestBody.get("luckDrawText");
        // String num = (String)requestBody.get("luckDrawNum");
        return adminCommandHandler.handleLuckyDrawReq(requestBody);
    }
    
    /**
     * request contains orgName, activityName, maxCount (optional)
     */
    @RequestMapping(value = "groupgen", method = RequestMethod.POST)
    @ResponseBody
    public String generateGroupIdentifier(HttpServletRequest servletRequest, HttpServletResponse servletResponse, 
            @RequestBody Map<String, Object> requestBody) {
        logger.info("generating group identifier for org lucky draw");
        try {
            return adminCommandHandler.generateGroupIdentifier(requestBody);
        } catch (BadReqException ex) {
            logger.error("BadReqException during group ID generating, " + ex.getMessage());
            throw new RuntimeException(ResponseMessage.BAD_REQ);
        } catch (Exception ex) {
            logger.error("unexpected error during group ID generating, " + ex.getMessage());
            throw new RuntimeException(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * request contains groupIdentifier, groupStatus (ACTIVE|IN_ACTIVE)
     */
    @RequestMapping(value = "groupchannel", method = RequestMethod.PUT)
    @ResponseBody
    public Boolean groupchannelControl(HttpServletRequest servletRequest, HttpServletResponse servletResponse, 
            @RequestBody Map<String, Object> requestBody) {
        logger.info("group channel status control");
        try {
            return adminCommandHandler.changeGroupChannelStatus(requestBody);
        } catch (Exception ex) {
            logger.error("unexpected error during changing group status, " + ex.getMessage());
            throw new RuntimeException(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }

}
