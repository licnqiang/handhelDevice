package cn.piesat.sanitation.util.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**

 * Created by sen.luo on 2020-02-19.
 */
public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i =new Intent(context,LocationService.class);
        context.startService(i);
    }
}
