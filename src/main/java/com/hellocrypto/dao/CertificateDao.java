package com.hellocrypto.dao;

import com.hellocrypto.entity.Certificate;
import com.hellocrypto.enumeration.ClientType;
import java.util.List;

/**
 *
 * @author leixu2
 */
public interface CertificateDao {
    
    public void addCertificate(Certificate certificate) throws Exception;
    public List<Certificate> findAll();
    public List<Certificate> findCertificatesByType(ClientType type);
    public List<String> findNames();
    public List<String> findNames(ClientType type);
    
}
