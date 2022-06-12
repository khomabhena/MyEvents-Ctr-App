package tech.myevents.hq;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import static tech.myevents.hq.EventsStaticWaiting.eventsAdapter;
import static tech.myevents.hq.EventsStaticWaiting.eventsList;
import static tech.myevents.hq.EventsStaticWaiting.lDate;
import static tech.myevents.hq.EventsStaticWaiting.lDay;
import static tech.myevents.hq.EventsStaticWaiting.lLiked;
import static tech.myevents.hq.EventsStaticWaiting.lLikes;
import static tech.myevents.hq.EventsStaticWaiting.lLocation;
import static tech.myevents.hq.EventsStaticWaiting.lPlace;
import static tech.myevents.hq.EventsStaticWaiting.lVideo;
import static tech.myevents.hq.EventsStaticWaiting.lViewed;
import static tech.myevents.hq.EventsStaticWaiting.lViews;
import static tech.myevents.hq.EventsStaticWaiting.sDate;
import static tech.myevents.hq.EventsStaticWaiting.sDay;
import static tech.myevents.hq.EventsStaticWaiting.sLiked;
import static tech.myevents.hq.EventsStaticWaiting.sLikes;
import static tech.myevents.hq.EventsStaticWaiting.sLocation;
import static tech.myevents.hq.EventsStaticWaiting.sPlace;
import static tech.myevents.hq.EventsStaticWaiting.sVideo;
import static tech.myevents.hq.EventsStaticWaiting.sViewed;
import static tech.myevents.hq.EventsStaticWaiting.sViews;
import static tech.myevents.hq.MainActivity.currentTabItem;

public class WaitingEventsService extends IntentService {

    String appURL = App.AppInfo.APP_URL;
    String rowCount;
    DBOperations dbOperations;

    String notificationCount;
    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;

    SQLiteDatabase db;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 675646;
    int numMessages = 0;
    String maxEventId;
    String action;

    String cityCode;
    String maxId;
    StringBuilder builderVenues;

    long id;
    int eventId, views, likes;
    String interestCode, locationCode, name, venue, eventDetails, minPrice, maxPrice, startTimestamp, endTimestamp;
    String bitmapName, video, when, confCode, broadcastCharge;

    public WaitingEventsService() {
        super("WaitingEventsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dbOperations = new DBOperations(getApplicationContext());
        db = dbOperations.getWritableDatabase();
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        prefUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        notificationCount = prefUserInfo.getString("eventNotificationCountWaiting", "");
        maxId = prefUserInfo.getString("maxEventIdWaiting", "");

        long diff = getCurrentTimestamp() - prefUserInfo.getLong("lastEventUpdateWaiting", 125000);
        if (diff > 60000) {
            checkTheData();
        }
    }

    private void checkTheData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor = prefUserInfo.edit();
                        editor.putLong("lastEventUpdateWaiting", getCurrentTimestamp());
                        editor.apply();
                        getData(response);
                        //showToast(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "get_waiting_events");
                params.put("max_id", maxId);

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQue(stringRequest);
    }

    private void getData(String response) {
        if (eventsList != null) {
            lLiked = new LinkedList<>(Arrays.asList(sLiked));
            lViewed = new LinkedList<>(Arrays.asList(sViewed));
            lDate = new LinkedList<>(Arrays.asList(sDate));
            lPlace = new LinkedList<>(Arrays.asList(sPlace));
            lDay = new LinkedList<>(Arrays.asList(sDay));
            lLocation = new LinkedList<>(Arrays.asList(sLocation));
            lViews = new LinkedList<>(Arrays.asList(sViews));
            lLikes = new LinkedList<>(Arrays.asList(sLikes));
        }
        builderVenues = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String eventName = "";
            String eventVenue = "";
            while (count < jsonArray.length()) {
                //showToast("<?>" + jsonArray.length());
                JSONObject jo = jsonArray.getJSONObject(count);
                if (count == 0) {
                    if (jo.getString("response").equals("yes")) {
                        editor = prefUserInfo.edit();
                        editor.putString("maxEventIdWaiting", jo.getString("maxId"));
                        editor.apply();
                    } else {
                        return;
                    }
                } else {
                    if (jsonArray.length() == 2) {
                        eventName = jo.getString("name");
                        eventVenue = jo.getString("venue");
                    }
                    getValues(jo);
                    insertValues(jo);
                    populateList();
                }
                count++;
            }
            populateArrays(jsonArray.length() - 1);
            callNotifications(jsonArray.length() - 1, eventName, eventVenue);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void getValues(JSONObject jo) {
        try {
            eventId = Integer.parseInt(jo.getString("id"));
            interestCode = jo.getString("interest_code");
            locationCode = jo.getString("location_code");
            name = jo.getString("name");
            venue = jo.getString("venue");
            eventDetails = jo.getString("details");
            minPrice = jo.getString("min_price");
            maxPrice = jo.getString("max_price");
            startTimestamp = jo.getString("start_timestamp");
            endTimestamp = jo.getString("end_timestamp");
            bitmapName = jo.getString("bitmap_name");
            views = 0;
            likes = 0;
            video = jo.getString("video");
            confCode = jo.getString("conf_code");
            broadcastCharge = jo.getString("broadcast_charge");
            when = jo.getString("issue_date");
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void insertValues(JSONObject jo) {
        try {
            ContentValues eventValues = new ContentValues();
            ContentValues statsValues = new ContentValues();
            statsValues.put(DBContract.EventStats.COLUMN_EVENT_ID, eventId);
            eventValues.put(DBContract.Event.COLUMN_USER_ID, jo.getString("user_id"));
            statsValues.put(DBContract.EventStats.COLUMN_USER_ID, jo.getString("user_id"));
            eventValues.put(DBContract.Event.COLUMN_EVENT_ID, eventId);
            eventValues.put(DBContract.Event.COLUMN_INTEREST_CODE, interestCode);
            eventValues.put(DBContract.Event.COLUMN_LOCATION_CODE, locationCode);
            eventValues.put(DBContract.Event.COLUMN_EVENT_NAME, name);
            eventValues.put(DBContract.Event.COLUMN_EVENT_VENUE, venue);
            eventValues.put(DBContract.Event.COLUMN_EVENT_DETAILS, eventDetails);
            eventValues.put(DBContract.Event.COLUMN_MIN_PRICE, minPrice);
            eventValues.put(DBContract.Event.COLUMN_MAX_PRICE, maxPrice);
            eventValues.put(DBContract.Event.COLUMN_START_TIMESTAMP, startTimestamp);
            eventValues.put(DBContract.Event.COLUMN_END_TIMESTAMP, endTimestamp);
            eventValues.put(DBContract.Event.COLUMN_BITMAP_NAME, bitmapName);
            eventValues.put(DBContract.Event.COLUMN_LIKES,  likes);
            eventValues.put(DBContract.Event.COLUMN_VIEWS, views);
            eventValues.put(DBContract.Event.COLUMN_VIDEO, video);
            eventValues.put(DBContract.Event.COLUMN_EVENT_STATUS, "waiting");
            eventValues.put(DBContract.Event.COLUMN_VIEW_STATUS, "none");
            eventValues.put(DBContract.Event.COLUMN_LIKE_STATUS, "none");
            eventValues.put(DBContract.Event.BROADCAST_CHARGE, broadcastCharge);
            eventValues.put(DBContract.Event.CONF_CODE, confCode);
            eventValues.put(DBContract.Event.COLUMN_WHEN, when);
            id = db.insert(DBContract.Event.TABLE_NAME,null,eventValues);
            db.insert(DBContract.EventStats.TABLE_NAME,null,statsValues);
            getBitmap(jo.getString("bitmap_name"));
            builderVenues.append(jo.getString("venue")).append(". ");
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void populateList() {
        if (eventsList != null) {
            EventsGetSetterWaiting eventsGetSetter =
                    new EventsGetSetterWaiting((int) id, eventId, views, likes, interestCode,
                            locationCode, name, venue, eventDetails,
                            minPrice, maxPrice, startTimestamp, endTimestamp,
                            bitmapName, null, null, video, when, confCode, broadcastCharge, "new");

            eventsList.add(0,eventsGetSetter);
            lLiked.add(0, R.drawable.ib_heart_gray);
            lViewed.add(0, R.drawable.ib_view_gray);
            lVideo.add(0, checkVideo(video));
            lDate.add(0, checkDate(startTimestamp));
            lPlace.add(0, checkLocation(locationCode));
            lDay.add(0, getDate(startTimestamp));
            lLocation.add(0, getLocation(locationCode));
            lViews.add(0, getViews(views));
            lLikes.add(0, getLikes(likes));
        }
    }

    private void populateArrays(int row_count) {
        if (eventsList != null) {
            int size = lLikes.size();
            sLikes = lLikes.toArray(new String[size]);
            sViews = lViews.toArray(new String[size]);
            sViewed = lViewed.toArray(new Integer[size]);
            sLiked = lLiked.toArray(new Integer[size]);
            sVideo = lVideo.toArray(new Integer[size]);
            sPlace = lPlace.toArray(new Integer[size]);
            sDate = lDate.toArray(new Integer[size]);
            sDay = lDay.toArray(new String[size]);
            sLocation = lLocation.toArray(new String[size]);
            eventsAdapter.notifyDataSetChanged();
        }
        editor = prefUserInfo.edit();
        editor.putString("eventNotificationCountWaiting", String.valueOf(row_count));
        editor.apply();
    }

    private void callNotifications(int row_count, String eventName, String eventVenue) {
        if(row_count != 0){
            if(row_count == 1){
                startNotification(eventName,eventVenue);
            } else {
                startNotification(String.valueOf(builderVenues));
            }
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

    private void startNotification(String eventName, String eventVenue) {
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New waiting Event From MyEvents");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("New waiting Event From MyEvents");
        notification.setContentText(eventName + " " + eventVenue);
        notification.setNumber(Integer.parseInt(prefUserInfo.getString("eventNotificationCountWaiting","1")));

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        currentTabItem = 3;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }

    private void startNotification(String venues){
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New waiting Events From MyEvents");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("New waiting Events Received");
        notification.setContentText(venues);
        notification.setNumber(Integer.parseInt(prefUserInfo.getString("eventNotificationCountWaiting", "1")));

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        currentTabItem = 3;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }

    private void getBitmap(final String bitmap_name) {
        String bitmapUrl = App.AppInfo.BITMAP_URL_EVENTS + bitmap_name + ".JPEG";
        ImageRequest imageRequest = new ImageRequest(bitmapUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        File newDir = new File("sdcard/MyEvents");
                        if(!newDir.exists()){
                            newDir.mkdir();
                        }
                        File eventsDir = new File(newDir,"Events");
                        if (!eventsDir.exists()){
                            eventsDir.mkdir();
                        }
                        File postersDir = new File(eventsDir,"MyEvents Posters");
                        if (!postersDir.exists()){
                            postersDir.mkdir();
                        }
                        File toPath = new File(postersDir,bitmap_name + ".JPEG");
                        try {
                            FileOutputStream outputStream = new FileOutputStream(toPath);
                            response.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //getBitmap(bitmap_name);
                    }
                });
        MySingleton.getInstance(getApplicationContext()).addToRequestQue(imageRequest);
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

    int checkVideo(String video){
        if (video == null) {
            return R.drawable.ib_video_gray;
        }
        if(video.equals("yes")){
            return R.drawable.ib_video_teal;
        } else {
            return R.drawable.ib_video_gray;
        }
    }

    int checkDate(String startTimestamp){
        long cur = System.currentTimeMillis();
        long sT = Long.parseLong(startTimestamp);
        long result = sT - cur;
        if (result > 604800000) {
            return R.drawable.ib_date_gray;
        } else {
            return R.drawable.ib_date_teal;
        }
    }

    int checkLocation(String locationCode){

        String prefLocationCode = prefUserInfo.getString("locationCode","");

        if(locationCode.equals(prefLocationCode)){

            return R.drawable.ib_place_teal;

        } else {

            return R.drawable.ib_place_gray;

        }

    }

    String getDate(String startTimestamp){

        String wkDay;
        String monthName;
        int day, year, hour,minute;

        StringBuilder builder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.US);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.US);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(startTimestamp));
        wkDay = dateFormat.format(calendar.getTime());
        monthName = monthFormat.format(calendar.getTime());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR) % 100;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        builder.append(wkDay).append("  ").append(getTheValue(day)).append("-").append(monthName).append("-").append(year)
                .append(",  ").append(getTheValue(hour)).append(":").append(getTheValue(minute)).append("hrs");

        return String.valueOf(builder);

    }

    private String getTheValue(int value){

        String theValue = String.valueOf(value);

        if (theValue.length() == 1){

            return "0"+theValue;

        } else {

            return theValue;

        }

    }

    String getLocation(String locationCode){

        String city = locationCode.substring(0,3);
        String suburb = locationCode.substring(3,6);

        String theCity = dbOperations.getCity(db, Integer.parseInt(city));
        String theSuburb = dbOperations.getSuburb(db,Integer.parseInt(suburb));

        return theSuburb+", "+theCity;

    }

    String getLikes(int num){

        StringBuilder builder = new StringBuilder();
        String thePlus;

        int mil, milMod, hundredK, hundredKMod, tenK, tenKMod, k, kMod, h, hMod, t, tMod, ones;

        mil = num / 1000000;
        milMod = (num % 1000000);

        hundredK = milMod / 100000;
        hundredKMod = milMod % 100000;

        tenK = hundredKMod / 10000;
        tenKMod = hundredKMod % 10000;

        k = tenKMod / 1000;
        kMod = tenKMod % 1000;

        h = kMod / 100;
        hMod = kMod % 100;

        t = hMod / 10;
        tMod = hMod % 10;


        if(mil != 0){
            if (h != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(mil).append(getMilValue(hundredK,tenK,k)).append("Mil").append(thePlus);

        }
        else if (hundredK != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(hundredK).append(tenK).append(k).append(getHundredKValue(h,t)).append("K").append(thePlus);
        }
        else if (tenK != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(tenK).append(k).append(getTenKValue(h,t)).append("K").append(thePlus);
        }
        else if (k != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(k).append(getKValue(h,t)).append("K").append(thePlus);
        }
        else {

            builder.append(num);
        }

        return String.valueOf(builder);
    }

    String getViews(int num){

        StringBuilder builder = new StringBuilder();
        String thePlus;

        int mil, milMod, hundredK, hundredKMod, tenK, tenKMod, k, kMod, h, hMod, t, tMod, ones;

        mil = num / 1000000;
        milMod = (num % 1000000);

        hundredK = milMod / 100000;
        hundredKMod = milMod % 100000;

        tenK = hundredKMod / 10000;
        tenKMod = hundredKMod % 10000;

        k = tenKMod / 1000;
        kMod = tenKMod % 1000;

        h = kMod / 100;
        hMod = kMod % 100;

        t = hMod / 10;
        tMod = hMod % 10;


        if(mil != 0){
            if (h != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(mil).append(getMilValue(hundredK,tenK,k)).append("Mil").append(thePlus);

        }
        else if (hundredK != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(hundredK).append(tenK).append(k).append(getHundredKValue(h,t)).append("K").append(thePlus);
        }
        else if (tenK != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(tenK).append(k).append(getTenKValue(h,t)).append("K").append(thePlus);
        }
        else if (k != 0){
            if (tMod != 0){
                thePlus = "\u002B";
            } else {
                thePlus = "";
            }
            builder.append(k).append(getKValue(h,t)).append("K").append(thePlus);
        }
        else {

            builder.append(num);
        }

        return String.valueOf(builder);
    }


    private String getMilValue(int hundredK, int tenK, int k){

        StringBuilder builder = new StringBuilder();
        if (hundredK == 0 && tenK == 0 && k == 0){
            builder.append("");
        } else {
            if (tenK == 0 && k == 0) {
                builder.append(".").append(hundredK);
            }
            else if (tenK != 0 && k == 0) {
                builder.append(".").append(hundredK).append(tenK);
            } else {
                builder.append(".").append(hundredK).append(tenK).append(k);
            }
        }

        return String.valueOf(builder);
    }

    private String getHundredKValue(int h, int t){

        StringBuilder builder = new StringBuilder();
        if (h ==0 && t == 0) {
            builder.append("");
        } else {
            if (h != 0 && t == 0) {
                builder.append(".").append(h);
            } else {
                builder.append(".").append(h).append(t);
            }
        }

        return String.valueOf(builder);
    }

    private String getTenKValue(int h, int t){

        StringBuilder builder = new StringBuilder();
        if (h ==0 && t == 0) {
            builder.append("");
        } else {
            if (h != 0 && t == 0) {
                builder.append(".").append(h);
            } else {
                builder.append(".").append(h).append(t);
            }
        }

        return String.valueOf(builder);

    }

    private String getKValue(int h, int t){

        StringBuilder builder = new StringBuilder();
        if (h ==0 && t == 0) {
            builder.append("");
        } else {
            if (h != 0 && t == 0) {
                builder.append(".").append(h);
            } else {
                builder.append(".").append(h).append(t);
            }
        }

        return String.valueOf(builder);

    }

}
