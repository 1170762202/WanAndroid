package com.example.module_main.activity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_main.R;
import com.example.module_main.R2;
import com.zlx.module_base.base_ac.BaseAc;
import com.zlx.module_base.base_util.LogUtil;
import com.zlx.module_base.constant.RouterConstant;
import com.zlx.widget.webview.Html5WebView;

import butterknife.BindView;

@Route(path = RouterConstant.ROUT_WEB_AC)
public class WebAc extends BaseAc {

    @BindView(R2.id.webView)
    Html5WebView webView;


    private String webUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_web;
    }

    @Override
    protected void initViews() {
        super.initViews();
        webUrl = getIntent().getStringExtra("webUrl");
        LogUtil.show("webUrl=" + webUrl);
        webView.setTvTitle(tvTitle);
        webView.loadUrl(webUrl);
    }
}
