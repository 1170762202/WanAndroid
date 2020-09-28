package com.zlx.module_project.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlx.module_base.base_api.res_data.ProjectListRes;
import com.zlx.module_project.R;

import org.jetbrains.annotations.NotNull;

public class RvAdapterTitle extends BaseQuickAdapter<ProjectListRes, BaseViewHolder> {


    public RvAdapterTitle() {
        super(R.layout.rv_item_title);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectListRes projectListRes) {
        baseViewHolder.setText(R.id.text, projectListRes.getName());
    }
}
