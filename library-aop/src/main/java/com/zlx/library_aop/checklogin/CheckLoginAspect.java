package com.zlx.library_aop.checklogin;


import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.library_aop.checklogin.annotation.CheckLogin;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.base_api.res_data.UserInfo;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * FileName:
 * Created by zlx on 2020/9/21 9:27
 * Email: 1170762202@qq.com
 * Description:
 */
@Aspect
public class CheckLoginAspect {

    @Pointcut("execution(" +//执行语句
            "@com.zlx.library_aop.checklogin.annotation.CheckLogin" +//注解筛选
            " * " + //类路径,*为任意路径
            "*" +   //方法名,*为任意方法名
            "(..)" +//方法参数,'..'为任意个任意类型参数
            ")" +
            " && " +//并集
            "@annotation(checkLogin)"//注解筛选,这里主要用于下面方法的'NeedLogin'参数获取
    )
    public void pointcutCheckLogin(CheckLogin checkLogin) {

    }

    @Around("pointcutCheckLogin(checkLogin)")
    public Object aroundCheckLogin(ProceedingJoinPoint joinPoint, final CheckLogin checkLogin) throws Throwable {
        Log.i("TAG", "登录校验");
        UserInfo userInfo = MMkvHelper.getInstance().getUserInfo();
        if (userInfo == null) {
            ARouter.getInstance().build("/module_login/Login").navigation();
            Log.i("TAG", "未登录");
            return null;
        } else {
            return joinPoint.proceed();
        }
    }
}
