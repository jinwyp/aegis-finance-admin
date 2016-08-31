package com.yimei.finance.representation.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapObject implements Serializable {
    private String id;
    private String name;
}
