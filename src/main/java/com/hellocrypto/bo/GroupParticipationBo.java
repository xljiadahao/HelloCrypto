package com.hellocrypto.bo;

/**
 *
 * @author xulei
 */
public class GroupParticipationBo {
    
    private String name;
    private String secureKey;
    private String groupIdentifier;
    private String orgName;
    private String activityName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getGroupIdentifier() {
        return groupIdentifier;
    }

    public void setGroupIdentifier(String groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

}
