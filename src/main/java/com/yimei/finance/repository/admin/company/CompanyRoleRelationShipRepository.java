package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRoleRelationShipRepository extends JpaRepository<CompanyRoleRelationShip, Long> {

    /**
     * 根据 role_number 查询公司id
     */
    @Query(" select c.companyId from CompanyRoleRelationShip c where c.roleNumber = ?1 ")
    List<Long> findCompanyIdByRoleNumberOrderByCompanyIdDesc(int roleNumber);

    /**
     * 根据 公司id 查询 role 列表
     */
    @Query(" select c from CompanyRoleRelationShip c where c.companyId = ?1 order by c.is asc limit 1 ")
    CompanyRoleRelationShip findRoleByCompanyId(Long companyId);

}
