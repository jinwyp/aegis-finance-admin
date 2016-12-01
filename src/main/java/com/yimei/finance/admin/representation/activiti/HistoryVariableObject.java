package com.yimei.finance.admin.representation.activiti;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.common.representation.enums.EnumCommonString;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class HistoryVariableObject implements Serializable {
    private String id;
    private String variableName;
    private String variableTypeName;
    private Object value;
    private String processInstanceId;
    private String taskId;
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date lastUpdatedTime;
}
