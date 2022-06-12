package tech.myevents.hq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Calendar;

class CheckDatedAds extends AsyncTask<Void, Void, Void> {

    Context context;
    CheckDatedAds(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();
        int id;
        String duration;
        Cursor cursor = dbOperations.checkDatedAds(db);

        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Ad.COLUMN_ID)));
            duration = cursor.getString(cursor.getColumnIndex(DBContract.Ad.COLUMN_DURATION));

            if (Long.parseLong(duration) < getCurrentTimestamp()) {
                ContentValues values = new ContentValues();
                values.put(DBContract.Ad.COLUMN_AD_STATUS, "dated");
                db.update(DBContract.Ad.TABLE_NAME, values, DBContract.Ad.COLUMN_ID + "='" + id + "'", null);
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