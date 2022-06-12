package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import static tech.myevents.hq.NowplayingStatic.npAdapter;
import static tech.myevents.hq.NowplayingStatic.npList;
import static tech.myevents.hq.NowplayingStatic.npParam;

class NowPlayingBackTask extends AsyncTask<Void, NowPlayingGetSetter, Integer> {

    Context context;
    private SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    private Activity activity;
    private LinearLayout llNPEmpty;
    private ListView listView;

    NowPlayingBackTask(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        npParam = "wait";
        int id, eventId, views, likes;
        String interestCode, locationCode, eventName, eventVenue, eventDetails, minPrice, maxPrice, startTimestamp, endTimestamp, bitmapName;
        String viewed, liked, video, when;

        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getReadableDatabase();
        npList = new ArrayList();
        npAdapter = new NowPlayingAdapter(context, R.layout.now_playing_layout);
        prefsList = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        listView = (ListView) activity.findViewById(R.id.lvNowPlaying);
        llNPEmpty = (LinearLayout) activity.findViewById(R.id.llNPEmpty);

        Cursor cursor = dbOperations.getNowPlaying(db);
        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_ID)));
            eventId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_ID)));
            views = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_VIEWS)));
            likes = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_LIKES)));
            interestCode = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_INTEREST_CODE));
            locationCode = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_LOCATION_CODE));
            eventName = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_NAME));
            eventVenue = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_VENUE));
            eventDetails = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_DETAILS));
            minPrice = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_MIN_PRICE));
            maxPrice = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_MAX_PRICE));
            startTimestamp = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_START_TIMESTAMP));
            endTimestamp = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_END_TIMESTAMP));
            bitmapName = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_BITMAP_NAME));
            viewed = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_VIEWED));
            liked = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_LIKED));
            video = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_VIDEO));
            when = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_WHEN));

            NowPlayingGetSetter eventsGetSetter =
                    new NowPlayingGetSetter(id, eventId, views, likes, interestCode,
                            locationCode, eventName, eventVenue, eventDetails,
                            minPrice, maxPrice, startTimestamp, endTimestamp,
                            bitmapName, viewed, liked, video, when);
            publishProgress(eventsGetSetter);

        }
        int x = cursor.getCount();
        cursor.close();

        return x;
    }

    @Override
    protected void onProgressUpdate(NowPlayingGetSetter... values) {
        npAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(Integer id) {
        if (id != 0){
            listView.setAdapter(npAdapter);
        }

        npParam = null;
        new NowPlayingItems(context).execute("");
    }

}