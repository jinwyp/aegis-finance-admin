package com.yimei.finance.admin.repository.company;

import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminCompanyRoleRelationShipRepository extends JpaRepository<CompanyRoleRelationShip, Long> {

    /**
     * 根据 role_number 查询公司id
     */
    @Query(" select distinct(c.companyId) from CompanyRoleRelationShip c where c.roleNumber = ?1 order by c.companyId desc ")
    List<Long> findCompanyIdByRoleNumberOrderByCompanyIdDesc(@Param("roleNumber") int roleNumber);

    /**
     * 根据 公司id 查询 role 列表
     */
    @Query(" select c.role from CompanyRoleRelationShip c where c.companyId = ?1 order by c.roleNumber desc ")
    List<String> findRoleByCompanyId(@Param("companyId") Long companyId);

}
