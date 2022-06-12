package tech.myevents.hq;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.LinkedList;

import static tech.myevents.hq.AdsStatic.adItems;
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

class AdItems extends AsyncTask<String, Void, Void> {

    Context context;
    DBOperations dbOperations;
    SQLiteDatabase db;

    AdItems(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        //adItems = null;
        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();

        lViews = new LinkedList<String>();
        lLikes = new LinkedList<String>();
        lWhen = new LinkedList<String>();

        Cursor c = dbOperations.getAds(db);
        int size;
        switch (params[0]) {
            case "likes":
                while (c.moveToNext()) {
                    lViews.add(new EventsCalculations(context).getViews(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWS)))));
                    lLikes.add(new EventsCalculations(context).getLikes(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKES)))));
                    lWhen.add(new EventsCalculations(context).calculateTimeReceived(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN))));
                }
                size = lLikes.size();
                sLikes = lLikes.toArray(new String[size]);
                sViews = lViews.toArray(new String[size]);
                sWhen = lWhen.toArray(new String[size]);

                break;
            case "when":
                while (c.moveToNext()) {
                    lWhen.add(new EventsCalculations(context).calculateTimeReceived(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN))));
                }
                sWhen = lWhen.toArray(new String[lWhen.size()]);

                break;
            default:
                adItems = null;
                lLiked = new LinkedList<Integer>();
                lViewed = new LinkedList<Integer>();
                lVideo = new LinkedList<Integer>();

                while (c.moveToNext()) {
                    lViews.add(new EventsCalculations(context).getViews(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWS)))));
                    lLikes.add(new EventsCalculations(context).getLikes(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKES)))));
                    lViewed.add(new EventsCalculations(context).checkView(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWED))));
                    lLiked.add(new EventsCalculations(context).checkLike(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKED))));
                    lVideo.add(new EventsCalculations(context).checkVideo(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIDEO))));
                    lWhen.add(new EventsCalculations(context).calculateTimeReceived(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN))));
                }
                size = lViews.size();
                sLikes = lLikes.toArray(new String[size]);
                sViews = lViews.toArray(new String[size]);
                sViewed = lViewed.toArray(new Integer[size]);
                sLiked = lLiked.toArray(new Integer[size]);
                sVideo = lVideo.toArray(new Integer[size]);
                sWhen = lWhen.toArray(new String[size]);

                adItems = "updated";
                break;
        }
        c.close();

        return null;
    }

}