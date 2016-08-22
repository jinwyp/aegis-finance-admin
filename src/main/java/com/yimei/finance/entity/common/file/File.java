package com.yimei.finance.entity.common.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/6/1.
 * 文件对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File implements Serializable {
    private String name;                   //文件名称
    private String path;                   //文件路径

}
