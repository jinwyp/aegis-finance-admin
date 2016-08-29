package com.yimei.finance.entity.common.result;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class TaskMap implements Serializable {
    private int need;                            //是否需要补充材料1   0:不需要, 1:需要
    private int need2;                           //是否需要补充材料2
    private int pass;                            //审核是否通过       0:审核不通过, 1:审核通过
    @NotBlank(message = "是否提交参数不能为空")
    private int submit;                          //是否提交          0:不提交,只是保存资料, 1:提交
    private String comment;                      //备注

}
