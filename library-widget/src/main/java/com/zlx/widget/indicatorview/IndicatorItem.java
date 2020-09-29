package com.zlx.widget.indicatorview;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

/**
 * Created by zlx on 2020/9/29 9:57
 * Email: 1170762202@qq.com
 * Description: 左边指示器item
 */
public class IndicatorItem {

    int color = Color.WHITE;
    Drawable icon;
    String text;
    int textSize = 10;
    int textColor = Color.WHITE;
    int iconTopPadding = 50;
    float cornerRadius = 120f;
    long duration = 1000L;
    int expandedSize = -1;
    IndicatorAnimation indicatorAnimation = IndicatorAnimation.NORMAL;
    View target;

    public IndicatorItem(View target) {
        this.target = target;
    }

    public static class Builder {
        private IndicatorItem indicatorItem;
        private View target;

        public Builder(View target) {
            this.target = target;
            indicatorItem = new IndicatorItem(target);
        }

        public Builder setItemColor(int color) {
            indicatorItem.color = color;
            return this;
        }

        public Builder setItemColorResource(int color) {
            indicatorItem.color = ContextCompat.getColor(target.getContext(), color);
            return this;
        }

        public Builder setItemIcon(Drawable value) {
            indicatorItem.icon = value;
            return this;
        }

        public Builder setItemIconResource(int value) {
            indicatorItem.icon = ContextCompat.getDrawable(target.getContext(), value);
            return this;
        }

        public Builder setItemText(String text) {
            indicatorItem.text = text;
            return this;
        }

        public Builder setItemTextColor(int color) {
            indicatorItem.textColor = color;
            return this;
        }

        public Builder setItemTextSize(int textSize) {
            indicatorItem.textSize = textSize;
            return this;
        }

        public Builder setItemIconTopPadding(int value) {
            indicatorItem.iconTopPadding = value;
            return this;
        }

        public Builder setItemCornerRadius(float value) {
            indicatorItem.cornerRadius = value;
            return this;
        }

        public Builder setItemDuration(long value) {
            indicatorItem.duration = value;
            return this;
        }

        public Builder setExpandedSize(int value) {
            indicatorItem.expandedSize = value;
            return this;
        }

        public Builder setIndicatorAnimation(IndicatorAnimation value) {
            indicatorItem.indicatorAnimation = value;
            return this;
        }

        public IndicatorItem build() {
            return indicatorItem;
        }

    }
}
