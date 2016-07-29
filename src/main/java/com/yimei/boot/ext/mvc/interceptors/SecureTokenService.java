package com.yimei.boot.ext.mvc.interceptors;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by hongpf on 15/7/16.
 */
@Service
public class SecureTokenService {

    private final String  sessionAttributeName = "CSRF_TOKEN";

    public String getOrGenerateToken(HttpSession session) {
        String  csrfToken =(String) session.getAttribute(sessionAttributeName);
        if(csrfToken!=null){
            return csrfToken ;
        }else{
            csrfToken = UUID.randomUUID().toString();
            session.setAttribute(sessionAttributeName, csrfToken);
            return csrfToken;
        }
    }

    public boolean checkToken(String tokenValue,HttpSession session) {
        if(tokenValue==null){
            return  false  ;
        }else if(session.getAttribute(sessionAttributeName)==null) {
            return true ;
        }else {
            return tokenValue.equals(session.getAttribute(sessionAttributeName).toString()) ;
        }
    }
}
