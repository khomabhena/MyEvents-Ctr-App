package tech.myevents.hq;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;

import static tech.myevents.hq.AdsStatic.adItems;
import static tech.myevents.hq.AdsStatic.adParam;
import static tech.myevents.hq.AdsStatic.adsAdapter;
import static tech.myevents.hq.AdsStatic.adsList;

public class AdsFragment extends Fragment {
    LinearLayout llAdEmpty;
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;
    ListView listView;

    public AdsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ads, container, false);
        listView = (ListView) view.findViewById(R.id.lvAds);
        adsAdapter = new AdsAdapter(getContext(), R.layout.ads_layout);
        llAdEmpty = (LinearLayout) view.findViewById(R.id.llAdEmpty);
        prefsList = getActivity().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        editor = prefsList.edit();
        editor.putInt("currentPage",1);
        editor.apply();

        dbOperations = new DBOperations(getContext());
        db = dbOperations.getReadableDatabase();
        Cursor cursor = dbOperations.getAds(db);

        if (cursor.getCount() == 0) {
            listView.getLayoutParams().width = 0;
            listView.getLayoutParams().height = 0;
            llAdEmpty.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            llAdEmpty.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            llAdEmpty.getLayoutParams().height = 0;
            llAdEmpty.getLayoutParams().width = 0;
            listView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            listView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        cursor.close();

        if (adsList == null) {
            new AdsBackTask(getContext()).execute();
        } else {
            if (adParam == null) {
                adsAdapter = new AdsAdapter(getContext(), R.layout.ads_layout);
                listView.setAdapter(adsAdapter);
                final int y = prefsList.getInt("adScrollPos", 0);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(y);
                    }
                });
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cancelNotification();
    }

    private void cancelNotification() {
        editor = prefsList.edit();
        editor.putString("adNotificationCount","0");
        editor.apply();
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(ns);
        notificationManager.cancel(675647);
    }

    @Override
    public void onPause() {
        super.onPause();
        int x = listView.getFirstVisiblePosition();
        editor = prefsList.edit();
        editor.putInt("adScrollPos",x);
        editor.apply();
        if (adItems != null) {
            long diff = getCurrentTimestamp() - prefsList.getLong("lastAdServerUpdate", 125000);
            if (diff > 900000) {
                editor.putLong("lastAdServerUpdate", getCurrentTimestamp());
                editor.apply();
                new AdServerUpdate(getContext()).execute("");
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

}