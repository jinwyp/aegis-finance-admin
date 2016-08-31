package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.basic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderTest extends BaseEntity implements Serializable {
    @Column(name = "apply_type", length = 20)
    @Size(min = 3, max = 10, message = "申请类型字段应在3-10个字符之间")
    @NotBlank(message = "申请类型字段不能为空")
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    private ArrayList<AttachmentObject> attachmentList;                   //线上交易员上传表单列表

}
