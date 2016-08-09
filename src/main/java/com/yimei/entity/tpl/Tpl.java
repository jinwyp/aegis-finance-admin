package com.yimei.entity.tpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liuxinjie on 16/7/30.
 */
@Data
@Entity
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class Tpl implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Tpl(String name) {
        this.name = name;
    }
}
