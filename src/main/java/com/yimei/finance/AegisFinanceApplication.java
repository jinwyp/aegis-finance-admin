package com.yimei.finance;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.repository.admin.company.CompanyRoleRelationShipRepository;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyRole;
import com.yimei.finance.representation.admin.company.enums.EnumCompanyStatus;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceStatus;
import com.yimei.finance.representation.admin.group.EnumSpecialGroup;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@Slf4j
public class AegisFinanceApplication {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(AegisFinanceApplication.class);
		Environment env = app.run(args).getEnvironment();
		log.info("Access URLs:\n----------------------------------------------------------\n\t" +
						"Local: \t\thttp://127.0.0.1:{}\n\t" +
						"External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

    @Bean
    public CommandLineRunner init(final IdentityService identityService, CompanyRepository companyRepository, CompanyRoleRelationShipRepository companyRoleRelationShipRepository, FinanceOrderRepository financeOrderRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
				for (EnumSpecialGroup specialGroup : EnumSpecialGroup.values()) {
					Group group = identityService.createGroupQuery().groupId(specialGroup.id).singleResult();
					if (group == null) {
						group = identityService.newGroup(specialGroup.id);
						group.setName(specialGroup.name);
						group.setType(specialGroup.type);
						identityService.saveGroup(group);
					} else if (StringUtils.isEmpty(group.getType())) {
						group.setType(specialGroup.type);
						identityService.saveGroup(group);
					}
				}
				if (identityService.createUserQuery().userFirstName("superadmin").singleResult() == null) {
					User user = identityService.newUser("");
					user.setId(null);
					user.setFirstName("superadmin");
					user.setEmail("hudanyang@yimei180.com");
					user.setPassword("3de13d80edbfb2f2523605252803deef");
					identityService.saveUser(user);
					identityService.setUserInfo(user.getId(), "username", "superadmin");
					identityService.setUserInfo(user.getId(), "companyId", "0");
					identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					identityService.createMembership(user.getId(), EnumSpecialGroup.SuperAdminGroup.id);
				}
				Company company = companyRepository.findByName("易煤网");
				if (company == null) {
					company = new Company("易煤网", EnumCompanyStatus.Normal.toString(), EnumCompanyStatus.Normal.id, new Date(), "0", new Date(), "0");
					companyRepository.save(company);
					companyRoleRelationShipRepository.save(new CompanyRoleRelationShip(company.getId(), EnumCompanyRole.RiskManager_Organization.id, EnumCompanyRole.RiskManager_Organization.toString(), new Date(), "0", new Date(), "0"));
				}
				final Company finalCompany = company;
				List<User> userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SystemAdminGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", "0");
						identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SuperAdminGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", "0");
						identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.ManageOnlineTraderGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", "0");
						identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.OnlineTraderGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", "0");
						identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.ManageSalesmanGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SalesmanGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.ManageInvestigatorGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.InvestigatorGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.ManageSupervisorGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SupervisorGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.ManageRiskGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.RiskGroup.id).list();
				userList.forEach(user -> {
					if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
						identityService.setUserInfo(user.getId(), "companyId", String.valueOf(finalCompany.getId()));
						identityService.setUserInfo(user.getId(), "companyName", finalCompany.getName());
					}
				});
				List<FinanceOrder> financeOrderList = financeOrderRepository.findByCreateTimeBeforeAndApproveStateIdNot(java.sql.Date.valueOf("2016-11-05"), EnumFinanceStatus.WaitForAudit.id);
				financeOrderList.forEach(financeOrder -> {
					if (financeOrder.getRiskCompanyId() == null) {
						financeOrder.setRiskCompanyId(finalCompany.getId());
						financeOrderRepository.save(financeOrder);
					}
				});
			}
        };
    }

}
