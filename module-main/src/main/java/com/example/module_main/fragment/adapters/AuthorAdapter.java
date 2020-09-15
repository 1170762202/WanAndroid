package com.example.module_main.fragment.adapters;

import android.widget.TextView;

import com.example.module_main.R;
import com.zlx.module_base.BaseRecycleAdapter;

import java.util.List;

public class AuthorAdapter extends BaseRecycleAdapter<String> {

    public AuthorAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rv_item_author;
    }

    @Override
    protected void bindData(BaseViewHolder holder, String s, int position) {
        TextView textView = holder.getView(R.id.text);
        textView.setText(position + "");
    }
}
