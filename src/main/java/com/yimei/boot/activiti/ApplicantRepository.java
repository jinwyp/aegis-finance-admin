package com.yimei.boot.activiti;

import com.yimei.api.tpl.representations.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liuxinjie on 16/8/1.
 */
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
