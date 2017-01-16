package com.yimei.finance.tpl.service;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangqi on 16/8/9.
 */

public class Myservice implements JavaDelegate {
    @Autowired
    TaskService taskservice;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String username = (String) execution.getVariable("username");
        System.out.println("应聘者姓名："+username);

        if(username.equals("wangqi")) {
            System.out.println("筛选通过，进入面试");

        }
        else{
            throw new Exception("别闹，回家看书去");
        }

        // TODO Auto-generated method stub

    }


}
