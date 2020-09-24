package com.zlx.library_db.manager;

import android.content.Context;
import android.text.TextUtils;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;



/**
 * @date: 2019\9\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class DbUtil {
    private AppDataBase appDataBase;

    private static DbUtil instance;
    private Context context;

    private String dbName;

    public static DbUtil getInstance() {
        if (instance == null) {
            instance = new DbUtil();
        }
        return instance;
    }

    public void init(Context context,String dbName) {
        this.context =context.getApplicationContext();
        this.dbName = dbName;
        appDataBase = null;
    }


    public AppDataBase getAppDataBase() {
        if (appDataBase == null) {
            if (TextUtils.isEmpty(dbName)) {
                throw new NullPointerException("dbName is null");
            }
            appDataBase = Room.databaseBuilder(context, AppDataBase.class, dbName)
                    .allowMainThreadQueries()
                    .enableMultiInstanceInvalidation()
//                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return appDataBase;
    }


    /**
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)");

        }
    };

    /**
     * 数据库版本 2->3 新增book表格
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)");
        }
    };


}
