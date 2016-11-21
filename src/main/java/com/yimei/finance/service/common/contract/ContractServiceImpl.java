package com.yimei.finance.service.common.contract;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderContractObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.utils.DozerUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("financeContractService")
public class ContractServiceImpl {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContractServiceImpl.class) ;

    @Autowired
    FreeMarker freeMarker;
    @Autowired
    private FinanceOrderContractRepository orderContractRepository;

    /**
     * 获取正式合同内容
     */
    public String getFinanceOrderContractContent(Long financeId, int type, boolean formal) {
        FinanceOrderContractObject financeOrderContract = DozerUtils.copy(orderContractRepository.findByFinanceIdAndType(financeId, type), FinanceOrderContractObject.class);
        try {
            return freeMarker.render(getFinanceOrderContentTemplateByType(type, formal), new HashMap<String, Object>() {{
                if (formal) {
                    put("contract", financeOrderContract);
                }
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumCommonError.Admin_System_Error);
        }
        return null;
    }


    public String getFinanceOrderContentTemplateByType(int type, boolean formal) {
        if (type == 1 && formal) {
            return "/admin/contract/contractup";
        } else if (type == 1 && !formal) {
            return "/admin/contract/contractupblank";
        } else if (type == 2 && formal) {
            return "/admin/contract/contractdown";
        } else if (type == 2 && !formal) {
            return "/admin/contract/contractdownblank";
        } else {
            throw new BusinessException(EnumCommonError.Admin_System_Error);
        }
    }

}