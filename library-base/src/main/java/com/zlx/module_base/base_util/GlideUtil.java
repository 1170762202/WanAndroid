package com.zlx.module_base.base_util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * FileName: GlideUtil
 * Created by zlx on 2020/9/18 16:02
 * Email: 1170762202@qq.com
 * Description:
 */
public class GlideUtil {

    private GlideUtil() {
    }

    private static class GlideUtilHolder {
        private static GlideUtil instance = new GlideUtil();
    }

    public static GlideUtil getInstance() {
        return GlideUtilHolder.instance;
    }

    /**
     * 加载普通图片
     */
    public void loadImage(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .transition(withCrossFade())
                .into(iv);
    }

    public void loadImage(ImageView iv, String url, Drawable drawable) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(RequestOptions.errorOf(drawable).placeholder(drawable))
                .transition(withCrossFade())
                .into(iv);
    }

    public void loadImage(ImageView iv, String url, int drawable) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(RequestOptions.errorOf(drawable).placeholder(drawable))
                .transition(withCrossFade())
                .into(iv);
    }


    /**
     * 加载圆角图片
     */
    public void loadRoundImage(ImageView iv, String url, int round) {
        Glide.with(iv.getContext())
                .load(url)
                .transform(new CenterCrop(), new RoundedCorners(round))
                .into(iv);
    }


    /**
     * 加载圆形图片
     */
    public void loadCircleImage(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .transform(new CircleCrop()).into(iv);
    }
}
