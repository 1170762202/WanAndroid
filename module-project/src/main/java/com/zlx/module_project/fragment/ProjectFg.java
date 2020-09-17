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
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterConstant;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.res_data.ArticleListRes;
import com.zlx.module_network.res_data.ProjectListRes;
import com.zlx.module_network.util.ApiUtil;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_project.R;
import com.zlx.module_project.R2;
import com.zlx.module_project.adapters.HomeArticleAdapter;
import com.zlx.module_project.adapters.RvAdapterTitle;
import com.zlx.widget.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

@Route(path = RouterConstant.ROUT_FG_PROJECT)
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

    private RvAdapterTitle adapterTitle;
    private List<ProjectListRes> projectList = new ArrayList<>();

    private HomeArticleAdapter homeArticleAdapter;
    private List<ArticleListRes.DatasBean> articleList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fg_project;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {

        initListView();
        initSlide();
        initEvents();
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

        ApiUtil.getProjectApi().listProjectsTab().observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<ProjectListRes>>>() {
                    @Override
                    public void onSuccess(ApiResponse<List<ProjectListRes>> data) {
                        adapterTitle.refresh(data.getData());
                        if (data.getData().size() > 0) {
                            ProjectListRes projectListRes = data.getData().get(0);
                            tvName.setText(projectListRes.getName());
                            id = projectListRes.getId();
                            listProjects(id, true);
                        }
                    }
                }));
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
                            articleList.clear();
                        }
                        articleList.addAll(data.getData().getDatas());
                        homeArticleAdapter.notifyDataSetChanged();
                        LogUtil.show("articleList=" + articleList.size());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
                    }
                }));
    }

    private void initListView() {
        adapterTitle = new RvAdapterTitle(projectList);
        adapterTitle.setListener(position -> {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            ProjectListRes projectListRes = projectList.get(position);
            tvName.setText(projectListRes.getName());

            id = projectListRes.getId();
            listProjects(id, true);

        });
        rvTitle.setAdapter(adapterTitle);

        homeArticleAdapter = new HomeArticleAdapter(articleList);
        homeArticleAdapter.setHasTop(true);
        rvContent.setAdapter(homeArticleAdapter);

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