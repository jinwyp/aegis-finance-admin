package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.repository.tpl.JpaRepositoryDemo;
import com.yimei.finance.repository.tpl.TplRepository;
import com.yimei.finance.service.common.NumberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private TplRepository tplRepository;
    @Autowired
    private JpaRepositoryDemo jpaRepositoryDemo;
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
    @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "String", paramType = "form")
//    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@RequestParam(value = "applyType", required = true)String applyType) {
        System.out.println("Order Type:" + applyType);
        FinanceOrder financeOrder = new FinanceOrder();
        financeOrder.setApplyType(applyType);
        financeOrder.setSourceId(numberService.getNextCode("JR"));
        financeOrder.setUserId(userSession.getUser().getId());
        financeOrder.setApplyDateTime(LocalDateTime.now());
        financeOrder.setApproveState(EnumFinanceStatus.PendingAudit.toString());
        financeOrderRepository.save(financeOrder);
        if (runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).singleResult() != null) return Result.error(EnumAdminFinanceError.此金融单已经创建流程.toString());
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).active().singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        return Result.success().setData(financeOrderRepository.findOne(financeOrder.getId()));
    }




    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */

    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "String", paramType = "query")
    })
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(@RequestParam(value = "applyType", required = false ) String applyType, Page page) {
        List<FinanceOrder> financeOrderList = financeOrderRepository.findByUserId(userSession.getUser().getId());

        return Result.success().setData(financeOrderList).setMeta(page);
    }



    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
//    @LoginRequired

    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/apply/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = financeOrderRepository.findByIdAndUserId(id, userSession.getUser().getId());
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(financeOrder);
    }
}



