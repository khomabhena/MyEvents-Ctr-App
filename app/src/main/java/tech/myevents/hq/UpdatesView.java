package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tech.myevents.hq.UpdatesAdapter.updatesList;

public class UpdatesView extends AppCompatActivity {

    ImageView ivPoster, ivLogo;
    TextView tvName, tvVenue, tvLocation, tvDate, tvBrandName, tvAdTitle, tvWhen;
    LinearLayout llEventContent, llAdContent, llEvent, llAd;
    UpdatesGetSetter getSetter;
    SQLiteDatabase db;
    DBOperations dbOperations;
    EventsGetSetter eventsSetter;
    AdsGetSetter adsSetter;
    static List updatesMainList = new ArrayList();
    static String updatesType, updatesState;
    static int updatesPosition, updatesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        tvName = (TextView) findViewById(R.id.tvName);
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvBrandName = (TextView) findViewById(R.id.tvBrandName);
        tvAdTitle = (TextView) findViewById(R.id.tvAdTitle);
        llEvent = (LinearLayout) findViewById(R.id.llEvent);
        llAd = (LinearLayout) findViewById(R.id.llAd);
        tvWhen = (TextView) findViewById(R.id.tvWhen);
        llEventContent = (LinearLayout) findViewById(R.id.llEventContent);
        llAdContent = (LinearLayout) findViewById(R.id.llAdContent);

        dbOperations = new DBOperations(this);
        db = dbOperations.getReadableDatabase();

        if (updatesType.equals("event")) {
            llAd.setVisibility(View.GONE);
            llEvent.setVisibility(View.VISIBLE);
            if (updatesState.equals("dated")) {
                eventsSetter = (EventsGetSetter) getEventList().get(0);
                updatesMainList = getEventList();

                String imagePath = "sdcard/MyEvents/Events/MyEvents Posters/" + eventsSetter.getBitmapName() + ".JPEG";
                File imageFile = new File("sdcard/MyEvents/Events/MyEvents Posters/" + eventsSetter.getBitmapName() + ".JPEG");
                String bitmap = eventsSetter.getBitmapName() + ".JPEG";
                if (imageFile.exists()) {
                    //ivBitmapView.setImageDrawable(Drawable.createFromPath(imagePath));
                    new LoadImageEvent(this).execute(imagePath);
                } else {
                    getBitmap(bitmap, "event");
                }

                tvName.setText(eventsSetter.getEventName());
                tvVenue.setText(eventsSetter.getEventVenue());
                tvLocation.setText(new EventsCalculations(this).getLocation(eventsSetter.getLocationCode()));
                tvDate.setText(new EventsCalculations(this).getDate(eventsSetter.getStartTimestamp()));
            } else {
                getSetter = (UpdatesGetSetter) getItem(updatesPosition);
                updatesMainList = updatesList;

                String imagePath = "sdcard/MyEvents/Events/MyEvents Posters/" + getSetter.getBitmapName() + ".JPEG";
                File imageFile = new File("sdcard/MyEvents/Events/MyEvents Posters/" + getSetter.getBitmapName() + ".JPEG");
                String bitmap = getSetter.getBitmapName() + ".JPEG";
                if (imageFile.exists()) {
                    //ivBitmapView.setImageDrawable(Drawable.createFromPath(imagePath));
                    new LoadImageEvent(this).execute(imagePath);
                } else {
                    getBitmap(bitmap, "event");
                }

                tvName.setText(getSetter.getBroadcastName());
                tvVenue.setText(getSetter.getVenueTitle());
                tvLocation.setText(new EventsCalculations(this).getLocation(getSetter.getLocationCode()));
                tvDate.setText(new EventsCalculations(this).getDate(getSetter.getStartTimestamp()));
            }
        } else {
            llEvent.setVisibility(View.GONE);
            llAd.setVisibility(View.VISIBLE);
            if (updatesState.equals("dated")) {
                adsSetter = (AdsGetSetter) getAdList().get(0);
                updatesMainList = getAdList();

                String imagePath = "sdcard/MyEvents/Ads/MyEvents Posters/" + adsSetter.getBitmapName() + ".JPEG";
                File imageFile = new File("sdcard/MyEvents/Ads/MyEvents Posters/" + adsSetter.getBitmapName() + ".JPEG");
                String bitmap = adsSetter.getBitmapName() + ".JPEG";
                if (imageFile.exists()) {
                    new LoadImageAd(this).execute(imagePath);
                } else {
                    getBitmap(bitmap, "ad");
                }

                tvBrandName.setText(adsSetter.getBrandName());
                tvAdTitle.setText(adsSetter.getTitle());
                tvWhen.setText(new EventsCalculations(this).calculateTimeReceived(adsSetter.getWhen()));
            } else {
                getSetter = (UpdatesGetSetter) getItem(updatesPosition);
                updatesMainList = updatesList;

                String imagePath = "sdcard/MyEvents/Ads/MyEvents Posters/" + getSetter.getBitmapName() + ".JPEG";
                File imageFile = new File("sdcard/MyEvents/Ads/MyEvents Posters/" + getSetter.getBitmapName() + ".JPEG");
                String bitmap = getSetter.getBitmapName() + ".JPEG";
                if (imageFile.exists()) {
                    new LoadImageAd(this).execute(imagePath);
                } else {
                    getBitmap(bitmap, "ad");
                }

                tvBrandName.setText(getSetter.getBroadcastName());
                tvAdTitle.setText(getSetter.getVenueTitle());
                tvWhen.setText(new EventsCalculations(this).calculateTimeReceived(getSetter.getUpdateTime()));
            }
        }

        llEventContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatesView.this, UpdatesContent.class);
                intent.putExtra("type", updatesType);
                intent.putExtra("state", updatesState);
                intent.putExtra("position", updatesPosition);
                UpdatesView.this.startActivity(intent);
            }
        });
        llAdContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatesView.this, UpdatesContent.class);
                intent.putExtra("type", updatesType);
                intent.putExtra("state", updatesState);
                intent.putExtra("position", updatesPosition);
                UpdatesView.this.startActivity(intent);
            }
        });
    }

    List getEventList() {
        List list = new ArrayList();
        int id, eventId, views, likes;
        String interestCode, locationCode, eventName, eventVenue, eventDetails, minPrice, maxPrice, startTimestamp, endTimestamp, bitmapName;
        String viewed, liked, video, when;

        Cursor cursor = dbOperations.getUpdatesEvent(db, updatesId);
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

            EventsGetSetter eventsGetSetter =
                    new EventsGetSetter(id, eventId, views, likes, interestCode,
                            locationCode, eventName, eventVenue, eventDetails,
                            minPrice, maxPrice, startTimestamp, endTimestamp,
                            bitmapName, viewed, liked, video, when);
            list.add(eventsGetSetter);
        }

        return list;
    }

    List getAdList() {
        List list = new ArrayList();
        String interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration, video, when;
        int id, adId, views, likes;

        Cursor c = dbOperations.getUpdatesAd(db, updatesId);
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

            AdsGetSetter setter = new AdsGetSetter(id, adId, views, likes,
                    interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed, liked, duration, video, when);

            list.add(setter);
        }

        return list;
    }

    Object getItem(int position) {
        return updatesList.get(updatesPosition);
    }



    private void getBitmap(final String bitmap_name, final String type) {
        String bitmapUrl = App.AppInfo.BITMAP_URL_EVENTS + bitmap_name;
        ImageRequest imageRequest = new ImageRequest(bitmapUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if (type.equals("event")) {
                            ivPoster.setImageBitmap(response);
                        } else {
                            ivLogo.setImageBitmap(response);
                        }
                        File newDir = new File("sdcard/MyEvents");
                        if(!newDir.exists()){
                            newDir.mkdir();
                        }
                        File eventsDir = new File(newDir, "Events");
                        if (!eventsDir.exists()){
                            eventsDir.mkdir();
                        }
                        File postersDir = new File(eventsDir, "MyEvents Posters");
                        if (!postersDir.exists()){
                            postersDir.mkdir();
                        }
                        File toPath = new File(postersDir, bitmap_name);
                        try {
                            FileOutputStream outputStream = new FileOutputStream(toPath);
                            response.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //getBitmap(bitmap_name);
                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQue(imageRequest);
    }

    private class LoadImageEvent extends AsyncTask<String, Void, Void> {
        Context context;
        Drawable drawable = null;

        LoadImageEvent(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivPoster.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            drawable = Drawable.createFromPath(imagePath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPoster.setImageDrawable(drawable);
        }

    }

    private class LoadImageAd extends AsyncTask<String, Void, Void> {
        Context context;
        Drawable drawable = null;

        LoadImageAd(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivLogo.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            drawable = Drawable.createFromPath(imagePath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivLogo.setImageDrawable(drawable);
        }

    }

}
