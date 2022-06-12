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

import static tech.myevents.hq.AdsStatic.adParam;
import static tech.myevents.hq.AdsStatic.adsAdapter;
import static tech.myevents.hq.AdsStatic.adsList;

class AdsBackTask extends AsyncTask<Void, AdsGetSetter, Integer> {
    Context context;
    Activity activity;
    private SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;
    private LinearLayout llAdEmpty;
    private ListView listView;

    AdsBackTask(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        adParam = "wait";
        String interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration, video, when;
        int id, adId, views, likes;
        dbOperations = new DBOperations(context);
        db = dbOperations.getReadableDatabase();
        adsList = new ArrayList();
        adsAdapter = new AdsAdapter(context, R.layout.ads_layout);

        prefsList = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        listView = (ListView) activity.findViewById(R.id.lvAds);
        llAdEmpty = (LinearLayout) activity.findViewById(R.id.llAdEmpty);

        Cursor c = dbOperations.getAds(db);
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
            when = c.getString(c.getColumnIndex(DBContract.Ad.COLUMN_WHEN));

            AdsGetSetter adsGetSetter = new AdsGetSetter(id, adId, views, likes,
                    interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration, video, when);

            publishProgress(adsGetSetter);
        }
        int x = c.getCount();
        c.close();

        return x;
    }

    @Override
    protected void onProgressUpdate(AdsGetSetter... values) {
        adsAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(Integer id) {
        if (id != 0){
            listView.setAdapter(adsAdapter);
        }
        adParam = null;
        new AdItems(context).execute("");
    }

}
