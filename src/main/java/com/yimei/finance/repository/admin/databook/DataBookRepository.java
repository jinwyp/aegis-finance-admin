package com.yimei.finance.repository.admin.databook;

import com.yimei.finance.entity.common.databook.DataBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by liuxinjie on 16/8/21.
 */
public interface DataBookRepository extends JpaRepository<DataBook, Long> {

    DataBook findByTypeAndSequence(@Param("type") String type,
                                       @Param("sequence") int sequence);

    List<DataBook> findByType(String type);
}
