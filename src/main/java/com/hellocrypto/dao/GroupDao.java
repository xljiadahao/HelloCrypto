package com.hellocrypto.dao;

import com.hellocrypto.entity.Group;

/**
 *
 * @author xulei
 */
public interface GroupDao {
    
    public Group findByGroupId(String groupIdentifier, boolean loadCert);
    public void registerGroup(Group group);
    public void updateGroup(Group group);
    
}
