package com.yimei.finance.admin.repository.company;

import com.yimei.finance.entity.admin.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AdminCompanyRepository extends JpaRepository<Company, Long> {
    Company findByIdAndStatusId(@Param("id") Long id,
                                @Param("statusId") int statusId);

    Company findByName(@Param("name")String name);
}
