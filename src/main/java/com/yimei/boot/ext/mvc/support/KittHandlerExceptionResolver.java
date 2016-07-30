package com.yimei.boot.ext.mvc.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.boot.services.ExceptionReporter;
import com.yimei.boot.utils.WithLogger;
import com.yimei.domain.common.exception.BusinessException;
import com.yimei.domain.common.exception.NotFoundException;
import com.yimei.api.common.exception.UnauthorizedException;
import org.apache.http.HttpStatus;
import org.eclipse.jetty.io.EofException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by joe on 1/15/15.
 */
@Service
public class KittHandlerExceptionResolver extends AbstractHandlerExceptionResolver implements WithLogger {
    @Autowired
    ExceptionReporter reporter;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private Session session;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        //模板前缀
        if (ex instanceof NotFoundException
                || ex instanceof NoSuchRequestHandlingMethodException
                || ex instanceof NoHandlerFoundException
                || ex instanceof HttpRequestMethodNotSupportedException
                || ex instanceof org.springframework.web.multipart.MultipartException) {
            modelAndView.setViewName("/http/404");
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            modelAndView.addObject("errorInfo", ex.getMessage());
        } else if (ex instanceof UnauthorizedException) {
            modelAndView.setViewName("/login");
            modelAndView.addObject("exception", ((UnauthorizedException) ex).getUrl());
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        } else if (ex instanceof BindException
                || ex instanceof TypeMismatchException
                || ex instanceof MissingServletRequestParameterException
                || ex instanceof MethodArgumentNotValidException) {
            logger().warn("400", request.getRequestURL().toString(),ex);
            modelAndView.setViewName("/http/400");
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
        } else if (ex instanceof BusinessException) {
                logger().warn("409", ex);
            if(isAjaxRequest(request)){
                try {
                    response.setHeader("content-type", "application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.SC_CONFLICT);
                    om.writeValue(response.getOutputStream(),ex);
              //      response.getWriter().write(;ex.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                modelAndView.setViewName("/http/409");
                modelAndView.addObject("msg",ex.getMessage());
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
            }

        } else {
            logger().error("500", ex);
            if(!(ex instanceof EofException)){
                handler500(request, ex);
            }
            modelAndView.setViewName( "/http/500");
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return modelAndView;
    }

    @Async
    private void handler500(HttpServletRequest request, Exception ex) {
        logger().warn("开始发送邮件");
        try {
            if("application/json".equals(request.getContentType())){
                reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(extractPostRequestBody(request)), getHeadersInfo(request), session.getUser());
            }else{
                reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(request.getParameterMap()), getHeadersInfo(request), session.getUser());
            }
        } catch (Exception e) {
            logger().warn("邮件发送失败", e);
        }
        logger().warn("邮件发送结束");
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
        map.put("Request Method",request.getMethod());
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
        }else {
            return "{}";
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

}
