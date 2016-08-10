package com.yimei.finance.repository.tpl;

import com.yimei.finance.entity.tpl.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wangqi on 16/8/10.
 */
public interface JpaRepositoryDemo extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByNameAndAge(String name, Long age);

    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

    @Query("delete from User")
    @Modifying(clearAutomatically = true)
    void deleteAll();
}
