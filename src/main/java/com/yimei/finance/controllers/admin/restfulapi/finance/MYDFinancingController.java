package com.yimei.finance.controllers.admin.restfulapi.finance;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "admin-api-flow-myd", description = "煤易贷相关接口")
@RequestMapping("/api/financing/admin/myd")
@RestController("adminMYDFinancingController")
public class MYDFinancingController {

}
