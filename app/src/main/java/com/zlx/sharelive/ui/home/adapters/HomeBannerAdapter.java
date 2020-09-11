package com.zlx.sharelive.ui.home.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.MZScaleInTransformer;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.zlx.module_base.bean.res_data.BannerRes;
import com.zlx.sharelive.R;
import com.zlx.sharelive.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBannerAdapter extends DelegateAdapter.Adapter<HomeBannerAdapter.ViewHolder> {


    private List<BannerRes> bannerResList;

    public HomeBannerAdapter(List<BannerRes> bannerResList) {
        this.bannerResList = bannerResList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_home_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.banner.setIndicator(new CircleIndicator(holder.banner.getContext()));
        holder.banner.setPageTransformer(new MZScaleInTransformer());
        holder.banner.setAdapter(new BannerImageAdapter<BannerRes>(bannerResList) {
            @Override
            public void onBindView(BannerImageHolder holder, BannerRes data, int position, int size) {
                Glide.with(holder.itemView)
                        .load(data.getImagePath())
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setOnBannerListener((data, position) -> {
                BannerRes bannerRes = (BannerRes) data;
                LogUtil.i(bannerRes.getImagePath());
            });
        }
    }
}
