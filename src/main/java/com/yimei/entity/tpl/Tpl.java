package com.yimei.entity.tpl;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxinjie on 16/7/30.
 */
@Data
@Entity
@Table(name = "test")
@NoArgsConstructor(staticName = "static")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tpl implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String name;

    public Tpl(String name) {
        this.name = name;
    }
}
