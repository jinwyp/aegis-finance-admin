package com.yimei.finance.service.admin.user;

import com.yimei.finance.representation.admin.group.EnumAdminGroupError;
import com.yimei.finance.representation.admin.group.GroupObject;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserStatus;
import com.yimei.finance.representation.admin.user.object.UserObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminGroupServiceImpl {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;

    /**
     * 添加组
     */
    public Result addGroup(GroupObject groupObject, String sessionUserId) {
        Result result = userService.checkSuperAdminRight(sessionUserId);
        if (!result.isSuccess()) return Result.error(EnumAdminUserError.只有超级管理员才能执行此操作.toString());
        if (StringUtils.isEmpty(groupObject.getName())) return Result.error(EnumAdminGroupError.组名称不能为空.toString());
        if (StringUtils.isEmpty(groupObject.getType())) return Result.error(EnumAdminGroupError.组类型不能为空.toString());
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
     * 根据 id 查询 用户组
     */
    public Result findById(String groupId) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        return Result.success().setData(changeGroupObject(group));
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
        return Result.success().setData(changeGroupObject(group));
    }

    /**
     * 将一个用户从指定组移出
     */
    public Result deleteUserFromGroup(String userId, String groupId, UserObject sessionUser) {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        Result result = userService.checkOperateUserAuthority(userService.changeUserObject(user), sessionUser);
        if (!result.isSuccess()) return result;
        Group group1 = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group1 == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        groupList.parallelStream().filter(group -> group.getId().equals(groupId)).map(group -> {
            identityService.deleteMembership(userId, groupId);
            return Result.success().setData(userService.changeUserObject(user));
        });
//        for (Group group2 : groupList) {
//            if (group2.getId().equals(groupId)) {
//                identityService.deleteMembership(userId, groupId);
//                return Result.success().setData(userService.changeUserObject(user));
//            }
//        }
        return Result.error(EnumAdminGroupError.该用户并不在此组中.toString());
    }

    /**
     * 将一个用户添加到指定组
     */
    public Result addUserToGroup(String userId, String groupId, UserObject sessionUser) {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        Result result = userService.checkOperateUserAuthority(userService.changeUserObject(user), sessionUser);
        if (!result.isSuccess()) return result;
        Group group1 = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group1 == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group2 : groupList) {
            if (group2.getId().equals(groupId)) return Result.error(EnumAdminGroupError.该用户已经在此组中.toString());
        }
        identityService.createMembership(userId, groupId);
        return Result.success().setData(userService.changeUserObject(user));
    }

    /**
     * 获取根据 groupId 查找本公司的用户列表
     */
    public Result findCompanyUserListByGroupId(String groupId, UserObject sessionUser) {
        if (identityService.createGroupQuery().groupId(groupId).singleResult() == null) return Result.error(EnumAdminGroupError.此组不存在.toString());
        List<UserObject> userList = userService.changeUserObject(identityService.createUserQuery().memberOfGroup(groupId).orderByUserId().desc().list());
        if (sessionUser.getCompanyId() != null) {
            userList = userList.parallelStream().filter(user -> (user.getCompanyId() != null && user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && user.getCompanyId().longValue() == sessionUser.getCompanyId().longValue())).collect(Collectors.toList());
        }
        return Result.success().setData(userList);
    }

    /**
     * 封装 group, 从 Group 到 GroupObject
     */
    public GroupObject changeGroupObject(Group group) {
        GroupObject groupObject = DozerUtils.copy(group, GroupObject.class);
        groupObject.setMemberNums(identityService.createUserQuery().memberOfGroup(group.getId()).count());
        return groupObject;
    }

    public List<GroupObject> changeGroupObject(List<Group> groupList) {
        if (groupList == null || groupList.size() == 0) return null;
        List<GroupObject> groupObjectList = DozerUtils.copy(groupList, GroupObject.class);
        groupObjectList.parallelStream().forEach(group -> {
            group.setMemberNums(identityService.createUserQuery().memberOfGroup(group.getId()).count());
        });
        return groupObjectList;
    }



}
