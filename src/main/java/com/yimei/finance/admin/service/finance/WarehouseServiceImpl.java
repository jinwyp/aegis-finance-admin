package com.yimei.finance.admin.service.finance;

import com.yimei.finance.admin.repository.finance.AdminFinanceOrderContractRepository;
import com.yimei.finance.admin.repository.finance.AdminFinanceOrderInvestigatorRepository;
import com.yimei.finance.admin.repository.finance.AdminFinanceOrderSupervisorRepository;
import com.yimei.finance.admin.representation.finance.object.FinanceOrderContractObject;
import com.yimei.finance.admin.representation.finance.object.FinanceOrderInvestigatorInfoObject;
import com.yimei.finance.admin.representation.finance.warehouse.WarehouseData;
import com.yimei.finance.admin.representation.finance.warehouse.WarehouseInitData;
import com.yimei.finance.admin.representation.finance.warehouse.WarehouseInvestigatorInfo;
import com.yimei.finance.admin.representation.finance.warehouse.WarehouseSupervisorInfo;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminWarehouseServiceImpl")
public class WarehouseServiceImpl {
    @Autowired
    private AdminFinanceOrderContractRepository orderContractRepository;
    @Autowired
    private AdminFinanceOrderInvestigatorRepository investigatorRepository;
    @Autowired
    private AdminFinanceOrderSupervisorRepository supervisorRepository;
    @Autowired
    private TaskService taskService;

    public WarehouseData getWarehouseData(Long financeId, FinanceOrder financeOrder, Task task) {
        WarehouseInitData initData = new WarehouseInitData();
        initData.setApplyCompanyId(String.valueOf(financeOrder.getApplyCompanyId()));
        initData.setApplyCompanyName(financeOrder.getApplyCompanyName());
        initData.setApplyUserId(String.valueOf(financeOrder.getUserId()));
        initData.setApplyUserName(financeOrder.getApplyUserName());
        initData.setApplyUserPhone(financeOrder.getApplyUserPhone());
        initData.setBusinessCode(financeOrder.getSourceId());
        initData.setFinanceCreateTime(financeOrder.getCreateTime());
        initData.setFinancingAmount(financeOrder.getFinancingAmount());
        initData.setFinancingDays(financeOrder.getExpectDate());
        FinanceOrderInvestigatorInfoObject investigatorInfoObject = DozerUtils.copy(investigatorRepository.findByFinanceId(financeId), FinanceOrderInvestigatorInfoObject.class);
        initData.setInterestRate(investigatorInfoObject.getInterestRate());
        //initData.setAuditFileList(DozerUtils.copy(taskService.getProcessInstanceAttachments(task.getProcessInstanceId()), AttachmentObject.class));

        FinanceOrderContractObject upstreamContract = DozerUtils.copy(orderContractRepository.findByFinanceIdAndType(financeId, 1), FinanceOrderContractObject.class);
        initData.setUpstreamContractNo(upstreamContract.getContractNo());
        initData.setCoalAmount(upstreamContract.getCoalAmount());
        initData.setCoalIndex_ADV(upstreamContract.getCoalIndex_ADV());
        initData.setCoalIndex_NCV(upstreamContract.getCoalIndex_NCV());
        initData.setCoalIndex_RS(upstreamContract.getCoalIndex_RS());
        initData.setCoalType(upstreamContract.getCoalType());
        initData.setStockPort(upstreamContract.getDeliveryPlace());
        FinanceOrderContractObject downstreamContract = DozerUtils.copy(orderContractRepository.findByFinanceIdAndType(financeId, 2), FinanceOrderContractObject.class);
        initData.setDownstreamContractNo(downstreamContract.getContractNo());
        initData.setDownstreamCompanyName(downstreamContract.getBuyerCompanyName());

        WarehouseInvestigatorInfo investigatorInfo = DozerUtils.copy(investigatorRepository.findByFinanceId(financeId), WarehouseInvestigatorInfo.class);
        WarehouseSupervisorInfo supervisorInfo = DozerUtils.copy(supervisorRepository.findByFinanceId(financeId), WarehouseSupervisorInfo.class);
        return new WarehouseData(initData, investigatorInfo, supervisorInfo);
    }
}
