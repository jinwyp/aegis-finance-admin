package com.yimei.finance.warehouse.representation.company.object;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehouseCompanyRoleObject extends BaseEntity implements Serializable {
    private Long id;
    private int number;                            //角色编号
    private String role;                           //角色名称
}
