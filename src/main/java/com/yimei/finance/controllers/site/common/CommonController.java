package com.yimei.finance.controllers.site.common;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.site.user.User;
import com.yimei.finance.utils.HttpUtils;
import com.yimei.finance.utils.JsonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangqi on 16/8/17.
 */

@Api(tags = {"example"})
@Controller
public class CommonController {
    public static final String passportCookieName = "passport";

    public static final String userNameCookieName = "userName";

    @Autowired
    UserSession userSession;


    @Value("${ssourl.env}")
    private String SSOURL;

    @Value("${sso.protocol}")
    private String SSOPROTOCOL;

    @Value("${sso.memberaddress}")
    private String MEDBERADDRESS;

    private
    @Value("${sso.securemode}")
    boolean securemode;

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(SSOPROTOCOL+"://"+SSOURL+"/logout?passport="+searchCookieValue(passportCookieName,request));
    }


    @RequestMapping(value = "/setSSOCookie", method = RequestMethod.GET)
    public void setSSOCookie(@RequestParam(value = "passport",required = true)String passport,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        Cookie ssoCookie = new Cookie("passport", passport);
        ssoCookie.setSecure(securemode);
        ssoCookie.setMaxAge(3600*24*7);
        response.addCookie(ssoCookie);
        String userData = HttpUtils.sendPostRequest("http://" + MEDBERADDRESS + "/auth?passport=" + passport);
        User user = JsonUtils.toObject(userData, User.class);
        userSession.login(user);
        PrintWriter out = response.getWriter();
        String jsonpCallback = request.getParameter("callback");//客户端请求参数
        out.println(jsonpCallback+"({\"demo\":\"demo\"})");//返回jsonp格式数据
        out.flush();
        out.close();
    }




    @RequestMapping(value = "/removeSSOCookie", method = RequestMethod.GET)
    public void removeSSOCookie(HttpServletRequest request,HttpServletResponse response) throws IOException {
        userSession.logout();
        Cookie passportCookie = null;
        if (null != (passportCookie = searchCookie(passportCookieName, request))) {
            passportCookie.setPath("/");
            passportCookie.setMaxAge(0);
            response.addCookie(passportCookie);
        }
        PrintWriter out = response.getWriter();
        String jsonpCallback = request.getParameter("callback");//客户端请求参数
        out.println(jsonpCallback+"({\"demo\":\"demo\"})");//返回jsonp格式数据
        out.flush();
        out.close();

    }

    @RequestMapping(value = "/check" , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> Check() {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }


    private Cookie searchCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
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
