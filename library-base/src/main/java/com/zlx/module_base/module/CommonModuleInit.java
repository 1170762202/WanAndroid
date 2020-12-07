package com.zlx.module_base.module;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.mmkv.MMKV;
import com.zlx.library_db.manager.DbUtil;
import com.zlx.module_base.BaseApplication;
import com.zlx.module_base.base_util.LogUtils;
import com.zlx.module_base.base_util.NeverCrashHelper;
import com.zlx.module_base.loadsir.EmptyCallback;
import com.zlx.module_base.loadsir.ErrorCallback;
import com.zlx.module_base.loadsir.LoadingCallback;

/**
 * Created by zlx on 2020/9/22 14:28
 * Email: 1170762202@qq.com
 * Description:
 */
public class CommonModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(application));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(application));
        MMKV.initialize(application);
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(application);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();


        DbUtil.getInstance().init(application, "wanandroid");


//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return false;
//        }
//        LeakCanary.install(application);


//        NeverCrashHelper.init((t, e) -> {
//            LogUtils.i(e.getMessage());
//            new Handler(Looper.getMainLooper()).post(() -> {
//                Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//        });
        return false;
    }

    @Override
    public boolean onInitAfter(BaseApplication application) {
        return false;
    }
}
