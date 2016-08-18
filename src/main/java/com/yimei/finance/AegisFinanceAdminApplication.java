package com.yimei.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.repository.admin.user.EnumSpecialGroup;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
@Slf4j
public class AegisFinanceAdminApplication {
	@Autowired
	AdminSession adminSession;

	public static void main(String[] args) throws UnknownHostException {
		//SpringApplication.run(AegisFinanceAdminApplication.class, args);
		SpringApplication app = new SpringApplication(AegisFinanceAdminApplication.class);
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
					if (identityService.createGroupQuery().groupId(specialGroup.id).singleResult() == null) {
						Group group = identityService.newGroup(specialGroup.id);
						group.setName(specialGroup.name);
						identityService.saveGroup(group);
					}
				}
				adminSession.login(identityService.createUserQuery().userId("7501").singleResult());
            }
        };
    }

}
