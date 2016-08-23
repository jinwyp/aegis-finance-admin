package com.yimei.finance.repository.common;

import com.yimei.finance.entity.common.number.Number;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by liuxinjie on 16/8/23.
 */
public interface NumberRepository extends JpaRepository<Number, Long> {

    List<Number> findByTypeAndCreateDate(@Param("type") String type,
                                         @Param("createDate") LocalDate LocalDate);

}
