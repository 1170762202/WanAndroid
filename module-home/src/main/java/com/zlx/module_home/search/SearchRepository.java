package com.zlx.module_home.search;

import com.zlx.library_db.dao.SearchHistoryDao;
import com.zlx.library_db.entity.SearchHistoryEntity;
import com.zlx.library_db.manager.DbUtil;
import com.zlx.module_base.base_ac.BaseModel;
import com.zlx.module_base.base_api.res_data.SearchBeanRes;
import com.zlx.module_base.base_api.util.ApiUtil;
import com.zlx.module_network.factory.ApiCallback;

import java.util.List;

public class SearchRepository extends BaseModel {
    private SearchHistoryDao searchHistoryDao;

    public SearchRepository() {
        searchHistoryDao = DbUtil.getInstance().getAppDataBase().searchHistoryDao();
    }

    public void deleteLocalHis() {
        searchHistoryDao.deleteAll();
    }

    public void deleteLocalHisById(long id) {
        searchHistoryDao.deleteById(id);
    }

    public List<SearchHistoryEntity> selectLocalHis() {
        return searchHistoryDao.selectHis();
    }

    public long insertPerson(SearchHistoryEntity entity) {
        return searchHistoryDao.insertPerson(entity);
    }

    public void hotSearch(ApiCallback<List<SearchBeanRes>> callback) {
        ApiUtil.getArticleApi().hotSearch().enqueue(callback);
    }
}
