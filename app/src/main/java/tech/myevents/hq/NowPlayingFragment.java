package tech.myevents.hq;

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

import static tech.myevents.hq.MainActivity.currentPage;
import static tech.myevents.hq.NowplayingStatic.npAdapter;
import static tech.myevents.hq.NowplayingStatic.npItems;
import static tech.myevents.hq.NowplayingStatic.npList;
import static tech.myevents.hq.NowplayingStatic.npParam;

public class NowPlayingFragment extends Fragment {

    ListView listView;
    LinearLayout llNPEmpty;
    SharedPreferences prefsList;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        prefsList = getActivity().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        listView = (ListView) view.findViewById(R.id.lvNowPlaying);
        llNPEmpty = (LinearLayout) view.findViewById(R.id.llNPEmpty);

        currentPage = 1;
        dbOperations = new DBOperations(getContext());
        db = dbOperations.getReadableDatabase();

        Cursor cursor = dbOperations.getNowPlaying(db);
        if (cursor.getCount() == 0) {
            listView.getLayoutParams().width = 0;
            listView.getLayoutParams().height = 0;
            llNPEmpty.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            llNPEmpty.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            llNPEmpty.getLayoutParams().height = 0;
            llNPEmpty.getLayoutParams().width = 0;
            listView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            listView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        if (npList == null) {
            new NowPlayingBackTask(getContext()).execute();
        } else {
            if (npParam == null) {
                npAdapter = new NowPlayingAdapter(getContext(), R.layout.now_playing_layout);
                listView.setAdapter(npAdapter);
                final int y = prefsList.getInt("npScrollPos", 0);
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
    public void onPause() {
        super.onPause();
        int x = listView.getFirstVisiblePosition();
        editor = prefsList.edit();
        editor.putInt("npScrollPos", x);
        editor.apply();
        if (npItems != null) {
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