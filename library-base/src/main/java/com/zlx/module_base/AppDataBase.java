package com.zlx.module_base;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zlx.module_base.database.DateConverter;
import com.zlx.module_base.database.dao.PersonDao;
import com.zlx.module_base.database.entity.PersonInfo;
import com.zlx.module_base.database.entity.StudentInfo;

/**
 * @date: 2019\9\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
@TypeConverters(value = {DateConverter.class})
@Database(entities = {PersonInfo.class, StudentInfo.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PersonDao personDao();
}

