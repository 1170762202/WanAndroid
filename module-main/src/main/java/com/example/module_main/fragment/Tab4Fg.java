package com.example.module_main.fragment;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_main.R;
import com.example.module_main.R2;
import com.example.module_main.fragment.adapters.AuthorAdapter;
import com.zlx.module_base.base_fg.BaseFg;
import com.example.module_main.widget.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jack.hive.HiveLayoutManager;

/**
 * @date: 2019\3\8 0008
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class Tab4Fg extends BaseFg {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R2.id.ivClose)
    ImageView ivClose;
    @BindView(R2.id.toolbar1)
    Toolbar toolbar;
    @BindView(R2.id.root)
    FrameLayout root;

    RecyclerView rvAuthor;
    private List<String> authList = new ArrayList<>();
    private AuthorAdapter authorAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fg4;
    }

    @Override
    protected boolean immersionBar() {
        return true;
    }


    @Override
    protected void initViews() {

        for (int i = 0; i < 20; i++) {
            authList.add(i + "");
        }
        View guillotineMenu = LayoutInflater.from(getContext()).inflate(R.layout.guillotine, null);
        rvAuthor = guillotineMenu.findViewById(R.id.rvAuthor);
        rvAuthor.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));
        authorAdapter = new AuthorAdapter(authList);
        rvAuthor.setAdapter(authorAdapter);
        root.addView(guillotineMenu);
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.ivHamburger), ivClose)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

    }

}
