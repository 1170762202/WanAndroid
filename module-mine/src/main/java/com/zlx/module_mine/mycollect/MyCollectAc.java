package com.zlx.module_mine.mycollect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_mine.databinding.AcMyCollectBinding;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.widget.CustomItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zlx on 2020/9/22 13:50
 * Email: 1170762202@qq.com
 * Description: 我的收藏
 */
public class MyCollectAc extends BaseMvvmAc<AcMyCollectBinding, MyCollectViewModel> implements OnRefreshLoadMoreListener {

    private RvAdapterArticleList adapterArticleList;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyCollectAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();

        adapterArticleList = new RvAdapterArticleList();
        binding.recyclerView.setAdapter(adapterArticleList);
        binding.recyclerView.addItemDecoration(new CustomItemDecoration(this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        adapterArticleList.setOnItemClickListener((adapter, view, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });
        adapterArticleList.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ivCollect) {
                List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
                ArticleBean articleBean = data.get(position);
                ApiUtil.getArticleApi().unCollect(articleBean.getId()).observe(this, apiResponse -> {
                });
                adapterArticleList.cancelCollect(position);
            }
        });
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        viewModel.collectLiveData.observe(this, articleBeans -> {
            if (articleBeans != null) {
                Boolean key = articleBeans.getKey();
                List<ArticleBean> value = articleBeans.getValue();
                if (key) {
                    adapterArticleList.setList(value);
                } else {
                    adapterArticleList.addData(value);
                }
            }
            binding.smartRefreshLayout.finishLoadMore();
            binding.smartRefreshLayout.finishRefresh();
            if (adapterArticleList.getData().size() <= 0) {
                showEmpty();
            } else {
                showSuccess();
            }
        });

        showLoading(binding.smartRefreshLayout);
        viewModel.listMyCollect(true);
    }


    @Override
    public void onRetryBtnClick() {
        showLoading();
        viewModel.listMyCollect(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listMyCollect(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listMyCollect(true);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_my_collect;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
