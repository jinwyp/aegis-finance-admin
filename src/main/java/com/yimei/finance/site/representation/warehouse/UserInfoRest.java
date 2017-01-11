package com.yimei.finance.site.representation.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserInfoRest implements Serializable {
    private String userId;
    private String userPhone;
    private String companyId;
}
