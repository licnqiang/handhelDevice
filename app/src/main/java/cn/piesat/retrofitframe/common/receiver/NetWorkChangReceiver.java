package cn.piesat.retrofitframe.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;

import cn.piesat.retrofitframe.common.event.NetworkChangeEvent;
import cn.piesat.retrofitframe.util.NetUtil;


/**
 * 网络连接监听
 *
 * @time 2018/8/14 14:04
 */
public class NetWorkChangReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        Log.i("NetBroadcastReceiver", "NetBroadcastReceiver changed");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = getNetWorkState(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            EventBus.getDefault().post(new NetworkChangeEvent(netWorkState));
        }
    }

    private int getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NetUtil.NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NetUtil.NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NetUtil.NETWORK_NONE;
        }
        return NetUtil.NETWORK_NONE;
    }

}