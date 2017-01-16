package com.yimei.finance.tpl.service;

import com.yimei.finance.tpl.entity.UserTest;
import com.yimei.finance.tpl.repository.JpaRepositoryDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangqi on 16/8/10.
 */
@Service
public class JpaRollbackDemo {
    @Autowired
    JpaRepositoryDemo jpaRepositoryDemo;

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public UserTest savePersonWithRollBack(UserTest person) {
        UserTest p = jpaRepositoryDemo.save(person);

        if (person.getName().equals("rollback")) {
            throw new IllegalArgumentException("rollback不能插入数据库,数据库将回滚");
        }
        return p;
    }

    @Transactional(noRollbackFor ={IllegalArgumentException.class})
    public UserTest savePersonWithoutRollBack(UserTest person) {
        UserTest p = jpaRepositoryDemo.save(person);

        if (person.getName().equals("norollback")) {
            throw new IllegalArgumentException("norollback虽然抛出异常,但是我并不会滚");
        }
        return p;
    }


}
