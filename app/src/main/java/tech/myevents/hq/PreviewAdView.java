package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

import static tech.myevents.hq.MainActivity.currentPage;
import static tech.myevents.hq.MakeAd.adBitmap;

public class PreviewAdView extends AppCompatActivity {
    ImageView ivPosterView;
    VideoView vvVideoView;
    TextView tvBrandName, tvAdTitle, tvAdDesc, tvDesc1, tvDesc2, tvDesc3, tvDesc4;
    SharedPreferences prefs;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editorY;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 9;
        setContentView(R.layout.activity_preview_ad_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;

        prefs = getSharedPreferences(App.AppInfo.PREF_AD_VALUES, Context.MODE_PRIVATE);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        ivPosterView = (ImageView) findViewById(R.id.ivPosterView);
        vvVideoView = (VideoView) findViewById(R.id.vvVideoView);
        tvBrandName = (TextView) findViewById(R.id.tvBrandName);
        tvAdTitle = (TextView) findViewById(R.id.tvAdTitle);
        tvAdDesc = (TextView) findViewById(R.id.tvAdDesc);
        tvDesc1 = (TextView) findViewById(R.id.tvDesc1);
        tvDesc2 = (TextView) findViewById(R.id.tvDesc2);
        tvDesc3 = (TextView) findViewById(R.id.tvDesc3);
        tvDesc4 = (TextView) findViewById(R.id.tvDesc4);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        if (!prefs.getString("adBrandName","").equals("")) {
            StringBuilder builder = new StringBuilder();
            builder.append("By. ").append(prefs.getString("adBrandName",""));
            tvBrandName.setText(builder);
        }
        if (!prefs.getString("adTitle","").equals("")) {
            tvAdTitle.setText(prefs.getString("adTitle",""));
        }
        if (!prefs.getString("desc1","").equals("")) {
            tvDesc1.setText(prefs.getString("desc1",""));
        }
        if (!prefs.getString("desc2","").equals("")) {
            tvDesc2.setText(prefs.getString("desc2",""));
        }
        if (!prefs.getString("desc3","").equals("")) {
            tvDesc3.setText(prefs.getString("desc3",""));
        }
        if (!prefs.getString("desc4","").equals("")) {
            tvDesc4.setText(prefs.getString("desc4",""));
        }

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);
        //tvBrandName.setTypeface(typefaceBC);
        tvAdDesc.setTypeface(typefaceBC);

        collapsingToolbarLayout.setCollapsedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setExpandedTitleTypeface(typefaceBC);
        collapsingToolbarLayout.setTitle("Ad Details");
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
        super.onResume();
        playMedia();
    }

    private void playMedia() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_ad.JPEG";
        String videoPath = "sdcard/MyEvents/PreBroadcast/pre_ad.mp4";
        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,380,resources.getDisplayMetrics());

        if(videoFile.exists()){
            appBarLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
        } else if (imageFile.exists()){
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
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), adBitmap);
            imagePoster.setCornerRadius(3);
            ivPosterView.setImageDrawable(imagePoster);
        }
    }

}
