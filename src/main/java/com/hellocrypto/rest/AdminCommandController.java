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
        logger.info("triggered lucky draw");
        // List<String> text = (List<String>)requestBody.get("luckDrawText");
        // String num = (String)requestBody.get("luckDrawNum");
        return adminCommandHandler.handleLuckyDrawReq(requestBody);
    }
    
}
