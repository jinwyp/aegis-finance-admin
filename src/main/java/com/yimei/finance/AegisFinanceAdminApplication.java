package com.yimei.finance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
@Slf4j
public class AegisFinanceAdminApplication {

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
}
