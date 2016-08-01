package com.yimei.controllers.restful;

import com.yimei.api.common.Result;
import com.yimei.api.tpl.representations.Applicant;
import com.yimei.boot.activiti.ApplicantRepository;
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
    private ApplicantRepository applicantRepository;

    @RequestMapping(value="/start-hire-process", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public Object startHireProcess(@RequestBody Map<String, String> data) {

        Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
        applicantRepository.save(applicant);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applicant", applicant);
        runtimeService.startProcessInstanceByKey("hireProcessWithJpa", variables);
        return Result.success().putData("hello", "hello").putData("variable", variables);
    }
}
