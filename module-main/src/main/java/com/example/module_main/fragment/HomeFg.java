package com.example.module_main.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.module_main.R;
import com.example.module_main.R2;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.TwoLevelHeader;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.zlx.module_base.BaseRecycleAdapter;
import com.zlx.module_base.base_api.BaseObserver;
import com.zlx.module_base.base_api.bean.ApiResponse;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.module_base.base_util.LogUtil;
import com.zlx.module_base.bean.res_data.ArticleListRes;
import com.zlx.module_base.bean.res_data.BannerRes;
import com.example.module_main.fragment.adapters.HomeArticleAdapter;
import com.example.module_main.fragment.adapters.HomeBannerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
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
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    private VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
    private DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
    private final ArrayList<DelegateAdapter.Adapter> adapters = new ArrayList<>();


    private List<BannerRes> bannerResList = new ArrayList<>();
    private HomeBannerAdapter homeBannerAdapter = new HomeBannerAdapter(bannerResList);

    private List<ArticleListRes.DatasBean> articleListResList = new ArrayList<>();
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
    }

    private void initEvents() {
        refreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                toolbar.setAlpha(1 - Math.min(percent, 1));
                secondFloor.setTranslationY(Math.min(offset - secondFloor.getHeight() + toolbar.getHeight(), refreshLayout.getLayout().getHeight() - secondFloor.getHeight()));

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                listArticle(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("触发刷新事件");
                listArticle(false);
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
        getBanner();
        listArticle(false);
    }

    private int page = 0;

    private void listArticle(boolean isLoadMore) {
        if (isLoadMore) {
            page++;
        } else {
            page = 0;
        }
        ApiUtil.getApi().listArticle(page).observe(this, new BaseObserver<ApiResponse<ArticleListRes>>() {

            @Override
            protected void onSuccess(ApiResponse<ArticleListRes> data) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (!isLoadMore){
                    articleListResList.clear();
                }
                articleListResList.addAll(data.data.getDatas());
                LogUtil.show("articleListResList="+articleListResList.size());
                homeArticleAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(String msg) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void getBanner() {
        ApiUtil.getApi().getBanner().observe(this, new BaseObserver<ApiResponse<List<BannerRes>>>() {
            @Override
            protected void onSuccess(ApiResponse<List<BannerRes>> data) {
                bannerResList.clear();
                bannerResList.addAll(data.data);
                homeBannerAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(String msg) {

            }
        });
    }


    @OnClick({R2.id.toolbar})
    public void onViewClick(View view) {
        header.openTwoLevel(true);
    }
}
