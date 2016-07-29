package com.yimei.domain.common;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by wangqi on 16/5/10.
 */
public class Employee implements Serializable {
    private int id;
    private String securephone;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createtime;
    private int companyId;
    private int status; // 0:正常 1:异常
    @NotNull(message = "员工密码不为空")
    private String password;
    private boolean reset;
    private int userid;

    public Employee() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeObject{" +
                "id=" + id +
                ", securephone='" + securephone + '\'' +
                ", name='" + name + '\'' +
                ", createtime=" + createtime +
                ", companyId=" + companyId +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", reset=" + reset +
                ", userid=" + userid +
                '}';
    }
}
