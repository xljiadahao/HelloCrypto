package com.hellocrypto.bo;

/**
 *
 * @author leixu2
 */
public class SecureInfoBo {

    private Boolean decryptSuccess;
    private String decryptedSecureInfo;

    public Boolean getDecryptSuccess() {
        return decryptSuccess;
    }

    public void setDecryptSuccess(Boolean decryptSuccess) {
        this.decryptSuccess = decryptSuccess;
    }

    public String getDecryptedSecureInfo() {
        return decryptedSecureInfo;
    }

    public void setDecryptedSecureInfo(String decryptedSecureInfo) {
        this.decryptedSecureInfo = decryptedSecureInfo;
    }

}
