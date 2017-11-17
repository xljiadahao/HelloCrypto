package com.hellocrypto.rest;

import com.hellocrypto.bo.GroupParticipationBo;
import com.hellocrypto.constant.ResponseMessage;
import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.CertificateHandler;
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

}
