package com.yimei.finance.representation.common.databook;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_finance_databook")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class DataBook implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 11)
    private int id;                    // id
    @Column(name = "sequence", length = 5, nullable = false)
    @NonNull
    private int sequence;
    @Column(name = "name", length = 30, nullable = false)
    @NonNull
    private String name;               // 名称
    @Column(name = "type", length = 30, nullable = false)
    @NonNull
    private String type;               // 类型

}

