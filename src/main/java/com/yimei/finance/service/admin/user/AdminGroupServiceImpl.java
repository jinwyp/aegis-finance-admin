package com.yimei.finance.service.admin.user;

import com.yimei.finance.representation.admin.group.EnumAdminGroupError;
import com.yimei.finance.representation.admin.group.GroupObject;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminGroupServiceImpl {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;

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

    /**
     * 查询所有用户组
     */
    public Result findAllGroupList(String userId, Page page) {
        Result result = userService.checkSuperAdminRight(userId);
        if (!result.isSuccess()) return result;
        List<Group> groupList = identityService.createGroupQuery().orderByGroupId().desc().list();
        page.setTotal(Long.valueOf(groupList.size()));
        int toIndex = page.getPage() * page.getCount() < groupList.size() ? page.getPage() * page.getCount() : groupList.size();
        List<GroupObject> groupObjectList = changeGroupObject(groupList.subList(page.getOffset(), toIndex));
        return Result.success().setData(groupObjectList).setMeta(page);
    }

    /**
     * 删除用户组
     */
    public Result deleteGroup(String userId, String id) {
        Result result = userService.checkSuperAdminRight(userId);
        if (!result.isSuccess()) return result;
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        GroupObject groupObject = changeGroupObject(group);
        identityService.deleteGroup(id);
        return Result.success().setData(groupObject);
    }

    /**
     * 修改用户组
     */
    public Result updateGroup(String userId, String id, GroupObject groupObject) {
        Result result = userService.checkSuperAdminRight(userId);
        if (!result.isSuccess()) return result;
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminGroupError.组id不能为空.toString());
        if (groupObject == null) return Result.error(EnumAdminGroupError.组对象不能为空.toString());
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        group.setName(groupObject.getName());
        identityService.saveGroup(group);
        return Result.success().setData(changeGroupObject(identityService.createGroupQuery().groupId(id).singleResult()));
    }

    /**
     * 讲一个用户从指定组移出
     */
    public Result deleteUserFromGroup(String userId, String groupId, UserObject sessionUser) {
        Result result = userService.findById(userId, sessionUser);
        if (!result.isSuccess()) return result;
        Group group1 = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group1 == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group2 : groupList) {
            if (group2.getId().equals(groupId)) {
                identityService.deleteMembership(userId, groupId);
                return Result.success().setData(userService.changeUserObject(user));
            }
        }
        return Result.error(EnumAdminGroupError.该用户并不在此组中.toString());
    }
}
