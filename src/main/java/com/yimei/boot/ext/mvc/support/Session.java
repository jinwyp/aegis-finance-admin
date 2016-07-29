package com.yimei.boot.ext.mvc.support;

import com.yimei.domain.common.Employee;
import com.yimei.domain.common.Phonevalidator;
import com.yimei.domain.common.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Session implements Serializable{

    public class CaptureData implements Serializable{
        public String phone;
        public String capturecode;

        public CaptureData(String phone, String capturecode) {
            this.phone = phone;
            this.capturecode = capturecode;
        }
    }
    protected User user;
    protected Phonevalidator phonevalidator;
    protected String picCode;
    protected Phonevalidator resetPasswdValidCode;
    protected String mailCode;  //邮箱验证码
    protected CaptureData captureData;
    private boolean tenderPrivilege;
    private int type;
    private Employee employee;
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getTenderPrivilege() {
        return tenderPrivilege;
    }

    public void setTenderPrivilege(boolean tenderPrivilege) {
        this.tenderPrivilege = tenderPrivilege;
    }

    public User  getUser() {
        return user ;
    }

    public boolean login(User user) {
        this.user = user;
        return true;
    }

    public boolean addPhonevalidator(Phonevalidator phonevalidator) {
        this.phonevalidator = phonevalidator;
        return true;
    }

    public boolean isLogined() {
        return this.user != null;
    }

    public void logout() {
        this.user = null;
        this.employee = null;

    }

    public Phonevalidator getPhonevalidator() {
        return phonevalidator;
    }

    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public Phonevalidator getResetPasswdValidCode() {
        return resetPasswdValidCode;
    }

    public void setResetPasswdValidCode(Phonevalidator resetPasswdValidCode) {
        this.resetPasswdValidCode = resetPasswdValidCode;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public CaptureData getCaptureData() {
        return captureData;
    }

    public void setCaptureData(CaptureData captureData) {
        this.captureData = captureData;
    }
}