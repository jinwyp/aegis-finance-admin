package com.yimei.finance.entity.admin.company;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_company")
@Entity
@Data
@NoArgsConstructor
public class Company extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;                           //公司名称

    @Column(name = "status")
    public String status;                          //状态

    @Column(name = "status_id")
    private int statusId;                          //状态id

}
