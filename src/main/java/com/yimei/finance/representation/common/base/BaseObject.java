package com.yimei.finance.representation.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class BaseObject implements Serializable {
    private String createManId;                                      //创建人id
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;                                         //创建时间
    @Column(name = "last_update_man_id")
    private String lastUpdateManId;                                  //最后一次更新人id
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date lastUpdateTime;                                     //最后一次更新时间
}
