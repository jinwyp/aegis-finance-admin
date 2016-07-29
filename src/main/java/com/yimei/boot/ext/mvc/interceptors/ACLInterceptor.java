package com.yimei.boot.ext.mvc.interceptors;

import com.yimei.boot.ext.mvc.annotations.LoginRequired;
import com.yimei.boot.ext.mvc.support.Session;
import com.yimei.boot.utils.HttpUtils;
import com.yimei.boot.utils.JsonUtils;
import com.yimei.domain.common.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by joe on 1/15/15.
 */
@Service
public class ACLInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected Session session;

    @Value("${ssourl.env}")
    private String SSOURL;

    @Value("${sso.protocol}")
    private String SSOPROTOCOL;

    @Value("${sso.memberaddress}")
    private String MEDBERADDRESS;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ACLInterceptor.class);

    public static final String passportCookieName = "passport";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String passportCookieValue = searchCookieValue(passportCookieName, request);
        String currentUrl=request.getRequestURL().toString();
        if (currentUrl.endsWith("/login")) {
            redirectLoginPage(request, response);
            return false;
        } else {
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) handler;
                if (method.getMethodAnnotation(LoginRequired.class) != null || method.getBeanType().getDeclaredAnnotation(LoginRequired.class) != null) {
                    if (!session.isLogined() && StringUtils.isBlank(passportCookieValue)) {
                        redirectLoginPage(request, response);
                        return false;
                    } else if (!session.isLogined()) {
                        String userData = HttpUtils.sendPostRequest("http://" + MEDBERADDRESS + "/auth?passport=" + passportCookieValue);
                       /* if (!userData.endsWith("}")) {
                            userData = userData.concat("}");
                        }*/
                        User user;
                        try {
                            user = JsonUtils.toObject(userData, User.class);
                        } catch (Exception e) {
                            logger.error("auth fail");
                            redirectLoginPage(request, response);
                            return false;
                        }
                        session.login(user);
                    }
                }
            }
        }
        return true;
    }

    private void redirectLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gotoURL = request.getHeader("Referer");
        if (gotoURL == null) {
            gotoURL = "/";
        }
        String setCookieURL = SSOPROTOCOL + "://" + request.getServerName() + "/setCookie";
        String url = SSOPROTOCOL + "://" + SSOURL + "/login?gotoURL=" + URLEncoder.encode(gotoURL,"UTF-8") + "&from=site&setCookieUrl=" + setCookieURL;
        response.sendRedirect(url);
    }

    private String searchCookieValue(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }



}
