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
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private CompRepository compRepository;

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

//    @Bean
//    public CommandLineRunner init(final ActivitiServiceImpl activitiService) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                if (personRepository.findAll().size() == 0) {
//                    personRepository.save(new Person("wtr"));
//                    personRepository.save(new Person("wyf"));
//                    personRepository.save(new Person("admin3"));
//                }
//                if (compRepository.findAll().size() == 0) {
//                    Comp group = new Comp("grate Company");
//                    compRepository.save(group);
//                    Person admin = personRepository.findByPersonName("admin3");
//                    Person wtr = personRepository.findByPersonName("wtr");
//                    admin.setComp(group);
//                    wtr.setComp(group);
//                    personRepository.save(admin);
//                    personRepository.save(wtr);
//                }
//            }
//        };
//    }

}