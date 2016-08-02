package com.yimei.controllers.restful;

import com.yimei.api.admin.AdminService;
import com.yimei.api.tpl.dao.ApplicantRepository;
import com.yimei.api.tpl.representations.Applicant;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxinjie on 16/8/1.
 */
@RestController
public class ActivitiTestController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @RequestMapping(value = "/start-hire-process", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public void startHireProcess(@RequestBody Map<String, String> data) {

        Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
        applicantRepository.save(applicant);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applicant", applicant);
        runtimeService.startProcessInstanceByKey("hireProcessWithJpa", variables);
    }


    //    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... strings) throws Exception {
//                Map<String, Object> variables = new HashMap<String, Object>();
//                variables.put("applicantName", "John Doe");
//                variables.put("email", "john.doe@activiti.com");
//                variables.put("phoneNumber", "123456789");
//                runtimeService.startProcessInstanceByKey("hireProcess", variables);
//            }
//        };
//
//    }

//    public void addGroup(final IdentityService identityService) {
//        Group group = identityService.newGroup("user");
//        group.setName("users");
//        group.setType("security-role");
//        identityService.saveGroup(group);
//
//        User admin = identityService.newUser("admin");
//        admin.setPassword("admin");
//        identityService.saveUser(admin);
//    }
}
