package com.yimei.finance.repository.admin.applyinfo;

import com.yimei.finance.entity.admin.user.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by zhangbolun on 16/8/16.
 */
public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Long> {
    @Query("select a from ApplyInfo a where a.id=:id")
    ApplyInfo findById(@Param("id") Long id);

    @Query("select applyType from ApplyInfo a where a.id=:id")
    String findTypeById(@Param("id")Long id);

//    @Modifying(clearAutomatically = true)
//    @Query(" update ApplyInfo applyInfo set applyInfo.applyType=:applyType where applyInfo.id=:id")
//    void updateApplyInfoType(@Param("id") Integer id,
//                             @Param("applyType") String applyType);
}
