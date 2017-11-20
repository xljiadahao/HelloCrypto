package com.hellocrypto.action;

import com.hellocrypto.bo.KeystoreBo;
import com.hellocrypto.bo.SecureInfoBo;
import com.hellocrypto.cache.LuckyDrawResult;
import com.hellocrypto.constant.GeneralConstant;
import com.hellocrypto.handler.DecryptionHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author leixu2
 */
public class DecryptionController extends BaseAction {

    // private static final Logger logger = Logger.getLogger(DecryptionController.class);
    @Autowired
    private DecryptionHandler decryptionHandler;

    // input
    private String encrypt;
    private String alias;
    private String storepass;
    private String keypass;
    // structs2 upload to the tmp folder and store as a tmp file
    private File file;
    private String fileFileName;
    private String fileContentType;

    // output
    private String decryptedSecureInfo;
    private Boolean decryptSuccess;
    // pre-prepared data
    private List<String> participateNames = new ArrayList<String>();
    private List<String> secureInfo = new ArrayList<String>();

    @Override
    public String execute() {
        if (CollectionUtils.isEmpty(LuckyDrawResult.getLuckDrawResults(GeneralConstant.ADHOC_KEY))) {
            return "failure";
        }
        // prepare encrypted data
        secureInfo.addAll(LuckyDrawResult.getLuckDrawResults(GeneralConstant.ADHOC_KEY));
        // prepare participate names
        participateNames.addAll(decryptionHandler.preDecryptGetParticipateName());
        SecureInfoBo secureInfoBo = decryptionHandler.getSecureInfo(constructKeystoreBo());
        decryptedSecureInfo = secureInfoBo.getDecryptedSecureInfo();
        decryptSuccess = secureInfoBo.getDecryptSuccess();
        return "success";
    }

    private KeystoreBo constructKeystoreBo() {
        KeystoreBo keystoreBo = new KeystoreBo();
        keystoreBo.setEncrypt(encrypt);
        keystoreBo.setAlias(alias);
        keystoreBo.setStorepass(storepass);
        keystoreBo.setKeypass(keypass);
        keystoreBo.setFile(file);
        keystoreBo.setFileFileName(fileFileName);
        keystoreBo.setFileContentType(fileContentType);
        return keystoreBo;
    }

    public Boolean getDecryptSuccess() {
        return decryptSuccess;
    }

    public void setDecryptSuccess(Boolean decryptSuccess) {
        this.decryptSuccess = decryptSuccess;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStorepass() {
        return storepass;
    }

    public void setStorepass(String storepass) {
        this.storepass = storepass;
    }

    public String getKeypass() {
        return keypass;
    }

    public void setKeypass(String keypass) {
        this.keypass = keypass;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getDecryptedSecureInfo() {
        return decryptedSecureInfo;
    }

    public void setDecryptedSecureInfo(String decryptedSecureInfo) {
        this.decryptedSecureInfo = decryptedSecureInfo;
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
