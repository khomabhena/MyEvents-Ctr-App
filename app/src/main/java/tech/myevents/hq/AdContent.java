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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
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

import static tech.myevents.hq.AdsStatic.adItems;
import static tech.myevents.hq.AdsStatic.adsList;
import static tech.myevents.hq.AdsStatic.sWhen;

public class AdContent extends AppCompatActivity {

    ImageView ivPosterView;
    VideoView vvVideoView;
    ProgressBar pbCircular;
    TextView tvDownloadInfo, tvWhen;
    TextView tvBrandName, tvAdTitle, tvAdDesc, tvDesc1, tvDesc2, tvDesc3, tvDesc4, tvMegaBytes, tvProgress;
    LinearLayout llDetails, llDesc1, llDesc2, llDesc3, llDesc4;
    RelativeLayout rlRel;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    AdsGetSetter adsGetSetter;
    AdsGetSetterWaiting adsGetSetterWaiting;
    int position;
    Handler handler;
    String from, when, brandName, adTitle, adDesc, desc1, desc2, desc3, desc4, bitmapName, video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;

        prefs = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        editor = prefs.edit();
        handler = new Handler();

        from = getIntent().getExtras().getString("from");
        position = getIntent().getExtras().getInt("position");

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
        tvWhen = (TextView) findViewById(R.id.tvWhen);

        if (from.equals("adsWaiting")) {
            editor = prefs.edit();
            editor.putInt("adScrollPosWaiting", position);
            editor.apply();
            adsGetSetterWaiting = (AdsGetSetterWaiting) getItem(position);

            when = adsGetSetterWaiting.getWhen();
            brandName = adsGetSetterWaiting.getBrandName();
            adTitle = adsGetSetterWaiting.getTitle();
            desc1 = adsGetSetterWaiting.getDesc1();
            desc2 = adsGetSetterWaiting.getDesc2();
            desc3 = adsGetSetterWaiting.getDesc3();
            desc4 = adsGetSetterWaiting.getDesc4();
            bitmapName = adsGetSetterWaiting.getBitmapName();
            video = adsGetSetterWaiting.getVideo();
        } else {
            editor = prefs.edit();
            editor.putInt("adScrollPos", position);
            editor.apply();
            adsGetSetter = (AdsGetSetter) getItem(position);

            when = adsGetSetter.getWhen();
            brandName = adsGetSetter.getBrandName();
            adTitle = adsGetSetter.getTitle();
            desc1 = adsGetSetter.getDesc1();
            desc2 = adsGetSetter.getDesc2();
            desc3 = adsGetSetter.getDesc3();
            desc4 = adsGetSetter.getDesc4();
            bitmapName = adsGetSetter.getBitmapName();
            video = adsGetSetter.getVideo();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("By. ").append(brandName);
        tvBrandName.setText(builder);
        tvAdTitle.setText(adTitle);

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

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);
        tvAdDesc.setTypeface(typefaceBC);

        collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setTitle("Ad Details");

        startPostDelay();
        if (adItems != null) {
            long diff = getCurrentTimestamp() - prefs.getLong("lastAdWhenUpdate", 125000);
            if (diff > 120000) {
                editor = prefs.edit();
                editor.putLong("lastAdWhenUpdate", getCurrentTimestamp());
                editor.apply();
                new AdItems(this).execute("likes");
            }
        }

    }

    private long getCurrentTimestamp(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    public Object getItem(int position) {
        if (from.equals("adsWaiting")) {
            return AdsStaticWaiting.adsList.get(position);
        } else {
            return adsList.get(position);
        }
    }

    private void startPostDelay() {
        handler.post(runnableCheckData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMedia();
    }

    private void playMedia() {
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
                        new DownloadVideo().execute(App.AppInfo.VID_URL + bitmapName + ".mp4", bitmapName);
                    }
                });
                new GetMegaBytes().execute(App.AppInfo.VID_URL + bitmapName + ".mp4");
            }
        } else {
            //Load Poster
            ivPosterView.getLayoutParams().height = ivHeight;
            ivPosterView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            //ivPosterView.setImageDrawable(Drawable.createFromPath(imagePath));
            new LoadImage(this).execute(imagePath);
        }
    }

    class GetMegaBytes extends AsyncTask<String,Integer,Integer> {
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

    class DownloadVideo extends AsyncTask<String,Integer,Boolean> {

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

    class LoadImage extends AsyncTask<String, Void, Void> {
        Context context;
        Drawable drawable = null;

        LoadImage(Context context) {
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

    @Override
    protected void onPause() {
        super.onPause();
        stopPostDelay();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            //overridePendingTransition(0, R.anim.splash_fade);
        }
        return super.onOptionsItemSelected(item);
    }

    private void stopPostDelay() {
        handler.removeCallbacks(runnableCheckData);
    }

    private final Runnable runnableCheckData = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, 60000);
                checkTimeToGo();
            } catch (Exception e){
            }
        }
    };

    private void checkTimeToGo() {
        tvWhen.setText(new EventsCalculations(this).calculateTimeReceived(when));
        if (!from.equals("adsWaiting")) {
            sWhen[position] = new EventsCalculations(this).calculateTimeReceived(when);
        }
    }

}