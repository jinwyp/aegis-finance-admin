package com.yimei.finance.common.repository.databook;

import com.yimei.finance.common.entity.databook.DataBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataBookRepository extends JpaRepository<DataBook, Long> {

    DataBook findByTypeAndSequence(@Param("type") String type,
                                   @Param("sequence") int sequence);

    List<DataBook> findByType(String type);
}
