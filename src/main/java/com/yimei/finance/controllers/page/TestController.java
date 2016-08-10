package com.yimei.finance.controllers.page;

import com.yimei.finance.entity.tpl.Tpl;
import com.yimei.finance.repository.common.result.Error;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
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


    @RequestMapping("/test/1")
    public Object test1(Page page) {
        tplService.save(new Tpl("jack"));
        return Result.success().setData("hello").setMeta(page);
    }

    @RequestMapping("/test/2")
    public Object test2(Page page) {
        tplService.save(new Tpl("jack"));
        return Result.error(new Error(1001, "出错了", null));
    }
}
