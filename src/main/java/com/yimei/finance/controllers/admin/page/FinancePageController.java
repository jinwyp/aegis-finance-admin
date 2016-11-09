package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceContractType;
import com.yimei.finance.representation.common.contract.ContractServiceImpl;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/finance/admin")
@Api(tags = {"admin-page"}, description = "管理后台-金融页面")
@Controller("adminFinancePageController")
public class FinancePageController {
    @Autowired
    private FinanceOrderContractRepository orderContractRepository;
    @Autowired
    private ContractServiceImpl contractService;

    @RequestMapping(value = "/finance/{financeId}/contract/{type}" , method = RequestMethod.GET)
    @ApiOperation(value = "合同页面", notes = "合同页面")
    public String contractPage(@PathVariable("financeId") Long financeId,
                               @PathVariable("type") int type) {
        if (StringUtils.isEmpty(EnumFinanceContractType.getTypeName(type))) throw new NotFoundException(EnumCommonError.传入参数错误.toString());
        if (orderContractRepository.findByFinanceIdAndType(financeId, type) == null) throw new NotFoundException(EnumAdminFinanceError.此合同不存在.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("contract", contractService.getFinanceOrderFormalContractContent(financeId, type));
        return "admin/contractPreview";
    }
}
