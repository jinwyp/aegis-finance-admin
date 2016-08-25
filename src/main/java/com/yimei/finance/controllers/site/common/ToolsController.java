package com.yimei.finance.controllers.site.common;

import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.common.databook.DataBook;
import com.yimei.finance.entity.common.result.MapObject;
import com.yimei.finance.entity.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "site-api-tools", description = "公用工具接口")
@RequestMapping("/api/financing/")
@RestController("siteCommonToolsController")
public class ToolsController {

    @RequestMapping(value = "/transportmodes", method = RequestMethod.GET)
    @ApiOperation(value = "前台金融单状态list", notes = "前台金融单状态list", response = DataBook.class, responseContainer = "List")
    public Result findFinanceStatusList() {
        List<MapObject> mapList = new ArrayList<>();
        for (EnumFinanceStatus status : EnumFinanceStatus.values()) {
            mapList.add(new MapObject(String.valueOf(status.id), status.name));
        }
        return Result.success().setData(mapList);
    }

}
