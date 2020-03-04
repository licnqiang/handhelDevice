package cn.piesat.sanitation.util.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.util.LogPrinter;

import java.util.Iterator;
import java.util.List;

import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * Created by sen.luo on 2020-02-19.
 */
public class LocationUtil {


    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    private static LocationUtil locationUtil;
    private Context context;
    private LocationManager locationManager;
    private int gpsCount=0;
    private float signalCont=0;


    @SuppressLint("MissingPermission")
    private LocationUtil(Context context) {
        this.context = context;
        locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(gpsListener);

    }

    public static LocationUtil getInstance(Context context){

        if (locationUtil==null){
            locationUtil=new LocationUtil(context);
        }

        return locationUtil;
    }

    /**
     * 单次定位
     *
     * @param locationListener
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    public void requestSingleLocation(@NonNull OnLocationResultListener locationListener) {
        mOnLocationListener = locationListener;
        if (lacksPermissions(PERMISSIONS)) {
            if (mOnLocationListener != null) {
                LogUtil.e("locationUtil","未获取定位权限，无法定位");
                mOnLocationListener.onLocationFailed("未获取定位权限，无法定位");
            }
        }
        locationManager.requestSingleUpdate(getProvider(), this.locationListener, null);


    }

    /**
     * 持续定位
     *
     * @param interval         间隔时长，以毫秒为单位
     * @param locationListener
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    public void requestIntervalLocation(long interval, @NonNull OnLocationResultListener locationListener) {
        mOnLocationListener = locationListener;
        if (lacksPermissions(PERMISSIONS)) {
            if (mOnLocationListener != null) {
                LogUtil.e("locationUtil","未获取定位权限，无法定位");
                mOnLocationListener.onLocationFailed("未获取定位权限，无法定位");
            }
        }
        locationManager.requestLocationUpdates(getProvider(), interval, 1, this.locationListener);
    }

    @SuppressLint("MissingPermission")
    public Location getLastKnowLocation() {
        return locationManager.getLastKnownLocation(getProvider());
    }

    @SuppressLint("MissingPermission")
    public String getLastKnowLocationStr() {
        Location location = locationManager.getLastKnownLocation(getProvider());
        if (location != null) {
            return String.format("%.6f,%.6f", location.getLongitude(), location.getLatitude());
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    private String getProvider() {

        // 获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) { // GPS
          /*  //如果gps信号弱获取不到经纬度，采用网络定位(此方法不能用，因为获取的最后一次位置是永远有值的)
            if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)==null){
                locationProvider = LocationManager.NETWORK_PROVIDER;
            }else {
                locationProvider = LocationManager.GPS_PROVIDER;
            }*/

            //如果卫星数量大于4并且信号>1才使用GPS
            if (gpsCount>4&&signalCont>1){
                locationProvider = LocationManager.GPS_PROVIDER;
            }else {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            }

        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) { // Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            //跳转手机位置设置
//            Intent i = new Intent();
//            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            mContext.startActivity(i);
            if (mOnLocationListener != null) {
                LogUtil.e("locationUtil","请在设置中打开定位功能");
                mOnLocationListener.onLocationFailed("请在设置中打开定位功能");
            }
        }
        return locationProvider;
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            ToastUtil.show(context,"GPS关闭，会影响定位精度");

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            location.dump(new LogPrinter(Log.DEBUG, "gps-dump"), "");
            if (mOnLocationListener != null) {
                mOnLocationListener.onLocationResult(location);
            }
        }
    };

    public void removeListener() {
        locationManager.removeUpdates(locationListener);
    }

    private OnLocationResultListener mOnLocationListener;

    public interface OnLocationResultListener {
        void onLocationResult(Location location);

        void onLocationFailed(String msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean lacksPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    GpsStatus.Listener gpsListener =new GpsStatus.Listener(){

        @Override
        public void onGpsStatusChanged(int i) {
            switch (i){
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    LogUtil.e("卫星状态改变",i+"");
                    //获取当前状态
                    GpsStatus gpsStatus=locationManager.getGpsStatus(null);

                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count =0;
                    signalCont = 0;//每监听一次对之前的信号清0
                    while (iters.hasNext() && count <= maxSatellites) {
                        count++;
                        GpsSatellite s = iters.next();
                        //卫星的信噪比
                        float snr = s.getSnr();
                        signalCont = signalCont + snr;

                    }
                    gpsCount=count;
                    Log.i("当前有"+gpsCount+"卫星","信噪比是："+signalCont);
                    break;

            }
        }
    };


}
