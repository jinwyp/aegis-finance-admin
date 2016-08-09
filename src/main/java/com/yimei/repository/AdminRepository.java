package com.yimei.repository;

import com.yimei.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by liuxinjie on 16/8/8.
 */
@Repository("adminDao")
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
