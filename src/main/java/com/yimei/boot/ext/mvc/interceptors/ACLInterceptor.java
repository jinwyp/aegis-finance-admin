package com.yimei.boot.ext.mvc.interceptors;

import com.yimei.boot.ext.mvc.support.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by joe on 1/15/15.
 */
@Service
public class ACLInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected Session session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(!session.isLogined()){
//            throw new UnauthorizedException();
//        }
        return super.preHandle(request, response, handler);
    }


}
