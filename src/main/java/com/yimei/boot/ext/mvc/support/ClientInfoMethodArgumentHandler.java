package com.yimei.boot.ext.mvc.support;

import com.yimei.boot.ext.mvc.annotations.Client;
import com.yimei.boot.utils.NetUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuxinjie on 16/1/15.
 */
@Service
public class ClientInfoMethodArgumentHandler implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Client.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return new ClientInfo(NetUtils.getIpAddress(request), request.getHeader("user-agent"), request.getHeader("accept-language"));
    }


}
