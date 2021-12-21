package com.zlx.module_project.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_fg.BaseMvvmFg;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_project.BR;
import com.zlx.module_project.R;
import com.zlx.module_project.adapters.RvAdapterTitle;
import com.zlx.module_project.databinding.FgProjectBinding;
import com.zlx.widget.CustomItemDecoration;
import com.zlx.widget.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import static android.content.ContentValues.TAG;

@Route(path = RouterFragmentPath.Project.PAGER_PROJECT)
public class ProjectFg extends BaseMvvmFg<FgProjectBinding, ProjectViewModel> implements OnRefreshLoadMoreListener {

    private RvAdapterTitle adapterTitle;

    private RvAdapterArticleList adapterArticleList;


    @Override
    protected int getLayoutId() {
        return R.layout.fg_project;
    }

    @Override
    protected void initViews() {
        showLoading(binding.root);
        viewModel.projectTabLiveData.observe(this, projectListRes -> {
            adapterTitle.setList(projectListRes);
            ProjectListRes first = projectListRes.get(0);
            binding.tvName.setText(first.getName());
            id = first.getId();
            viewModel.listProjects(true, id);
        });
        viewModel.projectLiveData.observe(this, booleanListSimpleEntry -> {
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
        initListView();
        initSlide();
        initEvents();
    }

    @Override
    protected int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fg_project;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.show("onResume");
        viewModel.listProjectsTab();

    }

    private void initEvents() {

        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private void initSlide() {
        binding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.e(TAG, "onPanelStateChanged " + newState);
            }
        });
        binding.slidingLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }


    private String id;

    private void initListView() {
        adapterTitle = new RvAdapterTitle();
        adapterTitle.setOnItemClickListener((adapter, view1, position) -> {
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            ProjectListRes projectListRes = adapterTitle.getData().get(position);
            binding.tvName.setText(projectListRes.getName());

            id = projectListRes.getId();
            viewModel.listProjects(true, id);
        });

        binding.rvTitle.setAdapter(adapterTitle);

        adapterArticleList = new RvAdapterArticleList();
        adapterArticleList.setHasTop(true);
        binding.rvContent.addItemDecoration(new CustomItemDecoration(getActivity(),
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        binding.rvContent.setAdapter(adapterArticleList);
        adapterArticleList.setOnItemClickListener((adapter, view1, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });
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
        binding.slidingLayout.setScrollableView(binding.rvTitle);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listProjects(false, id);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listProjects(true, id);

    }
}