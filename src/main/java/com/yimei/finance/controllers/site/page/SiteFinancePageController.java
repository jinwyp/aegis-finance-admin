package com.yimei.finance.controllers.site.page;

import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceContractType;
import com.yimei.finance.representation.common.contract.ContractServiceImpl;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.service.site.finance.SiteFinanceOrderServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller("financePageController")
public class SiteFinancePageController {
    @Autowired
    private SiteFinanceOrderServiceImpl financeOrderService;
    @Autowired
    private FinanceOrderContractRepository financeOrderContractRepository;
    @Autowired
    private ContractServiceImpl contractService;

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情", notes = "供应链金融 我的融资 煤易贷 合同详情页面")
    @LoginRequired
    @RequestMapping(value = "/finance/user/{financeId}/contract", method = RequestMethod.GET)
    public String personCenterContactInfo(@PathVariable("financeId") Long financeId, Model model) {
        model.addAttribute("currentMenu", 11);
        return "site/user/financeInfoContact";
    }

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情 - 下载合同", notes = "供应链金融 我的融资 煤易贷 合同详情 - 下载合同")
    @LoginRequired
    @RequestMapping(value = "/finance/user/{financeId}/contract/{type}/download", method = RequestMethod.GET)
    public void siteFinanceOrderDownloadContractByFinanceIdAndContractType(@PathVariable("financeId") Long financeId,
                                                                           @PathVariable("type") int type) {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        financeOrderService.downFinanceOrderContractByFinanceIdAndContractType(financeId, type);
    }

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情 - 预览合同", notes = "供应链金融 我的融资 煤易贷 合同详情 - 预览合同")
    @LoginRequired
    @RequestMapping(value = "/finance/user/{financeId}/contract/{type}/preview", method = RequestMethod.GET)
    public String siteFinanceOrderPreviewContractByFinanceIdAndContractType(@PathVariable("financeId") Long financeId,
                                                                            @PathVariable("type") int type, Map<String, Object> model) {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        if (financeOrderContractRepository.findByFinanceIdAndType(financeId, type) == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        model.put("contract", contractService.getFinanceOrderFormalContractContent(financeId, type));
        return "site/user/contractPreview";
    }

}
