package com.zlx.module_square.squarelist;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_square.BR;
import com.zlx.module_square.R;
import com.zlx.module_square.databinding.AcSquareListBinding;
import com.zlx.widget.CustomItemDecoration;

import java.util.List;

/**
 * FileName: SquareListAc
 * Created by zlx on 2020/9/18 14:47
 * Email: 1170762202@qq.com
 * Description:广场列表
 */
@Route(path = RouterActivityPath.Square.PAGER_SQUARE_LIST)
public class SquareListAc extends BaseMvvmAc<AcSquareListBinding, SquareListViewModel> implements OnRefreshLoadMoreListener {

    private String id;
    private String title;

    private RvAdapterArticleList adapterArticleList;

    @Override
    public void initViews() {
        super.initViews();
        showLoading();
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        setAcTitle(title);
        LogUtil.show("id=" + id + "  title=" + title);
        adapterArticleList = new RvAdapterArticleList();
        binding.recyclerView.setAdapter(adapterArticleList);
        binding.recyclerView.addItemDecoration(new CustomItemDecoration(this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        binding.smartRefreshLayout.setEnableLoadMore(true);
        binding.smartRefreshLayout.setEnableRefresh(true);
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        adapterArticleList.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ivCollect) {
                List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
                ArticleBean articleBean = data.get(position);
                if (articleBean.isCollect()) {
                    viewModel.unCollect(articleBean.getId());
                } else {
                    viewModel.collect(articleBean.getId());
                }
                articleBean.setCollect(!articleBean.isCollect());
                adapterArticleList.notifyItemChanged(position);

            }
        });
        adapterArticleList.setOnItemClickListener((adapter, view, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });

        viewModel.squareLiveData.observe(this, booleanListSimpleEntry -> {
            if (booleanListSimpleEntry != null) {
                List<ArticleBean> value = booleanListSimpleEntry.getValue();
                if (booleanListSimpleEntry.getKey()) {
                    adapterArticleList.setList(value);
                } else {
                    adapterArticleList.addData(value);
                }
            }
            showSuccess();
            binding.smartRefreshLayout.finishRefresh();
            binding.smartRefreshLayout.finishLoadMore();
        });
        viewModel.listArticle(true, id);

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listArticle(false, id);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listArticle(true, id);

    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_square_list;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }
}
