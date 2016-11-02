package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyFBRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyFBRelationShipRepository extends JpaRepository<CompanyFBRelationShip, Long> {

//    @Query(" select c.fundCompanyId from CompanyFBRelationShip c where c.BusinessCompanyId = ?1 ")
    List<Long> findFundCompanyIdByBusinessCompanyId(Long BusinessCompanyId);

    CompanyFBRelationShip findByBusinessCompanyIdAndFundCompanyId(Long businessCompanyId, Long fundCompanyId);

}
