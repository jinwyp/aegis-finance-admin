package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.repository.admin.databook.DataBookRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumMYRFinanceAllSteps;
import com.yimei.finance.representation.admin.finance.object.AttachmentObject;
import com.yimei.finance.representation.common.databook.DataBook;
import com.yimei.finance.representation.common.databook.EnumDataBookType;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.common.file.LocalStorage;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.StoreUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "admin-api-tools", description = "公用工具接口")
@RequestMapping("/api/financing/admin")
@RestController("adminCommonToolsController")
public class ToolsController {
    @Autowired
    private DataBookRepository dataBookRepository;
    @Autowired
    private LocalStorage localStorage;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/transportmodes", method = RequestMethod.GET)
    @ApiOperation(value = "获取运输方式列表", notes = "获取运输方式列表数据", response = DataBook.class, responseContainer = "List")
    public Result findTransportModeListMethod() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.transportmode.toString()));
    }

    @RequestMapping(value = "/myr/steps", method = RequestMethod.GET)
    @ApiOperation(value = "煤易融流程所有步骤", notes = "煤易融流程所有步骤", response = EnumMYRFinanceAllSteps.class, responseContainer = "List")
    public Result findMYRFinanceAllSteps() {
        List<MapObject> stepList = new ArrayList<>();
        for (EnumMYRFinanceAllSteps step : EnumMYRFinanceAllSteps.values()) {
            stepList.add(new MapObject(String.valueOf(step.id), step.name));
        }
        return Result.success().setData(stepList);
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件", response = AttachmentObject.class)
    public Result uploadFileMethod(@RequestParam("file") MultipartFile file) throws IOException {
        return Result.success().setData(StoreUtils.save(localStorage, file, "finance"));
    }

    @RequestMapping(value = "/files/{attachmentId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除文件", notes = "删除文件", response = AttachmentObject.class)
    @ApiImplicitParam(name = "attachmentId", value = "文件id", required = true, dataType = "String", paramType = "path")
    public Result deleteFileMethod(@PathVariable("attachmentId")String attachmentId) {
        Attachment attachment = taskService.getAttachment(attachmentId);
        if (attachment == null) return Result.error(EnumCommonError.此文件不存在.toString());
        taskService.deleteAttachment(attachmentId);
        return Result.success().setData(DozerUtils.copy(taskService.getAttachment(attachmentId), AttachmentObject.class));
    }




}
