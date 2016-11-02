package com.yimei.finance.repository.admin.company;

import com.yimei.finance.entity.admin.company.CompanyFBRelationShip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyFBRelationShipRepository extends JpaRepository<CompanyFBRelationShip, Long> {
    List<Long> findFundCompanyIdByBusinessCompanyId(Long companyId);

    CompanyFBRelationShip findByBusinessCompanyIdAndFundCompanyId(Long businessCompanyId, Long fundCompanyId);

}
