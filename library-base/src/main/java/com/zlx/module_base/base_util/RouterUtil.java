package com.zlx.module_base.base_util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_base.constant.RouterFragmentPath;

public class RouterUtil {

    public static void launchMain() {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();
    }
    public static void launchWeb(String webUrl) {
        ARouter.getInstance().build(RouterActivityPath.Web.PAGER_WEB).withString("webUrl", webUrl).navigation();
    }

    public static void launchArticleList(String id, String title) {
        ARouter.getInstance().build(RouterActivityPath.Square.PAGER_SQUARE_LIST).withString("id", id).withString("title", title).navigation();
    }

    public static void launchLogin() {
        ARouter.getInstance().build(RouterActivityPath.Login.PAGER_LOGIN).navigation();
    }
}
