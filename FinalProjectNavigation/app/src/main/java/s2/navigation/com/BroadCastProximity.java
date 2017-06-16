package s2.navigation.com;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-05-29.
 */

public class BroadCastProximity extends BroadcastReceiver{
    @Override

    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.sound = Uri.parse("android.resource://" + "s2.navigation.com" + "/" + R.raw.near100 );
        SoundPoolController pool = new SoundPoolController(context);
        Log.i("BroadCastProximity>>","call");
        Toast.makeText(context,"알림",Toast.LENGTH_SHORT).show();
        boolean bEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if(bEnter){
            Toast.makeText(context,"100미터 이내에 사고지역이 있습니다.",Toast.LENGTH_SHORT).show();
            nm.notify(1234,notification);
        }else{
            Toast.makeText(context,"나갔습니다.",Toast.LENGTH_SHORT).show();
        }
    }
}
