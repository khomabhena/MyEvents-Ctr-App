package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static tech.myevents.hq.MainActivity.currentPage;
import static tech.myevents.hq.MakeEvent.eventBitmap;

public class PreviewEvent extends AppCompatActivity {

    String name,venue,city,location,minPrice,maxPrice,details,startTimestamp,endTimestamp;
    TextView tvName, tvVenue, tvLocation, tvDate, tvView, tvLike;
    ImageButton ibPlace, ibDate, ibView, ibLike, ibVideo;
    ImageView ivPoster;
    LinearLayout llContent;
    Context context;
    SharedPreferences prefs;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 5;
        setContentView(R.layout.activity_preview_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        prefs = getSharedPreferences(App.AppInfo.PREF_BROADCAST_VALUES, Context.MODE_PRIVATE);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

        tvName = (TextView) findViewById(R.id.tvName);
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvView = (TextView) findViewById(R.id.tvView);
        tvLike = (TextView) findViewById(R.id.tvLike);
        ibPlace = (ImageButton) findViewById(R.id.ibPlace);
        ibDate = (ImageButton) findViewById(R.id.ibDate);
        ibView = (ImageButton) findViewById(R.id.ibView);
        ibLike = (ImageButton) findViewById(R.id.ibLike);
        ibVideo = (ImageButton) findViewById(R.id.ibVideo);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        llContent = (LinearLayout) findViewById(R.id.llContent);
        tvName.setTypeface(typefaceBC);
        ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPoster();
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewContent();
            }
        });

        name = prefs.getString("eventName","");
        venue = prefs.getString("eventVenue","");
        city = prefs.getString("selectedCity","");
        location = prefs.getString("selectedLocation","");
        startTimestamp = prefs.getString("startTimestamp","");
        endTimestamp = prefs.getString("endTimestamp","");
        minPrice = prefs.getString("minPrice","");
        maxPrice = prefs.getString("maxPrice","");
        details = prefs.getString("details","");

        loadValues();
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

    private void viewContent() {
        startActivity(new Intent(this, PreviewContentView.class));
    }

    private void viewPoster() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");
        if (imageFile.exists()) {
            startActivity(new Intent(this, PosterPreview.class));
        } else {
            Toast.makeText(getApplicationContext(),"Poster not available",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadValues() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
        StringBuilder locationBuilder = new StringBuilder();
        if(imageFile.exists()){
            /*Glide.with(this)
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_placeholder)
                    .into(ivPoster);*/
            RoundedBitmapDrawable imagePoster;
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), eventBitmap);
            imagePoster.setCornerRadius(3);
            ivPoster.setImageDrawable(imagePoster);
        }
        locationBuilder.append(location).append(", ").append(city);
        if(!name.equals("")) {
            tvName.setText(name);
        }
        ibPlace.setImageResource(R.drawable.ib_place_teal);
        if (!venue.equals("")) {
            tvVenue.setText(venue);
        }
        if (locationBuilder.length() > 3) {
            tvLocation.setText(locationBuilder);
        }
        if (videoFile.exists()){
            ibVideo.setImageResource(R.drawable.ib_video_teal);
        }
        if (!startTimestamp.equals("")) {
            tvDate.setText(getDate(Long.parseLong(startTimestamp)));
        }
        ibLike.setImageResource(R.drawable.ib_heart_teal);
        tvLike.setText("0");
        ibView.setImageResource(R.drawable.ib_view_teal);
        tvView.setText("0");
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
        stringBuilder.append(wkDay).append("   ").append(getTheValue(day)).append("-").append(monthName).append("-").append(getTheValue(year))
                .append(",  ").append(getTheValue(hour)).append(":").append(getTheValue(minute)).append("hrs");

        return String.valueOf(stringBuilder);
    }

    public String getTheValue(int value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

    public String getMonthName(int month){
        switch(month+1){
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return "";
    }










    class LoadImage extends AsyncTask<Void, Void, Void> {
        Context context;
        RoundedBitmapDrawable imagePoster = null;

        LoadImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivPoster.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            imagePoster.setCornerRadius(3);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPoster.setImageDrawable(imagePoster);
        }

    }

}
