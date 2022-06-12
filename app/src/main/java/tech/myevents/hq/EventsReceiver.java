package tech.myevents.hq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, EventsService.class));
        //Toast.makeText(context,"Events Received", Toast.LENGTH_SHORT).show();
    }
}