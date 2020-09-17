package com.zlx.module_base.base_ac;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zlx.module_base.LoadingCallback;
import com.zlx.module_base.R;
import com.zlx.module_base.base_manage.ActivityManage;
import com.zlx.module_base.base_util.DoubleClickExitDetector;
import com.zlx.module_base.base_util.InputTools;
import com.zlx.module_base.base_util.PostUtil;
import com.zlx.module_network.util.LogUtil;
import com.zlx.module_base.base_util.LogUtils;
import com.zlx.module_base.base_util.ToastUtil;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by zlx on 2017/6/23.
 */

public abstract class BaseAc extends SwipeBackActivity {


    protected Context context;
    protected Activity activity;

    protected TextView tvTitle;

    protected ImageView ivLeft;
    protected ImageView ivRight;

    protected Toolbar toolbar;
    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        afterOnCreate();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setTheme(getMTheme());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setSuspension();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }

        context = this;
        activity = this;
        ActivityManage.addActivity(this);
        initImmersionBar();
        initEvents();
        initViews();
        doubleClickExitDetector =
                new DoubleClickExitDetector(context, "再按一次退出", 2000);

        getSwipeBackLayout().setEnableGesture(canSwipeBack());
    }

    protected void beforeOnCreate() {

    }

    protected void afterOnCreate() {

    }

    private void initEvents() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (ivLeft != null) {
            ivLeft.setOnClickListener(view -> finish());
        }
    }

    protected void setonRightImgClickListener(View.OnClickListener listener) {
        if (ivRight != null) {
            ivRight.setOnClickListener(listener);
        }
    }

    protected void showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    // 重新加载逻辑
                    LogUtil.show("重新加载逻辑");
                }
            });
        }
        loadService.showCallback(LoadingCallback.class);
    }

    protected void showSuccess() {
        loadService.showSuccess();
    }

    protected void setRightImg(int bg) {
        if (ivRight != null) {
            if (bg <= 0) {
                ivRight.setVisibility(View.GONE);
            } else {

                ivRight.setVisibility(View.VISIBLE);
                ivRight.setImageResource(bg);
            }
        }

    }

    protected void setLeftImg(int bg) {
        if (ivLeft != null) {
            if (bg <= 0) {
                ivLeft.setVisibility(View.GONE);
            } else {
                ivLeft.setVisibility(View.VISIBLE);
                ivLeft.setImageResource(bg);
            }
        }
    }

    private void initImmersionBar() {
        if (!fullScreen()) {
            if (!transparent()) {
                if (ImmersionBar.isSupportStatusBarDarkFont()) {
                    ImmersionBar.with(this)
                            .keyboardEnable(true)
                            .statusBarColor(R.color.main)
                            .statusBarDarkFont(true)
                            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                            .init();
                } else {
                    LogUtil.show("当前设备不支持状态栏字体变色");
                    ImmersionBar.with(this)
                            .statusBarColor(R.color.main)
                            .keyboardEnable(true)
                            .statusBarDarkFont(true)
                            .navigationBarDarkIcon(true)
                            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                            .init();
                }
            } else {
                ImmersionBar.with(this)
                        .titleBar(R.id.toolbar, false)
                        .statusBarDarkFont(true)
                        .keyboardEnable(true)
                        .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                        .init();
            }
        } else {
            ImmersionBar.with(this)
                    .fullScreen(true)
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_BAR)
                    .init();
        }
    }

    protected boolean transparent() {
        return true;
    }

    protected boolean fullScreen() {
        return false;
    }

    protected void setAcTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchHideSoft()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否触摸edittext以外的隐藏软键盘
     *
     * @return
     */
    protected boolean touchHideSoft() {
        return true;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 悬浮窗设置
     */
    private void setSuspension() {
        WindowManager.LayoutParams mParams = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            //xxxx为你原来给低版本设置的Type
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
    }


//    protected void ChangeOrientation(){
//        Boolean change = (Boolean) SPUtil.get(this, Config.SP_Landscape, false);
//        if(change){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }else{
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (hasSocket()) {
//            if (!CommonUtil.isServiceExisted(this, "com.mrdxm.xqdwl.service.SocketService")) {
//                startSocket();
//            } else {
//                if (!WsManager.getInstance().isConnect()) {
//                    startSocket();
//                }
//            }
//        }
    }

    private void startSocket() {
//        log("-----------------startSocket-----------------");
//        Intent intent = new Intent(this, SocketService.class);
//        intent.setAction("com.zlx.myservice");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, SocketService.class));
//        } else {
//            context.startService(new Intent(context, SocketService.class));
//        }
    }

    protected boolean canSwipeBack() {
        return true;
    }

    protected void initViews() {
    }

    protected abstract int getLayoutId();

    protected int getMTheme() {
        return R.style.AppTheme;
    }

    final RxPermissions rxPermissions = new RxPermissions(this);

    @SuppressLint("CheckResult")
    public void requestPermissions(String... permissions) {
        rxPermissions.request(permissions)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        getPermissionSuccess();
                    } else {
                        getPermissionFailured();
                    }
                });
    }

    public void getPermissionSuccess() {
        LogUtil.show("Base--->getPermissionSuccess");
    }

    public void getPermissionFailured() {
        LogUtil.show("Base--->getPermissionFail");
    }


    public void log(String content) {
        LogUtils.e(content);
    }

    public void toast(String content) {
        ToastUtil.showShort(context, content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.removeActivity(this);
    }

    private DoubleClickExitDetector doubleClickExitDetector;

    public boolean isDoubleClickExit() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isDoubleClickExit()) {
            boolean isExit = doubleClickExitDetector.click();
            if (isExit) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected boolean hasAcAnim() {
        return true;
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        if (hasAcAnim()) {
            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (hasAcAnim()) {
            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (hasAcAnim()) {
            overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputTools.hideInputMethod(this);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (hasAcAnim()) {
            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode, boolean anim) {
        super.startActivityForResult(intent, requestCode, null);
        if (anim) {
            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        if (hasAcAnim()) {
            overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
        }
    }
}
