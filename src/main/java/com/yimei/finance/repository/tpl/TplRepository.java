package com.yimei.finance.repository.tpl;

import com.yimei.finance.entity.tpl.Tpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TplRepository extends JpaRepository<Tpl, Long> {

    Tpl findById(Long id);
}
