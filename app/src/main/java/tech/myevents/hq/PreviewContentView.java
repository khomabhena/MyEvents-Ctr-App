package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static tech.myevents.hq.MainActivity.currentPage;
import static tech.myevents.hq.MakeEvent.eventBitmap;

public class PreviewContentView extends AppCompatActivity {

    TextView tvEvName, tvDate, tvToGo, tvDet, tvPrice, tvVenue, tvLocation, tvEventDate, tvTimeToGo, tvDetails;
    String minPrice, maxPrice, startTimestamp, venue, details, city, location;
    ImageView ivPosterView;
    VideoView vvVideoView;
    SharedPreferences prefs;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editorY;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 6;
        setContentView(R.layout.activity_preview_content_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;

        prefs = getSharedPreferences(App.AppInfo.PREF_BROADCAST_VALUES, Context.MODE_PRIVATE);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

       /* tvEvName = (TextView) findViewById(R.id.tvEvName);
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
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        venue = prefs.getString("eventVenue","");
        city = prefs.getString("selectedCity","");
        location = prefs.getString("selectedLocation","");
        startTimestamp = prefs.getString("startTimestamp","");
        minPrice = prefs.getString("minPrice","");
        maxPrice = prefs.getString("maxPrice","");
        details = prefs.getString("details","");

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

        collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setTitle("Event Details");

        tvEvName.setTypeface(typefaceBC);
        tvDate.setTypeface(typefaceBC);
        tvToGo.setTypeface(typefaceBC);
        tvDet.setTypeface(typefaceBC);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            currentPage = 1;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        currentPage = 1;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onPostResume();
        //playMedia();
        //enterValues();
    }

    private void enterValues() {
        StringBuilder builderVenue = new StringBuilder();
        StringBuilder builderPrice = new StringBuilder();
        StringBuilder builderLocation = new StringBuilder();

        builderVenue.append(venue);
        builderLocation.append(location).append(", ").append(city);
        builderPrice.append("$").append(minPrice).append(".00 - ").append("$").append(maxPrice).append(".00");

        if (builderPrice.length() > 11) {
            tvPrice.setText(builderPrice);
        }
        if (builderVenue.length() > 3) {
            tvVenue.setText(builderVenue);
        }
        if (builderLocation.length() > 3) {
            tvLocation.setText(builderLocation);
        }
        if (startTimestamp.length() > 3) {
            tvEventDate.setText(getDate(Long.parseLong(startTimestamp)));
            tvTimeToGo.setText(calculateTimeToGo());
        }
        if (!details.equals("")) {
            tvDetails.setText(details);
        }
    }

    private String calculateTimeToGo() {
        long currentTimestamp = getCurrentTimestamp();
        long eventStartTimestamp = Long.parseLong(startTimestamp);
        long sec = 1000;
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = eventStartTimestamp - currentTimestamp;
        long days = timeBetween / day;
        long daysMod = timeBetween % day;
        long hours = daysMod / hour;
        long hoursMod = daysMod % hour;
        long minutes = hoursMod / min;

        StringBuilder builder = new StringBuilder();
        builder.append(getTheValue(days)).append(" Days ").append(getTheValue(hours)).append(" hrs ").append(getTheValue(minutes)).append(" mins");

        return String.valueOf(builder);
    }

    private String getDate(Long startTimestamp){
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.US);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTimestamp);

        String wkDay = dateFormat.format(calendar.getTime());
        String monthName = monthFormat.format(calendar.getTime());

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR)%100;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        stringBuilder.append(wkDay).append(" ").append(getTheValue(day))
                .append(", ").append(getTheValue(hour)).append(":").append(getTheValue(minute)).append("hrs");

        return String.valueOf(stringBuilder);
    }

    private void playMedia() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
        String videoPath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4";

        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,380,resources.getDisplayMetrics());

        if(videoFile.exists()){
            vvVideoView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            vvVideoView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            appBarLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
                    startVideoView();
                    return true;
                }
            });
        } else if (imageFile.exists()){
            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            appBarLayout.getLayoutParams().height = height;
            ivPosterView.getLayoutParams().height = height;
            ivPosterView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            /*Glide.with(this)
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_placeholder)
                    .into(ivPosterView);*/
            RoundedBitmapDrawable imagePoster;
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), eventBitmap);
            imagePoster.setCornerRadius(3);
            ivPosterView.setImageDrawable(imagePoster);
        }
    }

    private void startVideoView() {
        startActivity(new Intent(this, PreviewVideoView.class));
    }

    public long getCurrentTimestamp(){
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

    public String getTheValue(long value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }









    class LoadImage extends AsyncTask<Void, Void, Void> {
        Context context;
        RoundedBitmapDrawable imagePoster = null;

        LoadImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivPosterView.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";

            /*imagePoster = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            imagePoster.setCornerRadius(3);*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPosterView.setImageDrawable(imagePoster);
        }

    }

}
