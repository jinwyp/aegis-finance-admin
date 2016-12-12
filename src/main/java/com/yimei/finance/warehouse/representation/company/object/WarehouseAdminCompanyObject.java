package com.yimei.finance.warehouse.representation.company.object;

import com.yimei.finance.common.representation.base.BaseObject;
import com.yimei.finance.warehouse.representation.company.enums.EnumWarehouseAdminCompanyStatus;
import com.yimei.finance.warehouse.representation.company.object.validated.CreateWarehouseAdminCompany;
import com.yimei.finance.warehouse.representation.company.object.validated.EditWarehouseAdminCompany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehouseAdminCompanyObject extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 100, message = "公司名称应该在 {min}-{max} 个字符之间", groups = {CreateWarehouseAdminCompany.class, EditWarehouseAdminCompany.class})
    @NotBlank(message = "名称 不能为空", groups = {CreateWarehouseAdminCompany.class, EditWarehouseAdminCompany.class})
    private String name;                               //公司名称

    public String status;
    private int statusId;                              //状态id
    private String statusName;                         //状态名字

    private int roleNumber;
    private String roleName;

    @Size(max = 500, message = "备注不能超过 {max} 个字符", groups = {CreateWarehouseAdminCompany.class, EditWarehouseAdminCompany.class})
    private String remarks;                            //备注

    private String getStatusName() {
        if (StringUtils.isEmpty(status)) return null;
        return EnumWarehouseAdminCompanyStatus.valueOf(status).name;
    }

}
