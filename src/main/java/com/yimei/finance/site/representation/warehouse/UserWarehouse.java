package com.yimei.finance.site.representation.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWarehouse implements Serializable {
    private String userId;
    private String userPhone;
    private String companyId;
}
