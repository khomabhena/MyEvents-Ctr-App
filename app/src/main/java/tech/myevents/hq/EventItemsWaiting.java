package tech.myevents.hq;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.LinkedList;

import static tech.myevents.hq.EventsStaticWaiting.lCharge;
import static tech.myevents.hq.EventsStaticWaiting.lConfCode;
import static tech.myevents.hq.EventsStaticWaiting.sCharge;
import static tech.myevents.hq.EventsStaticWaiting.sConfCode;
import static tech.myevents.hq.EventsStaticWaiting.eventItems;
import static tech.myevents.hq.EventsStaticWaiting.lDate;
import static tech.myevents.hq.EventsStaticWaiting.lDay;
import static tech.myevents.hq.EventsStaticWaiting.lLiked;
import static tech.myevents.hq.EventsStaticWaiting.lLikes;
import static tech.myevents.hq.EventsStaticWaiting.lLocation;
import static tech.myevents.hq.EventsStaticWaiting.lPlace;
import static tech.myevents.hq.EventsStaticWaiting.lPublish;
import static tech.myevents.hq.EventsStaticWaiting.lVideo;
import static tech.myevents.hq.EventsStaticWaiting.lViewed;
import static tech.myevents.hq.EventsStaticWaiting.lViews;
import static tech.myevents.hq.EventsStaticWaiting.sDate;
import static tech.myevents.hq.EventsStaticWaiting.sDay;
import static tech.myevents.hq.EventsStaticWaiting.sLiked;
import static tech.myevents.hq.EventsStaticWaiting.sLikes;
import static tech.myevents.hq.EventsStaticWaiting.sLocation;
import static tech.myevents.hq.EventsStaticWaiting.sPlace;
import static tech.myevents.hq.EventsStaticWaiting.sPublish;
import static tech.myevents.hq.EventsStaticWaiting.sVideo;
import static tech.myevents.hq.EventsStaticWaiting.sViewed;
import static tech.myevents.hq.EventsStaticWaiting.sViews;

class EventItemsWaiting extends AsyncTask<String, Void, Void> {
    Context context;
    DBOperations dbOperations;
    SQLiteDatabase db;

    EventItemsWaiting(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        //adItems = null;
        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();
        lViews = new LinkedList<String>();
        lLikes = new LinkedList<String>();

        Cursor c = dbOperations.getEventsWaiting(db);
        int size;
        switch (params[0]) {
            case "likes":
                while (c.moveToNext()) {
                    lViews.add(new EventsCalculations(context).getViews(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_VIEWS)))));
                    lLikes.add(new EventsCalculations(context).getLikes(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_LIKES)))));
                }
                size = lLikes.size();
                sLikes = lLikes.toArray(new String[size]);
                sViews = lViews.toArray(new String[size]);

                break;
            default:
                eventItems = null;
                lPlace = new LinkedList<Integer>();
                lLiked = new LinkedList<Integer>();
                lViewed = new LinkedList<Integer>();
                lVideo = new LinkedList<Integer>();
                lDate = new LinkedList<Integer>();
                lDay = new LinkedList<String>();
                lLocation = new LinkedList<String>();
                lPublish = new LinkedList<Integer>();
                lConfCode = new LinkedList<String>();
                lCharge = new LinkedList<Integer>();

                while (c.moveToNext()) {
                    lPlace.add(new EventsCalculations(context).checkLocation(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_LOCATION_CODE))));
                    lLocation.add(new EventsCalculations(context).getLocation(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_LOCATION_CODE))));
                    lDay.add(new EventsCalculations(context).getDate(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_START_TIMESTAMP))));
                    lDate.add(new EventsCalculations(context).checkDate(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_START_TIMESTAMP))));
                    lViews.add(new EventsCalculations(context).getViews(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_VIEWS)))));
                    lLikes.add(new EventsCalculations(context).getLikes(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_LIKES)))));
                    lViewed.add(new EventsCalculations(context).checkView(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_VIEWED))));
                    lLiked.add(new EventsCalculations(context).checkLike(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_LIKED))));
                    lVideo.add(new EventsCalculations(context).checkVideo(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_VIDEO))));
                    lPublish.add(new EventsCalculations(context).publish(c.getString(c.getColumnIndex(DBContract.Event.COLUMN_EVENT_STATUS))));
                    String charge = c.getString(c.getColumnIndex(DBContract.Event.BROADCAST_CHARGE));
                    String confCode = c.getString(c.getColumnIndex(DBContract.Event.CONF_CODE));
                    lConfCode.add(new EventsCalculations(context).confCode(confCode, charge));
                    lCharge.add(new EventsCalculations(context).charge(charge));
                }
                size = lLikes.size();
                sLikes = lLikes.toArray(new String[size]);
                sViews = lViews.toArray(new String[size]);
                sViewed = lViewed.toArray(new Integer[size]);
                sLiked = lLiked.toArray(new Integer[size]);
                sVideo = lVideo.toArray(new Integer[size]);
                sPlace = lPlace.toArray(new Integer[size]);
                sDate = lDate.toArray(new Integer[size]);
                sDay = lDay.toArray(new String[size]);
                sLocation = lLocation.toArray(new String[size]);
                sPublish = lPublish.toArray(new Integer[size]);
                sConfCode = lConfCode.toArray(new String[size]);
                sCharge = lCharge.toArray(new Integer[size]);

                eventItems = "updated";
                break;
        }
        c.close();

        return null;
    }

}
