package com.zlx.widget.hivelayoutmanager;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.List;

public interface IHiveMathUtils {
    /**
     * calculate a item's bounds that beside of current item .
     *
     * @param current current item bounds
     * @param number  the public edge number
     * @param length  the length of regular hexagon
     * @return the item bounds
     */
    RectF calculateItemBounds(@NonNull RectF current, int number, float length);

    /**
     * calculate a item's center point that beside of current item .
     *
     * @param current current item center point
     * @param number  the public edge number
     * @param length  the length of regular hexagon
     * @return the center point
     */
    PointF calculateCenterPoint(@NonNull PointF current, int number, float length);

    /**
     * get the distance of neighbour item' center point.
     *
     * @param length the length of regular hexagon
     * @return the distance of neighbour item' center point
     */
    double getDistanceOfNeighbourCenter(float length);

    /**
     * clone a PointF object from current
     *
     * @param pointF resource
     * @return
     */
    PointF clone(@NonNull PointF pointF);

    /**
     * get {@link jack.hive.HiveConstants.VerticalNumber} from the edge number
     *
     * @param i edge number
     * @return the {@link jack.hive.HiveConstants.VerticalNumber}
     */
    @HiveConstants.VerticalNumber
    int getVerticalNumber(int i);

    /**
     * get {@link jack.hive.HiveConstants.HorizontalNumber} from the edge number
     *
     * @param i edge number
     * @return the {@link jack.hive.HiveConstants.HorizontalNumber}
     */
    @HiveConstants.HorizontalNumber
    int getHorizontalNumber(int i);

    /**
     * calculate the mFloor of the mPosition
     *
     * @param position item mPosition
     * @return the mFloor
     */
    HivePositionInfo getFloorOfPosition(int position);

    /**
     * calculate all item number int the mFloor
     *
     * @param floor mFloor
     * @return number of the mFloor
     */
    int getNumberOfFloor(int floor);

    /**
     * calculate the Length of regular hexagon
     *
     * @param rectF       the item view bounds
     * @param orientation the layout orientation
     * @return the length of regular hexagon
     */
    float calculateLength(@NonNull RectF rectF, @HiveLayoutManager.Orientation int orientation);

    /**
     * calculate the Length of regular hexagon
     *
     * @param rect        the item view bounds
     * @param orientation the layout orientation
     * @return the length of regular hexagon
     */
    float calculateLength(@NonNull Rect rect, @HiveLayoutManager.Orientation int orientation);

    /**
     * calculate current mFloor Rects by last mFloor
     *
     * @param lastFloorRects Rects at last mFloor
     * @param length         the length of regular hexagon
     * @param floor          last mFloor
     * @param orientation    orientation {@link jack.hive.HiveLayoutManager.Orientation}
     * @return RectFs at current mFloor
     */
    List<RectF> getRectListOfFloor(@NonNull List<RectF> lastFloorRects, float length, int floor
            , @HiveLayoutManager.Orientation int orientation);

    int getTheLeftSideIndexOfTheFloor(@IntRange(from = 0) int floor, @HiveLayoutManager.Orientation int orientation, @IntRange(from = 0) int maxPosition);

    int getTheRightSideIndexOfTheFloor(@IntRange(from = 0) int floor, @HiveLayoutManager.Orientation int orientation, @IntRange(from = 0) int maxPosition);

    int getTheTopSideIndexOfTheFloor(@IntRange(from = 0) int floor, @HiveLayoutManager.Orientation int orientation, @IntRange(from = 0) int maxPosition);

    int getTheBottomSideIndexOfTheFloor(@IntRange(from = 0) int floor, @HiveLayoutManager.Orientation int orientation, @IntRange(from = 0) int maxPosition);
}
