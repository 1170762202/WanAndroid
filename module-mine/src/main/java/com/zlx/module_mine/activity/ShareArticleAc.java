package com.zlx.module_mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zlx.library_common.util.ApiUtil;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.ToastUtil;
import com.zlx.module_mine.R;
import com.zlx.module_mine.R2;
import com.zlx.module_network.api1.livedata.BaseObserver;
import com.zlx.module_network.api1.livedata.BaseObserverCallBack;
import com.zlx.module_network.bean.ApiResponse;
import com.zlx.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zlx on 2020/9/25 17:24
 * Email: 1170762202@qq.com
 * Description: 分享文章
 */
public class ShareArticleAc extends BaseAc {

    @BindView(R2.id.etTitle)
    ClearEditText etTitle;
    @BindView(R2.id.etLink)
    ClearEditText etLink;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShareArticleAc.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.ac_share_article;
    }

    @Override
    public void initViews() {
        super.initViews();
        setAcTitle("分享文章");
    }

    @OnClick(R2.id.tvShare)
    public void onViewClicked() {
        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showShort("请输入标题");
            return;
        }
        String link = etLink.getText().toString().trim();
        if (TextUtils.isEmpty(link)) {
            ToastUtil.showShort("请输入链接");
        }
        ApiUtil.getArticleApi().shareArticle(title,link).observe(this,
                new BaseObserver<>(new BaseObserverCallBack<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse data) {
                        ToastUtil.showShort("分享成功");
                        finish();
                    }

                    @Override
                    public boolean showErrorMsg() {
                        return true;
                    }
                }));
    }
}
