package tech.myevents.hq;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static tech.myevents.hq.AdsStaticWaiting.adsList;
import static tech.myevents.hq.AdsStaticWaiting.sPublish;

class PublishAd extends AsyncTask<String, Void, Void> {

    Context context;
    SQLiteDatabase db;
    DBOperations dbOperations;
    private AdsGetSetterWaiting getSetter;

    PublishAd(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        final String adId = params[0];
        final String id = params[1];
        final int position = Integer.parseInt(params[2]);

        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        getSetter = (AdsGetSetterWaiting) getItem(position);

        String appUrl = App.AppInfo.APP_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        if (response.contains("yes")) {
                            Toast.makeText(context, "Ad Published", Toast.LENGTH_LONG).show();
                            getSetter.setStatus("published");
                            sPublish[position] = R.mipmap.published;
                            ContentValues values = new ContentValues();
                            values.put(DBContract.Ad.COLUMN_AD_STATUS, "published");
                            db.update(DBContract.Ad.TABLE_NAME, values, DBContract.Ad.COLUMN_ID + "='" + id + "'", null);
                        } else {
                            Toast.makeText(context, "Publishing Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "publish_ad");
                params.put("adId", adId);

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQue(stringRequest);

        return null;
    }

    Object getItem(int position) {
        return adsList.get(position);
    }

}