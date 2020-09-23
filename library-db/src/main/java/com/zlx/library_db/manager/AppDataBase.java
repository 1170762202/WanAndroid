package com.zlx.library_db.manager;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zlx.library_db.converter.DateConverter;
import com.zlx.library_db.dao.SearchHistoryDao;
import com.zlx.library_db.entity.SearchHistoryEntity;

/**
 * @date: 2019\9\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
@TypeConverters(value = {DateConverter.class})
@Database(entities = {SearchHistoryEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract SearchHistoryDao searchHistoryDao();
}

