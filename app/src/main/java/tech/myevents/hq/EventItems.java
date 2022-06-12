package tech.myevents.hq;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.LinkedList;

import static tech.myevents.hq.EventsStatic.eventItems;
import static tech.myevents.hq.EventsStatic.lDate;
import static tech.myevents.hq.EventsStatic.lDay;
import static tech.myevents.hq.EventsStatic.lLiked;
import static tech.myevents.hq.EventsStatic.lLikes;
import static tech.myevents.hq.EventsStatic.lLocation;
import static tech.myevents.hq.EventsStatic.lPlace;
import static tech.myevents.hq.EventsStatic.lVideo;
import static tech.myevents.hq.EventsStatic.lViewed;
import static tech.myevents.hq.EventsStatic.lViews;
import static tech.myevents.hq.EventsStatic.sDate;
import static tech.myevents.hq.EventsStatic.sDay;
import static tech.myevents.hq.EventsStatic.sLiked;
import static tech.myevents.hq.EventsStatic.sLikes;
import static tech.myevents.hq.EventsStatic.sLocation;
import static tech.myevents.hq.EventsStatic.sPlace;
import static tech.myevents.hq.EventsStatic.sVideo;
import static tech.myevents.hq.EventsStatic.sViewed;
import static tech.myevents.hq.EventsStatic.sViews;


class EventItems extends AsyncTask<String, Void, Void> {
    Context context;
    DBOperations dbOperations;
    SQLiteDatabase db;

    EventItems(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        //adItems = null;
        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();
        lViews = new LinkedList<String>();
        lLikes = new LinkedList<String>();

        Cursor c = dbOperations.getEvents(db);
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

                eventItems = "updated";
                break;
        }
        c.close();

        return null;
    }

}