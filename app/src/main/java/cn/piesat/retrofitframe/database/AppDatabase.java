package cn.piesat.retrofitframe.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author lq
 * @fileName appdatabase
 * @data on  2019/2/21 14:06
 * @describe 配置数据库
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public final  class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int VERSION = 1;
}
