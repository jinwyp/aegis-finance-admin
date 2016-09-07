package com.yimei.finance.representation.admin.finance.object;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttachmentObjectList implements Serializable {
    private List<AttachmentObject> attachmentList;
}
