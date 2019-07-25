package cn.piesat.retrofitframe.database;

import android.content.Context;
import com.raizlabs.android.dbflow.config.FlowManager;
import java.io.File;
import cn.piesat.retrofitframe.constant.FileConstant;


/**
 * Created by lq on 2018/7/3.
 * 初始化数据库
 */

public class InitDBUtil {
    public static void initDB(Context context,String userId) {

        String dbName = FileConstant.getDBPath(userId);

        FlowManager.init(new DatabaseContext(context, new File(dbName), false));
    }
}
