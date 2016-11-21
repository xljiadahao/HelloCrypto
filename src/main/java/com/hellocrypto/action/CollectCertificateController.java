package com.hellocrypto.action;

import com.hellocrypto.exception.BadReqException;
import com.hellocrypto.handler.CertificateHandler;
import java.io.File;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author xulei
 */
public class CollectCertificateController extends BaseAction {

    private static final Logger logger = Logger.getLogger(CollectCertificateController.class);
    
    @Autowired
    private CertificateHandler crtificateHandler;
    
    // input
    private String name;
    // structs2 upload to the tmp folder and store as a tmp file
    private File file;
    private String fileFileName;
    private String fileContentType;
    
    // output
    private String msg;

    @Override
    public String execute() throws Exception {
        // costruct input data, a. name, file
        logger.info("certificate req, name: " + name + ", fileFileName: " + fileFileName 
                + ", tmp file path: " + (file == null ? "null" : file.getAbsolutePath()));
        try {
            // InputStream is = new FileInputStream(file);
            crtificateHandler.handCertificateReq(name, file);
            msg = "Certificate upload successfully, thank you, good luck !";
        } catch (BadReqException ex) {
            msg = "Sorry, bad request, either you have uploaded the certificate already or wrong certificate !";
        } catch (Exception ex) {
            msg = "Sorry, server error, we are working on it !";
        }
        return "success";
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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

}
