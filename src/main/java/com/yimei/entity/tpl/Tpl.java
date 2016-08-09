package com.yimei.entity.tpl;

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
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Tpl implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String name;

}
