package com.yimei.boot.ext.mvc.interceptors;

/**
 * Created by hongpf on 15/7/16.
 */
import com.yimei.boot.ext.mvc.annotations.SecureToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SecureTokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SecureTokenService secureTokenService;


    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        SecureToken secureToken =
                method.getMethodAnnotation(SecureToken.class);

        if (secureToken != null
                && RequestMethod.POST
                == RequestMethod.valueOf(req.getMethod())) {
            return checkValidToken(req, resp);
        }
        return true;
    }

    private boolean checkValidToken(HttpServletRequest req,
                                    HttpServletResponse resp)
            throws IOException, ModelAndViewDefiningException {
        String tokenValue =
                req.getParameter(SecureToken.TOKEN_PARAMETER_NAME);
        if(tokenValue==null){
            tokenValue = req.getHeader(SecureToken.TOKEN_PARAMETER_NAME);

        }
        if (StringUtils.isEmpty(tokenValue)) {
            resp.sendError(400);
            // Bad page setup or someone is messing with us
            return false;
        } else if (!secureTokenService.checkToken( tokenValue,req.getSession(true))) {
            resp.sendError(400);
            return  false ;
            //    ModelAndView mav = new ModelAndView("redirect:/page-expired");
        //    throw new ModelAndViewDefiningException(mav);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req,
                           HttpServletResponse resp,
                           Object handler,
                           ModelAndView modelAndView)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        //SecureToken secureToken = method.getMethodAnnotation(SecureToken.class);
        //if (secureToken != null) {
            String tokenValue = secureTokenService
                    .getOrGenerateToken(req.getSession(true));
            if(modelAndView!=null&&!modelAndView.getViewName().startsWith("redirect:")){
                modelAndView.addObject(SecureToken.TOKEN_PARAMETER_NAME, tokenValue);
            }
        //}
    }



}