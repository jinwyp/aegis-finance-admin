package com.yimei.finance.repository.admin.user;

import com.yimei.finance.entity.admin.user.UserLoginRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AdminUserLoginRecordRepository extends JpaRepository<UserLoginRecord, Long> {

    UserLoginRecord findTopByUserIdOrderByCreateTimeDesc(String userId);

    @Query(value = " select u.createTime from UserLoginRecord u where u.userId=:userId order by u.id desc ")
    Date findTopCreateTimeByUserId(@Param("userId") String userId);
}
