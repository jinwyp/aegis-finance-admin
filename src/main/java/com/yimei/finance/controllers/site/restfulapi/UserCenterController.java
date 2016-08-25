package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.MapObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.common.NumberServiceImpl;
import io.swagger.annotations.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
//    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@ApiParam(name = "financeOrder", value = "只需填写applyType 字段即可", required = true) @Valid @RequestBody FinanceOrder financeOrder) {
        System.out.println("Order Type:" + financeOrder.getApplyType());
        financeOrder.setApplyType(financeOrder.getApplyType());
        financeOrder.setSourceId(numberService.getNextCode("JR"));
        financeOrder.setUserId(userSession.getUser().getId());
        financeOrder.setApplyCompanyName(userSession.getUser().getCompanyName());
//        financeOrder.setUserId(1);
        financeOrder.setApplyDateTime(LocalDateTime.now());
        financeOrder.setApproveStateId(EnumFinanceStatus.WaitForAudit.id);
        financeOrder.setApproveState(EnumFinanceStatus.WaitForAudit.name);
        financeOrderRepository.save(financeOrder);
        financeOrder = financeOrderRepository.findBySourceId(financeOrder.getSourceId());
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).active().singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageOnlineTraderGroup.id, IdentityLinkType.CANDIDATE);
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        return Result.success().setData(financeOrderRepository.findBySourceId(financeOrder.getSourceId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "approveStateId", value = "状态Id", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    })
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(@RequestParam(value = "startDate", required = false) String startDate,
                                            @RequestParam(value = "endDate", required = false) String endDate,
                                            @RequestParam(value = "approveStateId", required = false, defaultValue = "0") int approveStateId,
                                            @RequestParam(value = "sourceId", required = false) String sourceId,
                                            @RequestParam(value = "applyType", required = false ) String applyType,
                                            Page page) {
        List<FinanceOrder> financeOrderList = financeOrderRepository.findByUserId(userSession.getUser().getId());
        return Result.success().setData(financeOrderList).setMeta(page);
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
    @ApiOperation(value = "前台金融单状态list", notes = "前台金融单状态list", response = MapObject.class, responseContainer = "List")
    public Result findFinanceStatusList() {
        List<MapObject> mapList = new ArrayList<>();
        for (EnumFinanceStatus status : EnumFinanceStatus.values()) {
            mapList.add(new MapObject(String.valueOf(status.id), status.name));
        }
        return Result.success().setData(mapList);
    }
}



