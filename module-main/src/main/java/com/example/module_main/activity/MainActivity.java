package com.example.module_main.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.module_main.R;
import com.example.module_main.R2;
import com.example.module_main.adapter.VpAdapterMain;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.widget.bubblenavigation.BubbleNavigationConstraintView;
import com.zlx.widget.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.zlx.widget.viewpager.NoScrollViewPager;

import butterknife.BindView;


public class MainActivity extends BaseAc implements BubbleNavigationChangeListener {


    @BindView(R2.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R2.id.bottom_navigation_view_linear)
    BubbleNavigationConstraintView bubbleNavigationLinearView;

    private VpAdapterMain adapterMain;


    @Override
    protected int getLayoutId() {
        return R.layout.ac_main;
    }

    @Override
    protected boolean canSwipeBack() {
        return false;
    }

    @Override
    protected void initViews() {
        initTab();

        initNav();
    }

    private void initNav() {


        bubbleNavigationLinearView.setTypeface(Typeface.createFromAsset(getAssets(), "rubik.ttf"));

        bubbleNavigationLinearView.setBadgeValue(0, null);
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, null);
        bubbleNavigationLinearView.setBadgeValue(3, null);
        bubbleNavigationLinearView.setBadgeValue(4, null); //empty badge
        bubbleNavigationLinearView.setNavigationChangeListener(this);
    }

    private void initTab() {
        adapterMain = new VpAdapterMain(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setScrollable(false);
        viewPager.setAdapter(adapterMain);
    }


    @Override
    public void onNavigationChanged(View view, int position) {
        viewPager.setCurrentItem(position, false);
    }

    //用户按返回按钮不关闭页面，而是返回到系统桌面。相当于按home键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
