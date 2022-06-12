package tech.myevents.hq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Calendar;

class CheckNowPlaying extends AsyncTask<Void, Void, Void> {

    Context context;

    CheckNowPlaying(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();
        int id;
        String startTimestamp, endTimestamp;

        Cursor cursor = dbOperations.checkNowPlaying(db);
        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_ID)));
            startTimestamp = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_START_TIMESTAMP));
            endTimestamp = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_END_TIMESTAMP));
            if (Long.parseLong(startTimestamp) < getCurrentTimestamp()) {
                if (Long.parseLong(endTimestamp) < getCurrentTimestamp()) {
                    ContentValues values = new ContentValues();
                    values.put(DBContract.Event.COLUMN_EVENT_STATUS, "old");
                    db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);
                } else {
                    ContentValues values = new ContentValues();
                    values.put(DBContract.Event.COLUMN_EVENT_STATUS, "now_playing");
                    db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);
                }
            }
        }
        cursor.close();

        return null;
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
