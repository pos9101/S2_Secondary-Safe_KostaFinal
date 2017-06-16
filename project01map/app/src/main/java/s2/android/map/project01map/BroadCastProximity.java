package s2.android.map.project01map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-05-29.
 */

public class BroadCastProximity extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean bEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
        Toast.makeText(context,bEnter ? "들어왔습니다.":"나갔습니다",Toast.LENGTH_SHORT).show();
    }
}
