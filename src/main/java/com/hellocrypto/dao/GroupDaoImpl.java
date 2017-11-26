package com.hellocrypto.dao;

import com.hellocrypto.entity.Group;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

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
    @Transactional(Transactional.TxType.REQUIRED)
    public Group findByGroupId(String groupIdentifier, boolean loadCert) {
        Group group = this.findByPrimaryKey(groupIdentifier);
        if (loadCert) {
            // enforce lazy loading
            logger.info("enforce lazy loading");
            if (!CollectionUtils.isEmpty(group.getCertificates())) {
                logger.info("group: " + groupIdentifier 
                        + ", cert list size: " + group.getCertificates().size());
            }
        }
        return group;
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
