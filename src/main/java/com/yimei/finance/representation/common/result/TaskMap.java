package com.yimei.finance.representation.common.result;

import java.io.Serializable;

public class TaskMap implements Serializable {
    public int need;                            //是否需要补充材料1   0:不需要, 1:需要
    public int need2;                           //是否需要补充材料2
    public int pass;                            //审核是否通过       0:审核不通过, 1:审核通过

}
