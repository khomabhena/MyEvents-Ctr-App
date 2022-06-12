package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import static tech.myevents.hq.UpdatesView.updatesMainList;

public class UpdatesContent extends AppCompatActivity {

    LinearLayout llAd, llEvent;
    ImageView ivPosterView;
    VideoView vvVideoView;
    ProgressBar pbCircular;
    TextView tvDownloadInfo, tvUpdateTimeAd, tvUpdateTimeEvent;
    TextView tvBrandName, tvAdTitle, tvAdDesc, tvDesc1, tvDesc2, tvDesc3, tvDesc4, tvMegaBytes, tvProgress;
    LinearLayout llDetails, llDesc1, llDesc2, llDesc3, llDesc4;
    RelativeLayout rlRel;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    AdsGetSetter adsGetSetter;
    UpdatesGetSetter updatesGetSetter;
    String updateTime, brandName, adTitle, adDesc, desc1, desc2, desc3, desc4, bitmapName, video;

    TextView tvEvName, tvDate, tvToGo, tvDet, tvPrice, tvVenue, tvLocation, tvEventDate, tvTimeToGo, tvDetails;
    String minPrice, maxPrice, startTimestamp, venue, details, city, location, endTimestamp, locationCode;
    int position;
    EventsGetSetter eventsGetSetter;
    String posterPath = "sdcard/MyEvents/Events/MyEvents Posters";
    String vidUrl = App.AppInfo.VID_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;

        llAd = (LinearLayout) findViewById(R.id.llAd);
        llEvent = (LinearLayout) findViewById(R.id.llEvent);
        ivPosterView = (ImageView) findViewById(R.id.ivPosterView);
        vvVideoView = (VideoView) findViewById(R.id.vvVideoView);
        tvBrandName = (TextView) findViewById(R.id.tvBrandName);
        tvAdTitle = (TextView) findViewById(R.id.tvAdTitle);
        tvAdDesc = (TextView) findViewById(R.id.tvAdDesc);
        tvDesc1 = (TextView) findViewById(R.id.tvDesc1);
        tvDesc2 = (TextView) findViewById(R.id.tvDesc2);
        tvDesc3 = (TextView) findViewById(R.id.tvDesc3);
        tvDesc4 = (TextView) findViewById(R.id.tvDesc4);
        llDetails = (LinearLayout) findViewById(R.id.llDetails);
        llDesc1 = (LinearLayout) findViewById(R.id.llDesc1);
        llDesc2 = (LinearLayout) findViewById(R.id.llDesc2);
        llDesc3 = (LinearLayout) findViewById(R.id.llDesc3);
        llDesc4 = (LinearLayout) findViewById(R.id.llDesc4);
        rlRel = (RelativeLayout) findViewById(R.id.rlRel);
        tvMegaBytes = (TextView) findViewById(R.id.tvMegaBytes);
        pbCircular = (ProgressBar) findViewById(R.id.pbCircular);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        tvDownloadInfo = (TextView) findViewById(R.id.tvDownloadInfo);
        tvUpdateTimeAd = (TextView) findViewById(R.id.tvUpdateTimeAd);
        tvUpdateTimeEvent = (TextView) findViewById(R.id.tvUpdateTimeEvent);

        tvEvName = (TextView) findViewById(R.id.tvEvName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvToGo = (TextView) findViewById(R.id.tvToGo);
        tvDet = (TextView) findViewById(R.id.tvDet);
        ivPosterView = (ImageView) findViewById(R.id.ivPosterView);
        vvVideoView = (VideoView) findViewById(R.id.vvVideoView);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvTimeToGo = (TextView) findViewById(R.id.tvTimeToGo);
        tvDetails = (TextView) findViewById(R.id.tvDetails);

        String type = getIntent().getExtras().getString("type");
        String state = getIntent().getExtras().getString("state");
        int position = getIntent().getExtras().getInt("position");
        assert type != null;
        assert state != null;
        if (type.equals("event")) {
            llAd.setVisibility(View.GONE);
            llEvent.setVisibility(View.VISIBLE);

            if (state.equals("dated")) {
                eventsGetSetter = (EventsGetSetter) updatesMainList.get(0);
                loadDatedEvent(eventsGetSetter);
            } else {
                updatesGetSetter = (UpdatesGetSetter) updatesMainList.get(position);
                loadUpdatedEvent(updatesGetSetter);
            }
            String fontPathBC = "fonts/black_cry.TTF";
            Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

            collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
            collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
            collapsingToolbarLayout.setTitle("Event Details");

            playEventMedia();
            setEventValues();
            tvTimeToGo.setText(new EventsCalculations(this).calculateTimeToGo(startTimestamp));
            tvUpdateTimeEvent.setText(new EventsCalculations(this).calculateTimeReceived(updateTime));

        } else {
            llEvent.setVisibility(View.GONE);
            llAd.setVisibility(View.VISIBLE);

            if (state.equals("dated")) {
                adsGetSetter = (AdsGetSetter) updatesMainList.get(0);
                loadDatedAd(adsGetSetter);
            } else {
                updatesGetSetter = (UpdatesGetSetter) updatesMainList.get(position);
                loadUpdatedAd(updatesGetSetter);
            }
            String fontPathBC = "fonts/black_cry.TTF";
            Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);
            tvAdDesc.setTypeface(typefaceBC);

            collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
            collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
            collapsingToolbarLayout.setTitle("Ad Details");

            StringBuilder builder = new StringBuilder();
            builder.append("By. ").append(brandName);
            tvBrandName.setText(builder);
            tvAdTitle.setText(adTitle);

            setAdValues();
            playAdMedia();
            tvUpdateTimeAd.setText(new EventsCalculations(this).calculateTimeReceived(updateTime));
        }
    }

    private void setAdValues() {
        if (!desc1.equals("") || !desc2.equals("") ||
                !desc3.equals("") || !desc4.equals("")) {

            llDetails.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            llDetails.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (!desc1.equals("")) {
                llDesc1.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                llDesc1.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvDesc1.setText(desc1);
            }
            if (!desc2.equals("")) {
                llDesc2.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                llDesc2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvDesc2.setText(desc2);
            }

            if (!desc3.equals("")) {
                llDesc3.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                llDesc3.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvDesc3.setText(desc3);
            }
            if (!desc4.equals("")) {
                llDesc4.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                llDesc4.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvDesc4.setText(desc4);
            }
        }
    }

    private void playAdMedia() {
        File newDir = new File("sdcard/MyEvents");
        if(!newDir.exists()){
            newDir.mkdir();
        }
        File eventsDir = new File(newDir,"Ads");
        if (!eventsDir.exists()){
            eventsDir.mkdir();
        }
        File videosDir = new File(eventsDir,"MyEvents Videos");
        if (!videosDir.exists()){
            videosDir.mkdir();
        }

        File videoFile = new File(videosDir, bitmapName + ".mp4");
        File imageDir = new File("sdcard/MyEvents/Ads/MyEvents Posters");
        File imageFile = new File(imageDir, bitmapName + ".JPEG");
        String videoPath = "sdcard/MyEvents/Ads/MyEvents Videos/" + bitmapName + ".mp4";
        String imagePath = "sdcard/MyEvents/Ads/MyEvents Posters/" + bitmapName + ".JPEG";

        Resources resources = getResources();
        int relativeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250,resources.getDisplayMetrics());
        int ivHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,380,resources.getDisplayMetrics());

        if (video.equals("yes")) {
            //Check file availability and download if unavailable
            if (videoFile.exists()) {
                vvVideoView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.setVideoPath(videoPath);
                vvVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0,0);
                        mp.setLooping(true);
                    }
                });
                vvVideoView.start();
                vvVideoView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //startVideoView();
                        return true;
                    }
                });
            } else {
                rlRel.getLayoutParams().height = relativeHeight;
                rlRel.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                rlRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadVideoAd().execute(App.AppInfo.VID_URL + bitmapName + ".mp4", bitmapName);
                    }
                });
                new GetMegaBytesAd().execute(App.AppInfo.VID_URL + bitmapName + ".mp4");
            }
        } else {
            //Load Poster
            ivPosterView.getLayoutParams().height = ivHeight;
            ivPosterView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            //ivPosterView.setImageDrawable(Drawable.createFromPath(imagePath));
            new LoadImageAd(this).execute(imagePath);
        }
    }

    private void playEventMedia() {
        File newDir = new File("sdcard/MyEvents");
        if(!newDir.exists()){
            newDir.mkdir();
        }
        File eventsDir = new File(newDir,"Events");
        if (!eventsDir.exists()){
            eventsDir.mkdir();
        }
        File videosDir = new File(eventsDir,"MyEvents Videos");
        if (!videosDir.exists()){
            videosDir.mkdir();
        }

        File videoFile = new File(videosDir, bitmapName + ".mp4");
        File imageDir = new File("sdcard/MyEvents/Events/MyEvents Posters");
        File imageFile = new File(imageDir, bitmapName + ".JPEG");
        String videoPath = "sdcard/MyEvents/Events/MyEvents Videos/" + bitmapName + ".mp4";
        String imagePath = "sdcard/MyEvents/Events/MyEvents Posters/" + bitmapName + ".JPEG";

        Resources resources = getResources();
        int relativeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250,resources.getDisplayMetrics());
        int ivHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,380,resources.getDisplayMetrics());

        if (video.equals("yes")) {
            //Check file availability and download if unavailable
            if (videoFile.exists()) {
                vvVideoView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.setVideoPath(videoPath);
                vvVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0,0);
                        mp.setLooping(true);
                    }
                });
                vvVideoView.start();
                vvVideoView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //startVideoView();
                        return true;
                    }
                });
            } else {
                rlRel.getLayoutParams().height = relativeHeight;
                rlRel.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                rlRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadVideoEvent().execute(vidUrl + bitmapName + ".mp4", bitmapName);
                    }
                });
                new GetMegaBytesEvent().execute(vidUrl + bitmapName + ".mp4");
            }
        } else {
            //Load Poster
            ivPosterView.getLayoutParams().height = ivHeight;
            ivPosterView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            //ivPosterView.setImageDrawable(Drawable.createFromPath(imagePath));
            new LoadImageEvent(this).execute(imagePath);
        }
    }

    private void setEventValues() {
        StringBuilder builderPrice = new StringBuilder();
        builderPrice.append("$").append(minPrice).append(".00 - ").append("$").append(maxPrice).append(".00");
        tvVenue.setText(venue);
        tvLocation.setText(new EventsCalculations(this).getLocation(locationCode));
        tvEventDate.setText(new EventsCalculations(this).getDate(Long.parseLong(startTimestamp)));
        tvPrice.setText(builderPrice);
        tvDetails.setText(details);
    }

    private void loadDatedEvent(EventsGetSetter setter) {
        bitmapName = setter.getBitmapName();
        locationCode = setter.getLocationCode();
        startTimestamp = setter.getStartTimestamp();
        endTimestamp = setter.getEndTimestamp();
        minPrice = setter.getMinPrice();
        maxPrice = setter.getMaxPrice();
        venue = setter.getEventVenue();
        details = setter.getEventDetails();
        video = setter.getVideo();
        updateTime = setter.getWhen();
    }

    private void loadUpdatedEvent(UpdatesGetSetter setter) {
        bitmapName = setter.getBitmapName();
        locationCode = setter.getLocationCode();
        startTimestamp = setter.getStartTimestamp();
        endTimestamp = setter.getEndTimestamp();
        minPrice = setter.getMinPrice();
        maxPrice = setter.getMaxPrice();
        venue = setter.getVenueTitle();
        details = setter.getEventDetails();
        video = setter.getVideo();
        updateTime = setter.getUpdateTime();
    }

    private void loadDatedAd(AdsGetSetter setter) {
        updateTime = setter.getWhen();
        brandName = setter.getBrandName();
        adTitle = setter.getTitle();
        desc1 = setter.getDesc1();
        desc2 = setter.getDesc2();
        desc3 = setter.getDesc3();
        desc4 = setter.getDesc4();
        bitmapName = setter.getBitmapName();
        video = setter.getVideo();
    }

    private void loadUpdatedAd(UpdatesGetSetter setter) {
        updateTime = setter.getUpdateTime();
        brandName = setter.getBroadcastName();
        adTitle = setter.getVenueTitle();
        desc1 = setter.getDesc1();
        desc2 = setter.getDesc2();
        desc3 = setter.getDesc3();
        desc4 = setter.getDesc4();
        bitmapName = setter.getBitmapName();
        video = setter.getVideo();
    }



    private class GetMegaBytesEvent extends AsyncTask<String,Integer,Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String path = params[0];
            int file_length = 0;
            try {
                URL url = new URL(path);
                URLConnection urlCon = url.openConnection();
                file_length = urlCon.getContentLength();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            return file_length;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result < 3) {
                tvMegaBytes.setText("file size unknown");
            } else {
                int x = result / (1024 * 1024);
                int y = (result % (1024 * 1024)) / 10000;
                StringBuilder builder = new StringBuilder();
                builder.append(x).append(".").append(y).append("MB");
                tvMegaBytes.setText(builder);
            }
        }

    }

    private class DownloadVideoEvent extends AsyncTask<String,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            tvDownloadInfo.setText("Downloading Event Video ...");
            rlRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Download Has Started", Toast.LENGTH_SHORT).show();
                }
            });
            pbCircular.setProgress(1);
            tvProgress.setText("0.3%");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String path = params[0];
            String bitName = params[1];
            int file_length;
            try {
                URL url = new URL(path);
                URLConnection urlCon = url.openConnection();

                file_length = urlCon.getContentLength();
                File inputFile = new File("sdcard/MyEvents/Events/MyEvents Videos/" + bitName + ".mp4");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                byte[] oneKBData = new byte[1024];
                int total = 0;
                int count;

                OutputStream outputStream = new FileOutputStream(inputFile);
                while ((count = inputStream.read(oneKBData)) != -1) {
                    total += count;
                    outputStream.write(oneKBData, 0, count);
                    int progress = (int) total * 100 / file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();

                return true;
            } catch (IOException e) {
                //e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            StringBuilder builder = new StringBuilder();
            builder.append(values[0]).append("%");
            pbCircular.setProgress(values[0]);
            tvProgress.setText(builder);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                rlRel.getLayoutParams().height = 0;
                rlRel.getLayoutParams().width = 0;
                String videoPath = "sdcard/MyEvents/Events/MyEvents Videos/" + bitmapName + ".mp4";
                vvVideoView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.setVideoPath(videoPath);
                vvVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0,0);
                        mp.setLooping(true);
                    }
                });
                vvVideoView.start();
                vvVideoView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //startVideoView();
                        return true;
                    }
                });
            }
        }

    }

    private class LoadImageEvent extends AsyncTask<String, Void, Void> {
        Context context;
        Drawable drawable = null;

        LoadImageEvent(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivPosterView.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            drawable = Drawable.createFromPath(imagePath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPosterView.setImageDrawable(drawable);
        }

    }

    private class GetMegaBytesAd extends AsyncTask<String,Integer,Integer> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(String... params) {
            String path = params[0];
            int file_length = 0;
            try {
                URL url = new URL(path);
                URLConnection urlCon = url.openConnection();

                file_length = urlCon.getContentLength();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            return file_length;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result < 3) {
                tvMegaBytes.setText("file size unknown");
            } else {
                int x = result / (1024 * 1024);
                int y = (result % (1024 * 1024)) / 10000;
                StringBuilder builder = new StringBuilder();
                builder.append(x).append(".").append(y).append("MB");
                tvMegaBytes.setText(builder);
            }
        }

    }

    private class DownloadVideoAd extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            tvDownloadInfo.setText("Downloading Ad Video ...");
            rlRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Download Has Started", Toast.LENGTH_SHORT).show();
                }
            });
            pbCircular.setProgress(1);
            tvProgress.setText("0.3%");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String path = params[0];
            String bitName = params[1];
            int file_length;
            try {
                URL url = new URL(path);
                URLConnection urlCon = url.openConnection();

                file_length = urlCon.getContentLength();

                File inputFile = new File("sdcard/MyEvents/Ads/MyEvents Videos/" + bitName + ".mp4");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                byte[] oneKBData = new byte[1024];

                int total = 0;
                int count;

                OutputStream outputStream = new FileOutputStream(inputFile);
                while ((count = inputStream.read(oneKBData)) != -1) {
                    total += count;
                    outputStream.write(oneKBData, 0, count);
                    int progress = (int) total * 100 / file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();

                return true;
            } catch (IOException e) {
                //e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            StringBuilder builder = new StringBuilder();
            builder.append(values[0]).append("%");
            pbCircular.setProgress(values[0]);
            tvProgress.setText(builder);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                rlRel.getLayoutParams().height = 0;
                rlRel.getLayoutParams().width = 0;

                String videoPath = "sdcard/MyEvents/Ads/MyEvents Videos/" + bitmapName + ".mp4";
                vvVideoView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                vvVideoView.setVideoPath(videoPath);
                vvVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0,0);
                        mp.setLooping(true);
                    }
                });
                vvVideoView.start();
                vvVideoView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //startVideoView();
                        return true;
                    }
                });
            }
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
            ivPosterView.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            drawable = Drawable.createFromPath(imagePath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPosterView.setImageDrawable(drawable);
        }

    }

}
