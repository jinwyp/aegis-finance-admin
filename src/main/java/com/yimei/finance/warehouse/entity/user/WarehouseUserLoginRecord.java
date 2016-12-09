package com.yimei.finance.warehouse.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "warehouse_user_login_log")
@Entity
@Data
@NoArgsConstructor
public class WarehouseUserLoginRecord implements Serializable {
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
