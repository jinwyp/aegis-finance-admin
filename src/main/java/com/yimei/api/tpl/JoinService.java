package com.yimei.api.tpl;

import org.activiti.engine.delegate.DelegateExecution;

import java.util.List;

/**
 * Created by liuxinjie on 16/8/2.
 */
public interface JoinService {
    /**
     * 加入公司操作, 可以从DelegateExecution获取流程中的变量
     */
    void joinGroup(DelegateExecution execution);

    /**
     * 获取符合条件的审批人，演示这里写死，使用应用使用实际代码
     */
    List<String> findUsers(DelegateExecution execution);
}
