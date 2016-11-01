package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRoleRelationShipRepository extends JpaRepository<CompanyRoleRelationShip, Long> {

    /**
     * 根据 role_number 查询公司id
     */
    List<Long> findCompanyIdByRoleNumberOrderByCompanyIdDesc(@Param("role_number") int type);

    /**
     * 根据 公司id 查询 role 列表
     */
    List<String> findRoleByCompanyId(@Param("companyId") Long companyId);

}
