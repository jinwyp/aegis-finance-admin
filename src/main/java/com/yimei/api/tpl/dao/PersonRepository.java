package com.yimei.api.tpl.dao;

import com.yimei.api.tpl.representations.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liuxinjie on 16/8/1.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Person findByPersonName(String personName);

}
