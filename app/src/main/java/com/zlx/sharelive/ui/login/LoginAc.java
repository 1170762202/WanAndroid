package com.zlx.sharelive.ui.login;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zlx.sharelive.R;
import com.zlx.sharelive.base.base_ac.BaseAc;
import com.zlx.sharelive.base.event.oneclick.AntiShake;
import com.zlx.sharelive.ui.main.MainActivity;
import com.zlx.sharelive.widget.submit_button.SubmitButton1;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date: 2019\3\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class LoginAc extends BaseAc {
    @BindView(R.id.et_mobile)
    MaterialEditText etMobile;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.btn_login)
    SubmitButton1 btnLogin;

    private Animator animator;
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.ac_login;
    }

    @Override
    protected void initViews() {
        parent.getBackground().setAlpha(0);
    }


    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        btnLogin.startAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转
                gotoNew();
            }
        }, 2000);

    }

    private void gotoNew() {
        btnLogin.success();

        final Intent intent = new Intent(this, MainActivity.class);

        int xc = (btnLogin.getLeft() + btnLogin.getRight()) / 2;
        int yc = (btnLogin.getTop() + btnLogin.getBottom()) / 2;
        animator = ViewAnimationUtils.createCircularReveal(parent, xc, yc, 0, 1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                }, 200);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        parent.getBackground().setAlpha(255);
    }

    @Override
    protected void onStop() {
        super.onStop();
        parent.getBackground().setAlpha(0);
        btnLogin.reset();
    }
}
