package com.hellocrypto.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

public abstract class BaseAction implements SessionAware, RequestAware {

    protected Map<String, Object> session;
    protected Map<String, Object> request;

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;

    }

    @Override
    public void setRequest(Map<String, Object> request) {
        this.request = request;

    }

    public String execute() throws Exception {
        return "success";
    }

}
