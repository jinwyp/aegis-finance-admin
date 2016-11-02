package com.yimei.finance.service.admin.company;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyFBRelationShip;
import com.yimei.finance.entity.admin.company.CompanyRole;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.repository.admin.company.CompanyFBRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRepository;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyError;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyStatus;
import com.yimei.finance.representation.admin.company.object.CompanyObject;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminCompanyServiceImpl {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyRoleRelationShipRepository companyRoleRelationShipRepository;
    @Autowired
    private CompanyFBRelationShipRepository companyFBRelationShipRepository;
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private AdminUserServiceImpl userService;

    /**
     * 创建公司
     */
    public Result addCompany(CompanyObject companyObject, UserObject sessionUser) {
        Result result = userService.checkSuperAdminRight(sessionUser.getId());
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findByName(companyObject.getName());
        if (company != null) {
            return Result.error(EnumCompanyError.已存在同名的公司.toString());
        } else if (EnumCompanyRole.idList().indexOf(companyObject.getType()) == -1) {
            return Result.error(EnumCompanyError.公司类型错误.toString());
        } else {
            company = DozerUtils.copy(companyObject, Company.class);
            company.setStatus(EnumCompanyStatus.Normal.toString());
            company.setStatusId(EnumCompanyStatus.Normal.id);
            company.setCreateManId(sessionUser.getId());
            company.setCreateTime(new Date());
            company.setLastUpdateManId(sessionUser.getId());
            company.setLastUpdateTime(new Date());
            companyRepository.save(company);
            CompanyRole companyRole = companyRoleRepository.findByNumber(companyObject.getType());
            if (companyRole == null) return Result.error(EnumCommonError.Admin_System_Error);
            companyRoleRelationShipRepository.save(new CompanyRoleRelationShip(company.getId(), companyRole.getNumber(), companyRole.getRole(), new Date(), sessionUser.getId(), new Date(), sessionUser.getId()));
            return Result.success().setData(changeCompanyObject(companyRepository.findOne(company.getId())));
        }
    }

    /**
     * 修改公司
     */
    public Result editCompany(Long id, CompanyObject companyObject, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.此公司不存在.toString());
        Company company1 = companyRepository.findByName(companyObject.getName());
        if (company1 != null && company1.getId() != id) {
            return Result.error(EnumCompanyError.已存在同名的公司.toString());
        } else {
            company.setName(companyObject.getName());
            company.setStatus(EnumCompanyStatus.Normal.toString());
            company.setStatusId(EnumCompanyStatus.Normal.id);
            company.setLastUpdateManId(sessionUserId);
            company.setLastUpdateTime(new Date());
            companyRepository.save(company);
            return Result.success();
        }
    }

    /**
     * 根据 id 查询公司
     */
    public Result findById(Long id) {
        Company company = companyRepository.findOne(id);
        CompanyObject companyObject = new CompanyObject();
        if (companyObject != null) companyObject = changeCompanyObject(company);
        return Result.success().setData(companyObject);
    }

    public Result findByIdWithAuthority(Long id, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        return findById(id);
    }

    /**
     * 封装公司对象
     */
    public CompanyObject changeCompanyObject(Company company) {
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        if (company != null) {
//            List<String> roleList = companyRoleRelationShipRepository.findRoleByCompanyId(company.getId());
//            if (roleList != null && roleList.size() != 0) {
//                String roleName = "";
//                for (String role : roleList) {
//                    roleName += EnumCompanyRole.valueOf(role) + " ";
//                }
//                companyObject.setRoleName(roleName);
//            }
            companyObject.setAdminName(userService.findCompanyFirstAdminName(company.getId()));
        }
        return companyObject;
    }

    public List<CompanyObject> changeCompanyObject(List<Company> companyList) {
        List<CompanyObject> companyObjectList = new ArrayList<>();
        companyList.forEach(company -> {
            companyObjectList.add(changeCompanyObject(company));
        });
        return companyObjectList;
    }

    /**
     * 根据角色获取公司列表
     */
    public Result findCompanyListByRole(int type, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        List<Company> companyList = getNormalCompanyListByIdList(companyRoleRelationShipRepository.findCompanyIdByRoleNumberOrderByCompanyIdDesc(type));
        return Result.success().setData(changeCompanyObject(companyList));
    }

    /**
     * 根据 id 删除 公司
     */
    public Result deleteCompany(Long id, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.此公司不存在.toString());
        company.setStatusId(EnumCompanyStatus.Deleted.id);
        company.setStatus(EnumCompanyStatus.Deleted.toString());
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        companyRepository.save(company);
        return Result.success().setData(companyObject);
    }

    public Result findFundCompanyListByCompanyId(Long  companyId) {
        List<Company> companyList = getNormalCompanyListByIdList(companyFBRelationShipRepository.findFundCompanyIdByBusinessCompanyId(companyId));
        return Result.success().setData(changeCompanyObject(companyList));
    }

    public List<Company> getNormalCompanyListByIdList(List<Long> companyIdList) {
        List<Company> companyList = new ArrayList<>();
        if (companyIdList != null && companyIdList.size() != 0) {
            companyIdList.forEach(id -> {
                Company company = companyRepository.findByIdAndStatusId(id, EnumCompanyStatus.Normal.id);
                if (company != null) companyList.add(company);
            });
        }
        return companyList;
    }

    /**
     * 创建-业务线和资金方关系
     */
    public Result createBusinessFundCompanyRelation(Long businessCompanyId, Long fundCompanyId, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        if (companyFBRelationShipRepository.findByBusinessCompanyIdAndFundCompanyId(businessCompanyId, fundCompanyId) != null)
            return Result.error(EnumCompanyError.该业务线和资金方已经建立关系.toString());
        companyFBRelationShipRepository.save(new CompanyFBRelationShip(businessCompanyId, fundCompanyId, new Date(), sessionUserId, new Date(), sessionUserId));
        return Result.success();
    }

    /**
     * 解除-业务线和资金方关系
     */
    public Result deleteBusinessFundCompanyRelation(Long businessCompanyId, Long fundCompanyId, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        CompanyFBRelationShip companyFBRelationShip = companyFBRelationShipRepository.findByBusinessCompanyIdAndFundCompanyId(businessCompanyId, fundCompanyId);
        if (companyFBRelationShip == null) return Result.error(EnumCompanyError.该业务线和资金方关系已经解除.toString());
        companyFBRelationShipRepository.delete(companyFBRelationShip.getId());
        return Result.success();
    }
}
