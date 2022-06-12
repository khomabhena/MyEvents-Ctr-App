package tech.myevents.hq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Calendar;

class AdLikes extends AsyncTask<String, Void, String> {

    Context context;
    Activity activity;
    int id;
    private String adId;
    private SharedPreferences prefUserInfo;
    private SharedPreferences.Editor editor;

    AdLikes(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected String doInBackground(String... params) {
        String action = params[0];
        id = Integer.parseInt(params[1]);
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();
        prefUserInfo = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        if (action.equals("adLike")) {
            String liked = params[2];
            String likes = params[3];
            adId = params[4];

            if (liked.equals("yes")) {
                String likeStatus = dbOperations.getAdLikeStatus(db, id);
                String statusToPut;
                if (likeStatus.equals("updated")) {
                    statusToPut = "minus";
                } else {
                    statusToPut = "none";
                }

                ContentValues values = new ContentValues();
                values.put(DBContract.Ad.COLUMN_LIKED, "no");
                values.put(DBContract.Ad.COLUMN_LIKES, likes);
                values.put(DBContract.Ad.COLUMN_LIKE_STATUS, statusToPut);
                db.update(DBContract.Ad.TABLE_NAME, values, DBContract.Ad.COLUMN_ID + "='" + id + "'", null);

                return "minus";
            }
            else if (liked.equals("no")) {
                ContentValues values = new ContentValues();
                values.put(DBContract.Ad.COLUMN_LIKED, "yes");
                values.put(DBContract.Ad.COLUMN_LIKES, likes);
                values.put(DBContract.Ad.COLUMN_LIKE_STATUS,"plus");
                db.update(DBContract.Ad.TABLE_NAME, values, DBContract.Ad.COLUMN_ID + "='" + id + "'", null);

                return "plus";
            }

        } else if (action.equals("adView")) {
            String views = params[2];
            adId = params[3];

            ContentValues values = new ContentValues();
            values.put(DBContract.Ad.COLUMN_VIEWED, "yes");
            values.put(DBContract.Ad.COLUMN_VIEWS, views);
            values.put(DBContract.Ad.COLUMN_VIEW_STATUS,"plus");
            db.update(DBContract.Ad.TABLE_NAME, values, DBContract.Ad.COLUMN_ID + "='" + id + "'", null);

            return "view";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        long diff = getCurrentTimestamp() - prefUserInfo.getLong("lastAdServerUpdate",125000);
        if (diff > 900000) {
            editor = prefUserInfo.edit();
            editor.putLong("lastAdServerUpdate", getCurrentTimestamp());
            editor.apply();
            new AdServerUpdate(context).execute("");
        }
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

}