package com.yimei.domain.common;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by fanjun on 14-11-5.
 */
public class Phonevalidator implements Serializable {

    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiretime;
    private String phone;
    private String code;
    private int userid;
    private String contextjson;
    private LocalDateTime validatetime;
    private boolean validated;
    private ValidateType validatetype;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createtime;                 //发送短信验证码的时间
    private String ip;                                //请求验证码IP地址

    public Phonevalidator(){

    }

    public Phonevalidator(LocalDateTime expiretime, String phone, String code, int userid, String contextjson,
                          boolean validated, ValidateType validatetype){

        this.expiretime = expiretime;
        this.phone= phone;
        this.code = code;
        this.userid = userid;
        this.contextjson = contextjson;
        this.validated = validated;
        this.validatetype =validatetype;
    }

    public Phonevalidator(int userid, String phone, String code, LocalDateTime expiretime, ValidateType validatetype, boolean validated, String ip) {
        this.userid = userid;
        this.phone = phone;
        this.code = code;
        this.expiretime = expiretime;
        this.validatetype = validatetype;
        this.validated = validated;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getContextjson() {
        return contextjson;
    }

    public void setContextjson(String contextjson) {
        this.contextjson = contextjson;
    }

	public LocalDateTime getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(LocalDateTime expiretime) {
		this.expiretime = expiretime;
	}

	public LocalDateTime getValidatetime() {
		return validatetime;
	}

	public void setValidatetime(LocalDateTime validatetime) {
		this.validatetime = validatetime;
	}

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public ValidateType getValidatetype() {
        return validatetype;
    }

    public void setValidatetype(ValidateType validatetype) {
        this.validatetype = validatetype;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
