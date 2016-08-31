package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.repository.admin.databook.DataBookRepository;
import com.yimei.finance.representation.admin.finance.EnumMYRFinanceAllSteps;
import com.yimei.finance.representation.common.databook.DataBook;
import com.yimei.finance.representation.common.databook.EnumDataBookType;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.common.file.LocalStorage;
import com.yimei.finance.utils.StoreUtils;
import com.yimei.finance.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    public Result uploadFileMethod(@RequestParam("file") MultipartFile file) throws IOException {
        return Result.success().setData(StoreUtils.save(localStorage, file, "finance"));
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "/download/file", method = RequestMethod.GET)
    public void doDownloadFile(@RequestParam(value = "path", required = true) String path, HttpServletResponse response) {
        try {
            if (path != null && path.startsWith("/files/")) {
                File file = new File(localStorage.getServerFileRootPath(), path.substring("/files/".length()));
                WebUtils.doDownloadFile(file, response);
            }
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }


}
