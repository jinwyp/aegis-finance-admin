package com.yimei.finance.entity.admin.user;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_finance_userlogin_log")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserLoginRecord implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;                                                 //主键

    @NonNull
    @Column(name = "user_id")
    private String userId;                                           //用户id

    @NonNull
    @Column(name = "username")
    private String username;                                         //登陆账号

    @NonNull
    @Column(name = "create_time")
    private Date createTime;                                         //创建时间
}
