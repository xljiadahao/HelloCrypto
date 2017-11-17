package com.hellocrypto.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author xulei
 */
@Entity
@Table(name="`group`")
public class Group implements Serializable {
    
    @Id
    @Column(name = "IDENTIFIER")
    private String identifier;
    @Basic
    @Column(name="ORG_NAME")
    private String orgName;
    @Basic
    @Column(name="ACTIVITY_NAME")
    private String activityName;
    @Basic
    @Column(name="MAX_COUNT")
    private Integer maxCount;
    @Basic
    @Column(name="IS_ACTIVATED")
    private Boolean isActivated;
    @Basic
    @Column(name="TIMESTAMP")
    private Timestamp timestamp;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Collection<Certificate> certificates;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Collection<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Collection<Certificate> certificates) {
        this.certificates = certificates;
    }
    
    @Override
    public String toString() {
        return "Group{" + "identifier=" + identifier + ", orgName=" + orgName 
                + ", activityName=" + activityName + ", maxCount=" + maxCount 
                + ", isActivated=" + isActivated + ", timestamp=" + timestamp + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.identifier);
        hash = 79 * hash + Objects.hashCode(this.orgName);
        hash = 79 * hash + Objects.hashCode(this.activityName);
        hash = 79 * hash + Objects.hashCode(this.maxCount);
        hash = 79 * hash + Objects.hashCode(this.isActivated);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.activityName, other.activityName)) {
            return false;
        }
        if (!Objects.equals(this.maxCount, other.maxCount)) {
            return false;
        }
        if (!Objects.equals(this.isActivated, other.isActivated)) {
            return false;
        }
        return true;
    }

}
