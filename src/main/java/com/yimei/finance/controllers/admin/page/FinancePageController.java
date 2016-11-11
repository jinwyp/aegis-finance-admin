package com.yimei.finance.controllers.admin.page;

import com.lowagie.text.DocumentException;
import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceContractType;
import com.yimei.finance.representation.common.contract.ContractServiceImpl;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.service.common.file.PDF;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
        if (orderContractRepository.findByFinanceIdAndType(financeId, type) == null) {
            model.put("contract", contractService.getFinanceOrderBlankContractContent(type));
        } else {
            model.put("contract", contractService.getFinanceOrderFormalContractContent(financeId, type));
        }
        return "admin/contract/contractPreview";
    }

    @RequestMapping(value = "/finance/{financeId}/contract/{type}/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载金融合同", notes = "下载金融合同")
    public HttpEntity<byte[]> financeContractDownload(@PathVariable("financeId") Long financeId,
                                        @PathVariable("type") int type, HttpServletResponse response) throws IOException, DocumentException {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        FinanceOrderContract financeOrderContract = orderContractRepository.findByFinanceIdAndType(financeId, type);
        String contract = "";
        if (orderContractRepository.findByFinanceIdAndType(financeId, type) == null) {
            contract = contractService.getFinanceOrderBlankContractContent(type);
        } else {
            contract = contractService.getFinanceOrderFormalContractContent(financeId, type);
        }
        File file = PDF.createByHtml(contract);
        HttpHeaders headers = new HttpHeaders();
        String fileName = "合同-" + financeOrderContract.getContractNo() + ".pdf";
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName,"UTF-8"));
        return new HttpEntity<>(FileUtils.readFileToByteArray(file), headers);

    }

}
