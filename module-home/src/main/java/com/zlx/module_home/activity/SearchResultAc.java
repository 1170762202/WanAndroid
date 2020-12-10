package com.zlx.module_home.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.constant.PageImpl;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_home.R;
import com.zlx.module_home.R2;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by zlx on 2020/9/23 14:44
 * Email: 1170762202@qq.com
 * Description:
 */
public class SearchResultAc extends BaseAc implements OnRefreshLoadMoreListener {

    @BindView(R2.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.etSearch)
    TextView etSearch;

    private RvAdapterArticleList adapterArticleList;

    private String searchContent;
    private PageImpl pageImpl = new PageImpl();

    public static void start(Activity activity, String searchContent, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(activity, SearchResultAc.class);
        intent.putExtra("searchContent", searchContent);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_search_result;
    }


    @Override
    public void initViews() {
        super.initViews();
        searchContent = getIntent().getStringExtra("searchContent");
        etSearch.setText(searchContent);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterArticleList = new RvAdapterArticleList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterArticleList);
        showLoading(smartRefreshLayout);
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
        search(true);
    }

    @Override
    public void onRetryBtnClick() {
        super.onRetryBtnClick();
        showLoading(smartRefreshLayout);
        search(true);
    }

    private void search(boolean refresh) {
        if (refresh) {
            pageImpl.resetZero();
        } else {
            pageImpl.nextZeroPage();
        }
        ApiUtil.getArticleApi().search(pageImpl.zeroPage, searchContent).observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<ArticleListRes>>() {
                    @Override
                    public void onSuccess(ApiResponse<ArticleListRes> data) {
                        if (refresh) {
                            adapterArticleList.setList(data.getData().getDatas());
                        } else {
                            adapterArticleList.addData(data.getData().getDatas());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                        List<ArticleBean> data = adapterArticleList.getData();
                        if (data.size() > 0) {
                            showSuccess();
                        } else {
                            showEmpty();
                        }
                    }
                }));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        search(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        search(true);

    }

    @OnFocusChange(R2.id.etSearch)
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
//            finishAfterTransition();
        }
    }

    @OnClick({R2.id.tvCancel})
    public void back() {
        finishAfterTransition();
    }
}
