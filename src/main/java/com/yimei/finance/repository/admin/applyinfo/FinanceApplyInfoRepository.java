package com.yimei.finance.repository.admin.applyinfo;

import com.yimei.finance.entity.admin.finance.FinanceApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangbolun on 16/8/16.
 */
public interface FinanceApplyInfoRepository extends JpaRepository<FinanceApplyInfo, Long> {
//    @Query("select a from ApplyInfo a where a.id=:id")
//    FinanceApplyInfo findById(@Param("id") Long id);

     List<FinanceApplyInfo> findByUserId(int userId);

    FinanceApplyInfo findByIdAndUserId(@Param("id") Long id, @Param("userId")int userId);

//    @Query("select applyType from ApplyInfo a where a.id=:id")
//    String findTypeById(@Param("id")Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(" update FinanceApplyInfo applyInfo set applyInfo.applyType=:applyType where applyInfo.id=:id")
    void updateApplyInfoType(@Param("id") Long id,
                             @Param("applyType") String applyType);


}
