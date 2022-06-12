package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Updates extends AppCompatActivity {

    SharedPreferences prefsUserInfo;
    SharedPreferences prefsBroadcastUpdates;
    SharedPreferences.Editor editor;
    ListView listView;
    static int updatesScrollPos;
    static String updatesResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        prefsBroadcastUpdates = getSharedPreferences(App.AppInfo.PREF_BROADCAST_UPDATES, Context.MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.lvUpdates);

        long diff = getCurrentTimestamp() - prefsBroadcastUpdates.getLong("activeBroadcastUpdate", 1992);
        if (diff > 30000) {
            new BroadcastUpdates().execute();
        }
        getTheData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updatesScrollPos = listView.getFirstVisiblePosition();
    }

    private void getTheData() {
        if (updatesResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(updatesResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONArray jsonArrayEvents = jsonObject1.getJSONArray("events");
                JSONArray jsonArrayAds = jsonObject1.getJSONArray("ads");

                new UpdatesBackTask(this).execute(jsonArrayEvents, jsonArrayAds);

            } catch (JSONException e) {
                //e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Updates not loaded", Toast.LENGTH_SHORT).show();
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

    private class BroadcastUpdates extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, App.AppInfo.APP_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            editor = prefsBroadcastUpdates.edit();
                            editor.putLong("activeBroadcastUpdate", getCurrentTimestamp());
                            editor.apply();
                            updatesResponse = response;
                            getTheData();
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
                    params.put("action", "get_broadcast_updates_hq");

                    return params;
                }
            };
            MySingleton.getInstance(Updates.this).addToRequestQue(stringRequest);

            return null;
        }

    }

    private class UpdatesBackTask extends AsyncTask<JSONArray, UpdatesGetSetter, Integer> {

        int returnCount = 0;
        UpdatesAdapter adapter;
        ListView listView;
        LinearLayout llEmpty;
        Context context;
        Activity activity;

        UpdatesBackTask(Context context) {
            this.context = context;
            activity = (Activity) context;
        }

        @Override
        protected Integer doInBackground(JSONArray... params) {
            JSONArray jsonArrayEvents = params[0];
            JSONArray jsonArrayAds = params[1];
            returnCount = jsonArrayEvents.length() + jsonArrayAds.length();

            UpdatesAdapter.updatesList = new ArrayList();
            int id, broadcastId;
            String type, interestCode, locationCode, broadcastName, venueTitle, eventDetails,
                    minPrice, maxPrice, startTimestamp, endTimestamp, bitmapName;
            String video, updateTime, desc1, desc2, desc3, desc4, broadcastRangeCode;

            adapter = new UpdatesAdapter(context, R.layout.updates_layout);
            listView = (ListView) activity.findViewById(R.id.lvUpdates);
            llEmpty = (LinearLayout) activity.findViewById(R.id.llUpdatesEmpty);

            int countEvent = 0;
            while(countEvent < jsonArrayEvents.length()) {
                try {
                    JSONObject jo = jsonArrayEvents.getJSONObject(countEvent);

                    id = Integer.parseInt(jo.getString("id"));
                    broadcastId = Integer.parseInt(jo.getString("event_id"));
                    type = "event";
                    interestCode = jo.getString("interest_code");
                    locationCode = jo.getString("location_code");
                    broadcastName = jo.getString("name");
                    venueTitle = jo.getString("venue");
                    eventDetails = jo.getString("details");
                    minPrice = jo.getString("min_price");
                    maxPrice = jo.getString("max_price");
                    startTimestamp = jo.getString("start_timestamp");
                    endTimestamp = jo.getString("end_timestamp");
                    bitmapName = jo.getString("bitmap_name");
                    video = jo.getString("video");
                    broadcastRangeCode = jo.getString("broadcast_range_code");
                    updateTime = jo.getString("update_time");

                    UpdatesGetSetter setter = new UpdatesGetSetter(id, broadcastId, type, interestCode, locationCode, broadcastName, venueTitle, eventDetails, minPrice, maxPrice, startTimestamp, endTimestamp, bitmapName, video, updateTime, null, null, null, null, broadcastRangeCode);
                    publishProgress(setter);
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                countEvent++;
            }

            int countAd = 0;
            while (countAd < jsonArrayAds.length()) {
                try {
                    JSONObject jo = jsonArrayAds.getJSONObject(countAd);

                    id = Integer.parseInt(jo.getString("id"));
                    broadcastId = Integer.parseInt(jo.getString("ad_id"));
                    type = "ad";
                    interestCode = jo.getString("interest_code");
                    broadcastName = jo.getString("brand_name");
                    venueTitle = jo.getString("title");
                    desc1 = jo.getString("desc_one");
                    desc2 = jo.getString("desc_two");
                    desc3 = jo.getString("desc_three");
                    desc4 = jo.getString("desc_four");
                    broadcastRangeCode = jo.getString("broadcast_range_code");
                    bitmapName = jo.getString("bitmap_name");
                    video = jo.getString("video");
                    updateTime = jo.getString("update_time");

                    UpdatesGetSetter setter = new UpdatesGetSetter(id, broadcastId, type, interestCode, null, broadcastName, venueTitle, null, null, null, null, null, bitmapName, video, updateTime, desc1, desc2, desc3, desc4, broadcastRangeCode);
                    publishProgress(setter);
                } catch (JSONException e) {
                    //e.printStackTrace();
                }

                countAd++;
            }

            return returnCount;
        }

        @Override
        protected void onProgressUpdate(UpdatesGetSetter... values) {
            adapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                llEmpty.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(adapter);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(updatesScrollPos);
                    }
                });
            } else {
                listView.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
            }
        }

    }

}
