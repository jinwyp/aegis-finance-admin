package com.yimei.finance.tpl.repository;

import com.yimei.finance.tpl.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wangqi on 16/8/10.
 */
public interface JpaRepositoryDemo extends JpaRepository<UserTest, Long> {
    UserTest findByName(String name);

    UserTest findByNameAndAge(String name, Long age);

    @Query("from UserTest u where u.name=:name")
    UserTest findUser(@Param("name") String name);

    @Query("delete from UserTest")
    @Modifying(clearAutomatically = true)
    void deleteAll();
}
