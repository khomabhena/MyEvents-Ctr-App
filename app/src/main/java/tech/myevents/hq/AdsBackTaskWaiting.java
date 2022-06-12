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

import static tech.myevents.hq.AdsStaticWaiting.adParam;
import static tech.myevents.hq.AdsStaticWaiting.adsAdapter;
import static tech.myevents.hq.AdsStaticWaiting.adsList;

class AdsBackTaskWaiting extends AsyncTask<Void, AdsGetSetterWaiting, Integer> {

    Context context;
    Activity activity;
    private SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;
    private LinearLayout llAdEmpty;
    private ListView listView;

    AdsBackTaskWaiting(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        adParam = "wait";
        String interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration, video, when, confCode, broadcastCharge, status;
        int id, adId, views, likes;

        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();

        adsList = new ArrayList();
        adsAdapter = new AdsAdapterWaiting(context, R.layout.waiting_ads_layout);
        prefsList = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        listView = (ListView) activity.findViewById(R.id.lvAdsWaiting);
        llAdEmpty = (LinearLayout) activity.findViewById(R.id.llAdEmptyWaiting);

        Cursor c = dbOperations.getAdsWaiting(db);
        while (c.moveToNext()) {
            id = Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_ID)));
            adId = Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_AD_ID)));
            views = Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWS)));
            likes = Integer.valueOf(c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKES)));

            interestCode = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_INTEREST_CODE));
            brandName = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_BRAND_NAME));
            title = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_TITLE));
            desc1 = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_DESC_1));
            desc2 = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_DESC_2));
            desc3 = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_DESC_3));
            desc4 = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_DESC_4));
            bitmapName = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_BITMAP_NAME));
            viewed = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIEWED));
            liked = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_LIKED));
            duration = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_DURATION));
            video = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_VIDEO));
            confCode = c.getString(c.getColumnIndex(DBContract.Ad.CONF_CODE));
            broadcastCharge = c.getString(c.getColumnIndex(DBContract.Ad.BROADCAST_CHARGE));
            status = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_AD_STATUS));
            when = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN));

            AdsGetSetterWaiting adsGetSetter = new AdsGetSetterWaiting(id, adId, views, likes,
                    interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration,
                    video, when, confCode, broadcastCharge, status);

            publishProgress(adsGetSetter);
        }
        c.close();
        return 0;
    }

    @Override
    protected void onProgressUpdate(AdsGetSetterWaiting... values) {
        adsAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(Integer id) {
        adParam = null;
        listView.setAdapter(adsAdapter);
        new AdItemsWaiting(context).execute("");
    }

}
