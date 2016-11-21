package com.hellocrypto.dao;

import com.hellocrypto.entity.Certificate;
import java.util.List;

/**
 *
 * @author leixu2
 */
public interface CertificateDao {
    
    public void addCertificate(Certificate certificate) throws Exception;
    public List<Certificate> findAll();
    
}
