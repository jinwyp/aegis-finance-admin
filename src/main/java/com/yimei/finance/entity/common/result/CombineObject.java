package com.yimei.finance.entity.common.result;

import com.yimei.finance.entity.site.user.FinanceOrderSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombineObject implements Serializable {
    public FinanceOrderSearch financeOrderSearch;
    public Page page;
}
