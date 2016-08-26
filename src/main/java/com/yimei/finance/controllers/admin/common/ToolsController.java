package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.entity.admin.finance.EnumMYRFinanceAllSteps;
import com.yimei.finance.entity.common.databook.DataBook;
import com.yimei.finance.entity.common.databook.EnumDataBookType;
import com.yimei.finance.entity.common.result.MapObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.databook.DataBookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "admin-api-tools", description = "公用工具接口")
@RequestMapping("/api/financing/admin")
@RestController("adminCommonToolsController")
public class ToolsController {
    @Autowired
    private DataBookRepository dataBookRepository;

    @RequestMapping(value = "/transportmodes", method = RequestMethod.GET)
    @ApiOperation(value = "获取运输方式列表", notes = "获取运输方式列表数据", response = DataBook.class, responseContainer = "List")
    public Result findTransportModeListMethod() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.transportmode.toString()));
    }

    @RequestMapping(value = "/myr/steps", method = RequestMethod.GET)
    @ApiOperation(value = "煤易融流程所有步骤", notes = "煤易融流程所有步骤", response = EnumMYRFinanceAllSteps.class, responseContainer = "List")
    public Result findMYRFinanceAllSteps() {
        List<MapObject> stepList = new ArrayList<>();
        for (EnumMYRFinanceAllSteps step : EnumMYRFinanceAllSteps.values()) {
            stepList.add(new MapObject(String.valueOf(step.id), step.name));
        }
        return Result.success().setData(stepList);
    }


}
