package com.zlx.module_mine.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_mine.R;
import com.zlx.module_mine.bean.OpenSourcePro;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zlx on 2020/9/22 17:11
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterOpenSourcePro extends BaseQuickAdapter<OpenSourcePro, BaseViewHolder> {

    public RvAdapterOpenSourcePro() {
        super(R.layout.rv_item_open_source_pro);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OpenSourcePro openSourcePro) {
        baseViewHolder.setText(R.id.tvTitle, openSourcePro.getAuthor())
                .setText(R.id.tvContent, openSourcePro.getContent());
        baseViewHolder.itemView.setOnClickListener(view -> {
            RouterUtil.launchWeb(openSourcePro.getLink());
        });
    }
}
