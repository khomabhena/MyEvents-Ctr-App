package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Stats extends AppCompatActivity {

    TextView tvTotalUsers, tvFacebookSign, tvMobileSign, tvDailyActive, tvWeeklyActive, tvMonthlyActive, tvTotalInstalls;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        tvTotalInstalls = (TextView) findViewById(R.id.tvTotalInstalls);
        tvTotalUsers = (TextView) findViewById(R.id.tvTotalUsers);
        tvFacebookSign = (TextView) findViewById(R.id.tvFacebookSign);
        tvMobileSign = (TextView) findViewById(R.id.tvMobileSign);
        tvDailyActive = (TextView) findViewById(R.id.tvDailyActive);
        tvWeeklyActive = (TextView) findViewById(R.id.tvWeeklyActive);
        tvMonthlyActive = (TextView) findViewById(R.id.tvMonthlyActive);

        tvTotalInstalls.setText(prefsUserInfo.getString("totalInstalls", ""));
        tvTotalUsers.setText(prefsUserInfo.getString("totalUsers", ""));
        tvFacebookSign.setText(prefsUserInfo.getString("facebookSign", ""));
        tvMobileSign.setText(prefsUserInfo.getString("mobileSign", ""));
        tvDailyActive.setText(prefsUserInfo.getString("dailyActive", ""));
        tvWeeklyActive.setText(prefsUserInfo.getString("weeklyActive", ""));
        tvMonthlyActive.setText(prefsUserInfo.getString("monthlyActive", ""));

        long diff = getCurrentTimestamp() - prefsUserInfo.getLong("activeUserUpdate", 1992);
        if (diff > 30000) {
            getTotalInstalls();
            getTotalUsers();
            getFacebookSign();
            getMobileSign();
            getDailyActive();
            getWeeklyActive();
            getMonthlyActive();
        }
    }

    private void getTotalInstalls() {
        new ActiveUserUpdate(this).execute("get_total_installs", "totalInstalls", null, null);
    }

    private void getTotalUsers() {
        new ActiveUserUpdate(this).execute("get_total_users", "totalUsers", null, null);
    }

    private void getFacebookSign() {
        new ActiveUserUpdate(this).execute("get_facebook_sign", "facebookSign", null, null);
    }

    private void getMobileSign() {
        long before = getCurrentTimestamp();
        long after = before - 86400000;
        new ActiveUserUpdate(this).execute("get_mobile_sign", "mobileSign", String.valueOf(after), String.valueOf(before));
    }

    private void getDailyActive() {
        long before = getCurrentTimestamp();
        long after = before - 86400000;
        new ActiveUserUpdate(this).execute("get_daily_active", "dailyActive", String.valueOf(after), String.valueOf(before));
    }

    private void getWeeklyActive() {
        long before = getCurrentTimestamp();
        long after = before - 604800000;
        new ActiveUserUpdate(this).execute("get_weekly_active", "weeklyActive", String.valueOf(after), String.valueOf(before));
    }

    private void getMonthlyActive() {
        long before = getCurrentTimestamp();
        long after = before - 2419200000L;
        new ActiveUserUpdate(this).execute("get_monthly_active", "monthlyActive", String.valueOf(after), String.valueOf(before));
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

    class ActiveUserUpdate extends AsyncTask<String, Void, Void> {

        Context context;

        ActiveUserUpdate(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            final String action = params[0];
            final String ans = params[1];
            final String after = params[2];
            final String before = params[3];

            StringRequest stringRequest = new StringRequest(Request.Method.POST, App.AppInfo.APP_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("yes")) {
                                String res = response.replace("yes", "");
                                editor = prefsUserInfo.edit();
                                editor.putLong("activeUserUpdate", getCurrentTimestamp());
                                editor.putString(ans, res);
                                editor.apply();

                                switch (ans) {
                                    case "totalInstalls":
                                        tvTotalInstalls.setText(res);
                                        break;
                                    case "totalUsers":
                                        tvTotalUsers.setText(res);
                                        break;
                                    case "facebookSign":
                                        tvFacebookSign.setText(res);
                                        break;
                                    case "mobileSign":
                                        tvMobileSign.setText(res);
                                        break;
                                    case "dailyActive":
                                        tvDailyActive.setText(res);
                                        break;
                                    case "weeklyActive":
                                        tvWeeklyActive.setText(res);
                                        break;
                                    case "monthlyActive":
                                        tvMonthlyActive.setText(res);
                                        break;
                                }
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
                    params.put("action", action);
                    if (after != null && before != null) {
                        params.put("after", after);
                        params.put("before", before);
                    }

                    return params;
                }
            };
            MySingleton.getInstance(Stats.this).addToRequestQue(stringRequest);
            return null;
        }
    }
}
