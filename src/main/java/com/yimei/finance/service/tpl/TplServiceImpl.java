package com.yimei.finance.service.tpl;

import com.yimei.finance.entity.tpl.Tpl;
import com.yimei.finance.repository.tpl.TplRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TplServiceImpl {

    @Autowired
    TplRepository tplRepository;

    public void save(Tpl tpl) {
        tplRepository.save(tpl);
    }



}
