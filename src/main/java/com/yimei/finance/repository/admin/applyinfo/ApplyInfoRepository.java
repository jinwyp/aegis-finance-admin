package com.yimei.finance.repository.admin.applyinfo;

import com.yimei.finance.entity.admin.user.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhangbolun on 16/8/16.
 */
public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Integer> {

//    @Modifying(clearAutomatically = true)
//    @Query(" update ApplyInfo applyInfo set applyInfo.applyType=:applyType where applyInfo.id=:id")
//    void updateApplyInfoType(@Param("id") Integer id,
//                             @Param("applyType") String applyType);
}
