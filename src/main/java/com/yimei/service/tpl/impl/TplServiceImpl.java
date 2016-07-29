package com.yimei.service.tpl.impl;

import com.yimei.service.tpl.mapper.TplObjectMapper;
import com.yimei.domain.tpl.TplObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TplServiceImpl {

    @Autowired
    TplObjectMapper tplObjectMapper;


    public String index() {
        TplObject tplObject = new TplObject();
        tplObject.setName("key");
        tplObject.setValue("value");
        tplObjectMapper.addTplObject(tplObject);

       /* // page test
        PageHelper.startPage(1,5);
        Page<String> page = tplObjectMapper.findTplObject();
        for (String s : page) {
            System.out.println("got record - " + s);
        }*/

        return "this TplServiceImpl response";
    }


}
