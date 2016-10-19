package com.yimei.finance.entity.site.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    //会员新增信息
    private int companyId;                    //所属公司Id
    private boolean master;                   //是否为主账户

    public User(int id, String securephone) {
        this.id = id;
        this.securephone = securephone;
    }

}
