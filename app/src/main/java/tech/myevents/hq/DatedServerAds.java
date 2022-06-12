package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class DatedServerAds extends AsyncTask<Void, Void, Void> {
    Context context;
    Activity activity;
    private SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;

    DatedServerAds(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        prefUserInfo = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.AppInfo.APP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor = prefUserInfo.edit();
                        editor.putLong("datedServerAds", getCurrentTimestamp());
                        editor.apply();
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
                params.put("action", "check_dated_ads");
                params.put("current_stamp", String.valueOf(getCurrentTimestamp()));

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQue(stringRequest);
        return null;
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
