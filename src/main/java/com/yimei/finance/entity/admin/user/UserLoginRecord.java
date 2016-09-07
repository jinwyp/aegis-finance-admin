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
@AllArgsConstructor
public class UserLoginRecord implements Serializable {
    @Id
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键
    @NonNull
    @Column(name = "user_id", length = 30, nullable = false, updatable = false)
    private String userId;                                           //用户id
    @NonNull
    @Column(name = "username", length = 30, nullable = false, updatable = false)
    private String username;                                         //登陆账号
    @NonNull
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;                                         //创建时间
}
