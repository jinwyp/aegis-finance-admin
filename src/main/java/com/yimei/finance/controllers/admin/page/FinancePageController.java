package com.yimei.finance.controllers.admin.page;

import com.lowagie.text.DocumentException;
import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceContractType;
import com.yimei.finance.representation.common.contract.ContractServiceImpl;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.service.common.file.PDF;
import com.yimei.finance.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/finance/admin")
@Api(tags = {"admin-page"}, description = "管理后台-金融页面")
@Controller("adminFinancePageController")
public class FinancePageController {
    @Autowired
    private FinanceOrderContractRepository orderContractRepository;
    @Autowired
    private ContractServiceImpl contractService;

    @RequestMapping(value = "/finance/{financeId}/contract/{type}/preview", method = RequestMethod.GET)
    @ApiOperation(value = "预览金融合同页面", notes = "预览金融合同页面")
    public String financeContractPreviewPage(@PathVariable("financeId") Long financeId,
                                             @PathVariable("type") int type, Map<String, Object> model) {
        System.out.println(" ----------------------------- " + EnumFinanceContractType.getTypeName(type));
        System.out.println(" ----------------------------- " + EnumFinanceContractType.getTypeName(type));
        System.out.println(" ----------------------------- " + EnumFinanceContractType.getTypeName(type));
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        if (orderContractRepository.findByFinanceIdAndType(financeId, type) == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        model.put("contract", contractService.getFinanceOrderFormalContractContent(financeId, type));
        return "admin/contract/contractPreview";
    }

    @RequestMapping(value = "/finance/{financeId}/contract/{type}/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载金融合同", notes = "下载金融合同")
    public void financeContractDownload(@PathVariable("financeId") Long financeId,
                                        @PathVariable("type") int type, HttpServletResponse response) throws IOException, DocumentException {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        FinanceOrderContract financeOrderContract = orderContractRepository.findByFinanceIdAndType(financeId, type);
        if (financeOrderContract == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        String contract = contractService.getFinanceOrderFormalContractContent(financeId, type);
        File file = PDF.createByHtml(contract);
        WebUtils.doDownloadFile(file, "合同-" + financeOrderContract.getContractNo(), response);
    }

}
