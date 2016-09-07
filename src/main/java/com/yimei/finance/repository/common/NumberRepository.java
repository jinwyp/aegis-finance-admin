package com.yimei.finance.repository.common;

import com.yimei.finance.representation.common.number.Number;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NumberRepository extends JpaRepository<Number, Long> {

    List<Number> findByTypeAndCreateDateAndIdLessThan(@Param("type") String type,
                                                      @Param("createDate") LocalDate LocalDate,
                                                      @Param("id") Long id);

}
