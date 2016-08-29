package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.MapObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.common.NumberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NumberServiceImpl numberService;

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
        financeOrder.setApplyCompanyName(userSession.getUser().getCompanyName());
//        financeOrder.setUserId(1);
        financeOrder.setApproveStateId(EnumFinanceStatus.WaitForAudit.id);
        financeOrder.setApproveState(EnumFinanceStatus.WaitForAudit.name);
        financeOrder.setApplyDateTime(new Date());
        financeOrder.setLastUpdateTime(new Date());
        financeOrder.setEndDateTime(null);
        financeOrderRepository.save(financeOrder);
        financeOrder = financeOrderRepository.findBySourceId(financeOrder.getSourceId());
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).active().singleResult();
        return Result.success().setData(financeOrderRepository.findBySourceId(financeOrder.getSourceId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
//    public Result getFinancingApplyInfoList(@RequestBody CombineObject object) {
    public Result getFinancingApplyInfoList(Page page) {
//        return financeOrderService.getFinanceOrderBySelect(1, object.financeOrderSearch, object.page);
        return Result.success().setData(financeOrderRepository.findByUserId(userSession.getUser().getId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/apply/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = financeOrderRepository.findByIdAndUserId(id, userSession.getUser().getId());
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




