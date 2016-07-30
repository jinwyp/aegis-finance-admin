package com.yimei.service.admin.mapper;

import com.yimei.api.admin.representations.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * Created by liuxinjie on 16/7/30.
 */
public interface AdminMapper {

    /**
     * 通过用户名查找用户
     */
    @Select(" select * from admins where userName=#{userName} ")
    Admin getAdminByUserName(String userName);


}
