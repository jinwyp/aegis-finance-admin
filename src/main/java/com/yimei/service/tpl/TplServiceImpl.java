package com.yimei.service.tpl;

import com.yimei.entity.tpl.Tpl;
import com.yimei.repository.tpl.TplRepository;
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
