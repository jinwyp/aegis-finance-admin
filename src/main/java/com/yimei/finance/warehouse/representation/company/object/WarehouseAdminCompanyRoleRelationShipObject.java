package com.yimei.finance.warehouse.representation.company.object;

import com.yimei.finance.common.representation.base.BaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehouseAdminCompanyRoleRelationShipObject extends BaseObject implements Serializable {
    private Long id;
    private Long companyId;                            //公司id
    private int roleNumber;                            //角色编号
}
