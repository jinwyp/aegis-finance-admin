package com.yimei.finance.config.freemarkermethod;

import com.yimei.finance.config.session.UserSession;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangqi on 16/8/23.
 */
@Component
public class SessionMethod implements TemplateMethodModelEx {

    @Autowired
    protected UserSession session;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return session;
    }
}
