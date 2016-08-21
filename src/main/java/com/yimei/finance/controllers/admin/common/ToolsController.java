package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.entity.common.databook.DataBook;
import com.yimei.finance.entity.common.databook.EnumDataBookType;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.databook.DataBookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "admin-api-tools", description = "公用工具接口")
@RequestMapping("/api/financing/admin")
@RestController("adminCommonToolsController")
public class ToolsController {
    @Autowired
    private DataBookRepository dataBookRepository;

    @RequestMapping("/transportmodes")
    @ApiOperation(value = "获取运输方式列表", notes = "获取运输方式列表数据", response = DataBook.class, responseContainer = "List")
    public Result findTransportModeList() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.transportmode.toString()));
    }
}
