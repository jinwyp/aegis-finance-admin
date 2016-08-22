package com.yimei.finance.ext.intereceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.site.user.User;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.utils.HttpUtils;
import com.yimei.finance.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by joe on 1/15/15.
 */
@Service
@Slf4j
public class SiteACLInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    protected UserSession session;

    @Value("${ssourl.env}")
    private String SSOURL;

    @Value("${sso.protocol}")
    private String SSOPROTOCOL;

    @Value("${sso.memberaddress}")
    private String MEDBERADDRESS;

    private static ObjectMapper mapper = new ObjectMapper();

    public static final String passportCookieName = "passport";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String passportCookieValue = searchCookieValue(passportCookieName, request);
        String currentUrl = request.getRequestURL().toString();


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
                        String userData = HttpUtils.sendPostRequest("http://" + MEDBERADDRESS + "/auth?passport=" + passportCookieValue+"gotoURL="+currentUrl);
                        User user;

                        try {
                            user = mapper.readValue(userData, User.class);
                        } catch (Exception e) {
                            log.error("User auth failed!");
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
        String userAgent = request.getHeader("user-agent");
        String AJAX = request.getHeader("X-Requested-With");

        String gotoURL = request.getHeader("Referer");
        if (gotoURL == null) {
            gotoURL = "/";
        }
        String setCookieURL = SSOPROTOCOL + "://" + request.getServerName() + "/setCookie";
        String url = SSOPROTOCOL + "://" + SSOURL + "/login?gotoURL=" + URLEncoder.encode(gotoURL, "UTF-8") + "&from=site&setCookieUrl=" + setCookieURL;

        if (null != AJAX && AJAX.equals("XMLHttpRequest")) {
            OutputStream out = response.getOutputStream();
            out.write(mapper.writeValueAsBytes(Result.error(401, url)));
        } else {
            response.sendRedirect(url);
        }

        return;
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
