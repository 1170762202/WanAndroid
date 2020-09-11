package com.zlx.sharelive.ui.main;

import android.graphics.Typeface;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zlx.sharelive.R;
import com.zlx.sharelive.adapter.VpAdapterMain;
import com.zlx.sharelive.base.base_ac.BaseAc;
import com.zlx.sharelive.bean.enm.MainTabEnum;
import com.zlx.sharelive.widget.viewpager.NoScrollViewPager;
import com.zlx.widget.bubblenavigation.BubbleNavigationConstraintView;
import com.zlx.widget.bubblenavigation.BubbleToggleView;
import com.zlx.widget.bubblenavigation.listener.BubbleNavigationChangeListener;

import butterknife.BindView;


public class MainActivity extends BaseAc implements BottomNavigationBar.OnTabSelectedListener, BubbleNavigationChangeListener {


    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;

    @BindView(R.id.navigation_bar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.l_item_home)
    BubbleToggleView lItemHome;
    @BindView(R.id.l_item_search)
    BubbleToggleView lItemSearch;
    @BindView(R.id.l_item_profile_list)
    BubbleToggleView lItemProfileList;
    @BindView(R.id.l_item_notification)
    BubbleToggleView lItemNotification;
    @BindView(R.id.l_item_profile)
    BubbleToggleView lItemProfile;
    @BindView(R.id.bottom_navigation_view_linear)
    BubbleNavigationConstraintView bubbleNavigationLinearView;

    private VpAdapterMain adapterMain;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean transparent() {
        return true;
    }

    @Override
    protected void initViews() {
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


        bubbleNavigationLinearView.setTypeface(Typeface.createFromAsset(getAssets(), "rubik.ttf"));

        bubbleNavigationLinearView.setBadgeValue(0, null);
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, null);
        bubbleNavigationLinearView.setBadgeValue(3, null);
        bubbleNavigationLinearView.setBadgeValue(4, null); //empty badge
        bubbleNavigationLinearView.setNavigationChangeListener(this);
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
//        setAcTitle(MainTabEnum.values()[position].getText());
//        viewPager.setCurrentItem(position);
//        bubbleNavigationLinearView.setCurrentActiveItem(position);

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onNavigationChanged(View view, int position) {
        setAcTitle(MainTabEnum.values()[position].getText());
        viewPager.setCurrentItem(position, true);
    }
}
