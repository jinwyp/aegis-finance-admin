package com.yimei.finance.warehouse.service.user;

import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.warehouse.entity.user.WarehouseAdminUserLoginRecord;
import com.yimei.finance.warehouse.entity.user.WarehouseUser;
import com.yimei.finance.warehouse.repository.user.WarehouseAdminUserLoginRecordRepository;
import com.yimei.finance.warehouse.repository.user.WarehouseAdminUserRepository;
import com.yimei.finance.warehouse.representation.user.enums.EnumWarehouseAdminUserError;
import com.yimei.finance.warehouse.representation.user.enums.EnumWarehouseAdminUserStatus;
import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserLoginObject;
import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("warehouseAdminUserServiceImpl")
public class WarehouseAdminUserServiceImpl {
    @Autowired
    private WarehouseAdminUserRepository userRepository;
    @Autowired
    private WarehouseAdminUserLoginRecordRepository loginRecordRepository;

    /**
     * 用户登陆
     */
    public Result loginMethod(WarehouseAdminUserLoginObject userLoginObject) {
        WarehouseUser user = userRepository.findByUsernameAndPasswordAndStatusId(userLoginObject.getUsername(), securePassword(userLoginObject.getPassword()), EnumWarehouseAdminUserStatus.Normal.id);
        if (user == null) {
            return Result.error(401, EnumWarehouseAdminUserError.用户名或者密码错误.toString());
        } else {
            loginRecordRepository.save(new WarehouseAdminUserLoginRecord(String.valueOf(user.getId()), user.getUsername()));
            return Result.success().setData(changeToUserObject(user));
        }
    }

    public WarehouseAdminUserObject changeToUserObject(WarehouseUser user) {
        if (user == null) return null;
        WarehouseAdminUserObject userObject = DozerUtils.copy(user, WarehouseAdminUserObject.class);
        userObject.setLastLoginTime(loginRecordRepository.findTopByUserIdOrderByIdDesc(user.getId()).getCreateTime());
        return userObject;
    }

    public List<WarehouseAdminUserObject> changeToUserObject(List<WarehouseUser> userList) {
        if (userList == null || userList.size() == 0) return null;
        List<WarehouseAdminUserObject> userObjectList = DozerUtils.copy(userList, WarehouseAdminUserObject.class);
        userObjectList.parallelStream().forEach(user -> {
            user.setLastUpdateTime(loginRecordRepository.findTopByUserIdOrderByIdDesc(user.getId()).getCreateTime());
        });
        return userObjectList;
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }

}
