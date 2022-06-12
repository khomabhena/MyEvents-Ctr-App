package tech.myevents.hq;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String phoneNumber = remoteMessage.getData().get("phone_number");
        String confCode = remoteMessage.getData().get("conf_code");
        showNotification(phoneNumber, confCode);
        sendBroadcast(new Intent(this, SmsReceiver.class));
    }

    private void showNotification(String phoneNumber, String confCode) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New User");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("User Sign Up Firebase");
        notification.setContentText(phoneNumber + " : " + confCode);
        notification.setNumber(1);

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(2767,notification.build());
    }

}