package tech.myevents.hq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Calendar;

class EventLikes extends AsyncTask<String, Void, String> {
    Context context;
    Activity activity;
    int id;
    String eventId;
    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;

    EventLikes(Context context) {
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

        if (action.equals("eventLike")) {
            String liked = params[2];
            String likes = params[3];
            eventId = params[4];
            if (liked.equals("yes")) {
                String likeStatus = dbOperations.getLikeStatus(db,id);
                String statusToPut;
                if (likeStatus.equals("updated")) {
                    statusToPut = "minus";
                } else {
                    statusToPut = "none";
                }

                ContentValues values = new ContentValues();
                values.put(DBContract.Event.COLUMN_LIKED, "no");
                values.put(DBContract.Event.COLUMN_LIKES, likes);
                values.put(DBContract.Event.COLUMN_LIKE_STATUS, statusToPut);
                db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);

                return "minus";
            }
            else if (liked.equals("no")) {
                ContentValues values = new ContentValues();
                values.put(DBContract.Event.COLUMN_LIKED, "yes");
                values.put(DBContract.Event.COLUMN_LIKES, likes);
                values.put(DBContract.Event.COLUMN_LIKE_STATUS,"plus");
                db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);

                return "plus";
            }
        } else if (action.equals("eventView")) {
            String views = params[2];
            eventId = params[3];

            ContentValues values = new ContentValues();
            values.put(DBContract.Event.COLUMN_VIEWED, "yes");
            values.put(DBContract.Event.COLUMN_VIEWS, views);
            values.put(DBContract.Event.COLUMN_VIEW_STATUS,"plus");
            db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);

            return "view";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        long diff = getCurrentTimestamp() - prefUserInfo.getLong("lastEventServerUpdate",125000);
        if (diff > 900000) {
            editor = prefUserInfo.edit();
            editor.putLong("lastEventServerUpdate", getCurrentTimestamp());
            editor.apply();
            new EventServerUpdate(context).execute("");
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