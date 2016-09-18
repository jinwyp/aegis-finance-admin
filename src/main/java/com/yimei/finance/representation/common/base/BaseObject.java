package com.yimei.finance.representation.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class BaseObject implements Serializable {
    private String createManId;                                      //创建人id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;                                         //创建时间
    @Column(name = "last_update_man_id")
    private String lastUpdateManId;                                  //最后一次更新人id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;                                     //最后一次更新时间
}
