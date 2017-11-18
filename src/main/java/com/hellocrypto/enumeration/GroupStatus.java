package com.hellocrypto.enumeration;

/**
 *
 * @author xulei
 */
public enum GroupStatus {
    
    ACTIVE(true),
    
    IN_ACTIVE(false);
    
    private Boolean persistFlag;
    
    private GroupStatus(Boolean persistFlag) {
        this.persistFlag = persistFlag;
    }

    public Boolean getPersistFlag() {
        return persistFlag;
    }

}
