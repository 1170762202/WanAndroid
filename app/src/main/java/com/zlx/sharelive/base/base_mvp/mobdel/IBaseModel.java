package com.zlx.sharelive.base.base_mvp.mobdel;

import java.util.Map;

/**
 * @date: 2019\2\25 0025
 * @author: zlx
 * @description:
 */
public interface IBaseModel {
    void post(String url, Map<String, Object> map, IBaseModelListener listener);

    void get(String url, Map<String, Object> map, IBaseModelListener listener);
}
