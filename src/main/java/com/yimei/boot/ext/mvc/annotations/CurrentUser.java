package com.yimei.boot.ext.mvc.annotations;

import java.lang.annotation.*;

/**
 * Created by xiangyang on 15-3-14.
 * 说明：当controller方法参数中的参数有@CurrentUser标注时，就自动把当前登陆用户赋值给当前方法参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
