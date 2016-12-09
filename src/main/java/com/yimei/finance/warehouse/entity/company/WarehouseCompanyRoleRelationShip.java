package com.yimei.finance.warehouse.entity.company;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "warehouse_company_role_relationship")
@Entity
@Data
@NoArgsConstructor
public class WarehouseCompanyRoleRelationShip extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;                            //公司id

    @Column(name = "role_number")
    private int roleNumber;                            //角色编号
}
