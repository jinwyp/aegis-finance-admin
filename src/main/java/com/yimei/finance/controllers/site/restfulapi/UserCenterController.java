package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.representation.admin.finance.EnumFinanceStatus;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.user.FinanceOrderSearch;
import com.yimei.finance.service.admin.finance.FinanceOrderServiceImpl;
import com.yimei.finance.service.common.NumberServiceImpl;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JinWYP on 8/15/16.
 */
@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("siteUserCenterController")
public class UserCenterController {
    @Autowired
    private FinanceOrderRepository orderRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private NumberServiceImpl numberService;
    @Autowired
    private FinanceOrderServiceImpl orderService;
    @Autowired
    private IdentityService identityService;

    /**
    * 供应链金融 - 发起融资申请
    */
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = FinanceOrder.class)
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@ApiParam(name = "financeOrder", value = "只需填写applyType 字段即可", required = true) @Valid @RequestBody FinanceOrder financeOrder) {
        System.out.println("Order Type:" + financeOrder.getApplyType());
        financeOrder.setApplyType(financeOrder.getApplyType());
        financeOrder.setSourceId(numberService.getNextCode("JR"));
        financeOrder.setUserId(userSession.getUser().getId());
        financeOrder.setApplyUserName(userSession.getUser().getNickname());
        financeOrder.setApplyUserName(userSession.getUser().getSecurephone());
        financeOrder.setApplyCompanyName(userSession.getUser().getCompanyName());
        financeOrder.setCreateManId(String.valueOf(userSession.getUser().getId()));
        financeOrder.setLastUpdateManId(String.valueOf(userSession.getUser().getId()));
        financeOrder.setCreateTime(new Date());
        financeOrder.setLastUpdateTime(new Date());
        financeOrder.setEndTime(null);
//        financeOrder.setUserId(1);
        financeOrder.setApproveStateId(EnumFinanceStatus.WaitForAudit.id);
        financeOrder.setApproveState(EnumFinanceStatus.WaitForAudit.name);
        orderRepository.save(financeOrder);
        financeOrder = orderRepository.findBySourceId(financeOrder.getSourceId());
        identityService.setAuthenticatedUserId(String.valueOf(userSession.getUser().getId()));
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYGWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYDWorkFlow", String.valueOf(financeOrder.getId()));
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        return Result.success().setData(orderRepository.findBySourceId(financeOrder.getSourceId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "approveStateId", value = "审批状态id", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "业务类型", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(FinanceOrderSearch orderSearch, Page page) {
        return orderService.getFinanceOrderBySelect(userSession.getUser().getId(), orderSearch, page);
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/apply/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = orderRepository.findByIdAndUserId(id, userSession.getUser().getId());
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(financeOrder);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ApiOperation(value = "融资申请状态列表", notes = "融资申请状态列表", response = MapObject.class, responseContainer = "List")
    public Result findFinanceStatusList() {
        List<MapObject> mapList = new ArrayList<>();
        for (EnumFinanceStatus status : EnumFinanceStatus.values()) {
            mapList.add(new MapObject(String.valueOf(status.id), status.name));
        }
        return Result.success().setData(mapList);
    }


}




