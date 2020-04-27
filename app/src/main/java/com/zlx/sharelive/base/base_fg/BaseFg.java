package com.zlx.sharelive.base.base_fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public abstract class BaseFg extends Fragment {

    private View view;
    private ViewGroup parent;
    protected Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        mUnBinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }
}
