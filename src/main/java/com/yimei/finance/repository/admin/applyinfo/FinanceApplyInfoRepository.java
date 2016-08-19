package com.yimei.finance.repository.admin.applyinfo;

import com.yimei.finance.entity.admin.finance.FinanceApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhangbolun on 16/8/16.
 */
public interface FinanceApplyInfoRepository extends JpaRepository<FinanceApplyInfo, Long> {
//    @Query("select a from ApplyInfo a where a.id=:id")
//    FinanceApplyInfo findById(@Param("id") Long id);

     List<FinanceApplyInfo> findByUserId(int userId);

//    @Query("select applyType from ApplyInfo a where a.id=:id")
//    String findTypeById(@Param("id")Long id);

//    @Modifying(clearAutomatically = true)
//    @Query(" update ApplyInfo applyInfo set applyInfo.applyType=:applyType where applyInfo.id=:id")
//    void updateApplyInfoType(@Param("id") Integer id,
//                             @Param("applyType") String applyType);
}
