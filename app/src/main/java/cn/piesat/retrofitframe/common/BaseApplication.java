package cn.piesat.retrofitframe.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.piesat.retrofitframe.common.netchange.receiver.NetWorkChangReceiver;
import cn.piesat.retrofitframe.database.InitDBUtil;
import cn.piesat.retrofitframe.util.SpHelper;

public class BaseApplication extends Application {
    public static Map<String, Activity> activityMap = new HashMap<String, Activity>();        //管理activity 的容器
    public static Context ApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext = getApplicationContext();
        SpHelper.init(getApplicationContext());
        registerMessageReceiver();
    }

    /**
     * 初始化数据库
     * @param userID
     */
    public static void initDB(String userID){
        InitDBUtil.initDB(ApplicationContext,userID);
    }

    //收集创建的Activity
    public static void putActivityInfoToMap(Activity activity) {
        if (activity != null) {
            String activityName = activity.getClass().getSimpleName();
            Log.i("info", "putActivity--->" + activityName);

            activityMap.put(activityName, activity);
        }
    }

    //移除activity
    public static void removeActivityInfoFromMap(Activity activity) {
        if (activity != null) {
            String activityName = activity.getClass().getSimpleName();
            Log.i("info", "removeActivity--->" + activityName);
            if (activityMap.containsKey(activityName)) {
                activityMap.remove(activityName);
            }
        }
    }

    //关闭所有界面
    public static void closeAllActivityByMap() {
        if (!activityMap.isEmpty()) {
            Collection<Activity> activities = activityMap.values();
            Iterator<Activity> it = activities.iterator();
            while (it.hasNext()) {
                Activity activity = it.next();
                String activityName = activity.getClass().getSimpleName();

                Log.i("info", "removeActivity--->" + activityName);

                activity.finish();
            }
        }
    }

    /**
     * 注册网络监听
     */
    private void registerMessageReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // TODO Auto-generated method stub
            NetWorkChangReceiver receiver = new NetWorkChangReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(receiver, filter);
        }
    }

}
