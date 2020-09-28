package com.zlx.module_home.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zlx.module_base.base_adapter.BaseViewHolder;
import com.zlx.module_home.R;

/**
 * Created by zlx on 2020/9/23 11:46
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterSearchClear extends DelegateAdapter.Adapter<BaseViewHolder> {

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search_clear, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setOnClickListener(R.id.tvClear, view -> {
            if (onSearchClearCallBack != null) {
                onSearchClearCallBack.onClear();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private OnSearchClearCallBack onSearchClearCallBack;

    public void setOnSearchClearCallBack(OnSearchClearCallBack onSearchClearCallBack) {
        this.onSearchClearCallBack = onSearchClearCallBack;
    }

    public interface OnSearchClearCallBack {
        void onClear();
    }
}
