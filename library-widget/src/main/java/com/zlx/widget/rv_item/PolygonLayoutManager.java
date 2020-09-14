package com.zlx.widget.rv_item;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class PolygonLayoutManager extends RecyclerView.LayoutManager {


    private static final int GROUP_SIZE = 2;

    //每组之间的间隙(正六边形之间的)(横向)
    private static final int DEFAULT_GROUP_INTERVAL = 10;

    //代表横向的间距,只三个正六边形形成的等边三角形的中心距离 (存在默认值)
    private float mLandscapeInterval = DEFAULT_GROUP_INTERVAL;
    //代表纵向的间隔,指两个正六边形之间的上下间距
    private float mVerticalInterval;

    private Pool<Rect> mItemFrames;

    //居中的偏移量
    private int mGravityOffset;

    //总宽度
    private int mTotalWidth;
    private int mHorizontalOffset;


    //总高度
    private int mTotalHeight;
    private int mVerticalOffset;

    private boolean isGravityCenter;


    public PolygonLayoutManager(boolean isGravityCenter) {
        this.isGravityCenter = isGravityCenter;
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() {
                return new Rect();
            }
        });
    }

    /**
     * 设置正六边形的横向间距
     */
    public void setLandscapeInterval(int distance) {
        this.mLandscapeInterval = distance;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        normalLayout(recycler, state);
    }


    /**
     * 常规的布局 ----->所有的ItemView都是等大的
     *
     * @param recycler
     * @param state
     */
    private void normalLayout(RecyclerView.Recycler recycler, RecyclerView.State state) {
        View normalView = recycler.getViewForPosition(0);
        measureChildWithMargins(normalView, 0, 0);

        int itemWidth = getDecoratedMeasuredWidth(normalView);
        int itemHeight = getDecoratedMeasuredHeight(normalView);

        //正六边形外圆的半径
        int radius = itemWidth / 2;

        mVerticalInterval = (mLandscapeInterval / MathUtil.sin(60)) - 2 * (radius - radius * MathUtil.sin(60));

        //每组的最大宽度 第一排的宽度加上第二排的宽度
        //这里的0.75 * itemWidth 和 (3/2)R表达的意思都是一致的.
        int mGroupWidth = (int) (0.75 * itemWidth + itemWidth - mLandscapeInterval);

        if (isGravityCenter && mGroupWidth < getHorizontalSpace()) {
            mGravityOffset = (getHorizontalSpace() - mGroupWidth) / 2;
        } else {
            mGravityOffset = 0;
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect item = mItemFrames.get(i);
            int offsetHeight = (int) ((i / GROUP_SIZE) * (itemHeight + mVerticalInterval));
            //每组的第一行
            if (isItemInFirstLine(i)) {
                int left = mGravityOffset;
                int top = offsetHeight;
                int right = mGravityOffset + itemWidth;
                int bottom = itemHeight + offsetHeight;
                item.set(left, top, right, bottom);
//                Log.d("第一段的高度", "left : " + left);
//                Log.d("第一段的高度", "top : " + top);
//                Log.d("第一段的高度", "right : " + right);
//                Log.d("第一段的高度", "bottom : " + bottom);
            } else {
                //X轴的偏移是从 正六边形的外圆 3/2 R出开始偏移
                int itemOffsetWidth = (int) ((3f / 2f) * radius + mLandscapeInterval);
                //Y轴的第一次偏移是 取 (2个正六边形的宽度 + 中间间距) 得到当前第二排正六边形的中点 然后往回减 得到的.
                int itemOffsetHeight = (int) ((int) ((2 * itemWidth + mVerticalInterval) / 2) - 0.5 * itemWidth);
                int left = mGravityOffset + itemOffsetWidth;
                int top = itemOffsetHeight + offsetHeight;
                int right = mGravityOffset + itemOffsetWidth + itemWidth;
                int bottom = offsetHeight + itemOffsetHeight + itemHeight;
                item.set(left, top, right, bottom);
//                Log.d("第二段的高度", "left : " + left);
//                Log.d("第二段的高度", "top : " + top);
//                Log.d("第二段的高度", "right : " + right);
//                Log.d("第二段的高度", "bottom : " + bottom);
            }
        }
        //设置总的宽度
        mTotalWidth = Math.max(mGroupWidth, getHorizontalSpace());
        //设置总的高度
        int totalHeight = (int) (getGroupSize() * itemHeight + (getGroupSize() - 1) * mVerticalInterval);
        //判断当前最后一组如果不是第一行,那么高度还得加上第一段偏移量
        //Y轴的第一段偏移量
        int itemOffsetHeight = (int) ((int) ((2 * itemWidth + mVerticalInterval) / 2) - 0.5 * itemWidth);
        if (!isItemInFirstLine(getItemCount() - 1)) {
            totalHeight += itemOffsetHeight;
        }
        //设置总的高度 取当前的内容 和 RecyclerView的高度的最大值
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
        fill(recycler, state);
    }

    /**
     * 填充方法
     *
     * @param recycler
     * @param state
     */
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        //考虑到当前RecyclerView会处于滑动的状态,所以这里的Rect的作用是展示当前显示的区域
        //需要考虑到RecyclerView的滑动量
        //mHorizontalOffset 横向的滑动偏移量
        //mVerticalOffset  纵向的滑动偏移量
        Rect displayRect = new Rect(0, mVerticalOffset,
                getHorizontalSpace(),
                getVerticalSpace() + mVerticalOffset);

        /**
         * 对这些View进行测量和布局操作
         */
        for (int i = 0; i < getItemCount(); i++) {
            Rect frame = mItemFrames.get(i);
            if (Rect.intersects(displayRect, frame)) {
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                //测量子View
                measureChildWithMargins(scrap, 0, 0);
                //布局方法
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        //上边界判断
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset;
            //下边界判断
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }

        mVerticalOffset += dy;
        //使所有ChildView偏移 如果向上滑动 所有View就向下偏移 反之亦然
        offsetChildrenVertical(-dy);
        //重新布局
        fill(recycler, state);

        return dy;
    }


    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 判断当前索引位置 是否处于组内的第一行
     *
     * @param index
     * @return
     */
    private boolean isItemInFirstLine(int index) {
        //定制为2格的正六边形
        return index % 2 == 0;
    }

    private int getGroupSize() {
        //向上取整
        return (int) Math.ceil(getItemCount() / (float) GROUP_SIZE);
    }


    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }


    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
