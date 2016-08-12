package com.yimei.finance.controllers.page;

import com.yimei.finance.entity.tpl.Tpl;
import com.yimei.finance.repository.common.result.Error;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.service.tpl.TplServiceImpl;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/9.
 */
@RestController
public class TestController {
    @Autowired
    TplServiceImpl tplService;
    @Autowired
    IdentityService identityService;


    @RequestMapping("/api/test/1")
    public Object test1(Page page) {
        tplService.save(new Tpl("jack"));
//        User user = identityService.createUserQuery().orderByUserId().desc().list().get(0);
//        System.out.println(" -------------------------- " + user.getId() + " --- " + user.getFirstName() + " --- " + user.getLastName() + " --- " + user.getEmail());
        User user = identityService.newUser("");
        user.setId(null);
        user.setFirstName("jack");
        user.setLastName("liu");
        identityService.saveUser(user);
        System.out.println(" ------------------ " + user.getId());
        System.out.println(" ------------------ " + user.getLastName());

        return Result.success().setData("hello").setMeta(page);
    }

    @RequestMapping("/api/test/2")
    public Object test2(Page page) {
        tplService.save(new Tpl("jack"));
        return Result.error(new Error(1001, "出错了", null));
    }
}
