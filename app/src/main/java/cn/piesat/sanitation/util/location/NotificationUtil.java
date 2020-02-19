package cn.piesat.sanitation.util.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.ui.activity.MainActivity;

/**
 * Created by sen.luo on 2020-02-19.
 */
public class NotificationUtil {

    private static NotificationUtil notificationUtil;

    private Context context;
    private Notification.Builder builder;
    private  Notification notification;
    private NotificationManager notificationManager;

    public NotificationUtil(Context context) {
        this.context = context;
        builder=new Notification.Builder(context);
        notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static  NotificationUtil getInstance(Context context){
        if (notificationUtil==null){
            notificationUtil=new NotificationUtil(context);
        }
        return notificationUtil;
    }


    public void sendNotification( String msg){
        Intent intent =new Intent(context, MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(context,0,intent,0);
        builder.setSmallIcon(R.mipmap.logo)
                .setContentTitle("注意！")
                .setContentText(msg)
                .setAutoCancel(true)
                .setTicker(msg)
                .setPriority(Notification.PRIORITY_MAX)//优先级
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                notification=builder.build();

                notificationManager.notify(1,notification);

    }
}
