package com.yimei.finance.common.repository;

import com.yimei.finance.entity.common.DataBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataBookRepository extends JpaRepository<DataBook, Long> {

    DataBook findByTypeAndSequence(@Param("type") String type,
                                   @Param("sequence") int sequence);

    List<DataBook> findByType(String type);
}
