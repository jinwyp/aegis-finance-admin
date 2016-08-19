package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.EnumGroupError;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"admin-api-group"})
@RequestMapping("/api/financing/admin/groups")
@RestController
public class GroupController {

    @Autowired
    private IdentityService identityService;

    @ApiOperation(value = "查询所有的用户组", notes = "查询所有用户组列表", response = GroupEntity.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET)
    public Result getAllGroupsMethod( Page page) {
        page.setTotal(identityService.createGroupQuery().count());
        return Result.success().setData(identityService.createGroupQuery().orderByGroupId().desc().list()).setMeta(page);
    }

    @ApiOperation(value = "查询用户组", notes = "根据 GroupId 查询该用户组信息", response = GroupEntity.class)
    @ApiImplicitParam(name = "id", value = "用户组 Group Id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getGroupByIdMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        return Result.success().setData(group);
    }

    @ApiOperation(value = "查询用户组下的用户", notes = "根据 GroupId 查询该用户组下的用户", response = UserEntity.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "GroupId", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "分页类page", required = false, dataType = "String", paramType = "body")
    })
    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public Result getUsersByGroupIdMethod(@PathVariable(value = "id")String id, Page page) {
        if (identityService.createGroupQuery().groupId(id).singleResult() == null) return Result.error(EnumGroupError.此组不存在.toString());
        page.setTotal(identityService.createUserQuery().memberOfGroup(id).count());
        return Result.success().setData(identityService.createUserQuery().memberOfGroup(id).orderByUserId().desc().list()).setMeta(page);
    }

    @ApiOperation(value = "创建用户组", notes = "根据Group对象创建用户组", response = GroupEntity.class)
    @RequestMapping(method = RequestMethod.POST)
    public Result addGroupMethod(@ApiParam(value = "用户组对象") @RequestBody GroupEntity group) {
        if (StringUtils.isEmpty(group.getName())) return Result.error(EnumGroupError.组名称不能为空.toString());
        if (identityService.createGroupQuery().groupName(group.getName()).singleResult() != null) return Result.error(EnumGroupError.已经存在名称相同的组.toString());
        group.setId(null);
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

    @ApiOperation(value = "将一个用户添加到指定的组", notes = "将一个用户添加到指定的组", response = UserEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "Group 用户组Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 用户Id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/{groupId}/users/{userId}", method = RequestMethod.POST)
    public Result addUserToGroupMethod(@PathVariable("groupId")String groupId,
                                       @PathVariable("userId")String userId) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        for (Group g : groupList) {
            if (g.getId().equals(groupId)) return Result.error(EnumGroupError.该用户已经在此组中.toString());
        }
        identityService.createMembership(userId, groupId);
        return Result.success().setData(user);
    }

    @ApiOperation(value = "将一个用户从指定的组移出", notes = "将一个用户从指定的组移出", response = UserEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "Group 用户组Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 用户Id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/{groupId}/users/{userId}", method = RequestMethod.DELETE)
    public Result deleteUserFromGroupMethod(@PathVariable("groupId")String groupId,
                                            @PathVariable("userId")String userId) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        for (Group g : groupList) {
            if (g.getId().equals(groupId)) {
                identityService.deleteMembership(userId, groupId);
                return Result.success().setData(user);
            }
        }
        return Result.error(EnumGroupError.该用户并不在此组中.toString());

    }

    @ApiOperation(value = "修改用户组", notes = "根据Group Id 修改用户组", response = GroupEntity.class)
    @ApiImplicitParam(name = "name", value = "Group 用户组名称", required = true, dataType = "String", paramType = "form")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result updateGroupMethod(@PathVariable("id")String id, @RequestBody GroupEntity group) {
        if (StringUtils.isEmpty(id)) return Result.error(EnumGroupError.组id不能为空.toString());
        if (group == null) return Result.error(EnumGroupError.组对象不能为空.toString());
        if (identityService.createGroupQuery().groupId(id).singleResult() == null) return Result.error(EnumGroupError.此组不存在.toString());
        group.setId(id);
        identityService.deleteGroup(id);
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

    @ApiOperation(value = "删除用户组", notes = "根据Group Id 删除用户组", response = GroupEntity.class)
    @ApiImplicitParam(name = "id", value = "Group 用户组Id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteGroupMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        identityService.deleteGroup(id);
        return Result.success().setData(identityService.createGroupQuery().groupId(id).singleResult());
    }




}
