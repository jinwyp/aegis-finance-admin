package com.yimei.finance.entity.admin.company;

import com.yimei.finance.common.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "remarks")
    private String remarks;                        //备注

    public Company (String name, String status, int statusId, Date createTime, String createManId, Date lastUpdateTime, String lastUpdateManId) {
        this.name = name;
        this.status = status;
        this.statusId = statusId;
        super.setCreateTime(createTime);
        super.setCreateManId(createManId);
        super.setLastUpdateTime(lastUpdateTime);
        super.setLastUpdateManId(lastUpdateManId);
    }

}
