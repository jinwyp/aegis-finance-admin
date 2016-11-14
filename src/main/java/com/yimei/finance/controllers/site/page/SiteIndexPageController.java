package com.yimei.finance.controllers.site.page;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.service.site.finance.SiteFinanceOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = {"site-page"})
@Controller("siteFinanceIndexPageController")
public class SiteIndexPageController {
    @Autowired
    private SiteFinanceOrderServiceImpl financeOrderService;
    @Autowired
    private UserSession userSession;

    /**
     * 网站供应链金融 页面
     */
    @ApiOperation(value = "网站供应链金融 页面", notes = "网站页面需要登录")
    @RequestMapping(value = {"/finance","/"}, method = RequestMethod.GET)
    public String financeIndex() {
        return "site/index";
    }

    /**
    * 网站供应链金融 - 个人中心 - 我的申请
    */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请", notes = "供应链金融 我的融资 申请列表")
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing", method = RequestMethod.GET)
    public String personCenterFinancingList(Model model) {
        model.addAttribute("currentMenu", 11);
        return "site/user/financingList";
    }

    /**
     * 网站供应链金融 - 个人中心 - 我的申请 - 业务详情
     */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请 - 业务详情", notes = "供应链金融 我的融资 申请详情页面")
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing/{id}", method = RequestMethod.GET)
    public String personCenterFinancingRequest(@PathVariable("id") int id, Model model) {
        model.addAttribute("currentMenu", 11);
        return "site/user/financingInfo";
    }

    /**
     * 网站供应链金融 - 个人中心 - 我的申请
     */
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing/excel", method = RequestMethod.GET)
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请 - 导出金融申请单", notes = "供应链金融 我的融资 申请列表 导出金融申请单", response = MapObject.class, responseContainer = "List")
    public void exportFinancingOrderToExcel(HttpServletResponse response,
                                            HttpServletRequest request) throws IOException {
        financeOrderService.customerExportFinanceOrder(Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()), response, request);
    }

    

}
