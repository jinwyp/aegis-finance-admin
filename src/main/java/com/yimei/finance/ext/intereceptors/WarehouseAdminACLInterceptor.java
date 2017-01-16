package com.yimei.finance.ext.intereceptors;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class WarehouseAdminACLInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected AdminSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userAgent = request.getHeader("user-agent");
        String AJAX = request.getHeader("X-Requested-With");

        if(!session.isLogined()){

            if (null != AJAX && AJAX.equals("XMLHttpRequest")) {
                throw new UnauthorizedException();
            } else {
                response.sendRedirect("/warehouse/admin/login");
                return false;
            }

        }else{
            return super.preHandle(request, response, handler);
        }

    }
}
