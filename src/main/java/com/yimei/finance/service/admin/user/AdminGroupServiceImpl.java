package com.yimei.finance.service.admin.user;

import com.yimei.finance.representation.admin.user.GroupObject;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminGroupServiceImpl {
    @Autowired
    private IdentityService identityService;

    /**
     * 封装 group, 从 Group 到 GroupObject
     */
    public GroupObject changeGroupObject(Group group) {
        GroupObject groupObject = new GroupObject();
        DozerUtils.copy(group, groupObject);
        groupObject.setMemberNums(identityService.createUserQuery().memberOfGroup(group.getId()).list().size());
        return groupObject;
    }

    public List<GroupObject> changeGroupObject(List<Group> groupList) {
        if (groupList == null || groupList.size() == 0) return null;
        List<GroupObject> groupObjectList = new ArrayList<>();
        for (Group group : groupList) {
            groupObjectList.add(changeGroupObject(group));
        }
        return groupObjectList;
    }
}
