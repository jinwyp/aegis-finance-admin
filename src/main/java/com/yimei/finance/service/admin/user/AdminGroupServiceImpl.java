package com.yimei.finance.service.admin.user;

import com.yimei.finance.representation.admin.group.EnumAdminGroupError;
import com.yimei.finance.representation.admin.group.GroupObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminGroupServiceImpl {
    @Autowired
    private IdentityService identityService;

    /**
     * 添加组
     */
    public Result addGroup(GroupObject groupObject) {
        if (StringUtils.isEmpty(groupObject.getName())) return Result.error(EnumAdminGroupError.组名称不能为空.toString());
        if (identityService.createGroupQuery().groupName(groupObject.getName()).singleResult() != null)
            return Result.error(EnumAdminGroupError.已经存在名称相同的组.toString());
        Group group = identityService.newGroup("");
        group.setId(null);
        group.setName(groupObject.getName());
        group.setType(groupObject.getType());
        identityService.saveGroup(group);
        return Result.success().setData(changeGroupObject(identityService.createGroupQuery().groupId(group.getId()).singleResult()));
    }

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
