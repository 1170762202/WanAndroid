package com.zlx.widget.fantasyslide;

import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by CaoDongping on 9/7/16.
 */

public class SimpleFantasyListener implements FantasyListener {
    @Override
    public boolean onHover(@Nullable View view, int index) {
        return false;
    }

    @Override
    public boolean onSelect(View view, int index) {
        return false;
    }

    @Override
    public void onCancel() {

    }
}
