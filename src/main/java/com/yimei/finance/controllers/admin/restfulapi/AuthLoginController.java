package com.yimei.finance.controllers.admin.restfulapi;

import com.yimei.finance.config.AdminSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by liuxinjie on 07/30/16.
 * 登陆相关接口
 */

@RestController
public class AuthLoginController {

    @Autowired
    private AdminSession adminSession;

    /**
     * 登陆方法
     */
    @RequestMapping(value = "/api/financing/admin/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object authLoginWithPassword(String username, String password) {
//        return adminService.login(username, password);
        return new ResponseEntity<String>("{\n" +
                "    \"success\" : true,\n" +
                "    \"error\" : null,\n" +
                "    \"meta\" : null,\n" +
                "    \"data\" : {\n" +
                "        \"id\" : 11,\n" +
                "        \"orderNo\" : 1001\n" +
                "    }\n" +
                "}", HttpStatus.OK);
    }


    /**
     * 退出登陆
     */
    @RequestMapping(value = "/api/financing/admin/logout", method = RequestMethod.GET)
    public Object logout() {
        adminSession.logout();
        return new ResponseEntity<String>("{\n" +
                "    \"success\" : true,\n" +
                "    \"error\" : null,\n" +
                "    \"meta\" : null,\n" +
                "    \"data\" : {\n" +
                "        \"id\" : 11,\n" +
                "        \"orderNo\" : 1001\n" +
                "    }\n" +
                "}", HttpStatus.OK);
    }

}
