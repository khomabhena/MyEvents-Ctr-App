package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;

import static tech.myevents.hq.MainActivity.currentPage;

public class PreviewLogo extends AppCompatActivity {

    ImageView ivLogo;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 10;
        setContentView(R.layout.activity_preview_logo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_ad.JPEG";
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");

        if(imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            RoundedBitmapDrawable imagePoster = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            imagePoster.setCornerRadius(3);
            ivLogo.setImageDrawable(imagePoster);
        }
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.barColor));
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

}
