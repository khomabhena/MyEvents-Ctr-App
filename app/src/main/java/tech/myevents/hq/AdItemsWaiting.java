package tech.myevents.hq;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.LinkedList;

import static tech.myevents.hq.AdsStaticWaiting.lCharge;
import static tech.myevents.hq.AdsStaticWaiting.lConfCode;
import static tech.myevents.hq.AdsStaticWaiting.sCharge;
import static tech.myevents.hq.AdsStaticWaiting.sConfCode;
import static tech.myevents.hq.AdsStaticWaiting.adItems;
import static tech.myevents.hq.AdsStaticWaiting.lPublish;
import static tech.myevents.hq.AdsStaticWaiting.sPublish;
import static tech.myevents.hq.AdsStaticWaiting.lLiked;
import static tech.myevents.hq.AdsStaticWaiting.lLikes;
import static tech.myevents.hq.AdsStaticWaiting.lVideo;
import static tech.myevents.hq.AdsStaticWaiting.lViewed;
import static tech.myevents.hq.AdsStaticWaiting.lViews;
import static tech.myevents.hq.AdsStaticWaiting.lWhen;
import static tech.myevents.hq.AdsStaticWaiting.sLiked;
import static tech.myevents.hq.AdsStaticWaiting.sLikes;
import static tech.myevents.hq.AdsStaticWaiting.sVideo;
import static tech.myevents.hq.AdsStaticWaiting.sViewed;
import static tech.myevents.hq.AdsStaticWaiting.sViews;
import static tech.myevents.hq.AdsStaticWaiting.sWhen;

class AdItemsWaiting extends AsyncTask<String, Void, Void> {

    Context context;
    DBOperations dbOperations;
    SQLiteDatabase db;

    AdItemsWaiting(Context context) {
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

        Cursor c = dbOperations.getAdsWaiting(db);
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
                lPublish = new LinkedList<Integer>();
                lConfCode = new LinkedList<String>();
                lCharge = new LinkedList<Integer>();

                while (c.moveToNext()) {
                    lViews.add(new EventsCalculations(context).getViews(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWS)))));
                    lLikes.add(new EventsCalculations(context).getLikes(Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKES)))));
                    lViewed.add(new EventsCalculations(context).checkView(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWED))));
                    lLiked.add(new EventsCalculations(context).checkLike(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKED))));
                    lVideo.add(new EventsCalculations(context).checkVideo(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIDEO))));
                    lWhen.add(new EventsCalculations(context).calculateTimeReceived(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN))));
                    lPublish.add(new EventsCalculations(context).publish(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_AD_STATUS))));
                    String charge = c.getString(c.getColumnIndex(DBContract.Ad.BROADCAST_CHARGE));
                    String confCode = c.getString(c.getColumnIndex(DBContract.Ad.CONF_CODE));
                    lConfCode.add(new EventsCalculations(context).confCode(confCode, charge));
                    lCharge.add(new EventsCalculations(context).charge(charge));
                }
                size = lViews.size();
                sLikes = lLikes.toArray(new String[size]);
                sViews = lViews.toArray(new String[size]);
                sViewed = lViewed.toArray(new Integer[size]);
                sLiked = lLiked.toArray(new Integer[size]);
                sVideo = lVideo.toArray(new Integer[size]);
                sWhen = lWhen.toArray(new String[size]);
                sPublish = lPublish.toArray(new Integer[size]);
                sConfCode = lConfCode.toArray(new String[size]);
                sCharge = lCharge.toArray(new Integer[size]);

                adItems = "updated";
                break;
        }


        return null;
    }
}
