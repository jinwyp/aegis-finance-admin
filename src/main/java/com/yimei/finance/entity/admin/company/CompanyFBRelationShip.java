package com.yimei.finance.entity.admin.company;

import com.yimei.finance.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_finance_company_fb_relationship")
@Entity
@ApiModel(description = "资金-业务组织 关系")
@Data
@NoArgsConstructor
public class CompanyFBRelationShip extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "business_company_id")
    private Long businessCompanyId;                     //业务组织id

    @Column(name = "fund_company_id")
    private Long fundCompanyId;                         //资金方公司id

    public CompanyFBRelationShip(Long businessCompanyId, Long fundCompanyId, Date createTime, String createManId, Date lastUpdateTime, String lastUpdateManId) {
        this.businessCompanyId = businessCompanyId;
        this.fundCompanyId = fundCompanyId;
        super.setCreateTime(createTime);
        super.setCreateManId(createManId);
        super.setLastUpdateTime(lastUpdateTime);
        super.setLastUpdateManId(lastUpdateManId);
    }

}
