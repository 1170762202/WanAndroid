package com.zlx.widget.hivelayoutmanager;

import androidx.annotation.IntDef;

public class HiveConstants {
    static final int VERTICAL_ONE = 0;
    static final int VERTICAL_TWO = 2;
    static final int VERTICAL_THREE = 4;
    static final int VERTICAL_FOUR = 6;
    static final int VERTICAL_FIVE = 8;
    static final int VERTICAL_SIX = 10;

    static final int HORIZONTAL_ONE = 3;
    static final int HORIZONTAL_TWO = 5;
    static final int HORIZONTAL_THREE = 7;
    static final int HORIZONTAL_FOUR = 9;
    static final int HORIZONTAL_FIVE = 11;
    static final int HORIZONTAL_SIX = 1;

    @IntDef({VERTICAL_ONE, VERTICAL_TWO, VERTICAL_THREE, VERTICAL_FOUR, VERTICAL_FIVE, VERTICAL_SIX})
    @interface VerticalNumber {
    }


    @IntDef({HORIZONTAL_ONE, HORIZONTAL_TWO, HORIZONTAL_THREE, HORIZONTAL_FOUR, HORIZONTAL_SIX, HORIZONTAL_FIVE})
    @interface HorizontalNumber {
    }
}
