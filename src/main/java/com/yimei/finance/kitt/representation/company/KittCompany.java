package com.yimei.finance.kitt.representation.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class KittCompany implements Serializable {
    private int id;
    private int userid;
    @NotBlank
    @Length(max = 50,message = "公司名称不能超过50个字符")
    private String name;
    private String verifystatus;          //公司信息审核状态

}
