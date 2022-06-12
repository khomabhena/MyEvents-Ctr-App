package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.AutoCompleteTextView;

class DBBackgroundTask extends AsyncTask<String, Void, String> {
    Context context;
    Activity activity;
    AutoCompleteTextView autoCompleteTextView;

    DBBackgroundTask(Context context) {
        this.context = context;
        activity = (Activity)context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        DBOperations dbOperations = new DBOperations(context);
        SQLiteDatabase db = dbOperations.getWritableDatabase();

        String taskType = params[0];
        if (taskType.equals("insertAllData")) {
            dbOperations.addInfo(db);
            return "Data Inserted....";
        }else if(taskType.equals("user_id_insertion")){
            String user_id = params[1]; String phoneNumber = params[2];
            dbOperations.user(db, taskType,user_id,phoneNumber);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("getCity")){

        } else {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

}
