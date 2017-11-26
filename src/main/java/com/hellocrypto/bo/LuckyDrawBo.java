package com.hellocrypto.bo;

import java.util.List;

/**
 *
 * @author xulei
 */
public class LuckyDrawBo {

    private Boolean isSuccess;
    private Integer luckydrawNum;
    private List<String> names;
    private List<String> resultList;
    private String timestamp;
    private String orgName;
    private String activityName;
    private String description;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getLuckydrawNum() {
        return luckydrawNum;
    }

    public void setLuckydrawNum(Integer luckydrawNum) {
        this.luckydrawNum = luckydrawNum;
    }
    
    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
