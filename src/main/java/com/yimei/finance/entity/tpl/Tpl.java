package com.yimei.finance.entity.tpl;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liuxinjie on 16/7/30.
 */
@Entity
@Table(name = "test")
@Data
public class Tpl implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

}
