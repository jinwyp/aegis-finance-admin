package com.yimei.finance.admin.representation.company.object;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RiskCompanySearch implements Serializable {
    private String name;                                 //风控线名称
    private String adminName;                            //风控线管理员账号
}
