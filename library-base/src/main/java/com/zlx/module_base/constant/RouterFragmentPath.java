package com.zlx.module_base.constant;

/**
 * @date: 2019\9\25 0025
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class RouterFragmentPath {

    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/module_home";

        /**
         * 首页
         */
        public static final String PAGER_HOME = HOME + "/Home";

    }

    /**
     * 项目 组件
     */
    public static class Project {
        private static final String PROJECT = "/module_project";

        /**
         * 项目 页面
         */
        public static final String PAGER_PROJECT = PROJECT + "/Project";
    }

    /**
     * 广场 组件
     */
    public static class Square {
        private static final String SQUARE = "/module_square";

        /**
         * 控制页
         */
        public static final String PAGER_SQUARE = SQUARE + "/Square";
        /**
         * 系统
         */
        public static final String PAGER_SYSTEM = SQUARE + "/System";
        /**
         * 导航
         */
        public static final String PAGER_NAVIGATION = SQUARE + "/Navigation";
    }

    /**
     * 公众号 组件
     */
    public static class Public {
        private static final String PUBLIC = "/module_public";

        /**
         * 公众号 页面
         */
        public static final String PAGER_PUBLIC = PUBLIC + "/Public";
    }

    /**
     * 我的 组件
     */
    public static class Mine {
        private static final String MINE = "/module_mine";

        /**
         * 我的 页面
         */
        public static final String PAGER_MINE = MINE + "/Mine";
    }

}
