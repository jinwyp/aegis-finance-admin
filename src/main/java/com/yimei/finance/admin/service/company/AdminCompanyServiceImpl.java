package com.yimei.finance.admin.service.company;

import com.yimei.finance.admin.repository.company.AdminCompanyRepository;
import com.yimei.finance.admin.repository.company.AdminCompanyRoleRelationShipRepository;
import com.yimei.finance.admin.repository.company.AdminCompanyRoleRepository;
import com.yimei.finance.admin.representation.company.enums.EnumCompanyError;
import com.yimei.finance.admin.representation.company.enums.EnumCompanyRole;
import com.yimei.finance.admin.representation.company.enums.EnumCompanyStatus;
import com.yimei.finance.admin.representation.company.object.CompanyObject;
import com.yimei.finance.admin.representation.company.object.RiskCompanySearch;
import com.yimei.finance.admin.representation.user.enums.EnumAdminUserStatus;
import com.yimei.finance.admin.representation.user.object.UserObject;
import com.yimei.finance.admin.service.user.AdminUserServiceImpl;
import com.yimei.finance.common.representation.enums.EnumCommonError;
import com.yimei.finance.common.representation.result.Page;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyRole;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("adminCompanyService")
public class AdminCompanyServiceImpl {
    @Autowired
    private AdminCompanyRepository adminCompanyRepository;
    @Autowired
    private AdminCompanyRoleRelationShipRepository adminCompanyRoleRelationShipRepository;
    @Autowired
    private AdminCompanyRoleRepository adminCompanyRoleRepository;
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
        Company company = adminCompanyRepository.findByName(companyObject.getName());
        if (company != null && company.getStatusId() != EnumCompanyStatus.Deleted.id) {
            return Result.error(EnumCompanyError.此名称已存在.toString());
        } else if (EnumCompanyRole.idList().indexOf(companyObject.getType()) == -1) {
            return Result.error(EnumCompanyError.类型错误.toString());
        } else {
            company = DozerUtils.copy(companyObject, Company.class);
            company.setStatus(EnumCompanyStatus.Normal.toString());
            company.setStatusId(EnumCompanyStatus.Normal.id);
            company.setCreateManId(sessionUser.getId());
            company.setCreateTime(LocalDateTime.now());
            company.setLastUpdateManId(sessionUser.getId());
            company.setLastUpdateTime(LocalDateTime.now());
            adminCompanyRepository.save(company);
            CompanyRole companyRole = adminCompanyRoleRepository.findByNumber(companyObject.getType());
            if (companyRole == null) return Result.error(EnumCommonError.Admin_System_Error);
            adminCompanyRoleRelationShipRepository.save(new CompanyRoleRelationShip(company.getId(), companyRole.getNumber(), companyRole.getRole(), LocalDateTime.now(), sessionUser.getId(), LocalDateTime.now(), sessionUser.getId()));
            return Result.success().setData(changeCompanyObject(adminCompanyRepository.findOne(company.getId())));
        }
    }

    /**
     * 修改公司
     */
    @Transactional
    public Result editCompany(Long id, CompanyObject companyObject, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = adminCompanyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.对象不存在.toString());
        Company company1 = adminCompanyRepository.findByName(companyObject.getName());
        if (company1 != null && company1.getStatusId() != EnumCompanyStatus.Deleted.id && company1.getId().longValue() != id.longValue()) {
            return Result.error(EnumCompanyError.此名称已存在.toString());
        } else {
            company.setName(companyObject.getName());
            company.setRemarks(companyObject.getRemarks());
            company.setLastUpdateManId(sessionUserId);
            company.setLastUpdateTime(LocalDateTime.now());
            adminCompanyRepository.save(company);
            return Result.success();
        }
    }

    /**
     * 根据 id 查询公司
     */
    public Result findById(Long id) {
        Company company = adminCompanyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.对象不存在.toString());
        if (company.getStatusId() == EnumCompanyStatus.Deleted.id)
            return Result.error(EnumCompanyError.已经被删除.toString());
        CompanyObject companyObject = changeCompanyObject(company);
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
        if (company == null) return null;
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        List<UserObject> userObjectList = userService.changeUserObjectSimple(identityService.createUserQuery().list());
        companyObject.setPersonNum(userObjectList.parallelStream().filter(user -> user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && user.getCompanyId().longValue() == company.getId().longValue()).count());
        companyObject.setAdminName(userService.findCompanyFirstAdminName(company.getId()));
        return companyObject;
    }

    public List<CompanyObject> changeCompanyObject(List<Company> companyList) {
        List<CompanyObject> companyObjectList = new ArrayList<>();
        if (companyList == null || companyList.size() == 0) return companyObjectList;
        companyObjectList = DozerUtils.copy(companyList, CompanyObject.class);
        List<UserObject> userObjectList = userService.changeUserObjectSimple(identityService.createUserQuery().list()).parallelStream().filter(user -> user.getStatus().equals(EnumAdminUserStatus.Normal.toString())).collect(Collectors.toList());
        companyObjectList.parallelStream().forEach(company -> {
            company.setPersonNum(userObjectList.parallelStream().filter(u -> u.getCompanyId().longValue() == company.getId().longValue()).count());
            company.setAdminName(userService.findCompanyFirstAdminName(company.getId()));
        });
        return companyObjectList;
    }

    /**
     * 超级管理员, 交易员获取风控线列表
     */
    public Result adminFindRiskCompanyList(Long sessionCompanyId) {
        if (sessionCompanyId.longValue() != 0) return Result.error(EnumCompanyError.你没有权限查看风控线列表.toString());
        return Result.success().setData(findCompanyListByRole(EnumCompanyRole.RiskManager_Organization.id, null));
    }

    /**
     * 超级管理员, 交易员获取风控线列表
     */
    public Result adminFindRiskCompanyList(RiskCompanySearch riskCompanySearch, Long sessionCompanyId, Page page) {
        if (sessionCompanyId.longValue() != 0) return Result.error(EnumCompanyError.你没有权限查看风控线列表.toString());
        List<CompanyObject> companyObjectList = findCompanyListByRole(EnumCompanyRole.RiskManager_Organization.id, riskCompanySearch);
        if (companyObjectList != null && companyObjectList.size() != 0) {
            page.setTotal((long) companyObjectList.size());
            int toIndex = page.getPage() * page.getCount() < companyObjectList.size() ? page.getPage() * page.getCount() : companyObjectList.size();
            return Result.success().setData(companyObjectList.subList(page.getOffset(), toIndex)).setMeta(page);
        } else {
            return Result.success().setData(companyObjectList).setMeta(page);
        }
    }

    /**
     * 根据角色获取公司列表
     */
    public List<CompanyObject> findCompanyListByRole(int type, RiskCompanySearch riskCompanySearch) {
        List<CompanyObject> companyObjectList = getNormalCompanyListByIdList(adminCompanyRoleRelationShipRepository.findCompanyIdByRoleNumberOrderByCompanyIdDesc(type));
        if (riskCompanySearch != null && companyObjectList != null && companyObjectList.size() != 0) {
            if (!StringUtils.isEmpty(riskCompanySearch.getName()) && !StringUtils.isEmpty(riskCompanySearch.getAdminName())) {
                companyObjectList = companyObjectList.parallelStream().filter(company -> company.getName().contains(riskCompanySearch.getName()) && (!StringUtils.isEmpty(company.getAdminName()) && company.getAdminName().contains(riskCompanySearch.getAdminName()))).collect(Collectors.toList());
            } else if (!StringUtils.isEmpty(riskCompanySearch.getName())) {
                companyObjectList = companyObjectList.parallelStream().filter(company -> company.getName().contains(riskCompanySearch.getName())).collect(Collectors.toList());
            } else if (!StringUtils.isEmpty(riskCompanySearch.getAdminName())) {
                companyObjectList = companyObjectList.parallelStream().filter(company -> !StringUtils.isEmpty(company.getAdminName()) && company.getAdminName().contains(riskCompanySearch.getAdminName())).collect(Collectors.toList());
            }
        }
        return companyObjectList;
    }

    /**
     * 根据 id 删除 公司
     */
    @Transactional
    public Result deleteCompany(Long id, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return result;
        Company company = adminCompanyRepository.findOne(id);
        if (company == null) return Result.error(EnumCompanyError.对象不存在.toString());
        if (company.getStatusId() == EnumCompanyStatus.Deleted.id)
            return Result.error(EnumCompanyError.已经被删除.toString());
        company.setStatusId(EnumCompanyStatus.Deleted.id);
        company.setStatus(EnumCompanyStatus.Deleted.toString());
        CompanyObject companyObject = DozerUtils.copy(company, CompanyObject.class);
        adminCompanyRepository.save(company);
        List<UserObject> userObjectList = userService.getUserByRiskCompanyId(company.getId());
        userObjectList.parallelStream().forEach(user -> {
            identityService.setUserInfo(user.getId(), "status", EnumAdminUserStatus.Deleted.toString());
        });
        return Result.success().setData(companyObject);
    }

    public List<CompanyObject> getNormalCompanyListByIdList(List<Long> companyIdList) {
        List<CompanyObject> companyObjectList = new ArrayList<>();
        if (companyIdList == null || companyIdList.size() == 0) return companyObjectList;
        List<Company> companyList = companyIdList.parallelStream()
                .filter(id -> adminCompanyRepository.findByIdAndStatusId(id, EnumCompanyStatus.Normal.id) != null)
                .map(id -> adminCompanyRepository.findOne(id))
                .collect(Collectors.toList());
        companyObjectList = changeCompanyObject(companyList);
        return companyObjectList;
    }

}
