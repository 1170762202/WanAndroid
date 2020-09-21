package com.zlx.library_common.adapters;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.library_common.constrant.C;
import com.zlx.library_common.res_data.ArticleBean;
import com.zlx.library_common.util.GlideUtil;
import com.zlx.module_base.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: RvAdapterArticleList
 * Created by zlx on 2020/9/18 15:45
 * Email: 1170762202@qq.com
 * Description: 文章列表
 */
public class RvAdapterArticleList extends BaseMultiItemQuickAdapter<ArticleBean, BaseViewHolder> {

    private boolean hasTop = false;

    public void setHasTop(boolean hasTop) {
        this.hasTop = hasTop;
    }

    public RvAdapterArticleList(@Nullable List<ArticleBean> data) {
        super(data);
        addItemType(C.ARTICLE_ITEM_TEXT, R.layout.rv_item_article_text);
        addItemType(C.ARTICLE_ITEM_TEXT_PIC, R.layout.rv_item_article_text_pic);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ArticleBean articleBean) {
        switch (baseViewHolder.getItemViewType()) {
            case C.ARTICLE_ITEM_TEXT:
                baseViewHolder
                        .setText(R.id.tvChapter, String.format("%s·%s", articleBean.getSuperChapterName(), articleBean.getChapterName()))
                        .setText(R.id.tvTime, articleBean.getNiceDate())
                        .setGone(R.id.tvRefresh, !articleBean.isFresh());
                break;
            case C.ARTICLE_ITEM_TEXT_PIC:
                AppCompatImageView imageView = baseViewHolder.getView(R.id.image);
                GlideUtil.getInstance().loadRoundImage(imageView, articleBean.getEnvelopePic(), 8);
                baseViewHolder.setText(R.id.tvContent, articleBean.getDesc());
                break;
        }
        baseViewHolder.setText(R.id.tvTitle, articleBean.getTitle())
                .setText(R.id.tvAuthor, TextUtils.isEmpty(articleBean.getAuthor()) ? articleBean.getShareUser() : articleBean.getAuthor())
                .setGone(R.id.top, !(hasTop && baseViewHolder.getAdapterPosition() == 0));
    }
}
