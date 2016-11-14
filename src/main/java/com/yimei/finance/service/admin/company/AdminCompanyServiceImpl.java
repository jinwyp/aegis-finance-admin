package com.yimei.finance.service.admin.company;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyRole;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRelationShipRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRepository;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyError;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyStatus;
import com.yimei.finance.representation.admin.company.object.CompanyObject;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserStatus;
import com.yimei.finance.representation.admin.user.object.UserObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCompanyServiceImpl {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyRoleRelationShipRepository companyRoleRelationShipRepository;
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    /**
     * 创建公司
     */
    @Transactional
    public Result addCompany(CompanyObject companyObject, UserObject sessionUser) {
        Result result = userService.checkSuperAdminRight(sessionUser.getId());
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findByName(companyObject.getName());
        if (company != null && company.getStatusId() != EnumCompanyStatus.Deleted.id) {
            return Result.error(EnumCompanyError.此名称已存在.toString());
        } else if (EnumCompanyRole.idList().indexOf(companyObject.getType()) == -1) {
            return Result.error(EnumCompanyError.类型错误.toString());
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
    @Transactional
    public Result editCompany(Long id, CompanyObject companyObject, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.对象不存在.toString());
        Company company1 = companyRepository.findByName(companyObject.getName());
        if (company1 != null && company1.getStatusId() != EnumCompanyStatus.Deleted.id && company1.getId().longValue() != id.longValue()) {
            return Result.error(EnumCompanyError.此名称已存在.toString());
        } else {
            company.setName(companyObject.getName());
            company.setRemarks(companyObject.getRemarks());
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
        if (company.getStatusId() == EnumCompanyStatus.Deleted.id) return Result.error(EnumCompanyError.已经被删除.toString());
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
        List<UserObject> userObjectList = userService.changeUserObjectSimple(identityService.createUserQuery().list()).parallelStream().filter(user -> (user.getStatus().equals(EnumAdminUserStatus.Normal.toString()))).collect(Collectors.toList());
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        if (company != null) {
            List<String> roleList = companyRoleRelationShipRepository.findRoleByCompanyId(company.getId());
            if (roleList != null && roleList.size() != 0) {
                String roleName = "";
                for (String role : roleList) {
                    roleName += EnumCompanyRole.valueOf(role).name + " ";
                }
                companyObject.setRoleName(roleName);
            }
            companyObject.setAdminName(userService.findCompanyFirstAdminName(company.getId()));
            companyObject.setPersonNum(userObjectList.parallelStream().filter(u -> u.getCompanyId().longValue() == company.getId().longValue()).count());
        }
        return companyObject;
    }

    public List<CompanyObject> changeCompanyObject(List<Company> companyList) {
        List<CompanyObject> companyObjectList = new ArrayList<>();
        if (companyList != null && companyList.size() != 0) {
            List<UserObject> userObjectList = userService.changeUserObjectSimple(identityService.createUserQuery().list()).parallelStream().filter(user -> user.getStatus().equals(EnumAdminUserStatus.Normal.toString())).collect(Collectors.toList());
            companyObjectList = DozerUtils.copy(companyList, CompanyObject.class);
            companyObjectList.parallelStream().forEach(company -> {
                company.setPersonNum(userObjectList.parallelStream().filter(u -> u.getCompanyId().longValue() == company.getId().longValue()).count());
                company.setAdminName(userService.findCompanyFirstAdminName(company.getId()));
            });
        }
        return companyObjectList;
    }

    /**
     * 超级管理员, 交易员获取风控线列表
     */
    public Result adminFindRiskCompanyList(Long sessionCompanyId) {
        if (sessionCompanyId.longValue() != 0) {
            return Result.error(EnumCompanyError.你没有权限查看风控线列表.toString());
        }
        return findCompanyListByRole(EnumCompanyRole.RiskManager_Organization.id);
    }

    /**
     * 超级管理员, 交易员获取风控线列表
     */
    public Result adminFindRiskCompanyList(Long sessionCompanyId, Page page) {
        if (sessionCompanyId.longValue() != 0) {
            return Result.error(EnumCompanyError.你没有权限查看风控线列表.toString());
        }
        return findCompanyListByRole(EnumCompanyRole.RiskManager_Organization.id, page);
    }

    /**
     * 根据角色获取公司列表
     */
    public Result findCompanyListByRole(int type) {
        List<Company> companyList = getNormalCompanyListByIdList(companyRoleRelationShipRepository.findCompanyIdByRoleNumberOrderByCompanyIdDesc(type));
        return Result.success().setData(changeCompanyObject(companyList));
    }

    /**
     * 根据角色获取公司列表
     */
    public Result findCompanyListByRole(int type, Page page) {
        List<Company> companyList = getNormalCompanyListByIdList(companyRoleRelationShipRepository.findCompanyIdByRoleNumberOrderByCompanyIdDesc(type));
        page.setTotal((long) companyList.size());
        int toIndex = page.getPage() * page.getCount() < companyList.size() ? page.getPage() * page.getCount() : companyList.size();
        return Result.success().setData(changeCompanyObject(companyList.subList(page.getOffset(), toIndex))).setMeta(page);
    }

    /**
     * 根据 id 删除 公司
     */
    @Transactional
    public Result deleteCompany(Long id, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = companyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.对象不存在.toString());
        if (company.getStatusId() == EnumCompanyStatus.Deleted.id) return Result.error(EnumCompanyError.已经被删除.toString());
        company.setStatusId(EnumCompanyStatus.Deleted.id);
        company.setStatus(EnumCompanyStatus.Deleted.toString());
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        companyRepository.save(company);
        List<UserObject> userObjectList = userService.getUserByRiskCompanyId(company.getId());
        userObjectList.parallelStream().forEach(user -> {
            identityService.setUserInfo(user.getId(), "status", EnumAdminUserStatus.Deleted.toString());
        });
        return Result.success().setData(companyObject);
    }

    public List<Company> getNormalCompanyListByIdList(List<Long> companyIdList) {
        List<Company> companyList = new ArrayList<>();
        if (companyIdList != null && companyIdList.size() != 0) {
            companyIdList.parallelStream().forEach(id -> {
                Company company = companyRepository.findByIdAndStatusId(id, EnumCompanyStatus.Normal.id);
                System.out.println(" ------11111111111111111111111111111111111----- " + (company==null));
                if (company != null) {
                    companyList.add(companyRepository.findByIdAndStatusId(id, EnumCompanyStatus.Normal.id));
                }
            });
        }
        return companyList;
    }

}
