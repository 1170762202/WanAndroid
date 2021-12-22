package com.zlx.module_public.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.PublicAuthorListRes;
import com.zlx.module_base.base_fg.BaseMvvmFg;
import com.zlx.module_base.constant.RouterFragmentPath;
import com.zlx.module_public.BR;
import com.zlx.module_public.R;
import com.zlx.module_public.adapters.AuthorAdapter;
import com.zlx.module_public.adapters.PublicArticleAdapter;
import com.zlx.module_public.databinding.FgPublicBinding;
import com.zlx.module_public.guillotine.animation.GuillotineAnimation;
import com.zlx.widget.hivelayoutmanager.HiveLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C)
 * FileName: PublicFg
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 11:21
 * Description: 公众号
 */
@Route(path = RouterFragmentPath.Public.PAGER_PUBLIC)
public class PublicFg extends BaseMvvmFg<FgPublicBinding, PublicViewModel> implements OnRefreshLoadMoreListener {
    private static final long RIPPLE_DURATION = 250;

    RecyclerView rvAuthor;
    private List<PublicAuthorListRes> authList = new ArrayList<>();
    private AuthorAdapter authorAdapter;
    private GuillotineAnimation guillotineAnimation;

    private PublicArticleAdapter publicArticleAdapter;
    private List<ArticleBean> articleList = new ArrayList<>();
    private String id;

    @Override
    protected boolean immersionBar() {
        return true;
    }

    @Override
    protected void initViews() {
        View guillotineMenu = LayoutInflater.from(getContext()).inflate(R.layout.guillotine, null);
        rvAuthor = guillotineMenu.findViewById(R.id.rvAuthor);
        HiveLayoutManager layoutManager = new HiveLayoutManager(HiveLayoutManager.VERTICAL);
        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setPadding(0, 0, 0, 0);
        rvAuthor.setLayoutManager(layoutManager);
        authorAdapter = new AuthorAdapter(authList);
        authorAdapter.setListener(position -> {
            guillotineAnimation.close();
            PublicAuthorListRes publicAuthorListRes = authList.get(position);
            String name = publicAuthorListRes.getName();
            binding.tvTitle.setText(name);
            id = publicAuthorListRes.getId();
            viewModel.listArticle(true, id);

        });
        rvAuthor.setAdapter(authorAdapter);
        binding.root.addView(guillotineMenu);
        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.ivHamburger), binding.ivClose)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(binding.toolbar)
                .setClosedOnStart(true)
                .build();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        publicArticleAdapter = new PublicArticleAdapter(articleList);
        binding.recyclerView.setAdapter(publicArticleAdapter);
        publicArticleAdapter.setOnArticleCollect(articleBean -> {
            if (articleBean.isCollect()) {
                viewModel.unCollect(articleBean.getId());
            } else {
                viewModel.collect(articleBean.getId());
            }
        });
        binding.smartRefreshLayout.setEnableLoadMore(true);
        binding.smartRefreshLayout.setEnableRefresh(true);
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this);
        viewModel.publicLiveData.observe(this,booleanListSimpleEntry -> {
            if (booleanListSimpleEntry!=null){
                List<ArticleBean> value = booleanListSimpleEntry.getValue();
                if (booleanListSimpleEntry.getKey()){
                    articleList.clear();
                }
                articleList.addAll(value);
                runLayoutAnimation(binding.recyclerView);
            }
            showSuccess();
            binding.smartRefreshLayout.finishRefresh();
            binding.smartRefreshLayout.finishLoadMore();
        });
        viewModel.authorLiveData.observe(this, publicAuthorListRes -> {
            authorAdapter.refresh(publicAuthorListRes);
            if (publicAuthorListRes.size() > 0) {
                PublicAuthorListRes first = publicAuthorListRes.get(0);
                String name = first.getName();
                binding.tvTitle.setText(name);
                id = first.getId();
                viewModel.listArticle(true, id);
            }
        });
        showLoading(binding.root);

    }

    @Override
    protected int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fg_public;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.listPublicAuthor();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        viewModel.listArticle(false, id);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        viewModel.listArticle(true, id);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}

