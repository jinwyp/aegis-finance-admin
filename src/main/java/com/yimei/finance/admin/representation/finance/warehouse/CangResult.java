package com.yimei.finance.admin.representation.finance.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CangResult implements Serializable {
    public boolean success;
}
