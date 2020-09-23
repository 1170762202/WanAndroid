package com.zlx.library_common.module;


import android.app.Application;

import com.zlx.module_network.util.LogUtil;

/**
 * Created by zlx on 2020/9/22 14:28
 * Email: 1170762202@qq.com
 * Description:
 */
public class CommonModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {

        LogUtil.show("onInitAhead------");

//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return false;
//        }
//        LeakCanary.install(application);


        return false;
    }

    @Override
    public boolean onInitAfter(Application application) {
        return false;
    }
}
