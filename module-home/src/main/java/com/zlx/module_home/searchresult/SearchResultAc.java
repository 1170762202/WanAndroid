package com.zlx.module_home.searchresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.event.EventHandlers;
import com.zlx.module_home.BR;
import com.zlx.module_home.R;
import com.zlx.module_home.databinding.AcSearchResultBinding;

import java.util.List;

/**
 * Created by zlx on 2020/9/23 14:44
 * Email: 1170762202@qq.com
 * Description:
 */
public class SearchResultAc extends BaseMvvmAc<AcSearchResultBinding, SearchResultViewModel> implements OnRefreshLoadMoreListener {

    private RvAdapterArticleList adapterArticleList;

    private String searchContent;

    public static void start(Activity activity, String searchContent, ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(activity, SearchResultAc.class);
        intent.putExtra("searchContent", searchContent);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }


    @Override
    public void initViews() {
        super.initViews();
        binding.setEventHandlers(new SearchResultEvent());
        searchContent = getIntent().getStringExtra("searchContent");
        binding.etSearch.setText(searchContent);
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterArticleList = new RvAdapterArticleList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapterArticleList);
        showLoading(binding.smartRefreshLayout);
        adapterArticleList.setOnItemClickListener((adapter, view, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });
        adapterArticleList.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ivCollect) {
                List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
                ArticleBean articleBean = data.get(position);
                viewModel.unCollect(articleBean.getId());
                adapterArticleList.cancelCollect(position);
            }
        });
        viewModel.articleLiveData.observe(this, booleanListSimpleEntry -> {
            if (booleanListSimpleEntry != null) {
                List<ArticleBean> value = booleanListSimpleEntry.getValue();
                if (booleanListSimpleEntry.getKey()) {
                    adapterArticleList.setList(value);
                } else {
                    adapterArticleList.addData(value);
                }
            }
            binding.smartRefreshLayout.finishRefresh();
            binding.smartRefreshLayout.finishLoadMore();
            List<ArticleBean> data = adapterArticleList.getData();
            if (data.size() > 0) {
                showSuccess();
            } else {
                showEmpty();
            }
        });
        viewModel.search(true, searchContent);
    }

    @Override
    public void onRetryBtnClick() {
        super.onRetryBtnClick();
        showLoading(binding.smartRefreshLayout);
        viewModel.search(true, searchContent);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.search(false, searchContent);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.search(true, searchContent);
    }
    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_search_result;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }



    public class SearchResultEvent extends EventHandlers {
        @Override
        public void onClick(View view) {
            super.onClick(view);
            finishAfterTransition();
        }
    }
}
