package com.example.module_main.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.constant.RouterConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class VpAdapterMain extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragments = new ArrayList<>();


    public VpAdapterMain(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        fragments.add((Fragment) ARouter.getInstance().build(RouterConstant.ROUT_FG_HOME).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(RouterConstant.ROUT_FG_PROJECT).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(RouterConstant.ROUT_FG_SQUARE).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(RouterConstant.ROUT_FG_PUBLIC).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(RouterConstant.ROUT_FG_MINE).navigation());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


//    public void changeTabNormal(TabLayout.Tab tab) {
//        View view = tab.getCustomView();
//        TextView tv = (TextView) view.findViewById(R.id.tv);
//        ImageView img = (ImageView) view.findViewById(R.id.img);
//        tv.setScaleX(1f);
//        tv.setScaleY(1f);
//        img.setScaleX(1f);
//        img.setScaleY(1f);
//
//        MainTabEnum[] values = MainTabEnum.values();
//        tv.setTextColor(ContextCompat.getColor(context, values[tab.getPosition()].getText_normal()));
//        img.setImageResource(values[tab.getPosition()].getDrawable_normal());
//    }

//    public void changeTabPressed(TabLayout.Tab tab) {
//        View view = tab.getCustomView();
//        TextView tv = (TextView) view.findViewById(R.id.tv);
//        ImageView img = (ImageView) view.findViewById(R.id.img);
//        tv.setScaleX(1.1f);
//        tv.setScaleY(1.1f);
//        img.setScaleX(1.05f);
//        img.setScaleY(1.05f);
//
//        MainTabEnum[] values = MainTabEnum.values();
//        tv.setTextColor(ContextCompat.getColor(context, values[tab.getPosition()].getText_pressed()));
//        img.setImageResource(values[tab.getPosition()].getDrawable_pressed());
//    }
//
//    public View getTabView(Context context, int pos) {
//        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_main, null);
//        TextView tv = (TextView) view.findViewById(R.id.tv);
//        ImageView img = (ImageView) view.findViewById(R.id.img);
//        MainTabEnum[] values = MainTabEnum.values();
//        tv.setText(values[pos].getText());
//        if (pos == 0) {
//            tv.setTextColor(ContextCompat.getColor(context, values[pos].getText_pressed()));
//            img.setImageResource(values[pos].getDrawable_pressed());
//        } else {
//            tv.setTextColor(ContextCompat.getColor(context, values[pos].getText_normal()));
//            img.setImageResource(values[pos].getDrawable_normal());
//        }
//        return view;
//    }


}
