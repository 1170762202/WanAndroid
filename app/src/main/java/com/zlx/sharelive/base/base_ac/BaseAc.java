package com.zlx.sharelive.base.base_ac;

import android.os.Bundle;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.zlx.module_base.base_util.LogUtil;
import com.zlx.sharelive.R;
import com.zlx.sharelive.base.base_manage.ActivityManage;
import com.zlx.sharelive.widget.slide_close.BamActivity;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public abstract class BaseAc extends BamActivity {

    @Nullable
    @BindView(R.id.tvTitle)
    protected TextView tvTitle;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManage.addActivity(this);
        mUnBinder = ButterKnife.bind(this);
        initImmersionBar();
        initViews();
    }

    private void initImmersionBar() {
        if (!fullScreen()) {
            if (!transparent()) {
                if (ImmersionBar.isSupportStatusBarDarkFont()) {
                    ImmersionBar.with(this)
                            .titleBar(R.id.top_bar)
                            .keyboardEnable(true)
                            .statusBarColor(R.color.main)
                            .statusBarDarkFont(true)
                            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                            .init();
                } else {
                    LogUtil.show("当前设备不支持状态栏字体变色");
                    ImmersionBar.with(this)
                            .titleBar(R.id.top_bar)
                            .statusBarColor(R.color.main)
                            .keyboardEnable(true)
                            .statusBarDarkFont(true, 0.2f)
                            .navigationBarDarkIcon(true, 0.2f)
                            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                            .init();
                }
            } else {
                ImmersionBar.with(this)
                        .titleBarMarginTop(R.id.top_bar)
                        .transparentBar()
                        .keyboardEnable(true)
                        .statusBarDarkFont(false, 0f)
                        .navigationBarDarkIcon(false, 0f)
                        .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                        .init();
            }
        } else {
            ImmersionBar.with(this)
                    .fullScreen(true)
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_BAR)
                    .init();
        }
    }

    protected boolean transparent() {
        return false;
    }

    protected boolean fullScreen() {
        return false;
    }

    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initViews();


    protected void setAcTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        ActivityManage.finishActivity(this);
    }
}
