package com.yimei.api.admin.representations;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by joe on 11/4/14.
 */
public class Admin implements Serializable {
    private int id;                         //id
    private String userName;                //账号
    private String password;                //密码
    private String name;                    //姓名
    private String phone;                   //电话号码
    private String jobNum;                  //工号
    private boolean active;                 //是否激活，可用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;       //创建时间

    public Admin(){}

    public Admin(int id, String userName, String name, String phone, String jobNum) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.jobNum = jobNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", jobNum='" + jobNum + '\'' +
                ", active=" + active +
                ", createTime=" + createTime +
                '}';
    }
}
