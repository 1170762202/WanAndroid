package com.zlx.library_aop.checklogin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * FileName: CheckLogin
  * Created by zlx on 2020/9/21 9:25
  * Email: 1170762202@qq.com
  * Description:
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {

}
