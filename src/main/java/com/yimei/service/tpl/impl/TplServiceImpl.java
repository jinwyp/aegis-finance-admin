package com.yimei.service.tpl.impl;

import com.yimei.api.common.util.DozerUtils;
import com.yimei.api.tpl.TplService;
import com.yimei.api.tpl.representations.Tpl;
import com.yimei.service.tpl.domain.TplObject;
import com.yimei.service.tpl.mapper.TplObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TplServiceImpl implements TplService {

    @Autowired
    TplObjectMapper tplObjectMapper;


    public String index() {
        TplObject tplObject = new TplObject();
        tplObject.setId(1);
        tplObject.setName("易煤网");
        tplObjectMapper.addTplObject(tplObject);

       /* // page test
        PageHelper.startPage(1,5);
        Page<String> page = tplObjectMapper.findTplObject();
        for (String s : page) {
            System.out.println("got record - " + s);
        }*/

        return "this TplServiceImpl response";
    }


    @Override
    public List<Tpl> test() {
        return DozerUtils.map(tplObjectMapper.test(), Tpl.class);
    }


}
