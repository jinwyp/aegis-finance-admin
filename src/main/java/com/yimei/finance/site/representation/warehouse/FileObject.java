package com.yimei.finance.site.representation.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileObject implements Serializable {
    private String id;
    private String originName;
    private String fileType;
}
