package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapView extends AppCompatActivity {

    String posterPath = "";
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    ImageView ivBitmapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bitmap_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivBitmapView = (ImageView) findViewById(R.id.ivBitmapView);
        String bitmapName = getIntent().getExtras().getString("bitmapName");

        prefsList = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        editor = prefsList.edit();

        int scrollPos = getIntent().getExtras().getInt("itemPosition");
        String scrollAction = getIntent().getExtras().getString("scrollAction");
        editor.putInt(scrollAction, scrollPos);
        editor.apply();

        String imagePath = "sdcard/MyEvents/Events/MyEvents Posters/" + bitmapName + ".JPEG";
        File imageFile = new File("sdcard/MyEvents/Events/MyEvents Posters/" + bitmapName + ".JPEG");
        String bitmap = bitmapName + ".JPEG";
        if (imageFile.exists()) {
            //ivBitmapView.setImageDrawable(Drawable.createFromPath(imagePath));
            new LoadImage(this).execute(imagePath);
        } else {
            getBitmap(bitmap);
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
            finish();
            //overridePendingTransition(0, R.anim.splash_fade);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBitmap(final String bitmap_name) {
        String bitmapUrl = App.AppInfo.BITMAP_URL_EVENTS + bitmap_name;
        ImageRequest imageRequest = new ImageRequest(bitmapUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
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



    class LoadImage extends AsyncTask<String, Void, Void> {
        Context context;
        Drawable drawable = null;

        LoadImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivBitmapView.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            drawable = Drawable.createFromPath(imagePath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivBitmapView.setImageDrawable(drawable);
        }

    }

}