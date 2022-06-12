package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;

import static tech.myevents.hq.EventsStaticWaiting.eventParam;
import static tech.myevents.hq.EventsStaticWaiting.eventsAdapter;
import static tech.myevents.hq.EventsStaticWaiting.eventsList;

class EventsBackTaskWaiting extends AsyncTask<Void, EventsGetSetterWaiting, Integer> {

    Context context;
    private SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    private Activity activity;
    ListView listView;


    EventsBackTaskWaiting(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        eventParam = "wait";
        int id, eventId, views, likes;
        String interestCode, locationCode, eventName, eventVenue, eventDetails, minPrice, maxPrice, startTimestamp, endTimestamp, bitmapName;
        String viewed, liked, video, when, confCode, broadcastCharge, status;

        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getReadableDatabase();

        eventsList = new ArrayList();
        listView = (ListView) activity.findViewById(R.id.lvEventsWaiting);
        eventsAdapter = new EventsAdapterWaiting(context, R.layout.waiting_events_layout);
        prefsList = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        Cursor cursor = dbOperations.getEventsWaiting(db);
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
            confCode = cursor.getString(cursor.getColumnIndex(DBContract.Event.CONF_CODE));
            broadcastCharge = cursor.getString(cursor.getColumnIndex(DBContract.Event.BROADCAST_CHARGE));
            status = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_STATUS));
            when = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_WHEN));

            EventsGetSetterWaiting eventsGetSetter =
                    new EventsGetSetterWaiting(id, eventId, views, likes, interestCode,
                            locationCode, eventName, eventVenue, eventDetails,
                            minPrice, maxPrice, startTimestamp, endTimestamp,
                            bitmapName, viewed, liked, video, when, confCode, broadcastCharge, status);
            publishProgress(eventsGetSetter);

        }
        cursor.close();
        return 0;
    }

    @Override
    protected void onProgressUpdate(EventsGetSetterWaiting... values) {
        eventsAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(Integer id) {
        listView.setAdapter(eventsAdapter);
        eventParam = null;
        new EventItemsWaiting(context).execute("");
    }
}
