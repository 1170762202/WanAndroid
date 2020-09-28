package com.zlx.module_base.constant;

/**
 * Created by zlx on 2020/9/23 14:53
 * Email: 1170762202@qq.com
 * Description:
 */
public class PageImpl {
    public int page = 1;

    public void nextPage() {
        page++;
    }

    public void reset() {
        page = 1;
    }

    public boolean isFirstPage() {
        return page == 1;
    }

    public int zeroPage = 0;

    public void nextZeroPage() {
        zeroPage++;
    }

    public void resetZero() {
        zeroPage = 0;
    }

    public boolean isZeroPage() {
        return zeroPage == 0;
    }
}
