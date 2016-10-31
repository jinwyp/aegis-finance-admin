package com.yimei.finance.service.admin.company;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyRole;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.repository.admin.company.CompanyRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRepository;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyError;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyStatus;
import com.yimei.finance.representation.admin.company.object.CompanyObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
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
    private CompanyRoleRelationShipRepository companyRoleRelationShipRepository;
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private CompanyRelationShipRepository companyRelationShipRepository;

    /**
     * 创建公司
     */
    public Result addCompany(CompanyObject companyObject) {
        Company company = companyRepository.getCompanyByName(companyObject.getName());
        if (company != null) {
            return Result.error(EnumCompanyError.已存在同名的公司.toString());
        } else if (EnumCompanyRole.idList().indexOf(companyObject.getType()) == -1) {
            return Result.error(EnumCompanyError.公司类型错误.toString());
        } else {
            company = DozerUtils.copy(companyObject, Company.class);
            company.setStatus(EnumCompanyStatus.Normal.toString());
            company.setStatusId(EnumCompanyStatus.Normal.id);
            companyRepository.save(company);
            CompanyRole companyRole = companyRoleRepository.findByNumber(companyObject.getType());
            if (companyRole == null) return Result.error(EnumCommonError.Admin_System_Error);
            companyRoleRelationShipRepository.save(new CompanyRoleRelationShip(company.getId(), companyRole.getNumber(), companyRole.getRole()));
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
            company.setStatus(EnumCompanyStatus.Normal.toString());
            company.setStatusId(EnumCompanyStatus.Normal.id);
            companyRepository.save(company);
            return Result.success();
        }
    }

    public Result findById(Long id) {
        Company company = companyRepository.findOne(id);
        return Result.success().setData(changeCompanyObject(company));
    }

    public CompanyObject changeCompanyObject(Company company) {
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        List<String> roleList = companyRoleRelationShipRepository.findRoleByCompanyId(company.getId());
        if (roleList != null && roleList.size() != 0) {
            String roleName = null;
            for (String role : roleList) {
                if (roleList.indexOf(role) == 0) {
                    roleName += EnumCompanyRole.valueOf(role).name;
                } else {
                    roleName += ", " + EnumCompanyRole.valueOf(role).name;
                }
            }
            companyObject.setRoleName(roleName);
        }
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
    public Result findCompanyListByRole(int type) {
        List<Company> companyList = new ArrayList<>();
        List<Long> companyIds = companyRoleRelationShipRepository.findCompanyIdByRoleNumberOrderByCompanyIdDesc(type);
        if (companyIds != null && companyIds.size() != 0) {
            for (Long id : companyIds) {
                Company company = companyRepository.findOne(id);
                if (company == null) return Result.error(EnumCommonError.Admin_System_Error);
                companyList.add(company);
            }
        }
        return Result.success().setData(changeCompanyObject(companyList));
    }

    /**
     * 根据 id 删除 公司
     */
    public Result deleteCompany(Long id) {
        Company company = companyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.此公司不存在.toString());
        company.setStatusId(EnumCompanyStatus.Deleted.id);
        company.setStatus(EnumCompanyStatus.Deleted.toString());
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        companyRepository.save(company);
        return Result.success().setData(companyObject);
    }
}