package tech.myevents.hq;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegPage extends AppCompatActivity {

    String appURL = App.AppInfo.APP_URL;
    EditText etPhoneNumber;
    TextView tvResend, tvHaveCode;
    int n1, n2, n3, n4;
    ProgressDialog progressDialog;
    SharedPreferences prefsUserInfo;
    SharedPreferences.Editor editor;
    DBOperations dbOperations;
    SQLiteDatabase db;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbOperations = new DBOperations(this);
        db = dbOperations.getWritableDatabase();
        prefsUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        tvResend = (TextView) findViewById(R.id.tvResend);
        tvHaveCode = (TextView) findViewById(R.id.tvHaveCode);

        editor = prefsUserInfo.edit();
        if (prefsUserInfo.getString("userState", "").equals("")) {
            editor.putString("userState", "newUser");
            editor.apply();
            DBBackgroundTask dbBackgroundTask = new DBBackgroundTask(this);
            dbBackgroundTask.execute("insertAllData");
        }
        if (prefsUserInfo.getString("confirmation", "").equals("yes")) {
            startActivity(new Intent(getApplicationContext(), UserConfirmation.class));
            finish();
            overridePendingTransition(0, R.anim.splash_fade);
        }

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.barColor));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait .....");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Processing Your Contact ....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void doTheWork(View view) {
        switch (view.getId()) {
            case R.id.tvResend:
                //Toast.makeText(getApplicationContext(), "Resend", Toast.LENGTH_LONG).show();
                break;
            case R.id.tvHaveCode:
                startActivity(new Intent(getApplicationContext(), UserConfirmation.class));
                finish();
                overridePendingTransition(0, R.anim.splash_fade);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void numberSignUp(View view) {
        Toast.makeText(getApplicationContext(), "Disabled in HQ mode", Toast.LENGTH_SHORT).show();
        /*if(!etPhoneNumber.getText().toString().equals("")) {
            String phoneNumber;
            assert etPhoneNumber != null;
            phoneNumber = etPhoneNumber.getText().toString();

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkWifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConn = networkWifi.isConnected();
            NetworkInfo networkMobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean isMobileConn = networkMobile.isConnected();

            if (isWifiConn == true || isMobileConn == true) {
                if (phoneNumber.length() > 8 && phoneNumber.length() < 10) {
                    Random random = new Random();
                    String randConfCode;
                    n1 = random.nextInt(9);
                    n2 = random.nextInt(9);
                    n3 = random.nextInt(9);
                    n4 = random.nextInt(9);
                    randConfCode = "" + n1 + n2 + n3 + n4;

                    editor = prefsUserInfo.edit();
                    editor.putString("randConfCode", randConfCode);
                    editor.putString("phoneNumber", phoneNumber);
                    editor.apply();

                    SignUpTask signUpTask = new SignUpTask(this);
                    signUpTask.execute(phoneNumber, randConfCode);
                } else {
                    Toast.makeText(getApplicationContext(), "Input a valid Zim Mobile Number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Cannot Establish Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        etPhoneNumber.setText(prefsUserInfo.getString("phoneNumber", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor = prefsUserInfo.edit();
        editor.putString("phoneNumber", etPhoneNumber.getText().toString());
        editor.apply();
    }

    class SignUpTask extends AsyncTask<String, Void, String> {
        Context context;

        SignUpTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(appURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                String phoneNumber = params[0];
                String confCode = params[1];

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("sign_up", "UTF-8") + "&" +
                        URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("conf_code", "UTF-8") + "=" + URLEncoder.encode(confCode, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (IOException e) {
                //
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.contains("success")) {
                AlertDialog.Builder goLogin = new AlertDialog.Builder(context);
                goLogin.setTitle("Verification Complete");
                goLogin.setMessage("You'll Receive a Text Message with the Confirmation Code!!");
                goLogin.setCancelable(false);
                goLogin.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(getApplicationContext(), UserConfirmation.class));
                        finish();
                        overridePendingTransition(0, R.anim.splash_fade);
                    }
                });
                AlertDialog alertLogin = goLogin.create();
                alertLogin.show();
                //Toast.makeText(getApplicationContext(), "Contact Processing Successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Contact Processing Failed....", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*
        class UserInsertion extends AsyncTask<String, Void, String>{
            Context context;

            UserInsertion(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(appURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    String fbToken = params[0];
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("user_insertion_fb", "UTF-8") + "&" +
                            URLEncoder.encode("fb_token", "UTF-8") + "=" + URLEncoder.encode(fbToken, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (IOException e) {
                    //e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), "result" + result, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                if (result.contains("yes")) {
                    String user_id = result.replace("yes", "");
                    editor = prefsUserInfo.edit();
                    editor.putString("confirmation","stop");
                    editor.putString("randConfCode","");
                    editor.putString("userReg","stop");
                    editor.putString("userId", user_id);
                    editor.apply();

                    ContentValues userId = new ContentValues();
                    ContentValues userPref = new ContentValues();
                    userPref.put(DBContract.UserPref.COLUMN_USER_ID, Integer.parseInt(user_id));
                    userId.put(DBContract.User.COLUMN_USER_ID, Integer.parseInt(user_id));
                    userId.put(DBContract.User.COLUMN_PHONE_NUMBER, prefsUserInfo.getString("phoneNumber", ""));
                    userId.put(DBContract.User.COLUMN_APP_VERSION, "1");
                    db.insert(DBContract.User.TABLE_NAME, null, userId);
                    db.insert(DBContract.UserPref.TABLE_NAME, null, userPref);

                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                    overridePendingTransition(0, R.anim.splash_fade);
                } else {
                    Toast.makeText(getApplicationContext(), "Activation Failed Retry", Toast.LENGTH_SHORT).show();
                }
            }

        }
    */
    class UserInsertion extends AsyncTask<String, Void, String> {

        Context context;

        UserInsertion(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(appURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                String fbToken = params[0];
                String fbUserId = params[1];
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("user_insertion_hq", "UTF-8") + "&" +
                        URLEncoder.encode("fb_token", "UTF-8") + "=" + URLEncoder.encode(fbToken, "UTF-8") + "&" +
                        URLEncoder.encode("fb_user_id", "UTF-8") + "=" + URLEncoder.encode(fbUserId, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                //e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.contains("yes")) {
                String user_id = result.replace("yes", "");
                editor = prefsUserInfo.edit();
                editor.putString("confirmation", "stop");
                editor.putString("randConfCode", "");
                editor.putString("userReg", "stop");
                editor.putString("userId", user_id);
                editor.apply();

                ContentValues userId = new ContentValues();
                ContentValues userPref = new ContentValues();
                userPref.put(DBContract.UserPref.COLUMN_USER_ID, Integer.parseInt(user_id));
                userId.put(DBContract.User.COLUMN_USER_ID, Integer.parseInt(user_id));
                userId.put(DBContract.User.COLUMN_PHONE_NUMBER, prefsUserInfo.getString("phoneNumber", ""));
                userId.put(DBContract.User.COLUMN_APP_VERSION, "1");
                db.insert(DBContract.User.TABLE_NAME, null, userId);
                db.insert(DBContract.UserPref.TABLE_NAME, null, userPref);

                startActivity(new Intent(context, MainActivity.class));
                finish();
                overridePendingTransition(0, R.anim.splash_fade);
            } else {
                Toast.makeText(getApplicationContext(), "Activation Failed Retry", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
