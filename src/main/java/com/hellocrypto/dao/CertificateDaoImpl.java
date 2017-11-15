package com.hellocrypto.dao;

import com.hellocrypto.entity.Certificate;
import com.hellocrypto.enumeration.ClientType;
import com.hellocrypto.exception.BadReqException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author leixu2
 */
public class CertificateDaoImpl extends BaseDao<Certificate> implements CertificateDao {

    private static final Logger logger = Logger.getLogger(CertificateDaoImpl.class);
    
    public CertificateDaoImpl() {
        super(Certificate.class);
    }
    
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void addCertificate(Certificate certificate) throws BadReqException {
        try{
            this.create(certificate);
        } catch (ConstraintViolationException ex) {
            logger.error("dao exception, ConstraintViolationException, " + ex.getMessage());
            throw new BadReqException("dao exception, ConstraintViolationException, " + ex.getMessage());
        } catch (PersistenceException ex) {
            logger.error("dao exception, PersistenceException, " + ex.getMessage());
            throw new BadReqException("dao exception, PersistenceException, " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("dao unknown exception, " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Certificate> findAll() {
        CriteriaQuery query = this.getEntityManager().getCriteriaBuilder().createQuery();
        query.select(query.from(Certificate.class));
        return this.getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Certificate> findCertificatesByType(ClientType type) {
        TypedQuery<Certificate> queryCertificatesByType = this.getEntityManager()
                .createNamedQuery("Certificate.findCertificatesByType", Certificate.class);
        queryCertificatesByType.setParameter("type", type.getPersistClientType());
        return queryCertificatesByType.getResultList();
    }
    
    @Override
    public List<String> findNames() {
        return this.findNames(null);
    }

    @Override
    public List<String> findNames(ClientType type) {
        List<String> participants = new ArrayList<String>();
        List<Certificate> cers = type == null ? this.findAll() : this.findCertificatesByType(type);
        for (Certificate cer : cers) {
            if (cer != null && cer.getName() != null && !"".equals(cer.getName())) {
                participants.add(cer.getName());
            }
        }
        return participants;
    }

}
