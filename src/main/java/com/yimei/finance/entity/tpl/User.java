package com.yimei.finance.entity.tpl;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by wangqi on 16/8/10.
 */
@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long age;

    public User(String name,Long age) {
        this.name = name;
        this.age = age;
    }

}
