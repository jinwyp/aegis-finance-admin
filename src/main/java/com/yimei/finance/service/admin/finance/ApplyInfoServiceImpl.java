package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.user.ApplyInfo;
import com.yimei.finance.repository.admin.applyinfo.ApplyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbolun on 16/8/16.
 */
@Service
public class ApplyInfoServiceImpl {
    @Autowired
    ApplyInfoRepository applyInfoRepository;

    public void save(ApplyInfo applyInfo) {
        applyInfoRepository.save(applyInfo);
    }
//
//    public ApplyInfo findOne(Integer id) {
//        return applyInfoRepository.findOne(id);
//    }
//
//    public void updateApplyType(Integer id, String applyType) {
//        applyInfoRepository.updateApplyInfoType(id, applyType);
//    }

}
