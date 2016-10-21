package com.yimei.finance.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.exception.NotFoundException;
import com.yimei.finance.exception.UnauthorizedException;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    ExceptionReporter reporter;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdminSession adminSession;

    @Autowired
    private UserSession userSession;

    @ExceptionHandler({MethodArgumentNotValidException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result process400Error(Exception ex) {
        log.error("400Exception:", ex);
        String flag1 = "default message [";
        String flag2 = "]";
        int startPlace = ex.getMessage().toString().lastIndexOf(flag1) + 17;
        if (startPlace != -1) {
            String message = ex.getMessage().substring(startPlace);
            int endPlace = message.indexOf(flag2);
            String error = message.substring(0, endPlace);
            String field = "";
            if (error.startsWith("账号")) field = "username";
            else if (error.startsWith("密码")) field = "password";
            return Result.error(400, error, field);
        } else {
            return Result.error(400, ex.getMessage());
        }
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Result process401Error(UnauthorizedException ex) {
        log.error("401 Exception: " + ex);
        return Result.error(401, EnumCommonError.请您登录.toString());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result process404Error(NotFoundException ex) {
        log.error("404 Exception: ", ex);
        return Result.error(404, ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result requestHandlingNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("404 Exception: ", ex);
        String AJAX = request.getHeader("X-Requested-With");
        String currentUrl = request.getRequestURL().toString();

        if (null != AJAX && AJAX.equals("XMLHttpRequest") || currentUrl.contains("/api")) {
            return Result.error(404, ex.getMessage());
        } else {
            if (currentUrl.contains("/admin")) {
                response.sendRedirect("/finance/admin/404");
            } else {
                response.sendRedirect("/finance/404");
            }

        }

        return null;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Result process405Error(HttpRequestMethodNotSupportedException ex) {
        log.error("405 Exception: ", ex);
        return Result.error(405, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Result process409Error(BusinessException ex) {
        log.error("409 Exception: ", ex);
        return Result.error(409, ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Result processFileUploadException(HttpServletRequest request, Exception ex) {
        log.error("MultipartException: ", ex);
        if (ex instanceof MultipartException) {
            if (ex.toString().contains("org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException")) {
                return Result.error(409, EnumCommonError.上传文件大小不能超过30M.toString());
            }
        }
        return process500Error(request, ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result process500Error(HttpServletRequest request, Exception ex) {
        log.error("500 Exception: ", ex);
        handler500(request, ex);
        return Result.error(500, ex.getMessage());
    }

    @Async
    private void handler500(HttpServletRequest request, Exception ex) {
        log.warn("开始发送邮件");
        Object user = userSession.getUser() == null ? userSession.getUser() : adminSession.getUser();
        try {
            if ("application/json".equals(request.getContentType())) {
                reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(extractPostRequestBody(request)), getHeadersInfo(request), user);
            } else {
                reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(request.getParameterMap()), getHeadersInfo(request), user);
            }
        } catch (Exception e) {
            log.warn("邮件发送失败", e);
        }
        log.warn("邮件发送结束");
    }


    //获取header对象
    private String getHeadersInfo(HttpServletRequest request) throws JsonProcessingException {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        map.put("Request Method", request.getMethod());
        map.put("requestURL", request.getRequestURL().toString());
        return om.writeValueAsString(map);
    }


    //获取  application/json 数据
    public static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        } else {
            return "{}";
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }
}
