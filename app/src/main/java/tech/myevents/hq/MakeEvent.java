package tech.myevents.hq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static tech.myevents.hq.MainActivity.currentPage;

public class MakeEvent extends AppCompatActivity implements View.OnClickListener {

    TextView tvStartDate, tvStartTime, tvEndDate, tvEndTime, tvBroadcastCharge, tvAgentCode, tvAudience;
    TextView tvName, tvVenue, tvDetails, tvConfirmationCode;
    EditText etName, etVenue, etMinPrice, etMaxPrice, etConfirmationCode, etDetails;
    ImageView ivPoster;
    ImageButton ibPoster, ibVideo, ibDeletePoster, ibDeleteVideo;
    Spinner spinCategory, spinBroadcastRange;
    Button bPreviewEvent, bBroadcastEvent;
    AutoCompleteTextView autoCity, autoSuburb;
    LinearLayout llStartDate, llStartTime, llEndDate, llEndTime, llPoster;

    DBOperations dbOperations;
    SQLiteDatabase db;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorY;
    SharedPreferences prefUserInfo;
    Context context;
    Activity activity;

    String[] arrayCityName = new String[]{};
    String[] arrayLocationName = new String[]{};
    String[] arrayLocationId = new String[]{};
    String[] arrayBroadcastRange = new String[]{};
    String[] arrayRange = new String[]{};
    String[] arrayInterestCodes = new String[]{};
    String[] arrayCategoryNames = new String[]{};

    List<String> listCityName;
    List<String> listLocationName;
    List<String> listLocationId;
    List<String> listBroadcastRange;

    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> broadcastRangeAdapter;
    AlertDialog.Builder builder;

    String broadcastUrl = App.AppInfo.APP_URL;
    String selectedCityCode;

    int yearX, monthX, dayX, yearY, monthY, dayY;
    int hourX, minuteX, hourY, minuteY;
    static final int xDATE_DIALOG_ID = 275;
    static final int xTIME_DIALOG_ID = 282;
    static final int yDATE_DIALOG_ID = 556;
    static final int yTIME_DIALOG_ID = 543;

    final int GALLERY_REQUEST = 27277;
    final int VIDEO_REQUEST = 37377;
    GalleryPhoto galleryPhoto;
    ProgressDialog progressDialog;
    ProgressDialog dialog;

    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;
    static Bitmap eventBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 3;
        setContentView(R.layout.activity_make_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = (Activity) context;
        dbOperations = new DBOperations(this);
        db = dbOperations.getWritableDatabase();
        galleryPhoto = new GalleryPhoto(this);
        prefs = getSharedPreferences(App.AppInfo.PREF_BROADCAST_VALUES, Context.MODE_PRIVATE);
        prefUserInfo =  getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this);
        arrayInterestCodes = new String[]{"optional","1a","1b","1c","1d","1e","1f","1g","1h","1i",
                "1j","1k","1l","1m","1n","1o", "1p", "1q"};
        arrayCategoryNames= new String[]{"Choose Category","Amusement Park","Business","Clubbing","Concert","Exhibition","Festival","Band & Musicians","Music","Theatre",
                "Comedy","Retreat","Fashion","Recreational","Party","Outdoor", "Tech & Design", "Community"};

        instantiateViews();
        initializeClicks();
        getCityNames();
        loadBitmap();
        checkLocation();
        getBroadcastRange();
        retrieveValues();

        new GetAudience(context).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            currentPage = 1;
            finish();
            //overridePendingTransition(0, R.anim.splash_fade);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        currentPage = 1;
        super.onBackPressed();
    }

    private void getBroadcastRange() {
        String broadcastRange = prefs.getString("broadcastRange","");
        switch (broadcastRange) {
            case "local":
                arrayRange = new String[]{"broadcast range", "local"};
                break;
            case "city":
                arrayRange = new String[]{"broadcast range", "local", "city"};
                break;
            case "national":
                arrayRange = new String[]{"broadcast range", "local", "city", "national"};
                break;
            case "":
                arrayRange = new String[]{"select city", "& suburb", "first"};
                break;
        }
        broadcastRangeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,arrayRange);
        broadcastRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBroadcastRange.setAdapter(broadcastRangeAdapter);
    }

    private void getCityNames() {
        String cityId = prefs.getString("selectedCityCode","");
        arrayCityName = new String[]{""};
        listCityName = new LinkedList<String>(Arrays.asList(arrayCityName));

        Cursor cursor = dbOperations.getCity(db);
        while (cursor.moveToNext()) {
            listCityName.add(cursor.getString(cursor.getColumnIndex(DBContract.City.COLUMN_CITY_NAME)));
        }
        cursor.close();
        arrayCityName = listCityName.toArray(new String[listCityName.size()]);
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayCityName);
        if(!cityId.equals("")){
            getLocationNames(cityId);
        }
        searchForCity();
    }

    private void instantiateViews() {
        ibPoster = (ImageButton) findViewById(R.id.ibPoster);
        ibVideo = (ImageButton) findViewById(R.id.ibVideo);
        ibDeletePoster = (ImageButton) findViewById(R.id.ibDeletePoster);
        ibDeleteVideo = (ImageButton) findViewById(R.id.ibDeleteVideo);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        tvName = (TextView) findViewById(R.id.tvName);
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        tvDetails = (TextView) findViewById(R.id.tvDetails);
        tvConfirmationCode = (TextView) findViewById(R.id.tvConfirmationCode);
        autoCity = (AutoCompleteTextView) findViewById(R.id.autoCity);
        autoSuburb = (AutoCompleteTextView) findViewById(R.id.autoSuburb);
        llStartDate = (LinearLayout) findViewById(R.id.llStartDate);
        llStartTime = (LinearLayout) findViewById(R.id.llStartTime);
        llEndDate = (LinearLayout) findViewById(R.id.llEndDate);
        llEndTime = (LinearLayout) findViewById(R.id.llEndTime);
        llPoster = (LinearLayout) findViewById(R.id.llPoster);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvBroadcastCharge = (TextView) findViewById(R.id.tvBroadcastCharge);
        tvAgentCode = (TextView) findViewById(R.id.tvAgentCode);
        etName = (EditText) findViewById(R.id.etName);
        etVenue = (EditText) findViewById(R.id.etVenue);
        etMinPrice = (EditText) findViewById(R.id.etMinPrice);
        etMaxPrice = (EditText) findViewById(R.id.etMaxPrice);
        etConfirmationCode = (EditText) findViewById(R.id.etConfirmationCode);
        etDetails = (EditText) findViewById(R.id.etDetails);
        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        spinBroadcastRange = (Spinner) findViewById(R.id.spinBroadcastRange);
        bPreviewEvent = (Button) findViewById(R.id.bPreviewEvent);
        bBroadcastEvent = (Button) findViewById(R.id.bBroadcastEvent);
        tvAudience = (TextView) findViewById(R.id.tvAudience);

        Calendar calendar = Calendar.getInstance();
        yearX = calendar.get(Calendar.YEAR); yearY = calendar.get(Calendar.YEAR);
        monthX = calendar.get(Calendar.MONTH); monthY = calendar.get(Calendar.MONTH);
        dayX = calendar.get(Calendar.DAY_OF_MONTH); dayY = calendar.get(Calendar.DAY_OF_MONTH);
        hourX = calendar.get(Calendar.HOUR_OF_DAY); hourY = calendar.get(Calendar.HOUR_OF_DAY);
        minuteX = calendar.get(Calendar.MINUTE); minuteY = calendar.get(Calendar.MINUTE);

        categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,arrayCategoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);
    }

    private void initializeClicks() {
        editor = prefs.edit();
        ibPoster.setOnClickListener(this);
        ibVideo.setOnClickListener(this);
        ibDeletePoster.setOnClickListener(this);
        ibDeleteVideo.setOnClickListener(this);
        ivPoster.setOnClickListener(this);
        llStartDate.setOnClickListener(this);
        llStartTime.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        llEndTime.setOnClickListener(this);
        bPreviewEvent.setOnClickListener(this);
        bBroadcastEvent.setOnClickListener(this);
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int x=0; x<arrayCategoryNames.length; x++){
                    if(arrayCategoryNames[x].contains(String.valueOf(parent.getItemAtPosition(position)))){
                        if(x!=0){
                            editor.putString("categoryCode",arrayInterestCodes[x]);
                            editor.putString("selectedCategory",String.valueOf(parent.getItemAtPosition(position)));
                            editor.apply();
                        }
                    }
                }
                new GetAudience(context).execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinBroadcastRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor = prefs.edit();
                String locCode = null;
                String citCode = null;
                String getLocCode = prefs.getString("selectedLocationCode","");
                String getCitCode = prefs.getString("selectedCityCode","");

                switch (getLocCode.length()) {
                    case 1:
                        locCode = "00" + getLocCode;
                        break;
                    case 2:
                        locCode = "0" + getLocCode;
                        break;
                    case 3:
                        locCode = getLocCode;
                        break;
                }

                switch (getCitCode.length()) {
                    case 1:
                        citCode = "00" + getCitCode;
                        break;
                    case 2:
                        citCode = "0" + getCitCode;
                        break;
                    case 3:
                        citCode = getCitCode;
                        break;
                }

                switch (String.valueOf(parent.getItemAtPosition(position))){
                    case "local":
                        editor.putString("broadcastRangeCode", citCode+locCode);
                        editor.putString("selectedBroadcastRange","local");
                        editor.apply();
                        new GetAudience(context).execute();
                        break;
                    case "city":
                        editor.putString("broadcastRangeCode", citCode);
                        editor.putString("selectedBroadcastRange","city");
                        editor.apply();
                        new GetAudience(context).execute();
                        break;
                    case "national":
                        editor.putString("broadcastRangeCode", "national");
                        editor.putString("selectedBroadcastRange","national");
                        editor.apply();
                        new GetAudience(context).execute();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibPoster:
                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
                break;
            case R.id.ibVideo:
                startActivityForResult(new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI),VIDEO_REQUEST);
                break;
            case R.id.ibDeletePoster:
                deletePoster();
                break;
            case R.id.ibDeleteVideo:
                deleteVideo();
                break;
            case R.id.ivPoster:
                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
                break;
            case R.id.llStartDate:
                createFancyDateTimePicker(xDATE_DIALOG_ID).show();
                break;
            case R.id.llStartTime:
                createFancyDateTimePicker(xTIME_DIALOG_ID).show();
                break;
            case R.id.llEndDate:
                createFancyDateTimePicker(yDATE_DIALOG_ID).show();
                break;
            case R.id.llEndTime:
                createFancyDateTimePicker(yTIME_DIALOG_ID).show();
                break;
            case R.id.bPreviewEvent:
                startEventPreview();
                break;
            case R.id.bBroadcastEvent:
                broadcastEvent();
                break;
        }
    }




    private void startEventPreview() {
        startActivity(new Intent(this, PreviewEvent.class));
    }

    private void broadcastEvent() {
        final String userId = prefUserInfo.getString("userId","");
        final String interestCode = prefs.getString("categoryCode","");
        final String locationCode = prefs.getString("locationCode","");
        final String broadcastRangeCode = prefs.getString("broadcastRangeCode","");
        final String startTimestamp = prefs.getString("startTimestamp","");
        final String endTimestamp = prefs.getString("endTimestamp","");
        final String broadcastPrice = prefs.getString("broadcastPrice", "");
        final String broadcastCharge = prefs.getString("broadcastCharge","");
        final String imageAvail = prefs.getString("imageAvail","");

        final String name, venue, autoCity2, autoSuburb2, startDate, startTime, endDate, endTime, confCode, details, minPrice, maxPrice;
        String confCode1;
        name = etName.getText().toString();
        venue = etVenue.getText().toString();
        autoCity2 = autoCity.getText().toString();
        autoSuburb2 = autoSuburb.getText().toString();
        startDate = tvStartDate.getText().toString();
        startTime = tvStartTime.getText().toString();
        endDate = tvEndDate.getText().toString();
        endTime = tvEndTime.getText().toString();
        confCode1 = etConfirmationCode.getText().toString();
        details = etDetails.getText().toString();
        minPrice = etMinPrice.getText().toString();
        maxPrice = etMaxPrice.getText().toString();

        StringBuilder stringBuilder = new StringBuilder();
        if (broadcastPrice.equals("free")) {
            confCode1 = "its for free";
        }
        confCode = confCode1;
        if (imageAvail.equals("") || name.equals("") || name.length() <5 || venue.equals("") || venue.length() < 5 ||
                autoCity2.equals("") || autoSuburb2.equals("") || startDate.equals("") ||
                startTime.equals("") || endDate.equals("")||endTime.equals("")||confCode.equals("")) {
            if (imageAvail.equals("")) {
                stringBuilder.append("- Include Event Poster\n\n");
            }
            if (name.equals("") || name.length() < 5) {
                stringBuilder.append("- Event name must be +5 characters\n\n");
            }
            if (venue.equals("") || venue.length() < 5) {
                stringBuilder.append("- Venue must be +5 characters\n\n");
            }
            if (autoCity2.equals("")) {
                stringBuilder.append("- Event City is Required\n\n");
            }
            if (autoSuburb2.equals("")) {
                stringBuilder.append("- Event Suburb is Required\n\n");
            }
            if (startDate.equals("")) {
                stringBuilder.append("- Fill in Start Date\n\n");
            }
            if (startTime.equals("")) {
                stringBuilder.append("- Fill in Start Time\n\n");
            }
            if (endDate.equals("")) {
                stringBuilder.append("- Fill in End Date\n\n");
            }
            if (endTime.equals("")) {
                stringBuilder.append("- Fill in End Time\n\n");
            }
            if (broadcastRangeCode.equals("")) {
                stringBuilder.append("- Choose your Broadcast Range\n\n");
            }
            if (interestCode.equals("")) {
                stringBuilder.append("- Choose your Ad Category\n\n");
            }
            if (confCode.equals("") || confCode.length() < 3) {
                stringBuilder.append("- Include the Confirmation Code you received from Ecocash");
            }
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Empty Field(s)");
            builder.setMessage(stringBuilder);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do Nothing
                }
            });
            builder.show();
        } else {
            new AlertDialog.Builder(this).setTitle("Broadcast Confirmation")
                    .setMessage("Your Event will be reviewed and then Broadcast considering it is appropriate." +
                            "\n\nIf not you will be refunded considering the broadcast was charged." +
                            "\n\nKeep Your Network Connection open, broadcast may take a few seconds if a video is attached." +
                            "\n\nThank You ......\n")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AirEventBroadcast airEventBroadcast = new AirEventBroadcast();
                            airEventBroadcast.execute(userId,interestCode,locationCode,broadcastRangeCode,broadcastCharge,
                                    name,venue,details,minPrice,maxPrice,startTimestamp,endTimestamp);
                            //Toast.makeText(getApplicationContext(), "App is not in Production Mode", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    }).show();
        }
    }

    public void deleteBroadcastValues() {
        editor = prefs.edit();
        editor.clear().apply();
        etName.setText("");
        etVenue.setText("");
        autoCity.setText("");
        autoSuburb.setText("");
        tvStartDate.setText("");
        tvStartTime.setText("");
        tvEndDate.setText("");
        tvEndTime.setText("");
        etMinPrice.setText("");
        etMaxPrice.setText("");
        etDetails.setText("");
        etConfirmationCode.setText("");
        editor.putString("spotId","");
        editor.apply();
        deletePoster();
        deleteVideo();
        eventBitmap = null;
    }





    protected Dialog createFancyDateTimePicker(int id) {
        Calendar calendar = Calendar.getInstance();
        switch (id) {
            case xDATE_DIALOG_ID:
                return new DatePickerDialog(this, xDateSetListener, yearX, monthX, dayX);

            case xTIME_DIALOG_ID:
                hourX = calendar.get(Calendar.HOUR_OF_DAY);
                minuteX =calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(this, xTimeSetListener, hourX, minuteX, true);

            case yDATE_DIALOG_ID:
                return new DatePickerDialog(this, yDateSetListener, yearY, monthY, dayY);

            case yTIME_DIALOG_ID:
                hourY = calendar.get(Calendar.HOUR_OF_DAY);
                minuteY = calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(this, yTimeSetListener, hourY, minuteY, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener xDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearX = year;
            monthX = monthOfYear;
            dayX = dayOfMonth;
            tvStartDate.setText(new StringBuilder().append(getTheValue(dayX)).append("/")
                    .append(getTheValue(monthX+1)).append("/").append(getTheValue(yearX)));
            checkTimeDateValues("x");
        }
    };

    private TimePickerDialog.OnTimeSetListener xTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourX = hourOfDay;
            minuteX = minute;
            tvStartTime.setText(new StringBuilder().append(getTheValue(hourX)).append(":").append(getTheValue(minuteX)));
            checkTimeDateValues("x");
        }
    };

    private DatePickerDialog.OnDateSetListener yDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearY = year;
            monthY = monthOfYear;
            dayY = dayOfMonth;
            tvEndDate.setText(new StringBuilder().append(getTheValue(dayY)).append("/").append(getTheValue(monthY+1))
                    .append("/").append(getTheValue(yearY)));
            checkTimeDateValues("y");
        }
    };

    private TimePickerDialog.OnTimeSetListener yTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourY = hourOfDay;
            minuteY = minute;
            tvEndTime.setText(new StringBuilder().append(getTheValue(hourY)).append(":").append(getTheValue(minuteY)));
            checkTimeDateValues("y");
        }
    };

    public void checkTimeDateValues(String source){
        if(source.equals("x")) {
            if (!tvStartTime.getText().toString().equals("") && !tvStartDate.getText().toString().equals("")) {
                generateTimestamp("x", monthX, yearX, dayX, hourX, minuteX);
            }
        }
        else if(source.equals("y")) {
            if (!tvEndTime.getText().toString().equals("") && !tvEndDate.getText().toString().equals("")) {
                generateTimestamp("y", monthY, yearY, dayY, hourY, minuteY);
            }
        }
    }

    public void generateTimestamp(String source,int month, int year, int day, int hour, int minute){
        editor = prefs.edit();
        String preEnd = prefs.getString("endTimestamp","");
        String preStart = prefs.getString("startTimestamp","");
        long currentTimestamp = getCurrentTimestamp();
        if(source.equals("x")){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR, year);
            c.set(Calendar.DAY_OF_MONTH,day);
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE,minute);

            long startTimestamp = c.getTimeInMillis();
            if(startTimestamp <= currentTimestamp ){
                Toast.makeText(getApplicationContext(),"Cannot set date before Current date",Toast.LENGTH_SHORT).show();
                tvStartDate.setText("");
                tvStartTime.setText("");
                editor.putString("startTime","");
                editor.putString("startDate","");
                editor.apply();
                return;
            }
            if (!preEnd.equals("")) {
                if (startTimestamp > Long.parseLong(preEnd)) {
                    Toast.makeText(getApplicationContext(), "Start Date is after End Date", Toast.LENGTH_SHORT).show();
                    tvStartDate.setText("");
                    tvStartTime.setText("");
                    editor.putString("startTime","");
                    editor.putString("startDate","");
                    editor.apply();
                    return;
                }
            }
            editor.putString("startTimestamp", String.valueOf(startTimestamp));
            //tvStartDate.setText(String.valueOf(startTimestamp));
            editor.putString("startDate",String.valueOf(new StringBuilder().append(getTheValue(day)).append("/").append(getTheValue(month+1)).append("/").append(year)));
            editor.putString("startTime",String.valueOf(new StringBuilder().append(getTheValue(hour)).append(":").append(getTheValue(minute))));
            editor.apply();
        } else if (source.equals("y")){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR, year);
            c.set(Calendar.DAY_OF_MONTH,day);
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE,minute);

            long endTimestamp = c.getTimeInMillis();
            if(endTimestamp <= currentTimestamp ){
                Toast.makeText(getApplicationContext(),"Cannot set date before Current date",Toast.LENGTH_SHORT).show();
                tvEndDate.setText("");
                tvEndTime.setText("");
                editor.putString("endTime","");
                editor.putString("endDate","");
                editor.apply();
                return;
            }
            if(!preStart.equals("")) {
                if (endTimestamp < Long.parseLong(preStart)) {
                    Toast.makeText(getApplicationContext(), "End Date is before Start Date", Toast.LENGTH_SHORT).show();
                    tvEndDate.setText("");
                    tvEndTime.setText("");
                    editor.putString("endTime","");
                    editor.putString("endDate","");
                    editor.apply();
                    return;
                }
            }
            editor.putString("endTimestamp", String.valueOf(endTimestamp));
            //tvEndDate.setText(String.valueOf(endTimestamp));
            editor.putString("endDate", String.valueOf(new StringBuilder().append(getTheValue(day)).append("/").append(getTheValue(month+1)).append("/").append(year)));
            editor.putString("endTime",String.valueOf(new StringBuilder().append(getTheValue(hour)).append(":").append(getTheValue(minute))));
            editor.apply();
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

    public String getTheValue(int value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }











    private void searchForCity() {
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayCityName);
        autoCity.setAdapter(cityAdapter);
        autoCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityOperations(parent,position);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    private void cityOperations(AdapterView<?> parent, int position) {
        editor = prefs.edit();
        String cityCode = "";
        for(int x=0; x<arrayCityName.length; x++){
            if(arrayCityName[x].contains(String.valueOf(parent.getItemAtPosition(position)))){
                cityCode = String.valueOf(x);
                String codeToPut = "";
                switch (cityCode.length()){
                    case 1:
                        codeToPut = "00"+cityCode;
                        break;
                    case 2:
                        codeToPut = "0"+cityCode;
                        break;
                    case 3:
                        codeToPut = ""+cityCode;
                        break;
                }
                editor.putString("searchArray","location");
                editor.putString("locationCode",codeToPut);
                editor.putString("selectedCityCode", cityCode);
                editor.putString("selectedCity", String.valueOf(parent.getItemAtPosition(position)));
                editor.putString("selectedLocation","");
                editor.putString("broadcastRange","");
                editor.putString("selectedLocationCode","");
                editor.apply();
                autoSuburb.setText("");
            }
        }
        new GetAudience(context).execute();
        getBroadcastRange();
        getLocationNames(cityCode);
    }

    private void getLocationNames(String cityId){
        arrayLocationName = new String[]{""};
        arrayLocationId = new String[]{""};
        arrayBroadcastRange = new String[]{""};
        listLocationName = new LinkedList<String>(Arrays.asList(arrayLocationName));
        listLocationId = new LinkedList<String>(Arrays.asList(arrayLocationId));
        listBroadcastRange = new LinkedList<String>(Arrays.asList(arrayBroadcastRange));

        Cursor cursor = dbOperations.getLocation(db, cityId);
        while (cursor.moveToNext()) {
            listLocationName.add(cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_NAME)));
            listLocationId.add(cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_ID)));
            listBroadcastRange.add(cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE)));
        }
        cursor.close();
        arrayLocationName = listLocationName.toArray(new String[listLocationName.size()]);
        arrayLocationId = listLocationId.toArray(new String[listLocationId.size()]);
        arrayBroadcastRange = listBroadcastRange.toArray(new String[listBroadcastRange.size()]);

        searchForLocation();
    }

    private void searchForLocation() {
        locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayLocationName);
        autoSuburb.setAdapter(locationAdapter);
        autoSuburb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationOperations(parent, position);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                getBroadcastRange();
            }
        });
    }

    private void locationOperations(AdapterView<?> parent, int position) {
        editor = prefs.edit();
        int locCode;
        for(int x = 0; x < arrayLocationName.length; x++){
            if(arrayLocationName[x].equals(String.valueOf(parent.getItemAtPosition(position)))){
                locCode = x;
                String codeLen = arrayLocationId[locCode];
                String codeToPut = "";
                String locationCode = prefs.getString("locationCode","");
                switch (codeLen.length()) {
                    case 1:
                        codeToPut = "00" + arrayLocationId[locCode];
                        break;
                    case 2:
                        codeToPut = "0" + arrayLocationId[locCode];
                        break;
                    case 3:
                        codeToPut = arrayLocationId[locCode];
                        break;
                }
                if (locationCode.length() == 3){
                    editor.putString("locationCode",locationCode+codeToPut);
                } else {
                    String cityCode = locationCode.substring(0,3);
                    editor.putString("locationCode",cityCode + codeToPut);
                }
                editor.putString("broadcastRange",arrayBroadcastRange[locCode]);
                editor.putString("selectedLocation", String.valueOf(parent.getItemAtPosition(position)));
                editor.putString("selectedLocationCode",arrayLocationId[locCode]);
                editor.apply();
            }
        }
        new GetAudience(context).execute();
    }

    private void checkLocation() {
        if(!prefs.getString("selectedCity","").equals("")){
            autoCity.setText(prefs.getString("selectedCity",""));
        }
        else {
            autoCity.setText("");
            autoCity.setHint("Not Selected");
        }
        if(!prefs.getString("selectedLocation","").equals("")){
            autoSuburb.setText(prefs.getString("selectedLocation",""));
        }
        else {
            autoSuburb.setText("");
            autoSuburb.setHint("Not Selected");
        }
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,400,resources.getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int ibDeleteScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,resources.getDisplayMetrics());

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            handlePosterResult(data, height, width,ibDeleteScale);
        } else if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            String videoPath = getRealPathFromURI(this, uri);
            File file = new File(videoPath);
            int size = Integer.parseInt(String.valueOf((file.length() / 1024) / 1024));
            if (size > 6) {
                Toast.makeText(getApplicationContext(), "Video must be 6MB or Less", Toast.LENGTH_SHORT).show();
            } else {
                handleVideoResult(data, ibDeleteScale);
            }
        }
    }

    private void handlePosterResult(Intent data, int height, int width, int ibDeleteScale) {
        editor = prefs.edit();
        Uri uri = data.getData();
        String posterPath = getRealPathFromURI(this,uri);

        File newDir = new File("sdcard/MyEvents");
        if (!newDir.exists()) {
            newDir.mkdir();
        }
        File posterDir = new File(newDir,"PreBroadcast");
        if(!posterDir.exists()){
            posterDir.mkdir();
        }
        File toPath = new File(posterDir,"pre_broadcast.JPEG");
        try {
            FileInputStream readFrom = new FileInputStream(posterPath);
            FileOutputStream readTo = new FileOutputStream(toPath);

            byte[] oneKBData = new byte[1024];
            int x;
            while ((x = readFrom.read(oneKBData)) > 0){
                readTo.write(oneKBData,0,x);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        llPoster.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
        eventBitmap = BitmapFactory.decodeFile(imagePath);
        RoundedBitmapDrawable imagePoster = RoundedBitmapDrawableFactory.create(getResources(), eventBitmap);
        imagePoster.setCornerRadius(5);
        ivPoster.setImageDrawable(imagePoster);
        ibPoster.setImageResource(R.drawable.ib_poster_teal);
        ibDeletePoster.setLayoutParams(new LinearLayout.LayoutParams(ibDeleteScale,ibDeleteScale));
        ibDeletePoster.setImageResource(R.drawable.ib_delete);
        editor.putString("imageAvail","yes");
        editor.apply();
    }

    private void handleVideoResult(Intent data, int ibDeleteScale) {
        editor = prefs.edit();
        Uri uri = data.getData();
        String videoPath = getRealPathFromURI(this,uri);

        File newDir = new File("sdcard/MyEvents");
        if (!newDir.exists()) {
            newDir.mkdir();
        }
        File posterDir = new File(newDir,"PreBroadcast");
        if(!posterDir.exists()){
            posterDir.mkdir();
        }
        File toPath = new File(posterDir,"pre_broadcast.mp4");
        try {
            FileInputStream readFrom = new FileInputStream(videoPath);
            FileOutputStream readTo = new FileOutputStream(toPath);

            byte[] oneKBData = new byte[1024];
            int x;
            while ((x = readFrom.read(oneKBData)) > 0){
                readTo.write(oneKBData,0,x);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ibVideo.setImageResource(R.drawable.ib_video_teal);
        ibDeleteVideo.setLayoutParams(new LinearLayout.LayoutParams(ibDeleteScale,ibDeleteScale));
        ibDeleteVideo.setImageResource(R.drawable.ib_delete);
        editor.putString("videoAvail","yes");
        editor.apply();
    }

    public void loadBitmap(){
        String imageAvail = prefs.getString("imageAvail","");
        String videoAvail = prefs.getString("videoAvail","");
        final String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");

        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,400,resources.getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int ibDeleteScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,resources.getDisplayMetrics());

        if(imageFile.exists()) {
            llPoster.setLayoutParams(new LinearLayout.LayoutParams(width,height));
            //new LoadImage(context).execute();
            /*Glide.with(this)
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_placeholder)
                    .into(ivPoster);*/
            if (eventBitmap == null) {
                eventBitmap = BitmapFactory.decodeFile(imagePath);
            }

            RoundedBitmapDrawable imagePoster;
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), eventBitmap);
            imagePoster.setCornerRadius(3);
            ivPoster.setImageDrawable(imagePoster);
            ibDeletePoster.setImageResource(R.drawable.ib_delete);
        }
        if (imageAvail.equals("yes")){
            ibDeletePoster.setLayoutParams(new LinearLayout.LayoutParams(ibDeleteScale,ibDeleteScale));
            ibPoster.setImageResource(R.drawable.ib_poster_teal);
            ibDeletePoster.setImageResource(R.drawable.ib_delete);
        }
        if (videoAvail.equals("yes")){
            ibDeleteVideo.setLayoutParams(new LinearLayout.LayoutParams(ibDeleteScale,ibDeleteScale));
            ibVideo.setImageResource(R.drawable.ib_video_teal);
            ibDeleteVideo.setImageResource(R.drawable.ib_delete);
        }
    }

    private void deletePoster() {
        editor = prefs.edit();
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG");
        imageFile.delete();
        llPoster.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ivPoster.setImageResource(0);
        ibDeletePoster.setImageResource(0);
        ibPoster.setImageResource(R.drawable.ib_poster_gray);
        editor.putString("imageAvail","");
        editor.apply();
    }

    private void deleteVideo() {
        editor = prefs.edit();
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
        videoFile.delete();
        ibDeletePoster.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ibDeleteVideo.setImageResource(0);
        ibVideo.setImageResource(R.drawable.ib_video_gray);
        editor.putString("videoAvail","");
        editor.apply();
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projections = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, projections, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void retrieveValues() {
        etName.setText(prefs.getString("eventName",""));
        if (!prefs.getString("eventName","").equals("")){
            tvName.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvName.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvName.setText("event name");
        }
        etVenue.setText(prefs.getString("eventVenue",""));
        if (!prefs.getString("eventVenue","").equals("")){
            tvVenue.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvVenue.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvVenue.setText("event venue");
        }
        etMinPrice.setText(prefs.getString("minPrice",""));
        etMaxPrice.setText(prefs.getString("maxPrice",""));
        tvStartDate.setText(prefs.getString("startDate",""));
        tvStartTime.setText(prefs.getString("startTime",""));
        tvEndDate.setText(prefs.getString("endDate",""));
        tvEndTime.setText(prefs.getString("endTime",""));
        etDetails.setText(prefs.getString("details",""));
        if (!prefs.getString("details","").equals("")){
            tvDetails.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvDetails.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvDetails.setText("event details");
        }
        tvBroadcastCharge.setText(prefs.getString("broadcastCharge",""));
        tvAudience.setText(prefs.getString("audience",""));
        etConfirmationCode.setText(prefs.getString("confirmationCode",""));
        if (!prefs.getString("confirmationCode","").equals("")){
            tvConfirmationCode.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvConfirmationCode.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvConfirmationCode.setText("confirmation code");
        }
        spinCategory.setSelection(categoryAdapter.getPosition(prefs.getString("selectedCategory","")));
        spinBroadcastRange.setSelection(broadcastRangeAdapter.getPosition(prefs.getString("selectedBroadcastRange","")));

        StringBuilder builder = new StringBuilder();
        if (prefs.getString("broadcastPrice","").equals("paid")) {
            builder.append("$ ").append(prefs.getString("broadcastCharge", "...")).append(".00");
        } else {
            builder.append(prefs.getString("broadcastCharge", "..."));
        }
        tvBroadcastCharge.setText(builder);
        tvAgentCode.setText(prefs.getString("ecocashCode","..."));
        tvAudience.setText(prefs.getString("audience","..."));
    }

    @Override
    public void onPause() {
        super.onPause();
        editor = prefs.edit();
        editor.putString("eventName", etName.getText().toString());
        editor.putString("eventVenue", etVenue.getText().toString());
        editor.putString("minPrice", etMinPrice.getText().toString());
        editor.putString("maxPrice", etMaxPrice.getText().toString());
        editor.putString("details", etDetails.getText().toString());
        editor.putString("confirmationCode", etConfirmationCode.getText().toString());
        editor.apply();
    }







    class AirEventBroadcast extends AsyncTask<String, Void, String> {
        String mediaName, eventName, eventVenue, bitmapName;
        String video;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", "Processing Event Details ...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            editor = prefs.edit();
            Random random = new Random();
            long rand = random.nextInt(100);
            long currentTimestamp = getCurrentTimestamp();
            StringBuilder s = new StringBuilder();
            s.append(currentTimestamp).append("").append(rand);
            mediaName = String.valueOf(s);
            editor.putString("mediaName", mediaName);
            editor.apply();

            File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
            if (videoFile.exists()) {
                video = "yes";
            } else {
                video = "no";
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            eventBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            final String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", params[0]);
                jsonObject.put("interest_code", params[1]);
                jsonObject.put("location_code", params[2]);
                jsonObject.put("broadcast_range_code", params[3]);
                jsonObject.put("name", params[5]);
                jsonObject.put("venue", params[6]);
                jsonObject.put("details", params[7]);
                jsonObject.put("min_price", params[8]);
                jsonObject.put("max_price", params[9]);
                jsonObject.put("start_timestamp", params[10]);
                jsonObject.put("end_timestamp", params[11]);
                jsonObject.put("broadcast_charge", params[4]);
                jsonObject.put("media_name", mediaName);
                jsonObject.put("video", video);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            eventName = params[5];
            eventVenue = params[6];

            final String json = jsonObject.toString();
            try {
                URL url = new URL(broadcastUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("broadcast_event", "UTF-8") + "&" +
                        URLEncoder.encode("json_string", "UTF-8") + "=" + URLEncoder.encode(json, "UTF-8") + "&" +
                        URLEncoder.encode("bitmap", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8");
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

            } catch (Exception e) {
                //e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            dialog.dismiss();
            if (response.contains("yes")) {
                bitmapName = response.replace("yes", "");
                notificationBuilder.setOngoing(false);
                notificationBuilder.setContentTitle("Event Broadcast Successful")
                        .setContentText("Thank You for Choosing Our Service!!")
                        .setTicker("Event Review...")
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
                notificationBuilder.setProgress(0, 0, false);
                notificationManager.notify(6238, notificationBuilder.build());

                etName.setText("");
                etVenue.setText("");
                autoCity.setText("");
                autoSuburb.setText("");
                tvStartDate.setText("");
                tvStartTime.setText("");
                tvEndDate.setText("");
                tvEndTime.setText("");
                etMinPrice.setText("");
                etMaxPrice.setText("");
                etDetails.setText("");
                etConfirmationCode.setText("");

                File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
                String selectedVidPath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4";
                if (videoFile.exists()) {
                    askToShare();
                    new UploadBroadcastVideo(context, prefs.getString("mediaName", ""), "Event Video Uploaded",
                            "Uploading video to Broadcast Server", "Uploading video to Broadcast Server",
                            "MyEvents", selectedVidPath, "event").execute();
                    //Toast.makeText(getApplicationContext(), "Video Available", Toast.LENGTH_LONG).show();
                } else {
                    askToShare();
                    Toast.makeText(getApplicationContext(), "Broadcast Successful", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Broadcast Failed try again in a moment!", Toast.LENGTH_LONG).show();
                notificationBuilder.setOngoing(false);
                notificationBuilder.setContentTitle("MyEvents")
                        .setContentText("Event Broadcast Failed! Try Again in a Moment")
                        .setTicker("Broadcast Failed")
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
                notificationBuilder.setProgress(0, 0, false);
                notificationManager.notify(6236, notificationBuilder.build());
            }
        }

        private void askToShare() {
            new AlertDialog.Builder(context).setTitle("Share on Facebook")
                    .setMessage("Would you like to share your event on Facebook")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shareOnFb();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteBroadcastValues();
                        }
                    }).show();
            Toast.makeText(getApplicationContext(), "Broadcast Successful", Toast.LENGTH_LONG).show();
        }

        private void shareOnFb() {
            deleteBroadcastValues();
        }

        long getCurrentTimestamp() {
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

    class GetAudience extends AsyncTask<Void, Void, Void> {
        Context context;

        GetAudience(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String broadcastRangeCode = prefs.getString("broadcastRangeCode","");
            final String categoryCode = prefs.getString("categoryCode","");
            final String broadcastRange = prefs.getString("selectedBroadcastRange","");

            StringRequest stringRequest = new StringRequest(Request.Method.POST, broadcastUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
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
                    params.put("action", "get_event_audience");
                    params.put("broadcast_range_code", broadcastRangeCode);
                    params.put("category_code",categoryCode);
                    params.put("broadcast_range", broadcastRange);

                    return params;
                }
            };
            MySingleton.getInstance(context).addToRequestQue(stringRequest);

            return null;
        }

        private void getTheData(String response) {
            editor = prefs.edit();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                int count = 0;
                while (count<jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);
                    String respo = jo.getString("response");
                    if (respo.equals("more")) {
                        editor.putString("broadcastPrice","paid");
                    } else {
                        editor.putString("broadcastPrice","free");
                    }
                    editor.putString("broadcastCharge", jo.getString("broadcast_charge"));
                    editor.putString("audience", getAud(jo.getString("audience")));
                    editor.putString("ecocashCode", jo.getString("ecocash_code"));
                    editor.apply();

                    count++;
                }

                StringBuilder builder = new StringBuilder();
                if (prefs.getString("broadcastPrice","").equals("paid")) {
                    builder.append("$ ").append(prefs.getString("broadcastCharge", "...")).append(".00");
                } else {
                    builder.append(prefs.getString("broadcastCharge", "..."));
                }

                tvBroadcastCharge.setText(builder);
                tvAgentCode.setText(prefs.getString("ecocashCode","..."));
                tvAudience.setText(prefs.getString("audience","..."));
            } catch (JSONException e) {
                //showToast("An Error OccurredY");
            }
        }

        private String getAud(String audience) {
            char[] aud = audience.toCharArray();
            int len = audience.length();
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < aud.length; x++) {
                if (x == len - 9) {
                    if (len != 9) {
                        builder.append(",");
                    }
                }
                if (x == len - 6) {
                    if (len != 6) {
                        builder.append(",");
                    }
                }
                if (x == len - 3) {
                    if (len != 3) {
                        builder.append(",");
                    }
                }
                builder.append(aud[x]);
            }

            return String.valueOf(builder);
        }
    }

    class LoadImage extends AsyncTask<Void, Void, Void> {
        Context context;
        RoundedBitmapDrawable imagePoster = null;

        LoadImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ivPoster.setImageResource(R.drawable.glide_placeholder);
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String imagePath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.JPEG";
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imagePoster = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            imagePoster.setCornerRadius(3);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ivPoster.setImageDrawable(imagePoster);
        }

    }

}
