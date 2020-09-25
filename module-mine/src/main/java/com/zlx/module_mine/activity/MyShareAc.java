package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.adapters.RvAdapterMyShare;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zlx on 2020/9/25 11:12
 * Email: 1170762202@qq.com
 * Description: 我的分享
 */
public class MyShareAc extends BaseAc implements OnRefreshLoadMoreListener {

//    @BindView(R2.id.smartRefreshLayout)
//    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;


    private RvAdapterMyShare adapterMyShare;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyShareAc.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_my_share;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle("我的分享");
        adapterMyShare = new RvAdapterMyShare();
        recyclerView.setAdapter(adapterMyShare);
//        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        listMyShare(true);
    }

    private void listMyShare(boolean refresh) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+"");
        }
        adapterMyShare.setList(list);
//        smartRefreshLayout.finishRefresh();
//        smartRefreshLayout.finishLoadMore();
    }

    @OnClick(R2.id.fab)
    public void submit() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        listMyShare(false);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listMyShare(true);

    }
}
