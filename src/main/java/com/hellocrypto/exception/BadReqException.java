package com.hellocrypto.exception;

/**
 *
 * @author leixu2
 */
public class BadReqException extends Exception {

    private String message;
    
    public BadReqException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + ", " + super.getMessage();
    }
    
}
