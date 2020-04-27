package com.zlx.sharelive.base.base_ac;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    @BindView(R.id.top_bar)
    protected RelativeLayout topbar;

    @Nullable
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManage.addActivity(this);
        mUnBinder = ButterKnife.bind(this);
        initViews();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initViews();

    protected void setTopBarBg(int drawable) {
        if (topbar != null) {
            topbar.setBackgroundResource(drawable);
        }
    }
    protected void setAcTitle(String title){
        if (tvTitle!=null){
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
