package com.yimei.service.tpl.impl;

import com.yimei.api.tpl.dao.CompRepository;
import com.yimei.api.tpl.dao.PersonRepository;
import com.yimei.api.tpl.representations.Comp;
import com.yimei.api.tpl.representations.Person;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuxinjie on 16/8/1.
 */
@Service
public class JoinServiceImpl {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private CompRepository compRepository;

    /**
     * 加入公司操作, 可以从DelegateExecution获取流程中的变量
     */
    public void joinGroup(DelegateExecution execution) {
        Boolean bool = (Boolean) execution.getVariable("joinApproved");

        if (bool) {
            Long personId = (Long) execution.getVariable("personId");
            Long compId = (Long) execution.getVariable("compId");
            Comp comp = compRepository.findOne(compId);
            Person person = personRepository.findOne(personId);
            person.setComp(comp);
            personRepository.save(person);
            System.out.println(" ---------------- 加入组织成功.");
        } else {
            System.out.println(" ---------------- 加入组织失败.");
        }
    }

    /**
     * 获取符合条件的审批人，演示这里写死，使用应用使用实际代码
     */
    public List<String> findUsers(DelegateExecution execution) {
        return Arrays.asList("admin", "wtr");
    }

}
