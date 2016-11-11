package com.yimei.finance.representation.common.contract;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderContractObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.utils.DozerUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("financeContractService")
public class ContractServiceImpl {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContractServiceImpl.class) ;

    @Autowired
    FreeMarker freeMarker;
    @Autowired
    private FinanceOrderContractRepository orderContractRepository;

    /**
     * 获取正式合同内容
     */
    public String getFinanceOrderFormalContractContent(Long financeId, int type) {
        FinanceOrderContractObject financeOrderContract = DozerUtils.copy(orderContractRepository.findByFinanceIdAndType(financeId, type), FinanceOrderContractObject.class);
        if (financeOrderContract == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        try {
            return freeMarker.render(getFinanceOrderContentTemplateByType(type), new HashMap<String, Object>() {{
                put("contract", financeOrderContract);
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumCommonError.Admin_System_Error);
        }
        return null;
    }


    /**
     * 获取标准(空白)合同内容
     */
    public String getFinanceOrderBlankContractContent(int type) {
        try {
            return freeMarker.render(getFinanceOrderContentTemplateByType(type), new HashMap<String, Object>() {{
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumCommonError.Admin_System_Error);
        }
        return null;
    }



    public String getFinanceOrderContentTemplateByType(int type) {
        if (type == 1) {
            return "/admin/contract/contractup";
        } else if (type == 2) {
            return "/admin/contract/contractdown";
        } else {
            throw new BusinessException(EnumCommonError.Admin_System_Error);
        }
    }

}
