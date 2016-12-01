package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCompanyRoleRepository extends JpaRepository<CompanyRole, Long> {
    /**
     * 根据 number 查询 CompanyRole
     */
    CompanyRole findByNumber(int number);

}
