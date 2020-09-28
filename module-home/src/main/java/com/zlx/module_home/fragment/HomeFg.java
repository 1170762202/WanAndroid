package com.zlx.module_home.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.TwoLevelHeader;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.BannerRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_home.R;
import com.zlx.module_home.R2;
import com.zlx.module_home.activity.SearchAc;
import com.zlx.module_home.adapters.HomeArticleAdapter;
import com.zlx.module_home.adapters.HomeBannerAdapter;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.module_network.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFg extends BaseFg {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.second_floor)
    View secondFloor;
    @BindView(R2.id.second_floor_content)
    View secondFloorContent;
    @BindView(R2.id.classics)
    ClassicsHeader classics;
    @BindView(R2.id.header)
    TwoLevelHeader header;
    @BindView(R2.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R2.id.topbar)
    View topbar;
    @BindView(R2.id.root)
    RelativeLayout root;
    @BindView(R2.id.ivSearch)
    ImageView ivSearch;

    private VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
    private DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
    private final ArrayList<DelegateAdapter.Adapter> adapters = new ArrayList<>();


    private List<BannerRes> bannerResList = new ArrayList<>();
    private HomeBannerAdapter homeBannerAdapter = new HomeBannerAdapter(bannerResList);

    private List<ArticleBean> articleListResList = new ArrayList<>();
    private HomeArticleAdapter homeArticleAdapter = new HomeArticleAdapter(articleListResList);


    @Override
    protected int getLayoutId() {
        return R.layout.fg_home;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {
        adapters.add(homeBannerAdapter);
        adapters.add(homeArticleAdapter);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setAdapter(delegateAdapter);

        initEvents();

        showLoading(root);
        getBanner();
        listArticle(false);
    }


    private void initEvents() {
        homeArticleAdapter.setOnArticleCollect(articleBean -> {
            if (articleBean.isCollect()){
                ApiUtil.getArticleApi().unCollect(articleBean.getId()).observe(this,apiResponse -> {});
            }else {
                ApiUtil.getArticleApi().collect(articleBean.getId()).observe(this,apiResponse -> {});
            }
        });
        refreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                topbar.setAlpha(1 - Math.min(percent, 1));
                secondFloor.setTranslationY(Math.min(offset - secondFloor.getHeight() + topbar.getHeight(), refreshLayout.getLayout().getHeight() - secondFloor.getHeight()));

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                listArticle(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("触发刷新事件");
                listArticle(false);
                getBanner();
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                if (oldState == RefreshState.TwoLevel) {
                    secondFloorContent.animate().alpha(0).setDuration(1000);
                }
            }
        });

        header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("触发二楼事件");
                secondFloorContent.animate().alpha(1).setDuration(2000);
                return true;//true 将会展开二楼状态 false 关闭刷新

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private int page = 0;

    private void listArticle(boolean isLoadMore) {
        if (isLoadMore) {
            page++;
        } else {
            page = 0;
        }
        ApiUtil.getArticleApi().listArticle(page).observe(this, new BaseObserver<>(new BaseObserverCallBack<ApiResponse<ArticleListRes>>() {
            @Override
            public void onSuccess(ApiResponse<ArticleListRes> data) {
                if (!isLoadMore) {
                    articleListResList.clear();
                }
                articleListResList.addAll(data.getData().getDatas());
                LogUtil.show("articleListResList=" + articleListResList.size());
                homeArticleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                showSuccess();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        }));
    }

    private void getBanner() {
        ApiUtil.getArticleApi().getBanner().observe(this, new BaseObserver<>(new BaseObserverCallBack<ApiResponse<List<BannerRes>>>() {
            @Override
            public void onSuccess(ApiResponse<List<BannerRes>> data) {
                bannerResList.clear();
                bannerResList.addAll(data.getData());
                homeBannerAdapter.notifyDataSetChanged();
            }
        }));
    }


    @OnClick({R2.id.topbar,R2.id.ivSearch})
    public void onViewClick(View view) {
        if (view.getId()==R.id.topbar){
            header.openTwoLevel(true);
        }else if (view.getId()==R.id.ivSearch){
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),ivSearch,"search");
            ActivityCompat.startActivity(getActivity(),new Intent(getActivity(), SearchAc.class),activityOptionsCompat.toBundle());
        }
    }
}
