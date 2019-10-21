package cn.piesat.sanitation.database;

import android.content.Context;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

import java.io.File;
import cn.piesat.sanitation.constant.FileConstant;


/**
 * Created by lq on 2018/7/3.
 * 初始化数据库
 */

public class InitDBUtil {
    public static void initDB(Context context,String userId) {

        String dbName = FileConstant.getDBPath(userId);

//      FlowManager.init(new DatabaseContext(context, new File(dbName), false));

        /**
         * 方式二：加密dbFlow数据库
         *  自定义SQLiteCiperHelperImpl类，设定db密码
         *  加密情况下，找到路径下db，打开为空，表结构和表字段完全隐藏。
         *  pwd: walker789
         */
        FlowManager.init(new FlowConfig.Builder(new DatabaseContext(context, new File(dbName), true))
                .addDatabaseConfig(new DatabaseConfig.Builder(AppDatabase.class)
                        .openHelper(new DatabaseConfig.OpenHelperCreator() {
                            @Override
                            public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
                                return new SQLiteCiperHelperImpl(databaseDefinition, helperListener);
                            }
                        }).build()).build());

    }
}
