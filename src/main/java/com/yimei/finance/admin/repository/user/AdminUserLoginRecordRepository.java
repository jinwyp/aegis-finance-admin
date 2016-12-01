package com.yimei.finance.admin.repository.user;

import com.yimei.finance.entity.admin.user.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserLoginRecordRepository extends JpaRepository<UserLoginRecord, Long> {

    UserLoginRecord findTopByUserIdOrderByCreateTimeDesc(String userId);

//    @Query(value = " select u.createTime from UserLoginRecord u where u.userId=:userId order by u.id desc ")
//    Date findTopCreateTimeByUserId(@Param("userId") String userId);
}
