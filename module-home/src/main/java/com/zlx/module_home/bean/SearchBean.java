package com.zlx.module_home.bean;

import com.zlx.module_base.base_api.res_data.SearchBeanRes;

import java.util.List;

/**
 * Created by zlx on 2020/9/23 14:39
 * Email: 1170762202@qq.com
 * Description:
 */
public class SearchBean {
    private String title;
    private List<SearchBeanRes> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SearchBeanRes> getData() {
        return data;
    }

    public void setData(List<SearchBeanRes> data) {
        this.data = data;
    }
}
