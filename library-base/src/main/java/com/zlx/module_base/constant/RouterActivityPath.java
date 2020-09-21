package com.zlx.module_base.constant;

/**
 * FileName: RouterActivityPath
 * Created by zlx on 2020/9/18 10:21
 * Email: 1170762202@qq.com
 * Description:
 */
public class RouterActivityPath {

    /**
     * 登录组件
     */
    public static class Login {
        private static final String LOGIN = "/module_login";
        /**
         * 登录页
         */
        public static final String PAGER_LOGIN = LOGIN + "/Login";

    }


    /**
     * Main组件
     */
    public static class Main {
        private static final String MAIN = "/module_main";

        /**
         * 主页面
         */
        public static final String PAGER_MAIN = MAIN + "/Main";
    }

    /**
     * web 组件
     */
    public static class Web {
        public static final String WEB = "/module_web";
        public static final String PAGER_WEB = WEB + "/Web";

    }

    public static class Square {
        public static final String SQUARE = "/module_square";
        public static final String PAGER_SQUARE_LIST = SQUARE + "/square_list";
    }

}
