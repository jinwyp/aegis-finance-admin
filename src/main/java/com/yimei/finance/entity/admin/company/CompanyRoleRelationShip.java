package com.yimei.finance.entity.admin.company;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_company_role_relationship")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CompanyRoleRelationShip extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    @NonNull
    private Long companyId;                           //公司id

    @Column(name = "role_number")
    @NonNull
    private int roleNumber;

    @Column(name = "role")
    @NonNull
    private String role;
}
