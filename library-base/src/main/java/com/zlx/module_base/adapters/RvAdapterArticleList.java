package com.zlx.module_base.adapters;

import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.library_common.R;
import com.zlx.module_base.base_util.GlideUtil;
import com.zlx.module_base.constant.C;
import com.zlx.module_base.base_api.res_data.ArticleBean;
import com.zlx.module_base.widget.shinebutton.ShineButton;

import org.jetbrains.annotations.NotNull;

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

    public RvAdapterArticleList() {
        super(null);
        addItemType(C.ARTICLE_ITEM_TEXT, R.layout.rv_item_article_text);
        addItemType(C.ARTICLE_ITEM_TEXT_PIC, R.layout.rv_item_article_text_pic);
        addChildClickViewIds(R.id.ivCollect);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ArticleBean articleBean) {
        String superChapterName = articleBean.getSuperChapterName();
        String chapterName = articleBean.getChapterName();
        switch (baseViewHolder.getItemViewType()) {
            case C.ARTICLE_ITEM_TEXT:
                baseViewHolder
                        .setText(R.id.tvChapter,
                                TextUtils.isEmpty(superChapterName) ? chapterName : String.format("%s·%s", superChapterName, chapterName))
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
        ShineButton shineButton = baseViewHolder.getView(R.id.ivCollect);
        shineButton.setChecked(articleBean.isCollect());

    }

    /**
     * 取消收藏，做单个删除
     */
    public void cancelCollect(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }
}
