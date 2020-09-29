package com.zlx.module_square.adapters;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.base_api.res_data.TreeListRes;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_square.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * FileName: RvAdapterTree
 * Created by zlx on 2020/9/18 11:04
 * Email: 1170762202@qq.com
 * Description: 流式布局
 */
public class RvAdapterNavi extends BaseQuickAdapter<TreeListRes, BaseViewHolder> {

    private LayoutInflater layoutInflater = null;
    private Queue<AppCompatTextView> mFlexItemTextViewCaches = new LinkedList<>();

    public RvAdapterNavi() {
        super(R.layout.rv_item_flex);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TreeListRes naviListRes) {
        baseViewHolder.setText(R.id.tvTitle,naviListRes.getName());
        FlexboxLayout flexLayout =  baseViewHolder.getView(R.id.flexLayout);
        List<ArticleBean> children = naviListRes.getArticles();
//            flexLayout.removeAllViews();  //注释这条属性，用下面onViewRecycled()方法也行
        for (int i = 0; i < children.size(); i++) {
            ArticleBean datasBean = children.get(i);
            AppCompatTextView labelTv = createOrGetCacheTv(flexLayout);
            labelTv.setText(datasBean.getTitle());
            labelTv.setOnClickListener(v -> {
                RouterUtil.launchWeb(datasBean.getLink());
            });
            flexLayout.addView(labelTv);
        }
    }


    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        FlexboxLayout flexLayout = holder.getView(R.id.flexLayout);
        for (int i = 0; i < flexLayout.getChildCount(); i++) {
            mFlexItemTextViewCaches.offer((AppCompatTextView) flexLayout.getChildAt(i));
        }
        flexLayout.removeAllViews();
    }

    private AppCompatTextView createOrGetCacheTv(FlexboxLayout flexboxLayout) {
        AppCompatTextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        return findLabel(flexboxLayout);
    }

    private AppCompatTextView findLabel(FlexboxLayout flexboxLayout) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(flexboxLayout.getContext());
        return (AppCompatTextView) layoutInflater.inflate(R.layout.flextlayout_item_label, flexboxLayout, false);
    }

}
