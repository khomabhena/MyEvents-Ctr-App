package tech.myevents.hq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.barColor));

        new Thread(){
            public void run(){
                Random random = new Random();
                int num = random.nextInt(1000);
                if (num < 800) {
                    num += 500;
                }
                try {
                    sleep(num);
                }
                catch (InterruptedException e) {
                    //e.printStackTrace();
                } finally {
                    startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        overridePendingTransition(0, R.anim.splash_fade);
    }

}
