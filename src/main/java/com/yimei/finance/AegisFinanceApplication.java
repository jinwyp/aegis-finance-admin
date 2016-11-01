package com.yimei.finance;

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
    public CommandLineRunner init(final IdentityService identityService) {
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
					user.setEmail("yunying@yimei180.com");
					user.setPassword("961e37962ed659bf4eb45a6f074981a0");
					identityService.saveUser(user);
					identityService.setUserInfo(user.getId(), "username", "superadmin");
					identityService.setUserInfo(user.getId(), "companyId", "0");
					identityService.createMembership(user.getId(), EnumSpecialGroup.SuperAdminGroup.id);
				}
            }
        };
    }

}
