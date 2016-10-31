package com.yimei.finance.controllers.admin.restfulapi.company;

import com.yimei.finance.representation.admin.company.object.CompanyObject;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.admin.company.validated.CreateCompany;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.company.AdminCompanyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "admin-api-company")
@RequestMapping("/api/financing/admin/company")
@RestController("adminCompanyController")
public class CompanyController {
    @Autowired
    private AdminCompanyServiceImpl companyService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取公司对象", response = CompanyObject.class)
    public Result findByIdMethod(@PathVariable("id")Long id) {
        return companyService.findById(id);
    }

    @RequestMapping(value = "/business", method = RequestMethod.GET)
    @ApiOperation(value = "获取业务组织列表", response = CompanyObject.class, responseContainer = "List")
    public Result findBusinessOrganizationListMethod() {
        return companyService.findCompamyListByType(EnumCompanyRole.Business_Organization.id);
    }

    @RequestMapping(value = "/fund", method = RequestMethod.GET)
    @ApiOperation(value = "获取资金方公司列表", response = CompanyObject.class, responseContainer = "List")
    public Result findFundProviderListMethod() {
        return companyService.findCompamyListByType(EnumCompanyRole.Fund_Provider.id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建公司", notes = "根据 company 对象创建用户", response = CompanyObject.class)
    public Result addCompanyMethod(@ApiParam(name = "company", value = "公司对象", required = true) @Validated(value = {CreateCompany.class}) @RequestBody CompanyObject company) {
        return companyService.addCompany(company);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "创建公司", notes = "根据 company 对象创建用户", response = CompanyObject.class)
    public Result editCompanyMethod(@ApiParam(name = "company", value = "公司对象", required = true) @Validated(value = {CreateCompany.class}) @RequestBody CompanyObject company) {
        return companyService.addCompany(company);
    }

}
