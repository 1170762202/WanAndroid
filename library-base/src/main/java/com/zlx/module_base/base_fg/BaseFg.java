package com.zlx.module_base.base_fg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.zlx.module_base.loadsir.LoadingCallback;
import com.zlx.module_base.R;
import com.zlx.module_base.base_util.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zlx on 2017/5/23.
 */

public abstract class BaseFg extends Fragment {

    protected View view;
    private ViewGroup parent;
    protected Unbinder unbinder;
    protected Context context;
    private LoadService loadService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        unbinder = ButterKnife.bind(this, view);
        initImmersionBar();


        initViews();
        return view;
    }


    protected void initViews() {
    }

    private void initImmersionBar() {
        if (immersionBar()) {
            ImmersionBar.with(this)
                    .titleBar(R.id.statusBarView,false)
                    .statusBarDarkFont(true)
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                    .init();
        }
    }


    protected void showLoading(View view) {

        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    // 重新加载逻辑
                    LogUtils.i("重新加载逻辑");
                }
            });
        }
        loadService.showCallback(LoadingCallback.class);
    }

    protected void showSuccess() {
        if (loadService != null) {
            loadService.showSuccess();
        }

    }

    protected boolean immersionBar() {
        return false;
    }

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
