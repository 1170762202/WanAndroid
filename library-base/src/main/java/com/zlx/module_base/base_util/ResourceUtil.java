package com.zlx.module_base.base_util;

import java.lang.reflect.Field;

/**
 * Created by zlx on 2020/9/29 9:18
 * Email: 1170762202@qq.com
 * Description:
 */
public class ResourceUtil {
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
