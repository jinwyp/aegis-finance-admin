package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.entity.common.databook.DataBook;
import com.yimei.finance.entity.common.databook.EnumDataBookType;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.databook.DataBookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Api(value = "admin-api-tools", description = "公用工具接口")
@RequestMapping("/api/financing/admin")
@RestController("adminCommonToolsController")
public class ToolsController {
    @Autowired
    private DataBookRepository dataBookRepository;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/transportmodes", method = RequestMethod.GET)
    @ApiOperation(value = "获取运输方式列表", notes = "获取运输方式列表数据", response = DataBook.class, responseContainer = "List")
    public Result findTransportModeListMethod() {
        Map<Integer, Object> map = new LinkedHashMap<>();
        List<DataBook> dataBookList = dataBookRepository.findByType(EnumDataBookType.transportmode.toString());
        return Result.success().setData(dataBookToMap(dataBookList));
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有部门列表", notes = "获取所有部门列表", response = String.class, responseContainer = "List")
    public Result findAllDepartmentListMethod() {
        Map<Integer, Object> map = new LinkedHashMap<>();
        List<DataBook> dataBookList = dataBookRepository.findByType(EnumDataBookType.financedepartment.toString());
        return Result.success().setData(dataBookToMap(dataBookList));
    }


    Map<Integer, Object> dataBookToMap(List<DataBook> dataBookList) {
        Map<Integer, Object> map = new LinkedHashMap<>();
        for (DataBook dataBook : dataBookList) {
            map.put(dataBook.getSequence(), dataBook.getName());
        }
        return map;
    }
}
