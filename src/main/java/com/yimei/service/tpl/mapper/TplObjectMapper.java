package com.yimei.service.tpl.mapper;

import com.github.pagehelper.Page;
import com.yimei.service.tpl.domain.TplObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TplObjectMapper {

    @Insert("insert into test(id, name) values(#{id}, #{name})")
    int addTplObject(TplObject tplObject);

    Page<String> findTplObject();

    @Select(" select * from test ")
    List<TplObject> test();

}
