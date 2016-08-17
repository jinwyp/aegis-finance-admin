package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.admin.user.EnumAdminUserError;
import com.yimei.finance.repository.admin.user.EnumGroupError;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"adminapiuser"})
@RequestMapping("/api/financing/admin/group")
@RestController
public class GroupController {
    @Autowired
    private IdentityService identityService;


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有的用户组", notes = "查询所有用户组列表", response = GroupEntity.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "number", paramType = "query"),
        @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "number", paramType = "query"),
        @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "number", paramType = "query"),
        @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "number", paramType = "query")
    })
    public Result getAllGroupsMethod(Page page) {
        page.setTotal(identityService.createGroupQuery().count());
        return Result.success().setData(identityService.createGroupQuery().orderByGroupId().desc().list()).setMeta(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户组", notes = "根据 Group Id 查询用户组信息", response = GroupEntity.class)
    @ApiImplicitParam(name = "id", value = "用户组 Group Id", required = true, dataType = "number", paramType = "path")
    public Result getGroupByIdMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        return Result.success().setData(group);
    }

    @RequestMapping(value = "{id}/users", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户组下的用户", notes = "根据 Group Id 查询用户组下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Group 对象Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "分页类page", required = false, dataType = "Page", paramType = "body")
    })
    public Result getUsersByGroupIdMethod(@PathVariable(value = "id")String id, Page page) {
        if (identityService.createGroupQuery().groupId(id).singleResult() == null) return Result.error(EnumGroupError.此组不存在.toString());
        page.setTotal(identityService.createUserQuery().memberOfGroup(id).count());
        return Result.success().setData(identityService.createUserQuery().memberOfGroup(id).orderByUserId().desc().list()).setMeta(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建用户组", notes = "根据Group对象创建用户组")
    @ApiImplicitParam(name = "group", value = "Group 对象", required = true, dataType = "GroupEntity", paramType = "body")
    public Result addGroupMethod(@RequestBody GroupEntity group) {
        if (StringUtils.isEmpty(group.getName())) return Result.error(EnumGroupError.组名称不能为空.toString());
        if (identityService.createGroupQuery().groupName(group.getName()).singleResult() != null) return Result.error(EnumGroupError.已经存在名称相同的组.toString());
        group.setId(null);
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

    @RequestMapping(value = "{groupId}/user/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "将一个用户添加到指定的组", notes = "将一个用户添加到指定的组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "Group 对象Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 对象Id", required = true, dataType = "String", paramType = "path")
    })
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
        if (identityService.createGroupQuery().groupMember(userId).list().indexOf(group) != -1)
            identityService.createMembership(userId, groupId);
        return Result.success().setData(user);
    }

    @RequestMapping(value = "/user/{groupId}/{userId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "将一个用户从指定的组移出", notes = "将一个用户从指定的组移出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "Group 对象Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "User 对象Id", required = true, dataType = "String", paramType = "path")
    })
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户组", notes = "根据Group Id 删除用户组")
    @ApiImplicitParam(name = "id", value = "Group 对象Id", required = true, dataType = "String", paramType = "path")
    public Result deleteGroupMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        identityService.deleteGroup(id);
        return Result.success().setData(identityService.createGroupQuery().groupId(id).singleResult());
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户组", notes = "根据Group Id 修改用户组")
    @ApiImplicitParam(name = "group", value = "Group 对象", required = true, dataType = "GroupEntity", paramType = "body")
    public Result updateGroupMethod(@RequestBody GroupEntity group) {
        if (group == null) return Result.error(EnumGroupError.组对象不能为空.toString());
        if (StringUtils.isEmpty(group.getId())) return Result.error(EnumGroupError.组id不能为空.toString());
        if (identityService.createGroupQuery().groupId(group.getId()).singleResult() == null) return Result.error(EnumGroupError.此组不存在.toString());
        identityService.deleteGroup(group.getId());
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

}
