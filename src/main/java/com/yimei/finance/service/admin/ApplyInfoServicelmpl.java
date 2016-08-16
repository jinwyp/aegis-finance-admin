package com.yimei.finance.service.admin;

import com.yimei.finance.entity.admin.ApplyInfo;
import com.yimei.finance.repository.applyinfo.ApplyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbolun on 16/8/16.
 */
@Service
public class ApplyInfoServicelmpl {
    @Autowired
    ApplyInfoRepository applyInfoRepository;

    public void save(ApplyInfo applyInfo) {
        applyInfoRepository.save(applyInfo);
    }

}
