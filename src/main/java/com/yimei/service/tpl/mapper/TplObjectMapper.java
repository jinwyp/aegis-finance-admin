package com.yimei.service.tpl.mapper;

import com.github.pagehelper.Page;
import com.yimei.domain.tpl.TplObject;
import org.apache.ibatis.annotations.Insert;

public interface TplObjectMapper {

    @Insert("insert into tpl_object(name, value) values(#{name}, #{value})")
    int addTplObject(TplObject tplObject);

    Page<String> findTplObject();

}
