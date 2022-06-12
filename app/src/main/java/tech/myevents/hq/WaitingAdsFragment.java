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

import static tech.myevents.hq.AdsStaticWaiting.adParam;
import static tech.myevents.hq.AdsStaticWaiting.adsAdapter;
import static tech.myevents.hq.AdsStaticWaiting.adsList;
import static tech.myevents.hq.MainActivity.currentPage;

public class WaitingAdsFragment extends Fragment {

    LinearLayout llAdEmpty;
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;
    ListView listView;

    public WaitingAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waiting_ads, container, false);

        listView = (ListView) view.findViewById(R.id.lvAdsWaiting);
        llAdEmpty = (LinearLayout) view.findViewById(R.id.llAdEmptyWaiting);
        prefsList = getActivity().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        currentPage = 1;
        dbOperations = new DBOperations(getContext());
        db = dbOperations.getReadableDatabase();
        Cursor cursor = dbOperations.getAdsWaiting(db);
        //Toast.makeText(getContext(), "<ads>" + prefsList.getString("maxAdIdWaiting", "kk"), Toast.LENGTH_LONG).show();
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
            new AdsBackTaskWaiting(getContext()).execute();
        } else {
            if (adParam == null) {
                adsAdapter = new AdsAdapterWaiting(getContext(), R.layout.waiting_ads_layout);
                listView.setAdapter(adsAdapter);
                final int y = prefsList.getInt("adScrollPosWaiting", 0);
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
        editor.putString("adNotificationCountWaiting","0");
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
        editor.putInt("adScrollPosWaiting", x);
        editor.apply();
    }

}
