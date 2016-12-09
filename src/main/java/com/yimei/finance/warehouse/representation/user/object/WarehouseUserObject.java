package com.yimei.finance.warehouse.representation.user.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.admin.representation.user.enums.EnumAdminUserStatus;
import com.yimei.finance.common.representation.base.BaseObject;
import com.yimei.finance.common.representation.enums.EnumCommonString;
import com.yimei.finance.warehouse.representation.user.object.validated.CreateWarehouseUser;
import com.yimei.finance.warehouse.representation.user.object.validated.EditWarehouseUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class WarehouseUserObject extends BaseObject implements Serializable {
    private String id;

    @Size(min = 2, max = 20, message = "登录名应在2-20个字符之间", groups = {CreateWarehouseUser.class})
    @NotBlank(message = "用户登录名不能为空", groups = {CreateWarehouseUser.class})
    private String username;                    //账号

    @Size(min = 2, max = 20, message = "姓名应在2-20个字符之间", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    @NotBlank(message = "姓名不能为空", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    private String name;                        //姓名

    @Size(max = 11, message = "请输入正确的手机号")
//    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的手机号", groups = {CreateUser.class, EditUser.class})
    private String phone;                       //手机号

    @Size(max = 100, message = "邮箱不能超过 {max} 个字符", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    @NotBlank(message = "邮箱不能为空", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    @Email(message = "请输入正确的邮箱", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    private String email;                       //邮箱

    private String companyId;
    @Size(min = 1, max = 100, message = "公司名称应该在 {min}-{max} 个字符之间", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    @NotBlank(message = "所属公司不能为空", groups = {CreateWarehouseUser.class, EditWarehouseUser.class})
    private String companyName;                 //用户所在公司名称

    @NotBlank(message = "角色不能为空")
    private int roleNumber;
    private String roleName;

    private int statusId;
    private String status;                      //状态
    private String statusName;

    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date lastLoginTime;                 //最后一次登录时间
    private boolean operateAuthority;           //是否具有操作/更改此用户的权限, true: 有权限, false: 无

    public String getStatusName() {
        if (StringUtils.isEmpty(status)) return null;
        return EnumAdminUserStatus.valueOf(status).name;
    }

}
