package com.yimei.finance.warehouse.entity.company;

import com.yimei.finance.common.entity.base.BaseEntity;
import com.yimei.finance.warehouse.representation.company.enums.EnumWarehouseAdminCompanyRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "warehouse_company")
@Entity
@Data
@NoArgsConstructor
public class WarehouseAdminCompany extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;                           //公司名称

    @Column(name = "status")
    public String status;                          //状态

    @Column(name = "status_id")
    private int statusId;                          //状态id

    @Column(name = "role_number")
    private int roleNumber;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role")
    private EnumWarehouseAdminCompanyRole role;

    @Column(name = "remarks")
    private String remarks;                        //备注

}
