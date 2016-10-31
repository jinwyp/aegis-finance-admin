package com.yimei.finance.entity.admin.company;

import com.yimei.finance.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
    private String businessCompanyId;                     //业务组织id

    @Column(name = "fund_company_id")
    private String fundCompanyId;                        //资金方公司id

}
