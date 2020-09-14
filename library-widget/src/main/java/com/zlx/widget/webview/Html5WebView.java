package com.zlx.widget.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.zlx.widget.R;

public class Html5WebView   extends WebView {
    private ProgressView progressView;//进度条
    private Context context;
    private TextView tvTitle;

    public Html5WebView(Context context) {
        this(context, null);
    }

    public Html5WebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    private void init() {
        //初始化进度条
        progressView = new ProgressView(context);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 4)));
        progressView.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
        progressView.setProgress(10);
        //把进度条加到Webview中
        addView(progressView);
        initWebViewSettings();
        setWebChromeClient(new MyWebCromeClient());
    }

    private void initWebViewSettings() {
        //设置载入页面自适应手机屏幕，居中显示
        WebSettings mWebSettings = getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setUseWideViewPort(true); // 关键点
        mWebSettings.setAllowFileAccess(true); // 允许访问文件
        mWebSettings.setSupportZoom(true); // 支持缩放
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");//文本编码
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
        }
    }

    /**
     * dp转换成px
     *
     * @param context Context
     * @param dp      dp
     * @return px值
     */
    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
