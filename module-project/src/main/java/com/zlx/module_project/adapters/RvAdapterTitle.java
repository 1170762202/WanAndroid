package com.zlx.module_project.adapters;

import android.widget.TextView;

import com.zlx.module_base.BaseRecycleAdapter;
import com.zlx.module_base.OnItemClickListener;
import com.zlx.module_network.res_data.ProjectListRes;
import com.zlx.module_project.R;

import java.util.List;

public class RvAdapterTitle extends BaseRecycleAdapter<ProjectListRes> {

    public RvAdapterTitle(List<ProjectListRes> datas) {
        super(datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rv_item_title;
    }

    @Override
    protected void bindData(BaseViewHolder holder, ProjectListRes projectListRes, int position) {
        TextView text = holder.getView(R.id.text);
        text.setText(projectListRes.getName());
        text.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
