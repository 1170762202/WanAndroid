package com.zlx.widget.rv_item;

public class MathUtil {

    /**
     * sin计算
     *
     * @param angle
     * @return
     */
    public static float sin(int angle) {
        return (float) Math.sin(angle * Math.PI / 180);
    }

    /**
     * cos计算
     * @param angle
     * @return
     */
    public static float cos(int angle) {
        return (float) Math.cos(angle * Math.PI / 180);
    }
}
