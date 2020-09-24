package com.zlx.module_base.config;

import android.app.Application;

import androidx.annotation.Nullable;

import com.zlx.module_base.BaseApplication;
import com.zlx.module_base.module.IModuleInit;

/**
 * Created by zlx on 2020/9/22 14:26
 * Email: 1170762202@qq.com
 * Description:
 */
public class ModuleLifecycleConfig {

    private ModuleLifecycleConfig() {

    }

    private static class SingleHolder {
        private static ModuleLifecycleConfig instance =
                new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance() {
        return SingleHolder.instance;
    }

    /**
     * 优先初始化
     */
    public void initModuleAhead(@Nullable Application application) {
        for (String moduleName : ModuleLifecycleReflects.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                init.onInitAhead(application);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 后初始化
     */
    public void initModuleAfter(@Nullable BaseApplication application) {
        for (String moduleName : ModuleLifecycleReflects.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                // 调用初始化方法
                init.onInitAfter(application);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
