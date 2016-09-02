package com.yimei.finance.entity.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupObject implements Serializable {
    private String id;
    private String name;
    private String type;
    private int memberNums;

}
