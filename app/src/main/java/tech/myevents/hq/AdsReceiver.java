package tech.myevents.hq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AdsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AdsService.class));
        //Toast.makeText(context,"Ads Received", Toast.LENGTH_SHORT).show();
    }

}
