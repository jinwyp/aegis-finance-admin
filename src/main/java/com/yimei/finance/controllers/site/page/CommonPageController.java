package com.yimei.finance.controllers.site.page;

import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.service.common.file.LocalStorage;
import com.yimei.finance.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller("siteCommonPageController")
public class CommonPageController {
    @Autowired
    private LocalStorage localStorage;

    @RequestMapping(value = "/finance/files", method = RequestMethod.GET)
    @ApiOperation(value = "下载文件", notes = "通过文件url路径下载文件")
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
