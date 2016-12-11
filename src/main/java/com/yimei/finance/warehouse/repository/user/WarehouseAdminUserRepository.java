package com.yimei.finance.warehouse.repository.user;

import com.yimei.finance.warehouse.entity.user.WarehouseAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseAdminUserRepository extends JpaRepository<WarehouseAdminUser, Long> {

    WarehouseAdminUser findByUsernameAndPasswordAndStatusId(@Param("username") String username,
                                                            @Param("password") String password,
                                                            @Param("statusId") int statusId);

    @Query(" select u.id, u.username, u.name, u.phone, u.email, u.companyName, u.roleName " +
           " from WarehouseAdminUser u " +
           " where u.username like ?1 and u.name like ?2 and u.companyName like ?3 and u.roleName like ?4 " +
           " order by u.id desc ")
    List<WarehouseAdminUser> findUserList(@Param("username") String username,
                                          @Param("name") String name,
                                          @Param("companyName") String companyName,
                                          @Param("roleName") String roleName);

}
