package com.yimei.finance.site.controller.restfulapi.warehouse;

import com.yimei.finance.common.representation.result.Page;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.site.representation.finance.FinanceOrderSearch;
import com.yimei.finance.site.representation.warehouse.WarehouseDetail;
import com.yimei.finance.site.representation.warehouse.WarehouseList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("adminWarehouseInfoController")
public class WarehouseInfoController {

    @ApiOperation(value = "金融仓押列表", notes = "用户查询金融仓押列表", response = WarehouseList.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "date", paramType = "query")
    })
    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(FinanceOrderSearch orderSearch, Page page) {
        page.setCount(10);
        return null;
    }

    @ApiOperation(value = "根据 金融id 查看金融仓押详细", notes = "根据 id 查看金融仓押详细", response = WarehouseDetail.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        return null;
    }

}
