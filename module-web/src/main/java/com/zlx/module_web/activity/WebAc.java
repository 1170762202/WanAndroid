package com.zlx.module_web.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_web.R;
import com.zlx.module_web.R2;
import com.zlx.module_web.fragment.WebDialogFg;
import com.zlx.widget.webview.Html5WebView;

import butterknife.BindView;

@Route(path = RouterActivityPath.Web.PAGER_WEB)
public class WebAc extends BaseAc {

    @BindView(R2.id.webView)
    Html5WebView webView;


    private String webUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_web;
    }

    @Override
    public void initViews() {
        super.initViews();
        setRightImg(R.mipmap.ic_more_menu);
        webUrl = getIntent().getStringExtra("webUrl");
        LogUtil.show("webUrl=" + webUrl);
        webView.setTvTitle(tvTitle);
        webView.loadUrl(webUrl);
        setOnRightImgClickListener(view -> {
            WebDialogFg.newInstance(webUrl).show(getSupportFragmentManager(), "webDialog");
        });
    }

}
