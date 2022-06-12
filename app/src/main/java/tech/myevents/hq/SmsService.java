package tech.myevents.hq;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static tech.myevents.hq.MainActivity.currentTabItem;

public class SmsService extends IntentService {

    DBOperations dbOperations;
    SQLiteDatabase db;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 675646;

    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;
    String broadcastUrl = App.AppInfo.APP_URL;

    public SmsService() {
        super("SmsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dbOperations = new DBOperations(getApplicationContext());
        db = dbOperations.getWritableDatabase();
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        prefUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        long diff = getCurrentTimestamp() - prefUserInfo.getLong("lastSmsUpdate",125000);
        if (diff > 30000) {
            checkSignUps();
        }
        //showToast("Sms Service");
    }

    private void checkSignUps() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, broadcastUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor = prefUserInfo.edit();
                        editor.putLong("lastSmsUpdate", getCurrentTimestamp());
                        editor.apply();
                        //showToast(response);
                        getData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //showToast("AAAA ");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "get_sign_ups");

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQue(stringRequest);
    }

    private void getData(String response) {
        String phoneNumber = "";
        int numCount;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            numCount = jsonArray.length();
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                phoneNumber = jo.getString("phone_number");
                String confCode = jo.getString("conf_code");

                ContentValues values = new ContentValues();
                values.put(DBContract.SignUps.PHONE_NUMBER, phoneNumber);
                values.put(DBContract.SignUps.CONF_CODE, confCode);
                db.insert(DBContract.SignUps.TABLE_NAME, null, values);

                String number = "+263785389505";
                String message = "Welcome to MyEvents App!!\nYour Confirmation Code is " + confCode + ".\nThank You!!!";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+263"+ phoneNumber, null, message, null, null);

                count++;
            }
            if (numCount != 0) {
                startNotification(String.valueOf(numCount), "New Sign-up " + phoneNumber);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    long getCurrentTimestamp(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    private void startNotification(String count, String msg){
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New Sign-up Received");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Mobile Sign-up Sms");
        notification.setContentText(msg);
        notification.setNumber(Integer.parseInt(count));

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        currentTabItem = 3;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }

    public void showToast(String message) {
        final String msg = message;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

    }
}