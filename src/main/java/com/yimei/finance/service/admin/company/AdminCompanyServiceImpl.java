package com.yimei.finance.service.admin.company;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.repository.admin.company.CompanyRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.representation.admin.company.object.CompanyObject;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyError;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCompanyServiceImpl {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyRelationShipRepository companyRelationShipRepository;

    /**
     * 创建公司
     */
    public Result addCompany(CompanyObject companyObject) {
        Company company = companyRepository.getCompanyByName(companyObject.getName());
        if (company != null) {
            return Result.error(EnumCompanyError.已存在同名的公司.toString());
        } else {
            companyObject.setTypeName(EnumCompanyRole.getTypeName(companyObject.getType()));
            company = DozerUtils.copy(companyObject, Company.class);
            companyRepository.save(company);
            return Result.success().setData(changeCompanyObject(companyRepository.findOne(company.getId())));
        }

    }

    /**
     * 修改公司
     */
    public Result editCompany(CompanyObject companyObject) {
        Company company = companyRepository.findOne(companyObject.getId());
        if (company == null) {
            return Result.error(EnumCompanyError.此公司不存在.toString());
        } else {

            return Result.success();
        }
    }

    public Result findById(Long id) {
        Company company = companyRepository.findOne(id);
        return Result.success().setData(changeCompanyObject(company));
    }

    public CompanyObject changeCompanyObject(Company company) {
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        return companyObject;
    }

    public List<CompanyObject> changeCompanyObject(List<Company> companyList) {
        List<CompanyObject> companyObjectList = new ArrayList<>();
        for (Company company : companyList) {
            companyObjectList.add(changeCompanyObject(company));
        }
        return companyObjectList;
    }

    /**
     * 根据角色获取公司列表
     */
    public Result findCompamyListByType(int type) {
        List<Company> companyList = new ArrayList<>();
        return null;
//        return Result.success().setData(changeCompanyObject());
    }
}
