package tech.myevents.hq;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editor;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    private PendingIntent eventPendingIntent;
    private PendingIntent eventPendingIntentWaiting;
    private PendingIntent adPendingIntent;
    private PendingIntent adPendingIntentWaiting;
    private PendingIntent smsPendingIntent;
    private AlarmManager eventManager;
    private AlarmManager eventManagerWaiting;
    private AlarmManager adManager;
    private AlarmManager adManagerWaiting;
    private AlarmManager smsManager;

    Handler eventHandler;
    Handler eventHandlerWaiting;
    Handler adHandler;
    Handler adHandlerWaiting;
    Handler smsHandler;

    NotificationCompat.Builder notification;
    static int currentPage = 1;
    static int currentTabItem = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBOperations dbOperations = new DBOperations(this);
        SQLiteDatabase db = dbOperations.getReadableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        sectionsPagerAdapter.addFragments(new AdsFragment(), "Ads");
        sectionsPagerAdapter.addFragments(new EventsFragment(), "Events");
        sectionsPagerAdapter.addFragments(new NowPlayingFragment(), "Now Playing");
        sectionsPagerAdapter.addFragments(new WaitingEventsFragment(), "Events");
        sectionsPagerAdapter.addFragments(new WaitingAdsFragment(), "Ads");
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(currentTabItem, true);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        eventHandler = new Handler();
        eventHandlerWaiting = new Handler();
        adHandler = new Handler();
        adHandlerWaiting = new Handler();
        smsHandler = new Handler();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        if (currentPage != 1) {
            switch (currentPage) {
                case 2:
                    startActivity(new Intent(this, Profile.class));
                    break;
                case 3:
                    startActivity(new Intent(this, MakeEvent.class));
                    break;
                case 4:
                    startActivity(new Intent(this, MakeAd.class));
                    break;
                case 5:
                    startActivity(new Intent(this, PreviewEvent.class));
                    break;
                case 6:
                    startActivity(new Intent(this, PreviewContentView.class));
                    break;
                case 7:
                    startActivity(new Intent(this, PosterPreview.class));
                    break;
                case 8:
                    startActivity(new Intent(this, PreviewAd.class));
                    break;
                case 9:
                    startActivity(new Intent(this, PreviewAdView.class));
                    break;
                case 10:
                    startActivity(new Intent(this, PreviewLogo.class));
                    break;
            }
        }

        editor = prefsUserInfo.edit();
        if (prefsUserInfo.getString("userState", "").equals("")) {
            editor.putString("userState", "newUser");
            editor.apply();
            DBBackgroundTask dbBackgroundTask = new DBBackgroundTask(this);
            dbBackgroundTask.execute("insertAllData");
        }

        //checkRegistration();
        stopEventAlarm();
        stopEventAlarmWaiting();
        //stopSmsAlarm();

        stopAdAlarm();
        stopAdAlarmWaiting();

        startAdPostDelay();
        startAdPostDelayWaiting();

        startEventPostDelay();
        startEventPostDelayWaiting();
        //startSmsPostDelay();

    }

    private void startAdPostDelay() {
        adHandler.post(runnableAdData);
    }

    private void startEventPostDelay() {
        eventHandler.post(runnableEventData);
    }

    private void startSmsPostDelay() {
        smsHandler.post(runnableSmsData);
    }

    private void stopEventAlarm() {
        Intent alarmIntent = new Intent(this,EventsReceiver.class);
        eventPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        eventManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //long interval = 120000;
        //manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval,pendingIntent);
        eventManager.cancel(eventPendingIntent);
    }

    private void stopAdAlarm() {
        Intent alarmIntent = new Intent(this,AdsReceiver.class);
        adPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        adManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //long interval = 120000;
        //manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval,pendingIntent);
        adManager.cancel(adPendingIntent);
    }

    private void stopSmsAlarm() {
        Intent alarmIntent = new Intent(this, SmsReceiver.class);
        smsPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        smsManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        smsManager.cancel(smsPendingIntent);
    }


    private void startAdPostDelayWaiting() {
        adHandlerWaiting.post(runnableAdDataWaiting);
    }

    private void startEventPostDelayWaiting() {
        eventHandlerWaiting.post(runnableEventDataWaiting);
    }

    private void stopEventAlarmWaiting() {
        Intent alarmIntent = new Intent(this, WaitingEventsReceiver.class);
        eventPendingIntentWaiting = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        eventManagerWaiting = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        eventManagerWaiting.cancel(eventPendingIntentWaiting);
    }

    private void stopAdAlarmWaiting() {
        Intent alarmIntent = new Intent(this, WaitingAdsReceiver.class);
        adPendingIntentWaiting = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        adManagerWaiting = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        adManagerWaiting.cancel(adPendingIntentWaiting);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentTabItem = viewPager.getCurrentItem();
        startEventAlarm();
        startAdAlarm();
        startSmsAlarm();
        stopEventPostDelay();
        stopAdPostDelay();
        stopSmsPostDelay();

        startEventAlarmWaiting();
        startAdAlarmWaiting();
        stopEventPostDelayWaiting();
        stopAdPostDelayWaiting();
        //activiveUserUpdate();

        new CheckNowPlaying(this).execute();
        new CheckDatedAds(this).execute();
        long diff = getCurrentTimestamp() - prefsUserInfo.getLong("lastAllServerUpdate",125000);
        long diff2 = getCurrentTimestamp() - prefsUserInfo.getLong("datedServerAds", 60000);
        if (diff > 900000) {
            new AdServerUpdate(this).execute();
            new EventServerUpdate(this).execute();
        }

        if (prefsUserInfo.getString("interestsUpdate","").equals("yes")) {
            new UpdateUser(this)
                    .execute(prefsUserInfo.getString("userId", ""),
                            prefsUserInfo.getString("locationCode", ""), prefsUserInfo.getString("interestsCode", ""));
        }
        if (diff2 > 120000) {
            new DatedServerAds(this).execute();
        }
    }

    private void startAdAlarm() {
        Intent alarmIntent = new Intent(this,AdsReceiver.class);
        adPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        adManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 900000;
        adManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),interval, adPendingIntent);
    }

    private void startEventAlarm() {

        Intent alarmIntent = new Intent(this,EventsReceiver.class);
        eventPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        eventManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 900000;
        eventManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),interval, eventPendingIntent);

    }

    private void startSmsAlarm() {
        Intent alarmIntent = new Intent(this, SmsReceiver.class);
        smsPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        smsManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 60000;
        smsManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval, smsPendingIntent);
    }

    private void stopAdPostDelay() {
        adHandler.removeCallbacks(runnableAdData);
    }

    private void stopEventPostDelay() {
        eventHandler.removeCallbacks(runnableEventData);
    }

    private void stopSmsPostDelay() {
        smsHandler.removeCallbacks(runnableSmsData);
    }

    private final Runnable runnableEventData = new Runnable() {
        @Override
        public void run() {
            try {
                eventHandler.postDelayed(this, 900000);
                checkEventData();
            } catch (Exception e){
                //
            }
        }
    };

    private final Runnable runnableAdData = new Runnable() {
        @Override
        public void run() {
            try {
                adHandler.postDelayed(this, 900000);
                checkAdData();
            } catch (Exception e){
                //
            }
        }
    };

    private final Runnable runnableSmsData = new Runnable() {
        @Override
        public void run() {
            try {
                smsHandler.postDelayed(this, 30000);
                checkSmsData();
            } catch (Exception e){
                //
            }
        }
    };

    private void checkAdData() {
        sendBroadcast(new Intent(this,AdsReceiver.class));
    }

    private void checkEventData() {
        sendBroadcast(new Intent(this,EventsReceiver.class));
    }

    private void checkSmsData() {
        sendBroadcast(new Intent(this, SmsReceiver.class));
    }

    private void startAdAlarmWaiting() {
        Intent alarmIntent = new Intent(this, WaitingAdsReceiver.class);
        adPendingIntentWaiting = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        adManagerWaiting = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 300000;
        adManagerWaiting.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, adPendingIntentWaiting);
    }

    private void startEventAlarmWaiting() {
        Intent alarmIntent = new Intent(this, WaitingEventsReceiver.class);
        eventPendingIntentWaiting = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        eventManagerWaiting = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 300000;
        eventManagerWaiting.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, eventPendingIntentWaiting);
    }

    private void stopAdPostDelayWaiting() {
        adHandlerWaiting.removeCallbacks(runnableAdDataWaiting);
    }

    private void stopEventPostDelayWaiting() {
        eventHandlerWaiting.removeCallbacks(runnableEventDataWaiting);
    }

    private final Runnable runnableEventDataWaiting = new Runnable() {
        @Override
        public void run() {
            try {
                eventHandlerWaiting.postDelayed(this, 300000);
                checkEventDataWaiting();
            } catch (Exception e){
                //
            }
        }
    };

    private final Runnable runnableAdDataWaiting = new Runnable() {
        @Override
        public void run() {
            try {
                adHandlerWaiting.postDelayed(this, 300000);
                checkAdDataWaiting();
            } catch (Exception e){
                //
            }
        }
    };

    private void checkAdDataWaiting() {
        sendBroadcast(new Intent(this, WaitingAdsReceiver.class));
    }

    private void checkEventDataWaiting() {
        sendBroadcast(new Intent(this, WaitingEventsReceiver.class));
    }


    private void checkRegistration() {
        if(prefsUserInfo.getString("userReg","").equals("")){
            startActivity(new Intent(this, RegPage.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.make_ad) {
            startActivity(new Intent(this,MakeAd.class));
        }else if(id == R.id.make_event){
            startActivity(new Intent(this,MakeEvent.class));
        }else if(id == R.id.profile){
            startActivity(new Intent(MainActivity.this, Profile.class));
        }else if (id == R.id.stats) {
            startActivity(new Intent(this, Stats.class));
        } else if (id == R.id.updates) {
            startActivity(new Intent(this, Updates.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabTitles = new ArrayList<>();

        void addFragments(Fragment fragments, String titles){
            this.fragments.add(fragments);
            this.tabTitles.add(titles);
        }

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
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

}
