package com.zlx.module_project.fragment;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_project.R;
import com.zlx.module_project.R2;
import com.zlx.module_project.adapters.RvAdapterTitle;
import com.zlx.widget.CustomItemDecoration;
import com.zlx.widget.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

@Route(path = RouterFragmentPath.Project.PAGER_PROJECT)
public class ProjectFg extends BaseFg implements OnRefreshLoadMoreListener {


    @BindView(R2.id.rvTitle)
    RecyclerView rvTitle;
    @BindView(R2.id.rvContent)
    RecyclerView rvContent;
    @BindView(R2.id.dragView)
    LinearLayout dragView;
    @BindView(R2.id.sliding_layout)
    SlidingUpPanelLayout mLayout;
    @BindView(R2.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.tvName)
    TextView tvName;
    @BindView(R2.id.root)
    View root;

    private RvAdapterTitle adapterTitle;

    private RvAdapterArticleList adapterArticleList;


    @Override
    protected int getLayoutId() {
        return R.layout.fg_project;
    }

    @Override
    protected void initViews() {
        showLoading(root);
        initListView();
        initSlide();
        initEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.show("onResume");
        listProjectsTab();

    }

    private void initEvents() {

        smartRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private void initSlide() {
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                Log.e(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.e(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void listProjectsTab() {
        List<ProjectListRes> projectTabs = MMkvHelper.getInstance().getProjectTabs(ProjectListRes.class);
        if (projectTabs.size() > 0) {
            adapterTitle.setList(projectTabs);
            if (projectTabs.size() > 0) {
                ProjectListRes projectListRes = projectTabs.get(0);
                tvName.setText(projectListRes.getName());
                id = projectListRes.getId();
                listProjects(id, true);
            }
        }else {
            ApiUtil.getProjectApi().listProjectsTab().observe(this,
                    new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<ProjectListRes>>>() {
                        @Override
                        public void onSuccess(ApiResponse<List<ProjectListRes>> data) {
                            List<ProjectListRes> dataData = data.getData();
                            if (dataData.size() > 0) {
                                MMkvHelper.getInstance().saveProjectTabs(dataData);
                                adapterTitle.setList(dataData);
                                ProjectListRes projectListRes = dataData.get(0);
                                tvName.setText(projectListRes.getName());
                                id = projectListRes.getId();
                                listProjects(id, true);
                            }
                        }
                    }));
        }
    }

    private int page = 0;
    private String id;

    private void listProjects(String id, boolean refresh) {
        if (refresh) {
            page = 0;
        } else {
            page++;
        }
        ApiUtil.getProjectApi().listProjects(page, id).observe(this,
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
                        showSuccess();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                    }
                }));
    }

    private void initListView() {
        adapterTitle = new RvAdapterTitle();
        adapterTitle.setOnItemClickListener((adapter, view1, position) -> {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            ProjectListRes projectListRes = adapterTitle.getData().get(position);
            tvName.setText(projectListRes.getName());

            id = projectListRes.getId();
            listProjects(id, true);
        });

        rvTitle.setAdapter(adapterTitle);

        adapterArticleList = new RvAdapterArticleList();
        adapterArticleList.setHasTop(true);
        rvContent.addItemDecoration(new CustomItemDecoration(getActivity(),
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        rvContent.setAdapter(adapterArticleList);
        adapterArticleList.setOnItemClickListener((adapter, view1, position) -> {
            List<ArticleBean> data = (List<ArticleBean>) adapter.getData();
            RouterUtil.launchWeb(data.get(position).getLink());
        });
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
        mLayout.setScrollableView(rvTitle);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        listProjects(id, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listProjects(id, true);

    }
}