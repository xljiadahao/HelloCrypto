package com.hellocrypto.dao;

import com.hellocrypto.entity.Group;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

/**
 *
 * @author xulei
 */
public class GroupDaoImpl extends BaseDao<Group> implements GroupDao {

    private static final Logger logger = Logger.getLogger(GroupDaoImpl.class);
    
    public GroupDaoImpl() {
        super(Group.class);
    }
    
    @Override
    public Group findByGroupId(String groupIdentifier) {
        return this.findByPrimaryKey(groupIdentifier);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void registerGroup(Group group) {
        this.create(group);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateGroup(Group group) {
        this.edit(group);
    }
    
}
