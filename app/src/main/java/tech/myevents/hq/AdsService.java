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

import static tech.myevents.hq.AdsStatic.adsAdapter;
import static tech.myevents.hq.AdsStatic.adsList;
import static tech.myevents.hq.AdsStatic.lLiked;
import static tech.myevents.hq.AdsStatic.lLikes;
import static tech.myevents.hq.AdsStatic.lVideo;
import static tech.myevents.hq.AdsStatic.lViewed;
import static tech.myevents.hq.AdsStatic.lViews;
import static tech.myevents.hq.AdsStatic.lWhen;
import static tech.myevents.hq.AdsStatic.sLiked;
import static tech.myevents.hq.AdsStatic.sLikes;
import static tech.myevents.hq.AdsStatic.sVideo;
import static tech.myevents.hq.AdsStatic.sViewed;
import static tech.myevents.hq.AdsStatic.sViews;
import static tech.myevents.hq.AdsStatic.sWhen;

public class AdsService extends IntentService {

    DBOperations dbOperations;
    String notificationCount;
    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;
    SQLiteDatabase db;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 675647;
    String action = null;
    String maxId;
    int adId, views, likes;
    long id;
    String interestCode, name, adTitle, desc1, desc2, desc3, desc4, bitmapName, duration, video, when;
    StringBuilder builderVenues;

    public AdsService() {
        super("AdsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dbOperations = new DBOperations(getApplicationContext());
        db = dbOperations.getWritableDatabase();
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        prefUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        maxId = prefUserInfo.getString("maxAdId", "");
        notificationCount = prefUserInfo.getString("adNotificationCount", "");

        long diff = getCurrentTimestamp() - prefUserInfo.getLong("lastAdUpdate", 125000);
        if (diff > 120000) {
            checkTheData();
        }
    }

    private void checkTheData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.AppInfo.APP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor = prefUserInfo.edit();
                        editor.putLong("lastAdUpdate", getCurrentTimestamp());
                        editor.apply();
                        getData(response);
                        //showToast(maxId);
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
                params.put("action", "get_ads_hq");
                params.put("max_id", maxId);

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQue(stringRequest);
    }

    private void getData(String response) {
        if (adsList != null) {
            lViews = new LinkedList<>(Arrays.asList(sViews));
            lLikes = new LinkedList<>(Arrays.asList(sLikes));
            lViewed = new LinkedList<>(Arrays.asList(sViewed));
            lLiked = new LinkedList<>(Arrays.asList(sLiked));
            lVideo = new LinkedList<>(Arrays.asList(sVideo));
            lWhen = new LinkedList<>(Arrays.asList(sWhen));
        }
        builderVenues = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String brandName = "";
            String title = "";
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                if (count == 0) {
                    if (jo.getString("response").equals("yes")) {
                        editor = prefUserInfo.edit();
                        editor.putString("maxAdId", jo.getString("maxId"));
                        editor.apply();
                    } else {
                        return;
                    }
                } else {
                    if (jsonArray.length() == 2) {
                        brandName = jo.getString("brand_name");
                        title = jo.getString("title");
                    }
                    getValues(jo);
                    insertValues(jo);
                    populateList();
                }
                count++;
            }
            populateArrays(jsonArray.length() - 1);
            callNotifications(jsonArray.length() - 1, brandName, title);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void getValues(JSONObject jo) {
        try {
            adId = Integer.parseInt(jo.getString("id"));
            interestCode = jo.getString("interest_code");
            name = jo.getString("brand_name");
            adTitle = jo.getString("title");
            desc1 = jo.getString("desc1");
            desc2 = jo.getString("desc2");
            desc3 = jo.getString("desc3");
            desc4 = jo.getString("desc4");
            bitmapName = jo.getString("bitmap_name");
            views = Integer.parseInt(jo.getString("views"));
            likes = Integer.parseInt(jo.getString("likes"));
            duration = jo.getString("broadcast_duration");
            video = jo.getString("video");
            when = jo.getString("issue_date");
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void insertValues(JSONObject jo) {
        try {
            ContentValues adValues = new ContentValues();
            ContentValues statsValues = new ContentValues();
            statsValues.put(DBContract.AdStats.COLUMN_AD_ID, adId);
            adValues.put(DBContract.Ad.COLUMN_USER_ID, jo.getString("user_id"));
            statsValues.put(DBContract.AdStats.COLUMN_USER_ID, jo.getString("user_id"));
            adValues.put(DBContract.Ad.COLUMN_AD_ID, adId);
            adValues.put(DBContract.Ad.COLUMN_INTEREST_CODE, interestCode);
            adValues.put(DBContract.Ad.COLUMN_BRAND_NAME, name);
            adValues.put(DBContract.Ad.COLUMN_TITLE, adTitle);
            adValues.put(DBContract.Ad.COLUMN_DESC_1, desc1);
            adValues.put(DBContract.Ad.COLUMN_DESC_2, desc2);
            adValues.put(DBContract.Ad.COLUMN_DESC_3, desc3);
            adValues.put(DBContract.Ad.COLUMN_DESC_4, desc4);
            adValues.put(DBContract.Ad.COLUMN_BITMAP_NAME, bitmapName);
            adValues.put(DBContract.Ad.COLUMN_DURATION, duration);
            adValues.put(DBContract.Ad.COLUMN_VIEWS, jo.getString("views"));
            adValues.put(DBContract.Ad.COLUMN_LIKES,jo.getString("likes"));
            adValues.put(DBContract.Ad.COLUMN_VIEW_STATUS, "none");
            adValues.put(DBContract.Ad.COLUMN_LIKE_STATUS, "none");
            adValues.put(DBContract.Ad.COLUMN_WHEN, when);
            adValues.put(DBContract.Ad.COLUMN_VIDEO, video);
            adValues.put(DBContract.Ad.COLUMN_AD_STATUS,"new");
            id = db.insert(DBContract.Ad.TABLE_NAME, null, adValues);
            db.insert(DBContract.AdStats.TABLE_NAME, null, statsValues);
            getBitmap(jo.getString("bitmap_name"));
            builderVenues.append("`").append(jo.getString("brand_name")).append("`  ");

        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void populateList() {
        if (adsList != null) {
            AdsGetSetter adsGetSetter = new AdsGetSetter((int) id, adId, views, likes,
                    interestCode, name, adTitle, desc1, desc2, desc3, desc4, bitmapName, null, null, duration, video, when);

            adsList.add(0,adsGetSetter);
            lViews.add(0, getViews(views));
            lLikes.add(0, getLikes(likes));
            lViewed.add(0, R.drawable.ib_view_gray);
            lLiked.add(0, R.drawable.ib_heart_gray);
            lVideo.add(0, checkVideo(video));
            lWhen.add(0, calculateTimeReceived(when));
        }
    }

    private void populateArrays(int row_count) {
        if (adsList != null) {
            int size = lViews.size();
            sLikes = lLikes.toArray(new String[size]);
            sViews = lViews.toArray(new String[size]);
            sViewed = lViewed.toArray(new Integer[size]);
            sLiked = lLiked.toArray(new Integer[size]);
            sVideo = lVideo.toArray(new Integer[size]);
            sWhen = lWhen.toArray(new String[size]);
            adsAdapter.notifyDataSetChanged();
        }
        /***********************Don't Forget ******adItems = "updated";********************/
        editor = prefUserInfo.edit();
        editor.putString("adNotificationCount", String.valueOf(row_count));
        editor.apply();
    }

    private void callNotifications(int row_count, String brandName, String title) {
        if(row_count != 0){
            if(row_count == 1){
                startNotification(brandName,title);
            } else {
                startNotification(String.valueOf(builderVenues));
            }
        }
    }




    private void startNotification(String eventName, String eventVenue) {
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New Ad From MyEvents");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(eventName);
        notification.setContentText(eventVenue);
        notification.setNumber(Integer.parseInt(prefUserInfo.getString("adNotificationCount","1")));

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        editor = prefUserInfo.edit();
        editor.putInt("tabCurrentItem",0);
        editor.apply();
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }

    private void startNotification(String venues){
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
        notification.setTicker("New Ads From MyEvents");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("New Ads Received");
        notification.setContentText(venues);
        notification.setNumber(Integer.parseInt(prefUserInfo.getString("adNotificationCount","1")));
        //notification.setNumber(Integer.parseInt(notificationCount));

        /*NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("List of Brand Names");
        inboxStyle.addLine(venues);
        notification.setStyle(inboxStyle);*/

        editor = prefUserInfo.edit();
        editor.putInt("tabCurrentItem",0);
        editor.apply();

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }

    private void getBitmap(final String bitmap_name) {
        String bitmapUrl = App.AppInfo.BITMAP_URL_ADS + bitmap_name+".JPEG";
        ImageRequest imageRequest = new ImageRequest(bitmapUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        File newDir = new File("sdcard/MyEvents");
                        if(!newDir.exists()){
                            newDir.mkdir();
                        }
                        File eventsDir = new File(newDir,"Ads");
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
                        getBitmap(bitmap_name);
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

    String getDate(String startTimestamp){
        String wkDay;
        String monthName;
        int day, year, hour,minute;

        StringBuilder builder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.US);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.US);

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
        String theSuburb = dbOperations.getSuburb(db, Integer.parseInt(suburb));

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

    String calculateTimeReceived(String when) {
        long currentTimestamp = getCurrentTimestamp();
        long timeReceived = Long.parseLong(when);
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = currentTimestamp - timeReceived;
        String attach;

        if (timeBetween > day) {
            if (timeBetween > (day * 2)) {
                attach = " days ago";
            } else {
                attach = " day ago";
            }
            long result = timeBetween / day;

            return String.valueOf(result) + attach;
        } else if (timeBetween > hour) {
            if (timeBetween > (hour * 2)) {
                attach = " hours ago";
            } else {
                attach = " hour ago";
            }

            long result = timeBetween / hour;
            return String.valueOf(result) + attach;
        } else if (timeBetween > min){
            long result = timeBetween / min;
            return String.valueOf(result) + " min ago";
        } else {
            long result = timeBetween / 1000;
            return String.valueOf(result) + " sec ago";
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
