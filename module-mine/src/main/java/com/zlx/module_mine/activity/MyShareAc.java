package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.library_common.adapters.RvAdapterArticleList;
import com.zlx.library_common.constrant.PageImpl;
import com.zlx.library_common.res_data.ArticleBean;
import com.zlx.library_common.res_data.MyShareBean;
import com.zlx.library_common.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zlx on 2020/9/25 11:12
 * Email: 1170762202@qq.com
 * Description: 我的分享
 */
public class MyShareAc extends BaseAc implements OnRefreshLoadMoreListener {

    @BindView(R2.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;


    private RvAdapterArticleList adapterArticleList;

    private PageImpl pageImpl = new PageImpl();

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
        adapterArticleList = new RvAdapterArticleList();
        recyclerView.setAdapter(adapterArticleList);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterArticleList.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ivCollect) {
                List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
                ArticleBean articleBean = data.get(position);
                if (articleBean.isCollect()) {
                    ApiUtil.getArticleApi().unCollect(articleBean.getId()).observe(this, apiResponse -> {
                    });
                } else {
                    ApiUtil.getArticleApi().collect(articleBean.getId()).observe(this, apiResponse -> {
                    });
                }
                articleBean.setCollect(!articleBean.isCollect());
                adapterArticleList.notifyItemChanged(position);

            }
        });
        adapterArticleList.setOnItemClickListener((adapter, view, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });

        showLoading(smartRefreshLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listMyShare(true);
    }

    private void listMyShare(boolean refresh) {
        if (refresh) {
            pageImpl.reset();
        } else {
            pageImpl.nextPage();
        }
        ApiUtil.getArticleApi().listMyShare(pageImpl.page).observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<MyShareBean>>() {
                    @Override
                    public void onSuccess(ApiResponse<MyShareBean> data) {
                        if (refresh) {
                            adapterArticleList.setList(data.getData().getShareArticles().getDatas());
                        } else {
                            adapterArticleList.addData(data.getData().getShareArticles().getDatas());

                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (adapterArticleList.getData().size() > 0) {
                            showSuccess();
                        } else {
                            showEmpty();
                        }
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                    }
                }));
    }

    @Override
    public void onRetryBtnClick() {
        super.onRetryBtnClick();
        showLoading(smartRefreshLayout);
        listMyShare(true);
    }


    @OnClick(R2.id.fab)
    public void share() {
        ShareArticleAc.launch(this);
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
