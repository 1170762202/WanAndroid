package com.zlx.module_mine.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.module_mine.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zlx on 2020/9/25 11:32
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterMyShare extends BaseQuickAdapter<String, BaseViewHolder> {
    public RvAdapterMyShare() {
        super(R.layout.rv_item_my_share);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text, s);
    }
}
