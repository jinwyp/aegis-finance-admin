package com.yimei.finance.warehouse.entity.user;

import com.yimei.finance.common.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "warehouse_user")
@Entity
@Data
@NoArgsConstructor
public class WarehouseAdminUser extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;                    //账号

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;                        //姓名

    @Column(name = "phone")
    private String phone;                       //手机号

    @Column(name = "email")
    private String email;                       //邮箱

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "company_name")
    private String companyName;                 //用户所在公司名称

    @Column(name = "company_role_name")
    private String companyRoleName;

    @Column(name = "role_number")
    private int roleNumber;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status")
    private String status;                      //状态

}
