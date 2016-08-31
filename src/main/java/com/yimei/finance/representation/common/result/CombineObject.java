package com.yimei.finance.representation.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombineObject<T, U> implements Serializable {
    public T t;
    public U u;
}
