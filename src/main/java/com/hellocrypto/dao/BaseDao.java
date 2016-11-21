package com.hellocrypto.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leixu2
 */
public abstract class BaseDao<T> {

    @PersistenceContext(unitName = "HelloCryptoPersistenceUnit")
    private EntityManager entityManager;
    
    private Class<T> entityName;
    
    public BaseDao(Class<T> entityName) {
        this.entityName = entityName;
        if(entityManager != null) {
            entityManager.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        }
    }
    
    public BaseDao() {}
    
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    public void create(T entity) {
        this.entityManager.persist(entity);
    }
    
    public T edit(T entity) {
        return this.entityManager.merge(entity);
    }
    
    public T findByPrimaryKey(Object primaryKey) {
        return this.entityManager.find(entityName, primaryKey);
    }
  
}
