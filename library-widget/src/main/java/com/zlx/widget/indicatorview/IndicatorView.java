package com.zlx.widget.indicatorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.zlx.module_network.util.LogUtil;
import com.zlx.widget.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


/**
 * Created by zlx on 2020/9/29 9:41
 * Email: 1170762202@qq.com
 * Description:
 */
public class IndicatorView extends FrameLayout implements OnScrollChangedListener {

    private List<IndicatorItem> indicatorItemsList = new ArrayList();
    private List<IndicatorItemView> indicatorItemViewList = new ArrayList();
    private int indicatorItemPadding = dp2px(6);
    @FloatRange(
            from = 0.0F,
            to = 1.0F
    )
    private float expandingRatio = 0.2f;
    @FloatRange(
            from = 0.0F,
            to = 1.0F
    )
    private float expandingAllItemRatio = 0.9f;

    public IndicatorView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndicatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs, defStyleAttr);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    private void getAttrs(AttributeSet attributeSet, int defStyleAttr) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.IndicatorView, defStyleAttr, 0);

        try {
            Intrinsics.checkExpressionValueIsNotNull(typedArray, "typedArray");
            setTypeArray(typedArray);
        } finally {
            typedArray.recycle();
        }

    }

    @Override
    public void onChanged(int x, int y, int measuredScrollViewHeight) {
        if (y == 0 && this.expandingRatio != 0) {
            return;
        }
        this.post(() -> {
            int index = 0;
            for (int i = indicatorItemsList.size(); index < i; ++index) {
                int height = (indicatorItemsList.get(index)).expandedSize;
                if (index == indicatorItemsList.size() - 1 && height == -1) {
                    height = (int) ((float) getHeight() - (indicatorItemViewList.get(index)).getY() - (float) indicatorItemPadding);
                } else if (height == -1) {
                    height = (int) ((indicatorItemViewList.get(index + 1)).getY() - (indicatorItemViewList.get(index)).getY() - (float) indicatorItemPadding);
                }


                if ((indicatorItemViewList.get(index)).getY() - (float) y < (float) displaySize().y * expandingRatio) {
                    (indicatorItemViewList.get(index)).expand(getStandardSize(), height);
                } else if ((int) ((float) measuredScrollViewHeight * expandingAllItemRatio) > y) {
                    (indicatorItemViewList.get(index)).collapse(getStandardSize(), height);
                } else {
                    (indicatorItemViewList.get(index)).expand(getStandardSize(), height);
                }
            }
        });

    }

    Point point;

    private Point displaySize() {
        if (point == null) {
            point = new Point();
            WindowManager windowManager = (WindowManager) (getContext().getSystemService(Context.WINDOW_SERVICE));
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point;
    }

    public final void addIndicatorItem(@NotNull final IndicatorItem indicatorItem) {
        Intrinsics.checkParameterIsNotNull(indicatorItem, "indicatorItem");
        indicatorItem.target.post((Runnable) () -> {
            IndicatorItemView indicatorItemView = new IndicatorItemView(getContext());
            addItem(indicatorItem, indicatorItemView);
            indicatorItemView.setIndicatorItem(indicatorItem);
            indicatorItemView.setLayoutParams((LayoutParams) (new LayoutParams(getStandardSize(), getStandardSize())));
            View target = indicatorItem.target;
            Intrinsics.checkExpressionValueIsNotNull(target, "indicatorItem.target");
            indicatorItemView.setY(target.getY());
            addView(indicatorItemView);
            invalidate();
        });
    }


    public final void addIndicatorItemList(@NotNull List indicatorItemList) {
        Intrinsics.checkParameterIsNotNull(indicatorItemList, "indicatorItemList");
        Iterator iterator = indicatorItemList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            IndicatorItem indicatorItem = (IndicatorItem) next;
            addIndicatorItem(indicatorItem);
        }

    }

    private final IndicatorItemView addItem(IndicatorItem indicatorItem, IndicatorItemView indicatorItemView) {
        this.indicatorItemsList.add(indicatorItem);
        this.indicatorItemViewList.add(indicatorItemView);
        return indicatorItemView;
    }


    private final int getStandardSize() {
        return this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    public final void plus(@NotNull IndicatorItem indicatorItem) {
        Intrinsics.checkParameterIsNotNull(indicatorItem, "indicatorItem");
        this.addIndicatorItem(indicatorItem);
    }

    public final void plus(@NotNull List indicatorItemList) {
        Intrinsics.checkParameterIsNotNull(indicatorItemList, "indicatorItemList");
        this.addIndicatorItemList(indicatorItemList);
    }


    private final void setTypeArray(TypedArray a) {
        this.indicatorItemPadding = a.getDimensionPixelSize(R.styleable.IndicatorView_indicator_itemPadding, this.indicatorItemPadding);
        this.expandingRatio = a.getFloat(R.styleable.IndicatorView_indicator_expandingRatio, this.expandingRatio);
        this.expandingAllItemRatio = a.getFloat(R.styleable.IndicatorView_indicator_expandingAllItemRatio, this.expandingAllItemRatio);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.onChanged(0, 0, 0);
    }

}
