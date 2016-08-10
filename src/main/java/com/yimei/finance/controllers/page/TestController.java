package com.yimei.finance.controllers.page;

import com.yimei.finance.entity.tpl.Tpl;
import com.yimei.finance.service.tpl.TplServiceImpl;
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


    @RequestMapping("/test/123456")
    public Object test() {
        tplService.save(new Tpl("hello"));
        return true;
    }
}
