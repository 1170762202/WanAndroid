package com.zlx.sharelive.ui.home;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.TwoLevelHeader;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.zlx.module_base.BaseRecycleAdapter;
import com.zlx.module_base.base_api.BaseObserver;
import com.zlx.module_base.base_api.bean.ApiResponse;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_util.LogUtil;
import com.zlx.module_base.bean.res_data.ArticleListRes;
import com.zlx.module_base.bean.res_data.BannerRes;
import com.zlx.sharelive.R;
import com.zlx.sharelive.base.base_fg.BaseFg;
import com.zlx.sharelive.ui.home.adapters.HomeArticleAdapter;
import com.zlx.sharelive.ui.home.adapters.HomeBannerAdapter;

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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.second_floor)
    ImageView secondFloor;
    @BindView(R.id.second_floor_content)
    FrameLayout secondFloorContent;
    @BindView(R.id.classics)
    ClassicsHeader classics;
    @BindView(R.id.header)
    TwoLevelHeader header;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;

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
    protected void initViews() {
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
                topBar.setAlpha(1 - Math.min(percent, 1));
                secondFloor.setTranslationY(Math.min(offset - secondFloor.getHeight() + topBar.getHeight(), refreshLayout.getLayout().getHeight() - secondFloor.getHeight()));

            }
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("触发刷新事件");
                refreshLayout.finishRefresh(2000);
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
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("onLoadMore");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.show("onRefresh");

            }
        });


        List<Void> voids = Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView1.setAdapter(new BaseRecycleAdapter(voids) {
            @Override
            protected int getLayoutId() {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            protected void bindData(BaseViewHolder holder, Object o, int position) {
                TextView text1 = holder.getView(android.R.id.text1);
                text1.setText(position + "");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getBanner();
        listArticle();
    }

    private void listArticle() {
        ApiUtil.getApi().listArticle(0).observe(this, new BaseObserver<ApiResponse<ArticleListRes>>() {

            @Override
            protected void onSuccess(ApiResponse<ArticleListRes> data) {
                articleListResList.clear();
                articleListResList.addAll(data.data.getDatas());
                homeArticleAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(String msg) {

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


    @OnClick({R.id.top_bar})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar:
                header.openTwoLevel(true);
                break;
        }
    }
}
