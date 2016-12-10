package com.yimei.finance.warehouse.representation.user.object;

import com.yimei.finance.common.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehouseAdminUserRoleObject extends BaseEntity implements Serializable {
    private Long id;
    private int number;                            //角色编号
    private String role;                           //角色名称
}
