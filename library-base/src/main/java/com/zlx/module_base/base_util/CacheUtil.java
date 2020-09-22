package com.zlx.module_base.base_util;

import com.zlx.module_base.BaseApplication;

/**
 * FileName: CacheUtil
 * Created by zlx on 2020/9/22 9:47
 * Email: 1170762202@qq.com
 * Description:
 */
public class CacheUtil {
    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = FileUtils.getSize(BaseApplication.getInstance().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(BaseApplication.getInstance().getExternalCacheDir());
        }
        return FileUtils.formatSize(cacheSize);
    }

    public static void clearAllCache() {
        FileUtils.delete(BaseApplication.getInstance().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(BaseApplication.getInstance().getExternalCacheDir());
        }
    }
}
