package com.yimei.finance.admin.representation.finance.warehouse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehouseSupervisorInfo implements Serializable {
    private String storageLocation;                                  //煤炭仓储地
    private String storageProperty;                                  //仓储性质
    private String storageAddress;                                   //仓储地地址
    private String historicalCooperationDetail;                      //历史合作情况
    private String operatingStorageDetail;                           //经营及堆存情况
    private String portStandardDegree;                               //保管及进出口流程规范程度
    private String supervisionCooperateDetail;                       //监管配合情况
    private String supervisionScheme;                                //监管方案
    private String finalConclusion;                                  //最终结论/综合意见
}
