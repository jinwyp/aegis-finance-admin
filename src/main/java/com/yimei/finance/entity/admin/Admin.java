package com.yimei.finance.entity.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by joe on 11/4/14.
 */
@Data
public class Admin implements Serializable {
    private String id;
    private int revision;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;       //创建时间

}
