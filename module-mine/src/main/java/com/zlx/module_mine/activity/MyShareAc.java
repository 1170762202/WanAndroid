package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.constant.PageImpl;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.base_api.res_data.MyShareBean;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.widget.CustomItemDecoration;

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
    SwipeRecyclerView recyclerView;


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
        setAcTitle(getString(R.string.mine_share));
        recyclerView.addItemDecoration(new CustomItemDecoration(this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        recyclerView.setSwipeItemMenuEnabled(true);
        recyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                SwipeMenuItem deleteItem = new SwipeMenuItem(MyShareAc.this)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setBackgroundColorResource(android.R.color.holo_red_dark)
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        });
        recyclerView.setOnItemMenuClickListener((menuBridge, adapterPosition) -> {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition==0){
                    deleteArticle(adapterArticleList.getData().get(adapterPosition).getId());
                    adapterArticleList.removeAt(adapterPosition);
                }
            }
        });
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

    private void deleteArticle(String id) {
        ApiUtil.getArticleApi().deleteArticle(id).observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse data) {

                    }
                }));
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
                        ArticleListRes shareArticles = data.getData().getShareArticles();
                        if (refresh) {
                            adapterArticleList.setList(shareArticles.getDatas());
                        } else {
                            adapterArticleList.addData(shareArticles.getDatas());
                        }
                        smartRefreshLayout.setEnableLoadMore(shareArticles.getCurPage()<shareArticles.getPageCount());
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
