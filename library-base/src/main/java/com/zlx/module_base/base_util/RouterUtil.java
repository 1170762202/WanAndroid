package com.zlx.module_base.base_util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.constant.RouterConstant;

public class RouterUtil {


    public static void launchWeb(String webUrl) {
        ARouter.getInstance().build(RouterConstant.ROUT_AC_WEB).withString("webUrl", webUrl).navigation();
    }
}
