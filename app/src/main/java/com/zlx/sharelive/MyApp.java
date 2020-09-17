package com.zlx.sharelive;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.squareup.leakcanary.LeakCanary;
import com.zlx.module_base.BaseApplication;
import com.zlx.module_base.LoadingCallback;
import com.zlx.widget.smartrefreshlayout.wavedrop.WaterDropHead;
import com.zlx.widget.smartrefreshlayout.wavedrop.WaterDropHeader;
import com.zlx.widget.smartrefreshlayout.waveswipe.WaveSwipeHeader;

import org.aspectj.lang.annotation.Around;


/**
 * @date: 2019\3\8 0008
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(this));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(this));
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
