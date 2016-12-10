package com.yimei.finance.entity.admin.company;

import com.yimei.finance.common.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "t_finance_company_role_relationship")
@Entity
@Data
@NoArgsConstructor
public class CompanyRoleRelationShip extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;                           //公司id

    @Column(name = "role_number")
    private int roleNumber;

    @Column(name = "role")
    private String role;

    public CompanyRoleRelationShip(Long companyId, int roleNumber, String role, LocalDateTime createTime, String createManId, LocalDateTime lastUpdateTime, String lastUpdateManId) {
        this.companyId = companyId;
        this.roleNumber = roleNumber;
        this.role = role;
        super.setCreateTime(createTime);
        super.setCreateManId(createManId);
        super.setLastUpdateTime(lastUpdateTime);
        super.setLastUpdateManId(lastUpdateManId);
    }
}
