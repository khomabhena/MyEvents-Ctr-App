package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class EventsCalculations {
    private SQLiteDatabase db;
    private DBOperations dbOperations;
    Context context;

    EventsCalculations(Context context) {
        this.context = context;
        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();
    }

    String confCode(String confCode, String charge) {
        if (confCode != null || charge != null) {
            StringBuilder builder = new StringBuilder();
            if (confCode.contains("free")) {
                return confCode;
            } else {
                builder.append("$").append(charge).append(".00").append(" ---- ").append(confCode);
                return String.valueOf(builder);
            }
        } else {
            return "free";
        }
    }

    int charge(String charge) {
        if (charge != null) {
            if (!charge.contains("Free")) {
                return R.mipmap.dollar_teal;
            } else {
                return R.mipmap.dollar_gray;
            }
        } else {
            return R.mipmap.dollar_gray;
        }
    }

    int publish(String status) {
        if (status.equals("waiting")) {
            return R.mipmap.unpublished;
        } else {
            return R.mipmap.published;
        }
    }

    int checkLocation(String locationCode){
        SharedPreferences prefs = context.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        String prefLocationCode = prefs.getString("locationCode","");
        if(locationCode.equals(prefLocationCode)){
            return R.drawable.ib_place_teal;
        } else {
            return R.drawable.ib_place_gray;
        }
    }

    String getLocation(String locationCode){
        String city = locationCode.substring(0,3);
        String suburb = locationCode.substring(3,6);
        String theCity = dbOperations.getCity(db, Integer.parseInt(city));
        String theSuburb = dbOperations.getSuburb(db,Integer.parseInt(suburb));

        return theSuburb+", "+theCity;
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

    int checkLike(String liked){
        if (liked == null) {
            return R.drawable.ib_heart_gray;
        }
        if(liked.equals("yes")){
            return R.drawable.ib_heart_teal;
        } else {
            return R.drawable.ib_heart_gray;
        }
    }

    int checkView(String viewed){
        if (viewed == null){
            return R.drawable.ib_view_gray;
        }
        if(viewed.equals("yes")){
            return R.drawable.ib_view_teal;
        } else {
            return R.drawable.ib_view_gray;
        }
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

    String calculateTimeToGo(String startTimestamp) {
        long currentTimestamp = getCurrentTimestamp();
        long eventStartTimestamp = Long.parseLong(startTimestamp);
        long sec = 1000;
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = eventStartTimestamp - currentTimestamp;
        long days = timeBetween / day;
        long daysMod = timeBetween % day;
        long hours = daysMod / hour;
        long hoursMod = daysMod % hour;
        long minutes = hoursMod / min;

        StringBuilder builder = new StringBuilder();
        builder.append(getTheValue(days)).append(" Days ").append(getTheValue(hours)).append(" hrs ").append(getTheValue(minutes)).append(" min");

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

    String calculateTimeReceivedStats(String when) {
        long currentTimestamp = getCurrentTimestamp();
        long timeReceived = Long.parseLong(when);
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = currentTimestamp - timeReceived;
        String attach;
        if (timeBetween > day) {
            if (timeBetween > (day * 2)) {
                attach = " days";
            } else {
                attach = " day";
            }
            long result = timeBetween / day;

            return String.valueOf(result) + attach;
        } else if (timeBetween > hour) {
            if (timeBetween > (hour * 2)) {
                attach = " hrs";
            } else {
                attach = " hr";
            }

            long result = timeBetween / hour;
            return String.valueOf(result) + attach;
        } else if (timeBetween > min){

            long result = timeBetween / min;
            return String.valueOf(result) + " min";
        } else {
            long result = timeBetween / 1000;
            return String.valueOf(result) + " sec";
        }
    }

    String calculateNPTime(String endTimestamp) {
        long currentTimestamp = getCurrentTimestamp();
        long eventEndTimestamp = Long.parseLong(endTimestamp);
        long sec = 1000;
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = eventEndTimestamp - currentTimestamp;
        long days = timeBetween / day;
        long daysMod = timeBetween % day;
        long hours = timeBetween / hour;
        long hoursMod = timeBetween % hour;
        long minutes = hoursMod / min;

        StringBuilder builder = new StringBuilder();
        builder.append(getTheValue(hours)).append("hrs ").append(getTheValue(minutes)).append("min");

        return String.valueOf(builder);
    }

    private long getCurrentTimestamp(){
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
        if (h == 0 && t == 0) {
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

    private String getTheValue(int value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

    private String getTheValue(long value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

    String getDate(Long startTimestamp){
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.US);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTimestamp);

        String wkDay = dateFormat.format(calendar.getTime());
        String monthName = monthFormat.format(calendar.getTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR)%100;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        stringBuilder.append(wkDay).append(" ").append(getTheValue(day))
                .append(", ").append(getTheValue(hour)).append(":").append(getTheValue(minute)).append("hrs");

        return String.valueOf(stringBuilder);
    }

}
