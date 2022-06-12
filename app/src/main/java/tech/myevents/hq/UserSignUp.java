package tech.myevents.hq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import java.util.Random;

public class UserSignUp extends AppCompatActivity {

    String appUrl = App.AppInfo.APP_URL;
    EditText etPhoneNumber;
    int n1,n2,n3,n4;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait .....");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Processing Your Contact ....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.barColor));
    }

    public void numberSignUp(View view){
        if(!etPhoneNumber.getText().toString().equals("")) {
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

                    SharedPreferences confirmationCode = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = confirmationCode.edit();
                    editor.putString("randConfCode", randConfCode);
                    editor.putString("phoneNumber", phoneNumber);
                    editor.apply();

                    SignUpTask signUpTask = new SignUpTask();
                    signUpTask.execute(phoneNumber, randConfCode);
                } else {
                    Toast.makeText(getApplicationContext(), "Input a valid Zim Mobile Number", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Cannot Establish Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Enter Your Mobile Number",Toast.LENGTH_LONG).show();
        }
    }

    class SignUpTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String signUpUrl = App.AppInfo.APP_URL;
            try {
                URL url = new URL(signUpUrl);
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
            if(result.contains("success")){
                startActivity(new Intent(getApplicationContext(), UserConfirmation.class));
            } else {
                Toast.makeText(getApplicationContext(),"Contact Processing Failed....",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
