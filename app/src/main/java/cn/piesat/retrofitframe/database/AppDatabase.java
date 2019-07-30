package cn.piesat.retrofitframe.database;

import com.raizlabs.android.dbflow.annotation.Database;

import cn.piesat.retrofitframe.constant.SysContant;

/**
 * @author lq
 * @fileName appdatabase
 * @data on  2019/2/21 14:06
 * @describe 配置数据库
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public final  class AppDatabase {
    public static final String NAME = SysContant.DBContats.db_name;
    public static final int VERSION = SysContant.DBContats.db_version;
}
