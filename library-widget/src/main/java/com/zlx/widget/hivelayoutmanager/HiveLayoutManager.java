package com.zlx.widget.hivelayoutmanager;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class HiveLayoutManager  extends RecyclerView.LayoutManager {


    private static final String TAG = HiveLayoutManager.class.getSimpleName();

    /**
     * layout views in RV an horizontal direction
     */
    public static final int HORIZONTAL = HiveLayoutHelper.HORIZONTAL;

    /**
     * layout views in RV an vertical direction
     */
    public static final int VERTICAL = HiveLayoutHelper.VERTICAL;

    /**
     * at the center of parent at begin
     */
    public static final int CENTER = 0x00000001;

    /**
     * at the left of parent at begin
     */
    public static final int ALIGN_LEFT = 0x00000002;

    /**
     * at the right of parent at begin, if already set ALIGN_LEFT, this will not work
     */
    public static final int ALIGN_RIGHT = 0x00000004;

    /**
     * at the top of parent at begin
     */
    public static final int ALIGN_TOP = 0x00000008;

    /**
     * at the bottom of parent at begin, if already set ALIGN_TOP, this will not work
     */
    public static final int ALIGN_BOTTOM = 0x00000010;

    /**
     * the HiveLayoutManager's orientation
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({HORIZONTAL, VERTICAL})
    @interface Orientation {
    }

    private IHiveMathUtils mHiveMathUtils;
    private AnchorInfo mAnchorInfo;
    private LayoutState mLayoutState;
    private final List<List<RectF>> mFloors = new ArrayList<>();
    private final HiveBucket mBooleanMap = new HiveBucket();
    private int mOrientation;
    private int mGravity = CENTER;
    private boolean firstLayout = true;
    private RectF mPadding = new RectF();

    /**
     * @param orientation the LayoutManager orientation.
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public HiveLayoutManager(@Orientation int orientation) {
        mOrientation = orientation;
        init();
    }

    private void init() {
        mHiveMathUtils = HiveMathUtils.getInstance();
        mLayoutState = new LayoutState();

        mBooleanMap.reset();
    }

    public int getGravity() {
        return mGravity;
    }

    /**
     * set the Gravity for the item.
     *
     * @param gravity
     * @see #ALIGN_LEFT
     * @see #ALIGN_TOP
     * @see #ALIGN_RIGHT
     * @see #ALIGN_BOTTOM
     * @see #CENTER
     * <p>
     * you can use them combined, like setGravity(ALIGN_LEFT | ALIGN_TOP) will let the layoutManager
     * at the left top of the parent at begin.
     */
    public void setGravity(final int gravity) {
        this.mGravity = gravity;
    }

    /**
     * set the layout manager padding.if you need recyclerView have a padding,you can use this to
     * solve the problem that item cannot disappear from the edge of view.
     *
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void setPadding(float paddingLeft, float paddingTop, float paddingRight, float paddingBottom) {
        mPadding.set(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);
        int itemCount = state.getItemCount();
        if (itemCount <= 0) {
            return;
        }
        initAnchorInfo(recycler);
        initFloors();
        initEdgeDistance();
        detachAndScrapAttachedViews(recycler);

        mBooleanMap.reset();

        fill(recycler, state);
        firstLayout = false;
    }

    private void initEdgeDistance() {
        mLayoutState.edgeDistance.set(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2);
    }

    private void updateLayoutState() {
        mLayoutState.containerRect.set(0, 0, getWidth(), getHeight());
        mLayoutState.containerRect.offset(-mLayoutState.offsetX, -mLayoutState.offsetY);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        if (itemCount <= 0) {
            return;
        }

        checkAllRect(itemCount);
        updateLayoutState();

        if (firstLayout) {
            firstLayout = false;
            if ((mGravity & CENTER) == 0) { // 不是Center的时候
                if ((mGravity & ALIGN_LEFT) != 0) { // 如果是从左对其
                    doScrollHorizontalBx(recycler, state, mLayoutState.outLineRect.left - mPadding.left);
                } else if ((mGravity & ALIGN_RIGHT) != 0) {
                    doScrollHorizontalBx(recycler, state, mLayoutState.outLineRect.right - getWidth() + mPadding.right);
                }
                if ((mGravity & ALIGN_TOP) != 0) {
                    doScrollVerticalBy(recycler, state, mLayoutState.outLineRect.top - mPadding.top);
                } else if ((mGravity & ALIGN_BOTTOM) != 0) {
                    doScrollVerticalBy(recycler, state, mLayoutState.outLineRect.bottom - getHeight() + mPadding.bottom);
                }
            } else {
                fill(recycler, state);
            }
        } else {
            for (int i = 0; i < itemCount; i++) {
                RectF bounds = getBounds(i);

                if (!mBooleanMap.get(i) && RectF.intersects(bounds, mLayoutState.containerRect)) {
                    View view = recycler.getViewForPosition(i);
                    addView(view);
                    mBooleanMap.set(i);
                    measureChildWithMargins(view, 0, 0);

                    bounds.offset(mLayoutState.offsetX, mLayoutState.offsetY);

                    calculateEdgeDistance(bounds.left, bounds.top, bounds.right, bounds.bottom);

                    layoutDecoratedWithMargins(view, (int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
                }
            }
        }

    }

    private void calculateEdgeDistance(float left, float top, float right, float bottom) {
        RectF temp = mLayoutState.edgeDistance;
        float eLeft = Math.min(temp.left, left);
        float eTop = Math.min(temp.top, top);
        float eRight = Math.min(temp.right, getWidth() - right);
        float eBottom = Math.min(temp.bottom, getHeight() - bottom);
        mLayoutState.edgeDistance.set(eLeft, eTop, eRight, eBottom);
    }

    private RectF getBounds(int index) {
        HivePositionInfo positionInfo = mHiveMathUtils.getFloorOfPosition(index);
        List<RectF> floor = mFloors.get(positionInfo.mFloor);
        return new RectF(floor.get(positionInfo.mPosition));
    }

    private void initFloors() {
        if (mFloors.size() == 0) {
            List<RectF> list = new ArrayList<>();
            list.add(mAnchorInfo.anchorRect);
            mFloors.add(list);
        }
    }

    private void checkAllRect(int itemCount) {
        HivePositionInfo positionInfo = mHiveMathUtils.getFloorOfPosition(itemCount - 1);
        checkFloor(positionInfo.mFloor);
    }

    private void checkFloor(int floor) {
        if (floor < 0) {
            return;
        }
        if (mFloors.size() <= floor) {
            for (int i = mFloors.size(); i <= floor; i++) {
                int i1 = i - 1;
                Log.d(TAG, "checkFloor: i1 : " + i1 + " , i : " + i);
                List<RectF> temp = mHiveMathUtils.getRectListOfFloor(mFloors.get(i1), mAnchorInfo.length, i, mOrientation);
                mFloors.add(temp);
            }
            loadOutLineRect();
        }
    }

    private void loadOutLineRect() {
        if (getItemCount() <= 0) {
            mLayoutState.outLineRect.set(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2);
        } else {
            HivePositionInfo positionInfo = mHiveMathUtils.getFloorOfPosition(getItemCount());
            int lastFloorIndex = positionInfo.mFloor;  // 获得最后一层的索引
            if (lastFloorIndex == 0) {
                mLayoutState.outLineRect.set(mFloors.get(0).get(0));
            } else if (lastFloorIndex > 0) {
                int lastSecondFloorIndex = lastFloorIndex - 1;
                List<RectF> lastFloor = mFloors.get(lastFloorIndex);
                List<RectF> lastSecondFloor = mFloors.get(lastSecondFloorIndex);
                float leftF = Math.min(
                        lastSecondFloor.get(mHiveMathUtils.getTheLeftSideIndexOfTheFloor(lastSecondFloorIndex, mOrientation, lastSecondFloor.size() - 1)).left,
                        lastFloor.get(mHiveMathUtils.getTheLeftSideIndexOfTheFloor(lastFloorIndex, mOrientation, positionInfo.mPosition)).left);
                float rightF = Math.max(
                        lastSecondFloor.get(mHiveMathUtils.getTheRightSideIndexOfTheFloor(lastSecondFloorIndex, mOrientation, lastSecondFloor.size() - 1)).right,
                        lastFloor.get(mHiveMathUtils.getTheRightSideIndexOfTheFloor(lastFloorIndex, mOrientation, positionInfo.mPosition)).right);
                float topF = Math.min(
                        lastSecondFloor.get(mHiveMathUtils.getTheTopSideIndexOfTheFloor(lastSecondFloorIndex, mOrientation, lastSecondFloor.size() - 1)).top,
                        lastFloor.get(mHiveMathUtils.getTheTopSideIndexOfTheFloor(lastFloorIndex, mOrientation, positionInfo.mPosition)).top);
                float bottomF = Math.max(
                        lastSecondFloor.get(mHiveMathUtils.getTheBottomSideIndexOfTheFloor(lastSecondFloorIndex, mOrientation, lastSecondFloor.size() - 1)).bottom,
                        lastFloor.get(mHiveMathUtils.getTheBottomSideIndexOfTheFloor(lastFloorIndex, mOrientation, positionInfo.mPosition)).bottom);
                mLayoutState.outLineRect.set(leftF, topF, rightF, bottomF);
            }
        }
        Log.d(TAG, String.format("loadOutLineRect: out line rect:%s", mLayoutState.outLineRect));
    }

    private void initAnchorInfo(RecyclerView.Recycler recycler) {
        if (mAnchorInfo == null) {
            mAnchorInfo = new AnchorInfo();
            mAnchorInfo.anchorPoint.set(getWidth() / 2, getHeight() / 2);

            View view = recycler.getViewForPosition(0);
            addView(view);
            measureChildWithMargins(view, 0, 0);

            int height = view.getMeasuredHeight();
            int width = view.getMeasuredWidth();

            float left = mAnchorInfo.anchorPoint.x - width / 2f;
            float right = mAnchorInfo.anchorPoint.x + width / 2f;
            float top = mAnchorInfo.anchorPoint.y - height / 2f;
            float bottom = mAnchorInfo.anchorPoint.y + height / 2f;

            mAnchorInfo.anchorRect.set(left, top, right, bottom);
            mAnchorInfo.length = mHiveMathUtils.calculateLength(mAnchorInfo.anchorRect, mOrientation);
        }
    }


    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((mLayoutState.edgeDistance.left < mPadding.left && dx < 0) || (mLayoutState.edgeDistance.right < mPadding.right && dx > 0)) {
            float distance = dx < 0 ? Math.max(mLayoutState.edgeDistance.left - mPadding.left, dx) : Math.min(-(mLayoutState.edgeDistance.right - mPadding.right), dx);
            doScrollHorizontalBx(recycler, state, distance);
            return (int) distance;
        } else if (mLayoutState.offsetX != 0) {
            if (mLayoutState.offsetX * dx > 0) {
                float distance = (Math.abs(dx) / dx) * Math.min(Math.abs(mLayoutState.offsetX), Math.abs(dx));
                doScrollHorizontalBx(recycler, state, distance);
                return (int) distance;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private void doScrollHorizontalBx(RecyclerView.Recycler recycler, RecyclerView.State state, float distance) {
        mLayoutState.edgeDistance.left -= distance;
        mLayoutState.edgeDistance.right += distance;
        offsetChildrenHorizontal((int) -distance);
        mLayoutState.offsetX -= distance;
        mLayoutState.lastScrollDeltaX = (int) distance;

        scrapOutSetViews(recycler);
        scrollBy((int) distance, recycler, state);
    }

    private void scrapOutSetViews(RecyclerView.Recycler recycler) {
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (!RectF.intersects(new RectF(0, 0, getWidth(), getHeight()), new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()))) {
                int position = getPosition(view);
                mBooleanMap.clear(position);
                removeAndRecycleViewAt(i, recycler);
            }
        }
    }

    private int scrollBy(int distance, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fill(recycler, state);
        return distance;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((mLayoutState.edgeDistance.top < mPadding.top && dy < 0) || (mLayoutState.edgeDistance.bottom < mPadding.bottom && dy > 0)) {
            float distance = dy < 0 ? Math.max(mLayoutState.edgeDistance.top - mPadding.top, dy) : Math.min(-(mLayoutState.edgeDistance.bottom - mPadding.bottom), dy);
            doScrollVerticalBy(recycler, state, distance);
            return (int) distance;
        } else if (mLayoutState.offsetY != 0) {
            if (mLayoutState.offsetY * dy > 0) {
                float distance = (Math.abs(dy) / dy) * Math.min(Math.abs(mLayoutState.offsetY), Math.abs(dy));
                doScrollVerticalBy(recycler, state, distance);
                return (int) distance;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private void doScrollVerticalBy(RecyclerView.Recycler recycler, RecyclerView.State state, float distance) {
        mLayoutState.edgeDistance.top -= distance;
        mLayoutState.edgeDistance.bottom += distance;
        offsetChildrenVertical((int) -distance);
        mLayoutState.offsetY -= distance;
        mLayoutState.lastScrollDeltaY = (int) distance;

        scrapOutSetViews(recycler);
        scrollBy((int) distance, recycler, state);
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }


    public RectF getPadding() {
        return mPadding;
    }

    /**
     * the anchor info
     */
    private class AnchorInfo {

        final PointF anchorPoint = new PointF();
        final RectF anchorRect = new RectF();
        float length;

    }

    /**
     * the layout state of the LayoutManager
     */
    private class LayoutState {

        int offsetX;
        int offsetY;
        int lastScrollDeltaX;
        int lastScrollDeltaY;

        final RectF edgeDistance = new RectF();
        final RectF containerRect = new RectF();
        final RectF outLineRect = new RectF();
    }

}
