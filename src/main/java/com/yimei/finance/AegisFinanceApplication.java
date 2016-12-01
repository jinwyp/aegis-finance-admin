package com.yimei.finance;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.company.CompanyRoleRelationShip;
import com.yimei.finance.admin.repository.company.AdminCompanyRepository;
import com.yimei.finance.admin.repository.company.AdminCompanyRoleRelationShipRepository;
import com.yimei.finance.admin.repository.finance.AdminFinanceOrderRepository;
import com.yimei.finance.admin.representation.company.enums.EnumCompanyRole;
import com.yimei.finance.admin.representation.company.enums.EnumCompanyStatus;
import com.yimei.finance.admin.representation.group.EnumSpecialGroup;
import com.yimei.finance.admin.representation.user.enums.EnumAdminUserStatus;
import com.yimei.finance.admin.service.user.AdminUserServiceImpl;
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
    public CommandLineRunner init(IdentityService identityService, AdminUserServiceImpl userService, AdminCompanyRepository adminCompanyRepository, AdminCompanyRoleRelationShipRepository adminCompanyRoleRelationShipRepository, AdminFinanceOrderRepository adminFinanceOrderRepository) {
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
				User user = identityService.createUserQuery().userFirstName("superadmin").singleResult();
				if (user == null) {
					user = identityService.newUser("");
					user.setId(null);
					user.setFirstName("superadmin");
					user.setEmail("superadmin@yimei180.com");
					user.setPassword("3de13d80edbfb2f2523605252803deef");
					identityService.saveUser(user);
					identityService.setUserInfo(user.getId(), "username", "superadmin");
					identityService.setUserInfo(user.getId(), "companyId", "0");
					identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
					identityService.createMembership(user.getId(), EnumSpecialGroup.SuperAdminGroup.id);
				}
				identityService.setUserInfo(user.getId(), "status", EnumAdminUserStatus.Normal.toString());
				Company company = adminCompanyRepository.findByName("易煤风控线");
				if (company == null) {
					company = new Company("易煤风控线", EnumCompanyStatus.Normal.toString(), EnumCompanyStatus.Normal.id, new Date(), "0", new Date(), "0");
					adminCompanyRepository.save(company);
					adminCompanyRoleRelationShipRepository.save(new CompanyRoleRelationShip(company.getId(), EnumCompanyRole.RiskManager_Organization.id, EnumCompanyRole.RiskManager_Organization.toString(), new Date(), "0", new Date(), "0"));
				}
//				final Company finalCompany = company;
//				userService.updateOldUserData(finalCompany);
//				List<FinanceOrder> financeOrderList = financeOrderRepository.findByCreateTimeBeforeAndApproveStateIdNot(java.sql.Date.valueOf("2016-11-24"), EnumFinanceStatus.WaitForAudit.id);
//				financeOrderList.parallelStream().forEach(financeOrder -> {
//					if (financeOrder.getRiskCompanyId() == null) {
//						financeOrder.setRiskCompanyId(finalCompany.getId());
//						financeOrderRepository.save(financeOrder);
//					}
//				});
			}
        };
    }

}
