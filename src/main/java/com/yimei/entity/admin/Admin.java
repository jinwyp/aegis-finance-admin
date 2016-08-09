package com.yimei.entity.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by joe on 11/4/14.
 */
//@Entity
//@Table(name = "finance_admins")
//@PrimaryKeyJoinColumn(name = "id")
@Data
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

}
