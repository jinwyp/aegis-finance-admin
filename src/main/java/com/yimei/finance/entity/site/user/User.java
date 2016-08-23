package com.yimei.finance.entity.site.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.String;
import java.time.LocalDateTime;

/**
 * Created by wangqi on 16/4/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public User(int id, String securephone) {
        this.id = id;
        this.securephone = securephone;
    }

}
