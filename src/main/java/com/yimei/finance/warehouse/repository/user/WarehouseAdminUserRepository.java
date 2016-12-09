package com.yimei.finance.warehouse.repository.user;

import com.yimei.finance.warehouse.entity.user.WarehouseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WarehouseAdminUserRepository extends JpaRepository<WarehouseUser, Long> {

    WarehouseUser findByUsernameAndPasswordAndStatusId(@Param("username") String username,
                                                       @Param("password") String password,
                                                       @Param("statusId") int statusId);

}
