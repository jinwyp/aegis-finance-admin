package com.yimei.finance.warehouse.entity.user;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "warehouse_user_role")
@Entity
@Data
@NoArgsConstructor
public class WarehouseUserRole extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private int number;                            //角色编号

    @Column(name = "role")
    private String role;                           //角色名称
}
