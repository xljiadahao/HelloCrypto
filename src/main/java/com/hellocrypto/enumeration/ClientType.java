package com.hellocrypto.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * ClientType enumeration defines the certificate for the joined client 
 * under the specific group for org or individual for ad-hoc luck draw
 * 
 * @since Nov/15/2017
 * @author xulei
 */
public enum ClientType {
    
    GROUP("group"),
    
    INDIVIDUAL("individual");
    
    private String persistClientType;
    
    private ClientType(String persistClientType) {
        this.persistClientType = persistClientType;
    }

    public String getPersistClientType() {
        return persistClientType;
    }

    public void setPersistClientType(String persistClientType) {
        this.persistClientType = persistClientType;
    }
    
    public static ClientType getEnumByPersistClientType(String persistClientType) {
        if (StringUtils.isNotBlank(persistClientType)) {
            try {
                return ClientType.valueOf(persistClientType.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

}
