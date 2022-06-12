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

import static tech.myevents.hq.EventsStatic.eventItems;
import static tech.myevents.hq.EventsStatic.eventsList;
import static tech.myevents.hq.NowplayingStatic.npList;

public class EventContent extends AppCompatActivity {

    TextView tvEvName, tvDate, tvToGo, tvDet, tvPrice, tvVenue, tvLocation, tvEventDate, tvTimeToGo, tvDetails, tvMegaBytes, tvProgress;
    TextView tvDownloadInfo, tvWhen;
    String minPrice, maxPrice, startTimestamp, venue, details, city, location, endTimestamp, bitmapName, locationCode, video, when;
    RelativeLayout rlRel;
    ImageView ivPosterView;
    VideoView vvVideoView;
    ProgressBar pbCircular;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Handler handler;
    int position;
    String from;
    EventsGetSetter eventsGetSetter;
    EventsGetSetterWaiting eventsGetSetterWaiting;
    NowPlayingGetSetter playingGetSetter;
    String posterPath = "sdcard/MyEvents/Events/MyEvents Posters";
    String vidUrl = App.AppInfo.VID_URL;

    public EventContent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;

        prefs = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        editor = prefs.edit();
        handler = new Handler();

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
        rlRel = (RelativeLayout) findViewById(R.id.rlRel);
        tvMegaBytes = (TextView) findViewById(R.id.tvMegaBytes);
        pbCircular = (ProgressBar) findViewById(R.id.pbCircular);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        tvDownloadInfo = (TextView) findViewById(R.id.tvDownloadInfo);
        tvWhen = (TextView) findViewById(R.id.tvWhen);

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

        collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setTitle("Event Details");

        tvEvName.setTypeface(typefaceBC);
        tvDate.setTypeface(typefaceBC);
        tvToGo.setTypeface(typefaceBC);
        tvDet.setTypeface(typefaceBC);

        position = getIntent().getExtras().getInt("position");
        from = getIntent().getExtras().getString("from");


        assert from != null;
        if (from.equals("eventsWaiting")) {
            editor = prefs.edit();
            editor.putInt("eventScrollPosWaiting", position);
            editor.apply();
            eventsGetSetterWaiting = (EventsGetSetterWaiting) getItem(position);

            bitmapName = eventsGetSetterWaiting.getBitmapName();
            locationCode = eventsGetSetterWaiting.getLocationCode();
            startTimestamp = eventsGetSetterWaiting.getStartTimestamp();
            endTimestamp = eventsGetSetterWaiting.getEndTimestamp();
            minPrice = eventsGetSetterWaiting.getMinPrice();
            maxPrice = eventsGetSetterWaiting.getMaxPrice();
            venue = eventsGetSetterWaiting.getEventVenue();
            details = eventsGetSetterWaiting.getEventDetails().replace("nn", "\n\n");
            video = eventsGetSetterWaiting.getVideo();
            when = eventsGetSetterWaiting.getWhen();
        } else if (from.equals("nowPlaying")) {
            editor = prefs.edit();
            editor.putInt("npScrollPos", position);
            editor.apply();
            playingGetSetter = (NowPlayingGetSetter) getItem(position);

            bitmapName = playingGetSetter.getBitmapName();
            locationCode = playingGetSetter.getLocationCode();
            startTimestamp = playingGetSetter.getStartTimestamp();
            endTimestamp = playingGetSetter.getEndTimestamp();
            minPrice = playingGetSetter.getMinPrice();
            maxPrice = playingGetSetter.getMaxPrice();
            venue = playingGetSetter.getEventVenue();
            details = playingGetSetter.getEventDetails().replace("nn", "\n\n");
            video = playingGetSetter.getVideo();
            when = playingGetSetter.getWhen();
        }
        else {
            editor = prefs.edit();
            editor.putInt("eventScrollPos", position);
            editor.apply();
            eventsGetSetter = (EventsGetSetter) getItem(position);

            bitmapName = eventsGetSetter.getBitmapName();
            locationCode = eventsGetSetter.getLocationCode();
            startTimestamp = eventsGetSetter.getStartTimestamp();
            endTimestamp = eventsGetSetter.getEndTimestamp();
            minPrice = eventsGetSetter.getMinPrice();
            maxPrice = eventsGetSetter.getMaxPrice();
            venue = eventsGetSetter.getEventVenue();
            details = eventsGetSetter.getEventDetails().replace("nn", "\n\n");
            video = eventsGetSetter.getVideo();
            when = eventsGetSetter.getWhen();
        }

        playMedia();
        setValues();
        startPostDelay();
        if (eventItems != null) {
            long diff = getCurrentTimestamp() - prefs.getLong("lastEventWhenUpdate", 125000);
            if (diff > 120000) {
                editor = prefs.edit();
                editor.putLong("lastEventWhenUpdate", getCurrentTimestamp());
                editor.apply();
                new AdItems(this).execute("likes");
            }
        }
    }

    Object getItem(int position) {
        switch (from) {
            case "events":
                return eventsList.get(position);
            case "nowPlaying":
                return npList.get(position);
            case "eventsWaiting":
                return EventsStaticWaiting.eventsList.get(position);
            default:
                return eventsList.get(position);
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

    private void startPostDelay() {
        handler.post(runnableCheckData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMedia();
        setValues();
    }

    private void setValues() {
        StringBuilder builderPrice = new StringBuilder();
        builderPrice.append("$").append(minPrice).append(".00 - ").append("$").append(maxPrice).append(".00");
        tvVenue.setText(venue);
        tvLocation.setText(new EventsCalculations(this).getLocation(locationCode));
        tvEventDate.setText(new EventsCalculations(this).getDate(Long.parseLong(startTimestamp)));
        tvPrice.setText(builderPrice);
        tvDetails.setText(details);
    }



    private void playMedia() {
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
                        new DownloadVideo().execute(vidUrl + bitmapName + ".mp4", bitmapName);
                    }
                });
                new GetMegaBytes().execute(vidUrl + bitmapName + ".mp4");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            //overridePendingTransition(0, R.anim.splash_fade);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPostDelay();
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
        if (from.equals("nowPlaying")) {
            tvTimeToGo.setText("Now Playing");
        } else {
            tvTimeToGo.setText(new EventsCalculations(this).calculateTimeToGo(startTimestamp));
        }
        tvWhen.setText(new EventsCalculations(this).calculateTimeReceived(when));
    }

}
