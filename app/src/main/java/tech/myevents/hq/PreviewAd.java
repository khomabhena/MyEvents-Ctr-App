package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

import static tech.myevents.hq.MainActivity.currentPage;
import static tech.myevents.hq.MakeAd.adBitmap;

public class PreviewAd extends AppCompatActivity {

    TextView tvAdTitle, tvBrandName, tvLike, tvView, tvWhen;
    ImageView ivLogo;
    ImageButton ibLike, ibView, ibVideo;
    LinearLayout llContent, llLikesViews;

    SharedPreferences prefs;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 8;
        setContentView(R.layout.activity_preview_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences(App.AppInfo.PREF_AD_VALUES, Context.MODE_PRIVATE);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        String fontPathBC = "fonts/black_cry.TTF";
        Typeface typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

        tvAdTitle = (TextView) findViewById(R.id.tvAdTitle);
        tvBrandName = (TextView) findViewById(R.id.tvBrandName);
        tvLike = (TextView) findViewById(R.id.tvLike);
        tvView = (TextView) findViewById(R.id.tvView);
        tvWhen = (TextView) findViewById(R.id.tvWhen);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ibLike = (ImageButton) findViewById(R.id.ibLike);
        ibView = (ImageButton) findViewById(R.id.ibView);
        ibVideo = (ImageButton) findViewById(R.id.ibVideo);
        llContent = (LinearLayout) findViewById(R.id.llContent);
        llLikesViews = (LinearLayout) findViewById(R.id.llLikesViews);

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLogo();
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewContent();
            }
        });

        if (!prefs.getString("adTitle","").equals("")) {
            tvAdTitle.setText(prefs.getString("adTitle", "Your Ad Title"));
        }
        if (!prefs.getString("adBrandName","").equals("")) {
            tvBrandName.setText(prefs.getString("adBrandName", "Your Brand Name"));
        }

        //assert tvAdTitle != null;
        //tvAdTitle.setTypeface(typefaceBC);
        //tvBrandName.setTypeface(typefaceBC);
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
        startActivity(new Intent(this,PreviewAdView.class));
    }

    private void viewLogo() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");
        if (imageFile.exists()) {
            startActivity(new Intent(this, PreviewLogo.class));
        } else {
            Toast.makeText(getApplicationContext(),"Image not available",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadValues() {
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_ad.JPEG";
        if(imageFile.exists()){
            /*Glide.with(this)
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_placeholder)
                    .into(ivLogo);*/
            RoundedBitmapDrawable imagePoster;

            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), adBitmap);
            imagePoster.setCornerRadius(3);
            ivLogo.setImageDrawable(imagePoster);
        }
        if (videoFile.exists()){
            ibVideo.setImageResource(R.drawable.ib_video_teal);
        }
        ibLike.setImageResource(R.drawable.ib_heart_teal);
        tvLike.setText("0");
        ibView.setImageResource(R.drawable.ib_view_teal);
        tvView.setText("0");
    }

}
