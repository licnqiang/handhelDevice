package cn.piesat.sanitation.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

//import com.meituan.android.walle.WalleChannelReader;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.entry.DefaultApplicationLike;

import com.tencent.tinker.entry.DefaultApplicationLike;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.piesat.sanitation.common.netchange.receiver.NetWorkChangReceiver;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.database.InitDBUtil;
import cn.piesat.sanitation.database.dbTab.RolesInfo_Tab;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.faceUtil.FaceSDKManager;
import cn.piesat.sanitation.util.faceUtil.SdkInitListener;

/**
 * 自定义ApplicationLike类.
 * <p>
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br/>
 *
 * @author wenjiewu
 * @since 2016/11/7
 */
public class BaseApplication extends DefaultApplicationLike {

    public static final String TAG = "Tinker.BaseApplication";
    public static Map<String, Activity> activityMap = new HashMap<String, Activity>();        //管理activity 的容器
    public static Context ApplicationContext;
    private static BaseApplication singleton;
    public static BaseApplication  getIns(){
        return singleton;
    }

    public BaseApplication(Application application, int tinkerFlags,
                           boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                           long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        ApplicationContext = getApplication().getApplicationContext();
        SpHelper.init(getApplication().getApplicationContext());
        registerMessageReceiver();
        //initBuglyCrashReport();
        initJPush();
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(ApplicationContext);
        initLicense();
    }

    public  String getUserId(){
        return SpHelper.getStringValue(SysContant.userInfo.USER_ID);
    }
    public String getUserToken(){
        return SpHelper.getStringValue(SysContant.userInfo.USER_TOKEN);
    }

    //角色id UserType
    public String getUserRoleId(){
        return SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID);
    }

    //站点名称
    public String getSiteName(){
        return SpHelper.getStringValue(SysContant.userInfo.USER_SITE_NAME);
    }


    public static UserInfo_Tab getUserInfo() {
        return new Select().from(UserInfo_Tab.class).querySingle();
    }

    public static RolesInfo_Tab getUserRoleInfo() {
        return new Select().from(RolesInfo_Tab.class).querySingle();
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // TODO: 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(
            Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }



    /**
     * 初始化数据库
     *
     * @param userID
     */
    public static void initDB(String userID) {

        InitDBUtil.initDB(ApplicationContext, userID);
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
            getApplication().registerReceiver(receiver, filter);
        }
    }

    /**
     * 初始化bugly
     */
    private void initBuglyCrashReport() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                Toast.makeText(getApplication(), "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(getApplication(),
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Toast.makeText(getApplication(), "补丁下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplication(), "补丁应用成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {

            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        // 多渠道需求塞入
        // String channel = WalleChannelReader.getChannel(getApplication());
        // Bugly.setAppChannel(getApplication(), channel);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.init(getApplication(), "ff00511d2a", true);
    }


    /**
     * 启动应用程序，如果之前初始过，自动初始化鉴权和模型（可以添加到Application 中）
     */
    private void initLicense() {
        if (FaceSDKManager.initStatus != FaceSDKManager.SDK_MODEL_LOAD_SUCCESS) {
            FaceSDKManager.getInstance().init(getApplication(), new SdkInitListener() {
                @Override
                public void initStart() {

                }

                @Override
                public void initLicenseSuccess() {

                }

                @Override
                public void initLicenseFail(int errorCode, String msg) {
                }

                @Override
                public void initModelSuccess() {
                }

                @Override
                public void initModelFail(int errorCode, String msg) {

                }
            });
        }
    }


}
