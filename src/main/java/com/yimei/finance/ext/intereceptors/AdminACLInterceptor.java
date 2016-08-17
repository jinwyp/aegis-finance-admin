package com.yimei.finance.ext.intereceptors;

import com.yimei.finance.config.AdminSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by joe on 11/4/14.
 */
@Service
public class AdminACLInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected AdminSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(request.getRequestURI().startsWith("/api/financing/admin") && !session.isLogined()){
//            throw new UnauthorizedException();
//        }
        return super.preHandle(request, response, handler);
    }
}
