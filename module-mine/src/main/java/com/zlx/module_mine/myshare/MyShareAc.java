package com.zlx.module_mine.myshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zlx.module_base.adapters.RvAdapterArticleList;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_api.res_data.ArticleListRes;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcMyShareBinding;
import com.zlx.widget.CustomItemDecoration;

import java.util.List;

/**
 * Created by zlx on 2020/9/25 11:12
 * Email: 1170762202@qq.com
 * Description: 我的分享
 */
public class MyShareAc extends BaseMvvmAc<AcMyShareBinding, MyShareViewModel> implements OnRefreshLoadMoreListener {

    private RvAdapterArticleList adapterArticleList;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MyShareAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle(getString(R.string.mine_share));
        binding.recyclerView.addItemDecoration(new CustomItemDecoration(this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST, R.drawable.linear_split_line));
        binding.recyclerView.setSwipeItemMenuEnabled(true);
        binding.recyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
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
        binding.recyclerView.setOnItemMenuClickListener((menuBridge, adapterPosition) -> {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    viewModel.deleteArticle(adapterArticleList.getData().get(adapterPosition).getId());
                    adapterArticleList.removeAt(adapterPosition);
                }
            }
        });
        adapterArticleList = new RvAdapterArticleList();
        binding.recyclerView.setAdapter(adapterArticleList);

        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
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

        viewModel.shareLiveData.observe(this, booleanMyShareBeanSimpleEntry -> {
            if (booleanMyShareBeanSimpleEntry != null) {
                ArticleListRes shareArticles = booleanMyShareBeanSimpleEntry.getValue().getShareArticles();
                if (booleanMyShareBeanSimpleEntry.getKey()) {
                    adapterArticleList.setList(shareArticles.getDatas());
                } else {
                    adapterArticleList.addData(shareArticles.getDatas());
                }
                binding.smartRefreshLayout.setEnableLoadMore(shareArticles.getCurPage() < shareArticles.getPageCount());
            }
            if (adapterArticleList.getData().size() > 0) {
                showSuccess();
            } else {
                showEmpty();
            }
            binding.smartRefreshLayout.finishRefresh();
            binding.smartRefreshLayout.finishLoadMore();
        });
        showLoading(binding.smartRefreshLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.listMyShare(true);
    }


    @Override
    public void onRetryBtnClick() {
        super.onRetryBtnClick();
        showLoading(binding.smartRefreshLayout);
        viewModel.listMyShare(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listMyShare(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listMyShare(true);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_my_share;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
