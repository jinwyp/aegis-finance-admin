package com.yimei.finance.entity.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/8/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserObject implements Serializable {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
}
