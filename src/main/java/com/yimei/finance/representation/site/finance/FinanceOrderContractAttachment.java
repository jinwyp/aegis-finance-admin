package com.yimei.finance.representation.site.finance;

import com.yimei.finance.representation.common.file.AttachmentObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "一个金融单中合同附件列表")
@Data
@NoArgsConstructor
public class FinanceOrderContractAttachment {
    List<AttachmentObject> attachmentList1;                          //下游合同附件list
    List<AttachmentObject> attachmentList2;                          //下游合同附件list
}
