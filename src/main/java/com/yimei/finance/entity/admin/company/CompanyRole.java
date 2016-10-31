package com.yimei.finance.entity.admin.company;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_company_role")
@Entity
@Data
@NoArgsConstructor
public class CompanyRole extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private int number;                            //角色编号

    @Column(name = "name")
    private String role;                           //角色名称

}
