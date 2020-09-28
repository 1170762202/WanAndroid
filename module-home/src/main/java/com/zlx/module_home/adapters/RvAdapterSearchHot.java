package com.zlx.module_home.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.google.android.flexbox.FlexboxLayout;
import com.zlx.module_base.base_api.res_data.SearchBeanRes;
import com.zlx.module_base.base_adapter.BaseViewHolder;
import com.zlx.module_home.R;
import com.zlx.module_home.bean.SearchBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by zlx on 2020/9/23 11:54
 * Email: 1170762202@qq.com
 * Description:
 */
public class RvAdapterSearchHot extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Queue<AppCompatTextView> mFlexItemTextViewCaches = new LinkedList<>();
    private LayoutInflater layoutInflater = null;

    private List<SearchBean> dataList;

    public RvAdapterSearchHot() {
        this.dataList = new ArrayList<>();
    }

    public void refresh(List<SearchBean> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search_hot, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        FlexboxLayout flexboxLayout = holder.getView(R.id.flexLayout);
        flexboxLayout.removeAllViews();  //注释这条属性，用下面onViewRecycled()方法也行
        List<SearchBeanRes> children = dataList.get(position).getData();
        for (int i = 0; i < children.size(); i++) {
            SearchBeanRes datasBean = children.get(i);
            AppCompatTextView labelTv = createOrGetCacheTv(flexboxLayout);
            labelTv.setText(datasBean.getName());
            labelTv.setOnClickListener(v -> {
                if (onSearchHotCallBack != null) {
                    onSearchHotCallBack.onHot(datasBean.getName());
                }
            });
            flexboxLayout.addView(labelTv);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
//        FlexboxLayout flexLayout = holder.getView(R.id.flexLayout);
//        if (flexLayout != null) {
//            for (int i = 0; i < flexLayout.getChildCount(); i++) {
//                mFlexItemTextViewCaches.offer((AppCompatTextView) flexLayout.getChildAt(i));
//            }
//            flexLayout.removeAllViews();
//        }
    }

    private AppCompatTextView createOrGetCacheTv(FlexboxLayout flexboxLayout) {
        AppCompatTextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        return findLabel(flexboxLayout);
    }

    private AppCompatTextView findLabel(FlexboxLayout flexboxLayout) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(flexboxLayout.getContext());
        return (AppCompatTextView) layoutInflater.inflate(R.layout.flextlayout_item_label, flexboxLayout, false);
    }

    public interface OnSearchHotCallBack {
        void onHot(String title);
    }

    private OnSearchHotCallBack onSearchHotCallBack;

    public void setOnSearchHotCallBack(OnSearchHotCallBack onSearchHotCallBack) {
        this.onSearchHotCallBack = onSearchHotCallBack;
    }
}
