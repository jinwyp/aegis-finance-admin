package com.yimei.finance.entity.common.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by liuxinjie on 16/6/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileList {
    private List<File> files;
}
