package com.yimei.finance.tpl.repository;

import com.yimei.finance.tpl.entity.Tpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TplRepository extends JpaRepository<Tpl, Long> {

    Tpl findById(Long id);
}
