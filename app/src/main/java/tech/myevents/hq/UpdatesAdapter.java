package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UpdatesAdapter extends ArrayAdapter {

    static List updatesList = new ArrayList();
    SQLiteDatabase db;
    DBOperations dbOperations;
    private UpdatesGetSetter getSetter;
    SharedPreferences prefsUserInfo;

    UpdatesAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(UpdatesGetSetter object) {
        updatesList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return updatesList.size();
    }

    @Override
    public Object getItem(int position) {
        return updatesList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.updates_layout, parent, false);
            holder = new Holder();

            holder.tvBroadcastName = (TextView) row.findViewById(R.id.tvBroadcastName);
            holder.tvVenueOrTitle = (TextView) row.findViewById(R.id.tvVenueOrTitle);
            holder.tvUpdateTime = (TextView) row.findViewById(R.id.tvUpdateTime);
            holder.tvViewBroadcast = (TextView) row.findViewById(R.id.tvViewBroadcast);
            holder.tvUpdateBroadcast = (TextView) row.findViewById(R.id.tvUpdateBroadcast);
            holder.tvViewUpdate = (TextView) row.findViewById(R.id.tvViewUpdate);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        dbOperations = new DBOperations(getContext());
        db = dbOperations.getWritableDatabase();
        getSetter = (UpdatesGetSetter) getItem(position);
        prefsUserInfo = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        String posterPath = "sdcard/MyEvents/Events/MyEvents Posters/";

        holder.tvBroadcastName.setText(getSetter.getBroadcastName());
        holder.tvVenueOrTitle.setText(getSetter.getVenueTitle());
        holder.tvUpdateTime.setText(new EventsCalculations(getContext()).calculateTimeReceivedStats(getSetter.getUpdateTime()));

        if (getSetter.getType().equals("event")) {
            holder.tvViewBroadcast.setText("VIEW EVENT");
            holder.tvUpdateBroadcast.setText("UPDATE EVENT");

            holder.tvViewBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdatesView.updatesType = "event";
                    UpdatesView.updatesState = "dated";
                    UpdatesView.updatesId = getSetter.getBroadcastId();
                    UpdatesView.updatesPosition = position;
                    getContext().startActivity(new Intent(getContext(), UpdatesView.class));
                }
            });
            holder.tvViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdatesView.updatesType = "event";
                    UpdatesView.updatesState = "updated";
                    UpdatesView.updatesId = getSetter.getBroadcastId();
                    UpdatesView.updatesPosition = position;
                    getContext().startActivity(new Intent(getContext(), UpdatesView.class));
                }
            });
            holder.tvUpdateBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Update Event", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            holder.tvUpdateBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UpdateBroadcast()
                            .execute("event", String.valueOf(getSetter.getId()),
                                    String.valueOf(getSetter.getBroadcastId()), String.valueOf(position));
                }
            });

        } else {
            holder.tvViewBroadcast.setText("VIEW AD");
            holder.tvUpdateBroadcast.setText("UPDATE AD");

            holder.tvViewBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdatesView.updatesType = "ad";
                    UpdatesView.updatesState = "dated";
                    UpdatesView.updatesId = getSetter.getBroadcastId();
                    UpdatesView.updatesPosition = position;
                    getContext().startActivity(new Intent(getContext(), UpdatesView.class));
                }
            });
            holder.tvViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdatesView.updatesType = "ad";
                    UpdatesView.updatesState = "updated";
                    UpdatesView.updatesId = getSetter.getBroadcastId();
                    UpdatesView.updatesPosition = position;
                    getContext().startActivity(new Intent(getContext(), UpdatesView.class));
                }
            });
            holder.tvUpdateBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Update Ad", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            holder.tvUpdateBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UpdateBroadcast()
                            .execute("ad", String.valueOf(getSetter.getId()),
                                    String.valueOf(getSetter.getBroadcastId()), String.valueOf(position));
                }
            });

        }

        return row;
    }

    private static class Holder {
        TextView tvBroadcastName, tvVenueOrTitle, tvUpdateTime, tvViewBroadcast, tvUpdateBroadcast, tvViewUpdate;
    }

    private class UpdateBroadcast extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            final String type = params[0];
            final String id = params[1];
            final String broadcastId = params[2];
            final String position = params[3];

            StringRequest stringRequest = new StringRequest(Request.Method.POST, App.AppInfo.APP_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            if (response.equals("yes")) {
                                updatesList.remove(position);
                            } else {
                                Toast.makeText(getContext(), "Failed to Update", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("action", "update_broadcasts_hq");
                    params.put("type", type);
                    params.put("id", id);
                    params.put("broadcast_id", broadcastId);
                    params.put("update_time", String.valueOf(getCurrentTimestamp()));

                    return params;
                }
            };
            MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

            return null;
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
