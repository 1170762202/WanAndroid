package com.zlx.module_mine.sharearticle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.databinding.AcShareArticleBinding;
import com.zlx.module_network.widget.popwindow.PopUtil;

/**
 * Created by zlx on 2020/9/25 17:24
 * Email: 1170762202@qq.com
 * Description: 分享文章
 */
public class ShareArticleAc extends BaseMvvmAc<AcShareArticleBinding, ShareArticleViewModel> {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShareArticleAc.class));
    }

    @Override
    public void initViews() {
        super.initViews();
        viewModel.shareLiveData.observe(this, o -> showSuccess());
    }

    public void onShareClick() {
        String title = binding.etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            PopUtil.show("请输入标题");
            return;
        }
        String link = binding.etLink.getText().toString().trim();
        if (TextUtils.isEmpty(link)) {
            PopUtil.show("请输入链接");
        }
        showLoading();
        viewModel.shareArticle(title, link);
    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_share_article;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }
}
