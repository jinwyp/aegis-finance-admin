package com.yimei.finance.site.controller.restfulapi.warehouse;

import com.yimei.finance.common.representation.enums.EnumCommonError;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.common.representation.result.ResultTwo;
import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.site.repository.finance.SiteFinanceOrderRepository;
import com.yimei.finance.site.representation.warehouse.UserWarehouse;
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
    @Autowired
    private SiteFinanceOrderRepository siteFinanceOrderRepository;

    @ApiOperation(value = "融资方上传合同,财务等文件", notes = "融资方上传合同,财务等文件", response = Result.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/customer/uploadfile", method = RequestMethod.POST)
    public Result customerUploadFile(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = siteFinanceOrderRepository.findOne(id);
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        String url = cangServiceAddress + "/user/company";
        UserWarehouse userWarehouse = new UserWarehouse(String.valueOf(session.getUser().getId()), session.getUser().getSecurephone(), String.valueOf(session.getUser().getCompanyId()));
        ResultTwo result = restTemplate.postForObject(url, userWarehouse, ResultTwo.class);
        if (result.status == 1) return Result.success();
        else return Result.error(result.message);
    }

    @ApiOperation(value = "融资方(客户)回款", notes = "融资方回款", response = Result.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/warehouse/customer/repayment", method = RequestMethod.POST)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = siteFinanceOrderRepository.findOne(id);
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        String url = cangServiceAddress + "/user/company";
        UserWarehouse userWarehouse = new UserWarehouse(String.valueOf(session.getUser().getId()), session.getUser().getSecurephone(), String.valueOf(session.getUser().getCompanyId()));
        ResultTwo result = restTemplate.postForObject(url, userWarehouse, ResultTwo.class);
        if (result.status == 1 || result.status == 2) return Result.success();
        else return Result.error(result.message);
    }

}
