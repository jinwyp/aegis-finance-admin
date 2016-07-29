package com.yimei;

import com.yimei.boot.Boot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.InetAddress;


/**
 * Created by hary on 16/3/14.
 */
/*@Configuration
@ComponentScan
@Import({
        Boot.class,
        Tpl.class
})
@EnableAutoConfiguration*/
@Import({
        Boot.class
})
@SpringBootApplication
public class AegisFinanceAdmin {
    private static final Logger log = LoggerFactory.getLogger(AegisFinanceAdmin.class);

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        SpringApplication app = new SpringApplication(AegisFinanceAdmin.class);
        app.setShowBanner(true);
        Environment env = app.run(args).getEnvironment();
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}