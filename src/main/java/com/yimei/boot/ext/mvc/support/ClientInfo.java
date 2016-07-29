package com.yimei.boot.ext.mvc.support;

/**
 * Created by liuxinjie on 16/1/15.
 * 获取用户客户端信息
 */
public class ClientInfo {
    private String IP;                   //客户端IP地址
    private String userAgent;            //客户端硬件信息
    private String acceptLanguage;       //可接受的语言

    public ClientInfo() {
    }

    public ClientInfo(String IP, String userAgent, String acceptLanguage) {
        this.IP = IP;
        this.userAgent = userAgent;
        this.acceptLanguage = acceptLanguage;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }
}
