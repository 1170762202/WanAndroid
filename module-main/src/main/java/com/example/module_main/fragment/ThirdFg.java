package com.example.module_main.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_main.R;
import com.example.module_main.R2;
import com.zlx.module_base.base_fg.BaseFg;
import com.zlx.widget.fantasyslide.FantasyDrawerLayout;
import com.zlx.widget.fantasyslide.SideBar;
import com.zlx.widget.fantasyslide.Transformer;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @date: 2019\3\7 0007
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class ThirdFg extends BaseFg {

    @BindView(R2.id.rightSideBar)
    SideBar rightSideBar;
    Unbinder unbinder;
    @BindView(R2.id.drawerLayout)
    FantasyDrawerLayout drawerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fg3;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }
    @Override
    protected void initViews() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        setTransformer();

    }

    private void setTransformer() {
        final float spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }

}
