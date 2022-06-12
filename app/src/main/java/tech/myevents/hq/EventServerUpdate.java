package tech.myevents.hq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class EventServerUpdate extends AsyncTask<String, Void, String> {

    Context context;
    Activity activity;
    final StringBuilder builder = new StringBuilder();
    private String[] arrayId, arrayEventId, arrayLikeStatus, arrayViewStatus;
    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;

    EventServerUpdate(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected String doInBackground(String... params) {
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();
        String appUrl = App.AppInfo.APP_URL;
        List<String> listId, listEventId, listLikeStatus, listViewStatus;

        Cursor cursor = dbOperations.getEventLikesAndViews(db);
        prefUserInfo = activity.getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        listId = new LinkedList<String>();
        listEventId = new LinkedList<String>();
        listLikeStatus = new LinkedList<String>();
        listViewStatus = new LinkedList<String>();

        while (cursor.moveToNext()) {
            listId.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBContract.Event.COLUMN_ID))));
            listEventId.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBContract.Event.COLUMN_EVENT_ID))));
            listLikeStatus.add(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_LIKE_STATUS)));
            listViewStatus.add(cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_VIEW_STATUS)));
        }
        arrayId = listId.toArray(new String[listId.size()]);
        arrayEventId = listEventId.toArray(new String[listEventId.size()]);
        arrayLikeStatus = listLikeStatus.toArray(new String[listLikeStatus.size()]);
        arrayViewStatus = listViewStatus.toArray(new String[listViewStatus.size()]);
        cursor.close();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor = prefUserInfo.edit();
                        editor.putLong("lastEventServerUpdate", getCurrentTimestamp());
                        editor.apply();
                        getTheData(response);
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
                params.put("action", "update_event_likes");
                params.put("count", String.valueOf(arrayId.length));
                for (int x = 0; x < arrayId.length; x++) {
                    params.put("id" + x, arrayId[x]);
                    params.put("event_id" + x, arrayEventId[x]);
                    params.put("view_status" + x, arrayViewStatus[x]);
                    params.put("like_status" + x, arrayLikeStatus[x]);
                }

                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQue(stringRequest);

        return null;
    }

    private void getTheData(String response) {
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            while (count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                String id = jo.getString("id");
                ContentValues values = new ContentValues();
                values.put(DBContract.Event.COLUMN_LIKES,  jo.getString("likes"));
                values.put(DBContract.Event.COLUMN_VIEWS,  jo.getString("views"));
                values.put(DBContract.Event.COLUMN_LIKE_STATUS, "updated");
                values.put(DBContract.Event.COLUMN_VIEW_STATUS, "updated");
                db.update(DBContract.Event.TABLE_NAME, values, DBContract.Event.COLUMN_ID + "='" + id + "'", null);

                count++;
            }
        } catch (JSONException e) {
            //showToast("An Error OccurredY");
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