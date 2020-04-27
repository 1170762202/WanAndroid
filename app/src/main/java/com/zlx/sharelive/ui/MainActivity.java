package com.zlx.sharelive.ui;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gyf.barlibrary.ImmersionBar;
import com.zlx.sharelive.R;
import com.zlx.sharelive.adapter.VpAdapterMain;
import com.zlx.sharelive.base.base_ac.BaseAc;
import com.zlx.sharelive.bean.enm.MainTabEnum;
import com.zlx.sharelive.widget.viewpager.NoScrollViewPager;

import butterknife.BindView;


public class MainActivity extends BaseAc implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;

    @BindView(R.id.navigation_bar)
    BottomNavigationBar navigationBar;

    private VpAdapterMain adapterMain;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        ImmersionBar.with(this).init();
        setUpFinish(true);
        initTab();

        initNavi();
    }

    private void initNavi() {
        MainTabEnum[] values = MainTabEnum.values();
        for (MainTabEnum value : values) {
            navigationBar.addItem(new BottomNavigationItem(value.getDrawable_normal(), value.getText())
                    .setActiveColorResource(value.getRippleColor()));
        }
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        navigationBar.setAnimationDuration(100);
        navigationBar.setFirstSelectedPosition(0).initialise();
        navigationBar.setTabSelectedListener(this);
    }

    private void initTab() {
        MainTabEnum[] values = MainTabEnum.values();
        adapterMain = new VpAdapterMain(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(values.length);
        viewPager.setScrollable(false);
        viewPager.setAdapter(adapterMain);

    }

    @Override
    public void onTabSelected(int position) {
        setAcTitle(MainTabEnum.values()[position].getText());
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
