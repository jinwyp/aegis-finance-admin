package com.yimei.finance.site.controller.restfulapi.warehouse;

import com.yimei.finance.common.representation.enums.EnumCommonError;
import com.yimei.finance.common.representation.result.Page;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.site.repository.finance.SiteFinanceOrderRepository;
import com.yimei.finance.site.representation.finance.FinanceOrderSearch;
import com.yimei.finance.site.representation.warehouse.UserWarehouse;
import com.yimei.finance.site.representation.warehouse.WarehouseDetail;
import com.yimei.finance.site.representation.warehouse.WarehouseList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("adminWarehouseInfoController")
public class WarehouseInfoController {
    @Value("${cang.address}")
    private String cangServiceAddress;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserSession session;
    @Autowired
    private SiteFinanceOrderRepository siteFinanceOrderRepository;

    @ApiOperation(value = "金融仓押列表", notes = "用户查询金融仓押列表", response = WarehouseList.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "date", paramType = "query")
    })
    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public Result getWarehouseInfoList(FinanceOrderSearch orderSearch, Page page) {
        page.setCount(10);
        UserWarehouse userWarehouse = new UserWarehouse(String.valueOf(session.getUser().getId()), session.getUser().getSecurephone(), String.valueOf(session.getUser().getCompanyId()));
        String url = cangServiceAddress + "/user/company";
        List<WarehouseList> warehouseList = new ArrayList<>();
        warehouseList = restTemplate.postForObject(url, userWarehouse, warehouseList.getClass());
        if (warehouseList != null && warehouseList.size() != 0) {
            warehouseList.parallelStream().forEach(warehouse -> {
                FinanceOrder financeOrder = siteFinanceOrderRepository.findBySourceId(warehouse.getBusinessCode());
                if (financeOrder == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
                warehouse.setFinanceId(financeOrder.getId());
                warehouse.setUserId(financeOrder.getUserId());
                warehouse.setCreateTime(financeOrder.getCreateTime());
                warehouse.setFinancingAmount(financeOrder.getFinancingAmount());
            });
        }
        return Result.success().setData(warehouseList);
    }

    @ApiOperation(value = "根据 金融id 查看金融仓押详细", notes = "根据 id 查看金融仓押详细", response = WarehouseDetail.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/{financeId}", method = RequestMethod.GET)
    public Result getWarehouseInfo(@PathVariable("financeId") Long financeId) {
        String url = cangServiceAddress + "/user/company";
        UserWarehouse userWarehouse = new UserWarehouse(String.valueOf(session.getUser().getId()), session.getUser().getSecurephone(), String.valueOf(session.getUser().getCompanyId()));
        WarehouseDetail warehouse = restTemplate.postForObject(url, userWarehouse, WarehouseDetail.class);
        FinanceOrder financeOrder = siteFinanceOrderRepository.findBySourceId(warehouse.getBusinessCode());
        if (financeOrder == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        warehouse.setFinanceId(financeOrder.getId());
        return Result.success().setData(warehouse);
    }

}
