package com.yimei.finance.tpl.service;

import com.yimei.finance.tpl.entity.Tpl;
import com.yimei.finance.tpl.repository.TplRepository;
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
