package com.yimei.finance.controllers.site.page;

import com.lowagie.text.DocumentException;
import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceContractType;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderContractObject;
import com.yimei.finance.service.common.contract.ContractServiceImpl;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.service.common.file.PDF;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Api(tags = {"site-page"})
@Controller("siteFinancePageController")
public class SiteFinancePageController {
    @Autowired
    private FinanceOrderContractRepository financeOrderContractRepository;
//    @Autowired
    private ContractServiceImpl contractService;

    public void setContractService(ContractServiceImpl contractService) {
        this.contractService = contractService;
    }

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情", notes = "供应链金融 我的融资 煤易贷 合同详情页面")
    @LoginRequired
    @RequestMapping(value = "/finance/user/order/{financeId}/contract", method = RequestMethod.GET)
    public String personCenterContactInfo(@PathVariable("financeId") Long financeId, Model model) {
        model.addAttribute("currentMenu", 11);
        return "site/user/financeInfoContact";
    }

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情 - 下载合同", notes = "供应链金融 我的融资 煤易贷 合同详情 - 下载合同")
    @LoginRequired
    @RequestMapping(value = "/finance/user/order/{financeId}/contract/{type}/download", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "合同类型", required = true, dataType = "int", paramType = "path")
    })
    public HttpEntity<byte[]> siteFinanceOrderDownloadContractByFinanceIdAndContractType(@PathVariable("financeId") Long financeId,
                                                                           @PathVariable("type") int type) throws IOException, DocumentException {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        FinanceOrderContractObject financeOrderContractObject = DozerUtils.copy(financeOrderContractRepository.findByFinanceIdAndType(financeId, type), FinanceOrderContractObject.class);
        if (financeOrderContractObject == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        String contract = contractService.getFinanceOrderContractContent(financeId, type, true);
        String fileName  = "合同-" + financeOrderContractObject.getContractNo() + ".pdf";
        File file = PDF.createByHtml(contract);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName,"UTF-8"));
        return new HttpEntity<>(FileUtils.readFileToByteArray(file), headers);
    }

    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的合同 - 合同详情 - 预览合同", notes = "供应链金融 我的融资 煤易贷 合同详情 - 预览合同")
    @LoginRequired
    @RequestMapping(value = "/finance/user/order/{financeId}/contract/{type}/preview", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "合同类型", required = true, dataType = "int", paramType = "path")
    })
    public String siteFinanceOrderPreviewContractByFinanceIdAndContractType(@PathVariable("financeId") Long financeId,
                                                                            @PathVariable("type") int type, Map<String, Object> model) {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        if (financeOrderContractRepository.findByFinanceIdAndType(financeId, type) == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        model.put("contract", contractService.getFinanceOrderContractContent(financeId, type, true));
        return "site/user/contractPreview";
    }

}
