package com.zlx.module_public.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.zlx.module_base.base_adapter.BaseRecycleAdapter;
import com.zlx.module_base.base_adapter.OnItemClickListener;
import com.zlx.module_base.base_api.res_data.PublicAuthorListRes;
import com.zlx.module_public.R;
import com.zlx.widget.hivelayoutmanager.HiveDrawable;
import com.zlx.widget.hivelayoutmanager.HiveLayoutManager;

import java.util.List;

public class AuthorAdapter extends BaseRecycleAdapter<PublicAuthorListRes> {

    public AuthorAdapter(List<PublicAuthorListRes> datas) {
        super(datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rv_item_author;
    }

    @Override
    protected void bindData(BaseViewHolder holder, PublicAuthorListRes bean, int position) {
        TextView textView = holder.getView(R.id.text);
        ImageView image = holder.getView(R.id.image);
        textView.setText(bean.getName());
        HiveDrawable hiveDrawable = new HiveDrawable(HiveLayoutManager.VERTICAL, ContextCompat.getColor(getContext(),R.color.main_text));
        image.setImageDrawable(hiveDrawable);
        image.setOnClickListener(view -> {
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
