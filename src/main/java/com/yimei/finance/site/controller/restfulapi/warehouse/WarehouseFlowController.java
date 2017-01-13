package com.yimei.finance.site.controller.restfulapi.warehouse;

import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.common.representation.result.ResultTwo;
import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.site.repository.finance.SiteFinanceOrderRepository;
import com.yimei.finance.site.representation.warehouse.WarehousePostObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("adminWarehouseFlowController")
public class WarehouseFlowController {
    @Value("${cang.address}")
    private String cangServiceAddress;
    @Autowired
    private UserSession session;
    @Autowired
    private RestTemplate restTemplate;


    @ApiOperation(value = "融资方上传合同,财务等文件", notes = "融资方上传合同,财务等文件", response = Result.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/customer/uploadcontract/{flowId}/{taskId}", method = RequestMethod.POST)
    public Result customerUploadFile(@PathVariable("flowId") String flowId,
                                     @PathVariable("taskId") String taskId, List<String> fileList) {
        String url = cangServiceAddress + "/api/cang/financerTask/{actionName}/{userId}/{companyId}";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", session.getUser().getId());
        map.put("companyId", session.getUser().getCompanyId());
        map.put("actionName", "a12FinishedUpload");
        WarehousePostObject object = new WarehousePostObject(flowId, taskId, fileList);
        ResultTwo result = restTemplate.postForObject(url, object, ResultTwo.class, map);
        if (result.status == 1) return Result.success();
        else return Result.error(result.message);
    }

    @ApiOperation(value = "融资方(客户)回款", notes = "融资方回款", response = Result.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/customer/repayment/{flowId}/{taskId}/{money}", method = RequestMethod.POST)
    public Result getFinancingApplyInfo(@PathVariable("flowId") String flowId,
                                        @PathVariable("taskId") String taskId,
                                        @PathVariable("money")BigDecimal money) {
        String url = cangServiceAddress + "/api/cang/financerTask/{actionName}/{userId}/{companyId}";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", session.getUser().getId());
        map.put("companyId", session.getUser().getCompanyId());
        map.put("actionName", "a19SecondReturnMoney");
        WarehousePostObject object = new WarehousePostObject(flowId, taskId, money);
        ResultTwo result = restTemplate.postForObject(url, object, ResultTwo.class, map);
        if (result.status == 1 || result.status == 2) return Result.success();
        else return Result.error(result.message);
    }

}
