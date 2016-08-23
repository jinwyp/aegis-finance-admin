package com.yimei.finance.entity.site.user;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.String;
import java.time.LocalDateTime;

/**
 * Created by wangqi on 16/4/14.
 */
public class User implements Serializable {

    private int id;
    @NotNull(message = "手机号码不为空")
    private String securephone;
    private String nickname;
    @NotNull(message = "用户名密码不为空")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registertime;
    private boolean isactive;
    private String verifystatus;
    private String qq;                        //QQ号
    private String telephone;                 //固定电话
    private LocalDateTime verifytime;
    private int clienttype;                   //客户端类型
    private String email;			          //email
    private LocalDateTime sendmailtime;
    private LocalDateTime verifymailtime;
    private String verifyuuid;		          //激活码
    private String userFrom;                  //用户来源
    private Integer traderid;                 //配置交易员id
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecurephone() {
        return securephone;
    }

    public void setSecurephone(String securephone) {
        this.securephone = securephone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistertime() {
        return registertime;
    }

    public void setRegistertime(LocalDateTime registertime) {
        this.registertime = registertime;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public String getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(String verifystatus) {
        this.verifystatus = verifystatus;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getVerifytime() {
        return verifytime;
    }

    public void setVerifytime(LocalDateTime verifytime) {
        this.verifytime = verifytime;
    }

    public int getClienttype() {
        return clienttype;
    }

    public void setClienttype(int clienttype) {
        this.clienttype = clienttype;
    }

    public LocalDateTime getSendmailtime() {
        return sendmailtime;
    }

    public void setSendmailtime(LocalDateTime sendmailtime) {
        this.sendmailtime = sendmailtime;
    }

    public LocalDateTime getVerifymailtime() {
        return verifymailtime;
    }

    public void setVerifymailtime(LocalDateTime verifymailtime) {
        this.verifymailtime = verifymailtime;
    }

    public String getVerifyuuid() {
        return verifyuuid;
    }

    public void setVerifyuuid(String verifyuuid) {
        this.verifyuuid = verifyuuid;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public Integer getTraderid() {
        return traderid;
    }

    public void setTraderid(Integer traderid) {
        this.traderid = traderid;
    }

    @Override
    public String toString() {
        return "User{" +
                "securephone='" + securephone + '\'' +
                ", isactive=" + isactive +
                ", registertime=" + registertime +
                ", verifystatus='" + verifystatus + '\'' +
                ", verifytime=" + verifytime +
                ", email='" + email + '\'' +
                '}';
    }
}
