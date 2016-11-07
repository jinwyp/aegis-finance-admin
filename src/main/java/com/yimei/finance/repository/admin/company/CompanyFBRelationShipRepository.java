package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyFBRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyFBRelationShipRepository extends JpaRepository<CompanyFBRelationShip, Long> {

    @Query(" select c.fundCompanyId from CompanyFBRelationShip c where c.businessCompanyId = ?1 ")
    List<Long> findFundCompanyIdByBusinessCompanyId(@Param("businessCompanyId") Long businessCompanyId);

    CompanyFBRelationShip findByBusinessCompanyIdAndFundCompanyId(Long businessCompanyId, Long fundCompanyId);

}
