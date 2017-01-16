package com.yimei.finance.site.controller.page;

import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.common.service.file.LocalStorage;
import com.yimei.finance.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Api(tags = {"site-page"})
@Controller("siteCommonPageController")
public class SiteCommonPageController {
    @Autowired
    private LocalStorage localStorage;

    @RequestMapping(value = "/finance/files", method = RequestMethod.GET)
    @ApiOperation(value = "下载文件", notes = "通过文件url路径下载文件")
    @ApiImplicitParam(name = "url", value = "文件路径", required = true, dataType = "string", paramType = "query")
    public void siteDownloadFile(@RequestParam(value = "url", required = true) String url, HttpServletResponse response) {
        try {
            if (url != null && url.startsWith("/files/")) {
                File file = new File(localStorage.getServerFileRootPath(), url.substring("/files/".length()));
                WebUtils.doDownloadFile(file, response);
            }
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }

}
