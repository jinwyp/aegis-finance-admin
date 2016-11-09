package com.yimei.finance.representation.common.contract;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("financeContractService")
public class ContractServiceImpl {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContractServiceImpl.class) ;

    @Autowired
    FreeMarker freeMarker ;

    /**
     * 获取正式合同内容
     */
    public String getFinanceOrderFormalContractContent(Long financeId, int type) {
        try {
            return freeMarker.render(getFinanceOrderContentTemplateByType(type), new HashMap<String, Object>() {{
                int year=0;
                int month=0;
                int day=0;
                put("contractNo", "HT201611090001");
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
    public String getFinanceOrderBlankContractContent(Long financeId, int type) {
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
            return "/admin/src/admin/contractup";
        } else if (type == 2) {
            return "/admin/src/admin/contractup";
        } else {
            throw new BusinessException(EnumCommonError.Admin_System_Error);
        }
    }

}
