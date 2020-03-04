package cn.piesat.sanitation.util.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 定位后台服务
 * 1、间隔5分钟获取一次坐标并上传服务器
 * 2、每获取一次坐标判断其是否在指定范围内，若超出此范围通知栏发送广播提示
 * 3、保留三次上传服务器失败的坐标至本地，并在下次上传时依次上传或一起上传
 * 4、如果一段时间内获取坐标相同，说明其没有移动，通知栏发送广播提示。(?)
 *
 * Created by sen.luo on 2020-02-19.
 */
public class LocationService extends Service {

    private long updateLocationTime= 1*60*1000;// 5分钟启动一次

    // 34.3421958700,108.7267971000;34.3466782100,108.7230849300;34.3463770300,108.7271833400;
    //34.3450128600,108.7184500700;34.3384751700,108.7196517000;34.3357997000,108.7240719800;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     *  一旦启动 LocationService，就会在 onStartCommand() 方法里设定一个定时任务，
     *  指定时间内 UpdateReceiver 的 OnReceive 就会执行再次启动LocationService，形成循环。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("服务","启动");
           LocationUtil.getInstance(this).requestSingleLocation(new LocationUtil.OnLocationResultListener() {
               @Override
               public void onLocationResult(Location location) {
                   double latitude = location.getLatitude();
                   double longitude = location.getLongitude();
                   LogUtil.e("经纬度：",latitude+";"+longitude);

                   getLocationNext(latitude,longitude);
               }

               @Override
               public void onLocationFailed(String msg) {
                   LogUtil.e("获取经纬度失败",msg);
                   ToastUtil.show(LocationService.this,msg);
               }
           });

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + updateLocationTime;
        Intent i = new Intent(this, UpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);


    }

    /**
     * 获取经纬度后的操作
     * 1、判断近5次是否相等，通知栏提示其为静止状态（？误差值怎么算，经纬度）
     * 2、上传服务器
     * @param latitude
     * @param longitude
     */
    private void getLocationNext(double latitude, double longitude) {
        LocationBean bean =new LocationBean();
        bean.latitude=latitude;
        bean.longitude=longitude;

        List<LocationBean> beanList=new ArrayList<>();
//        beanList.addAll();
        //是否在指定电子围栏内
        if (LocationHelper.PtInPolygon(bean,beanList)){
            NotificationUtil.getInstance(this).sendNotification("您已经超出活动范围了！");
            // 获取一次位置上报服务器
        }
        //NotificationUtil.getInstance(this).sendNotification("您坐着半天都没动了，赶紧起来干活！");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
