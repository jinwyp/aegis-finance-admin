package com.yimei.finance.site.representation.warehouse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehousePostObject implements Serializable {
    public String flowId;
    public String taskId;
    public List<String> fileList;
    public BigDecimal repaymentValue;

    public WarehousePostObject(String flowId, String taskId, List<String> fileList) {
        this.flowId = flowId;
        this.taskId = taskId;
        this.fileList = fileList;
    }

    public WarehousePostObject(String flowId, String taskId, BigDecimal repaymentValue) {
        this.flowId = flowId;
        this.taskId = taskId;
        this.repaymentValue = repaymentValue;
    }
}
