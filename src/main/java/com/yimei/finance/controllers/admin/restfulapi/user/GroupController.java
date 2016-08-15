package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.repository.user.EnumGroupError;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuxinjie on 16/8/11.
 */
@RestController
public class GroupController {
    @Autowired
    private IdentityService identityService;


    /**
     * 添加组
     * @param group                组 对象
     */
    @RequestMapping(value = "/api/group", method = RequestMethod.POST)
    public Result addGroupMethod(Group group) {
        group.setId(null);
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

    /**
     * 删除组
     * @param id                   组 id
     */
    @RequestMapping(value = "/api/group/{id}", method = RequestMethod.DELETE)
    public Result deleteGroupMethod(@PathVariable("id")String id) {
        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        if (group == null) return Result.error(EnumGroupError.此组不存在.toString());
        identityService.deleteGroup(id);
        return Result.success().setData(identityService.createGroupQuery().groupId(id).singleResult());
    }

    /**
     * 修改组
     * @param group                组 对象
     */
    @RequestMapping(value = "/api/group", method = RequestMethod.PUT)
    public Result updateGroupMethod(Group group) {
        if (group == null) return Result.error(EnumGroupError.组对象不能为空.toString());
        if (StringUtils.isEmpty(group.getId())) return Result.error(EnumGroupError.组id不能为空.toString());
        if (identityService.createGroupQuery().groupId(group.getId()) == null) return Result.error(EnumGroupError.此组不存在.toString());
        identityService.saveGroup(group);
        return Result.success().setData(identityService.createGroupQuery().groupId(group.getId()).singleResult());
    }

    /**
     * 根据 id 查询组
     *
     */
    @RequestMapping(value = "/api/group/{id}", method = RequestMethod.GET)
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
