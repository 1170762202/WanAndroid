package com.zlx.module_base.module;

import android.app.Application;

import com.zlx.module_base.BaseApplication;

/**
 * Created by zlx on 2020/9/22 14:28
 * Email: 1170762202@qq.com
 * Description: 动态配置组件Application,有需要初始化的组件实现该接口,统一在宿主app 的Application进行初始化
 */
public interface IModuleInit {

    /**
     * 需要优先初始化的
     */
    boolean onInitAhead(Application application);

    /**
     * 可以后初始化的
     */
    boolean onInitAfter(BaseApplication application);
}
