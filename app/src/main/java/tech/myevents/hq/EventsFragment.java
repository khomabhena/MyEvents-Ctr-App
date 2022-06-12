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
import android.widget.Toast;

import java.util.Calendar;

import static tech.myevents.hq.EventsStatic.eventItems;
import static tech.myevents.hq.EventsStatic.eventParam;
import static tech.myevents.hq.EventsStatic.eventsAdapter;
import static tech.myevents.hq.EventsStatic.eventsList;
import static tech.myevents.hq.MainActivity.currentPage;

public class EventsFragment extends Fragment {

    View view;
    ListView listView;
    LinearLayout llEventEmpty;
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        prefsList = getActivity().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        listView = (ListView) view.findViewById(R.id.lvEvents);
        llEventEmpty = (LinearLayout) view.findViewById(R.id.llEventEmpty);

        currentPage = 1;
        dbOperations = new DBOperations(getContext());
        db = dbOperations.getReadableDatabase();

        Cursor cursor = dbOperations.getEvents(db);
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

        if (eventsList == null) {
            new EventsBackTask(getContext()).execute();
        } else {
            if (eventParam == null) {
                eventsAdapter = new EventsAdapter(getContext(), R.layout.events_row_layout);
                listView.setAdapter(eventsAdapter);
                final int y = prefsList.getInt("eventScrollPos", 0);
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
        editor.putString("eventNotificationCount","0");
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
        editor.putInt("eventScrollPos",x);
        editor.apply();
        if (eventItems != null) {
            long diff = getCurrentTimestamp() - prefsList.getLong("lastEventServerUpdate", 125000);
            if (diff > 900000) {
                editor.putLong("lastEventServerUpdate", getCurrentTimestamp());
                editor.apply();
                new EventServerUpdate(getContext()).execute("");
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
