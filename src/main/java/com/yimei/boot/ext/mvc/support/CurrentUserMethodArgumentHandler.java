package com.yimei.boot.ext.mvc.support;


import com.yimei.api.common.exception.UnauthorizedException;
import com.yimei.boot.ext.mvc.annotations.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by xiangyang on 15-6-4.
 */
@Service
public class CurrentUserMethodArgumentHandler implements HandlerMethodArgumentResolver {

    @Autowired
    private Session session;

    public boolean supportsParameter(MethodParameter parameter) {
        if(parameter.hasParameterAnnotation(CurrentUser.class)){
            return true;
        }
        return false;
    }

    //处理参数解析的逻辑
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (session.getUser()==null) {
           throw  new UnauthorizedException();
        }
        return session.getUser();
    }
}
