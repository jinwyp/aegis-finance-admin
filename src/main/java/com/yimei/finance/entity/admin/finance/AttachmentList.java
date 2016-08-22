package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuxinjie on 16/8/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentList implements Serializable {
    private List<AttachmentObject> attachmentObjects;
}
