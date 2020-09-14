package com.example.module_main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_main.R;
import com.example.module_main.constrant.MainTabEnum;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class VpAdapterMain extends FragmentPagerAdapter {

    private Context context;

    public VpAdapterMain(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) MainTabEnum.getTab(position).getCls().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return MainTabEnum.values().length;
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
