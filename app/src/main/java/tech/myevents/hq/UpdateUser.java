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

import java.util.HashMap;
import java.util.Map;

class UpdateUser extends AsyncTask<String,Void,String> {

    Context context;
    private Activity activity;
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String userId, locationCode, interestsCode;

    UpdateUser(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected String doInBackground(String... params) {
        userId = params[0];
        locationCode = params[1];
        interestsCode = params[2];

        prefs = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final String token = prefs.getString("token", "");
        String appUrl = App.AppInfo.APP_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("yes")) {
                            editor = prefs.edit();
                            editor.putString("interestsUpdate","");
                            editor.putString("tokenRefresh", "");
                            editor.apply();
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
                params.put("action", "update_user");
                params.put("user_id", userId);
                params.put("token", token);
                params.put("location_code", locationCode);
                params.put("interests_code", interestsCode);

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQue(stringRequest);

        return null;
    }

}