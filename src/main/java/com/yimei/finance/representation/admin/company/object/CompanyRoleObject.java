package com.yimei.finance.representation.admin.company.object;

import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_company_role")
@Entity
@Data
@NoArgsConstructor
public class CompanyRoleObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int number;                            //角色编号
    private String role;                           //角色
    private String roleName;                       //角色名称

    public String getRoleName(String role) {
        return EnumCompanyRole.valueOf(role).name;
    }
}
