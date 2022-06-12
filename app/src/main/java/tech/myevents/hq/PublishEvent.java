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

import static tech.myevents.hq.EventsStaticWaiting.eventsList;
import static tech.myevents.hq.EventsStaticWaiting.sPublish;

class PublishEvent extends AsyncTask<String, Void, Void> {

    Context context;
    SQLiteDatabase db;
    DBOperations dbOperations;
    private EventsGetSetterWaiting getSetter;

    PublishEvent(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        final String eventId = params[0];
        final String id = params[1];
        final int position = Integer.parseInt(params[2]);

        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        getSetter = (EventsGetSetterWaiting) getItem(position);

        String appUrl = App.AppInfo.APP_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "<?>" + response, Toast.LENGTH_LONG).show();
                        if (response.contains("yes")) {
                            Toast.makeText(context, "Event Published", Toast.LENGTH_LONG).show();
                            getSetter.setStatus("published");
                            sPublish[position] = R.mipmap.published;
                            ContentValues values = new ContentValues();
                            values.put(DBContract.Event.COLUMN_EVENT_STATUS, "published");
                            db.update(DBContract.Event.TABLE_NAME,values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);
                        } else {
                            Toast.makeText(context, "Publishing Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "publish_event");
                params.put("eventId", eventId);

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQue(stringRequest);

        return null;
    }

    Object getItem(int position) {
        return eventsList.get(position);
    }

}