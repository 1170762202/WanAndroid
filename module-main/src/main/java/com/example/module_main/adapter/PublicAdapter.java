package com.example.module_main.adapter;

import com.example.module_main.R;
import com.zlx.module_base.BaseRecycleAdapter;
import com.zlx.widget.rv_item.PolygonItemView;

import java.util.List;

public class PublicAdapter extends BaseRecycleAdapter<String> {

    public PublicAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rv_item_public;
    }

    @Override
    protected void bindData(BaseViewHolder holder, String s, int position) {
        PolygonItemView itemview = holder.getView(R.id.itemview);
    }
}
