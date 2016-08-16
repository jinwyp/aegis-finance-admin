package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.repository.user.EnumGroupError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/financing/admin/group")
@Api(value = "Group-Controller", description = "用户组相关方法")
@RestController
public class GroupController {
    @Autowired
    private IdentityService identityService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建用户组", notes = "根据Group对象创建用户组")
    @ApiImplicitParam(name = "group", value = "Group 对象", required = true, dataType = "GroupEntity", paramType = "body")
    public Result addGroupMethod(@RequestBody GroupEntity group) {
        group.setId(null);
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户组", notes = "根据 Group Id 查询用户组")
    @ApiImplicitParam(name = "id", value = "Group 对象Id", required = true, dataType = "String", paramType = "path")
    public Result getGroupByIdMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        return Result.success().setData(group);
    }

    @RequestMapping(value = "/api/group", method = RequestMethod.GET)
    public Result getAllGroupsMethod(Page meta) {
        meta.setTotal(identityService.createGroupQuery().count());
        return Result.success().setData(identityService.createGroupQuery().list()).setMeta(meta);
    }

    @RequestMapping(value = "/api/group/users/{id}", method = RequestMethod.GET)
    public Result getUsersByGroupIdMethod(@PathVariable(value = "id")String id, Page meta) {
        meta.setTotal(identityService.createUserQuery().memberOfGroup(id).count());
        return Result.success().setData(identityService.createUserQuery().memberOfGroup(id).list()).setMeta(meta);
    }


}
