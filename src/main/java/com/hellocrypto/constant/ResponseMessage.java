package com.hellocrypto.constant;

/**
 *
 * @author xulei
 */
public class ResponseMessage {

    /**
     * general response
     */
    public static final String BAD_REQ = "The request is invalid, please try it again";
    public static final String INTERNAL_SERVER_ERROR = "Oops, sorry, we got some problems, "
            + "our engineers are working on it, please come back later";
    
    /**
     * business logic response
     */
    public static final String BAD_DECRYPTION = "Oops, bad decryption, try next time!";
    
}
