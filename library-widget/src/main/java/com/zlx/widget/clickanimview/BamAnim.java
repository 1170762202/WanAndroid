package com.zlx.widget.clickanimview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.zlx.widget.R;


/**
 * 点击动画效果工具类
 * <p>
 * 尘少为大家提供的，
 * 有两种点击效果，
 * <p>
 * 第一种是华丽效果，
 * 第二种是缩放效果，
 * <p>
 * 它与华丽效果的区别在于，
 * 使用默认的华丽效果时，
 * 点击View的上、下、左、右、中，
 * 分别对应的5种不一样的动画：
 * <p>
 * 上 ————> 后翻
 * 下 ————> 前翻
 * 左 ————> 左侧翻
 * 右 ————> 右侧翻
 * 中 ————> 缩放
 * <p>
 * 而缩放效果，
 * 无论点击哪个位置，
 * 都只有缩放的动画。
 * <p>
 * 而想要切换效果也很简单：
 * 先把View转为BamView，
 * 然后调用closeSuperb()即可。
 * <p>
 * 如：
 * ((BamImageView)iv_bam).closeSuperb();
 *
 * @author Bamboy
 */
public class BamAnim {

    /**
     * 动画执行速度
     */
    public static final int ANIM_SPEED = 300;

    /**
     * 旋转角度
     */
    private static final float POTATION_VALUE = 7f;

    /**
     * 变速器
     */
    public static OvershootInterpolator interpolator = new OvershootInterpolator(3f);

    /**
     * 缩放比例
     */
    private static final float SCALE_END = 0.95f;

    /**
     * 阴影最小值
     */
    private static final float SHADOW_END = 0;

    /**
     * 启动按压动画
     *
     * @param view   执行动画的View
     * @param superb 效果类型【true：华丽效果】【false：缩放效果】
     * @param x      触点X坐标
     * @param y      触点Y坐标
     * @return 动画执行顶点
     */
    public static int startAnimDown(View view, boolean superb, float x, float y) {
        if (false == view.isClickable()) {
            return 1;
        }

        int pivot;
        // 缩放效果
        if (false == superb) {
            pivot = 0;
            // 执行缩小动画【缩放效果】
            froBig_ToSmall(view);
            return pivot;
        }

        // 华丽效果
        int w = view.getWidth();
        int h = view.getHeight();

        if ((w / 5 * 2) < x && x < (w / 5 * 3) && (h / 5 * 2) < y && y < (h / 5 * 3)) {
            pivot = 0;
        } else if (x < w / 2 && y < h / 2) { // 第一象限
            if (x / (w / 2) > y / (h / 2)) {
                pivot = 1;
            } else {
                pivot = 4;
            }
        } else if (x < w / 2 && y >= h / 2) { // 第四象限
            if ((w - x) / (w / 2) > y / (h / 2)) {
                pivot = 4;
            } else {
                pivot = 3;
            }
        } else if (x >= w / 2 && y >= h / 2) { // 第三象限
            if ((w - x) / (w / 2) > (h - y) / (h / 2)) {
                pivot = 3;
            } else {
                pivot = 2;
            }
        } else { // 第二象限
            if (x / (w / 2) > (h - y) / (h / 2)) {
                pivot = 2;
            } else {
                pivot = 1;
            }
        }

        String anim = "";
        switch (pivot) {
            case 0:
                view.setPivotX(w / 2);
                view.setPivotY(h / 2);
                // 执行缩小动画【缩放效果】
                froBig_ToSmall(view);
                return pivot;
            case 1:
            case 3:
                anim = "rotationX";
                break;
            case 2:
            case 4:
                anim = "rotationY";
                break;

            default:
                break;
        }

        view.setPivotX(w / 2);
        view.setPivotY(h / 2);

        // 执行缩小动画【华丽效果】
        froBig_ToSmall(view, pivot, anim);
        return pivot;
    }

    /**
     * 启动抬起动画
     *
     * @param view  执行动画的View
     * @param pivot 动画执行顶点
     */
    public static void startAnimUp(View view, int pivot) {
        if (false == view.isClickable()) {
            return;
        }

        if (pivot == 0) {
            // 执行放大动画【缩放效果】
            froSmall_ToBig(view);
        } else {
            String anim = "";
            switch (pivot) {
                case 1:
                case 3:
                    anim = "rotationX";
                    break;
                case 2:
                case 4:
                    anim = "rotationY";
                    break;
            }
            // 执行放大动画【华丽效果】
            froSmall_ToBig(view, pivot, anim);
        }
    }

    /**
     * 【华丽效果】从大过渡到小
     */
    private static void froBig_ToSmall(View view, int pivot, String anim) {

        float potationEnd;
        if (pivot == 3 || pivot == 4) {
            potationEnd = 0 - POTATION_VALUE;
        } else {
            potationEnd = POTATION_VALUE;
        }

        int potationStart = 0;
        if (pivot == 2 || pivot == 4) {
            potationStart = (int) view.getRotationY();
        } else {
            potationStart = (int) view.getRotationX();
        }

        ObjectAnimator animObject = ObjectAnimator.ofFloat(view, anim, potationStart, potationEnd)
                .setDuration(ANIM_SPEED);
        animObject.setInterpolator(interpolator);
        animObject.start();
    }

    /**
     * 【华丽效果】从小过渡到大
     */
    private static void froSmall_ToBig(View view, int pivot, String anim) {
        int potation;
        if (pivot == 2 || pivot == 4) {
            potation = (int) view.getRotationY();
        } else {
            potation = (int) view.getRotationX();
        }
        ObjectAnimator animObject = ObjectAnimator.ofFloat(view, anim, potation, 0).setDuration(ANIM_SPEED);
        animObject.setInterpolator(interpolator);
        animObject.start();
    }

    /**
     * 【缩放效果】从大过渡到小
     */
    public static void froBig_ToSmall(View view) {
        try {
            float tzStart = 0;
            Object viewTag = view.getTag(R.string.tag_key_translation_z);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                tzStart = view.getTranslationZ();
                if (viewTag == null || false == viewTag instanceof Float) {
                    view.setTag(R.string.tag_key_translation_z, tzStart);
                }
            }

            /**
             * Z轴变低
             */
            PropertyValuesHolder tz = PropertyValuesHolder.ofFloat("translationZ", tzStart, SHADOW_END);
            /**
             * 控件的宽变小
             */
            PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), SCALE_END);
            /**
             * 控件的高变小
             */
            PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), SCALE_END);

            /**
             * 动画集合，所有动画一起播放
             */
            ObjectAnimator animatorD = ObjectAnimator.ofPropertyValuesHolder(view, tz, sx, sy).setDuration(ANIM_SPEED);
            animatorD.setInterpolator(interpolator);
            animatorD.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【缩放效果】从小过渡到大
     */
    public static void froSmall_ToBig(View view) {
        try {
            float tzStart = 0, tzEnd = 0;
            Object viewTag = view.getTag(R.string.tag_key_translation_z);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                tzStart = view.getTranslationZ();
                if (viewTag != null && viewTag instanceof Float) {
                    tzEnd = (Float) viewTag;
                }
            }

            /**
             * Z轴变低
             */
            PropertyValuesHolder tz = PropertyValuesHolder.ofFloat("translationZ", tzStart, tzEnd);
            /**
             * 控件的宽变小
             */
            PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), 1);
            /**
             * 控件的高变小
             */
            PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), 1);

            /**
             * 动画集合，所有动画一起播放
             */
            ObjectAnimator animatorD = ObjectAnimator.ofPropertyValuesHolder(view, tz, sx, sy).setDuration(ANIM_SPEED);
            animatorD.setInterpolator(interpolator);
            animatorD.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
