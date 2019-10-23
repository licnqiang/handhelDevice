package cn.piesat.sanitation.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;
import cn.piesat.sanitation.ui.activity.MainActivity;
import cn.piesat.sanitation.util.LogUtil;

/**
 * 自定义通知栏打开
 * Created by sen.luo on 2019/10/23.
 */

public class PushMyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("推送",intent.getAction()+"");

        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
//            LogUtil.e("推送","点击通知打开指定页面");
        }else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            LogUtil.e("推送","点击通知打开指定页面");
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }
}
