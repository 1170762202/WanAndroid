package com.zlx.module_base.base_ac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zlx.module_base.R;
import com.zlx.module_base.base_manage.ActivityUtil;
import com.zlx.module_base.base_util.DoubleClickExitDetector;
import com.zlx.module_base.base_util.InputTools;
import com.zlx.module_base.base_util.LanguageUtil;
import com.zlx.module_base.base_util.LogUtils;
import com.zlx.module_base.impl.IAcView;
import com.zlx.module_base.impl.INetView;
import com.zlx.module_base.loadsir.EmptyCallback;
import com.zlx.module_base.loadsir.LoadingCallback;
import com.zlx.module_base.widget.slideback.SlideBack;

import butterknife.ButterKnife;


/**
 * Created by zlx on 2017/6/23.
 */

public abstract class BaseAc extends AppCompatActivity implements INetView, IAcView {


    protected TextView tvTitle;

    protected ImageView ivLeft;
    protected ImageView ivRight;

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        ActivityUtil.addActivity(this);
        afterOnCreate();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setTheme(getMTheme());
        setSuspension();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        initImmersionBar();
        initEvents();
        initViews();
        doubleClickExitDetector =
                new DoubleClickExitDetector(this, "再按一次退出", 2000);

        if (canSwipeBack()) {
            //开启滑动返回
            SlideBack.create()
                    .attachToActivity(this);
        }
    }

    @Override
    public void beforeOnCreate() {

    }

    @Override
    public void afterOnCreate() {

    }

    @Override
    public void initEvents() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        if (ivLeft != null) {
            ivLeft.setOnClickListener(view -> finish());
        }
    }

    protected void setOnRightImgClickListener(View.OnClickListener listener) {
        if (ivRight != null) {
            ivRight.setOnClickListener(listener);
        }
    }

    @Override
    public void showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showLoading(View view) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showEmpty() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showSuccess() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showSuccess();
    }

    @Override
    public void onRetryBtnClick() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (shouldSupportMultiLanguage()) {
            Context context = LanguageUtil.attachBaseContext(newBase);
            final Configuration configuration = context.getResources().getConfiguration();
            // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
            // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
            // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
            final ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,
                    R.style.Theme_AppCompat_Empty) {
                @Override
                public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                    if (overrideConfiguration != null) {
                        overrideConfiguration.setTo(configuration);
                    }
                    super.applyOverrideConfiguration(overrideConfiguration);
                }
            };
            super.attachBaseContext(wrappedContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    protected boolean shouldSupportMultiLanguage() {
        return true;
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


    @Override
    public void initImmersionBar() {
        if (!fullScreen()) {
            ImmersionBar.with(this)
                    .statusBarView(R.id.statusBarView)
                    .statusBarDarkFont(true)
                    .transparentBar()
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .fullScreen(true)
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_BAR)
                    .init();
        }
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


    protected boolean canSwipeBack() {
        return true;
    }

    @Override
    public void initViews() {

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
        LogUtils.i("Base--->getPermissionSuccess");
    }

    public void getPermissionFailured() {
        LogUtils.i("Base--->getPermissionFail");
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

    @Override
    protected void onPause() {
        super.onPause();
        InputTools.hideInputMethod(this);
    }


}
