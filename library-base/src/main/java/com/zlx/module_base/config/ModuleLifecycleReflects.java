package com.zlx.module_base.config;

/**
 * Created by zlx on 2020/9/22 14:26
 * Email: 1170762202@qq.com
 * Description:
 */
public class ModuleLifecycleReflects {
    /**
     * 基础库
     */
    private static final String BaseInit = "com.zlx.module_base.module.CommonModuleInit";
    private static final String BaseInit1 = "com.zlx.library_common.module.CommonModuleInit";

    public static String[] initModuleNames = {BaseInit,BaseInit1};
}
