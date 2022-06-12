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

import static tech.myevents.hq.EventsStaticWaiting.eventParam;
import static tech.myevents.hq.EventsStaticWaiting.eventsAdapter;
import static tech.myevents.hq.EventsStaticWaiting.eventsList;
import static tech.myevents.hq.MainActivity.currentPage;

public class WaitingEventsFragment extends Fragment {
    View view;
    ListView listView;
    LinearLayout llEventEmpty;
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;

    public WaitingEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_waiting_events, container, false);
        prefsList = getActivity().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        listView = (ListView) view.findViewById(R.id.lvEventsWaiting);
        llEventEmpty = (LinearLayout) view.findViewById(R.id.llEventEmptyWaiting);

        currentPage = 1;
        dbOperations = new DBOperations(getContext());
        db = dbOperations.getReadableDatabase();
        Cursor cursor = dbOperations.getEventsWaiting(db);
        //Toast.makeText(getContext(), "<events>" + cursor.getCount(), Toast.LENGTH_LONG).show();
        if (cursor.getCount() == 0) {
            listView.getLayoutParams().width = 0;
            listView.getLayoutParams().height = 0;
            llEventEmpty.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            llEventEmpty.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            llEventEmpty.getLayoutParams().height = 0;
            llEventEmpty.getLayoutParams().width = 0;
            listView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            listView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        cursor.close();
        if (eventsList == null) {
            new EventsBackTaskWaiting(getContext()).execute();
        } else {
            if (eventParam == null) {
                eventsAdapter = new EventsAdapterWaiting(getContext(), R.layout.waiting_events_layout);
                listView.setAdapter(eventsAdapter);
                final int y = prefsList.getInt("eventScrollPosWaiting", 0);
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
        editor.putString("eventNotificationCountWaiting","0");
        editor.apply();
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(ns);
        notificationManager.cancel(675646);
    }

    @Override
    public void onPause() {
        super.onPause();
        int x = listView.getFirstVisiblePosition();
        editor = prefsList.edit();
        editor.putInt("eventScrollPosWaiting",x);
        editor.apply();
    }

}
