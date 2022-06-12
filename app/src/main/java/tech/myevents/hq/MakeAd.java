package tech.myevents.hq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class MakeAd extends AppCompatActivity implements View.OnClickListener {
    TextView tvTitle, tvBrandName, tvDesc1, tvDesc2, tvDesc3, tvDesc4, tvLocation, tvBroadcastCharge, tvAgentCode, tvAudience;
    EditText etTitle, etBrandName, etDesc1, etDesc2, etDesc3, etDesc4, etConfirmationCode;
    ImageView ivPoster;
    ImageButton ibPoster, ibVideo, ibDeletePoster, ibDeleteVideo;
    LinearLayout llPoster, llCity, llSuburb, llLocation;
    Spinner spinCategory, spinBroadcastRange, spinDuration;
    AutoCompleteTextView autoCity, autoSuburb;
    Button bPreviewAd, bBroadcastAd;

    String[] arrayCategoryNames = new String[]{};
    String[] arrayBroadcastRange = new String[]{};
    String[] arrayInterestCodes = new String[]{};
    String[] arrayCityName = new String[]{};
    String[] arrayLocationName = new String[]{};
    String[] arrayLocationId = new String[]{};
    String[] arrayDuration = new String[]{};

    List<String> listCityName;
    List<String> listLocationName;
    List<String> listLocationId;
    List<String> listBroadcastRange;

    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> broadcastRangeAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> durationAdapter;

    final int GALLERY_REQUEST = 27277;
    final int VIDEO_REQUEST = 37377;
    GalleryPhoto galleryPhoto;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorY;
    SharedPreferences prefUserInfo;
    DBOperations dbOperations;
    SQLiteDatabase db;
    Activity activity;
    Context context;
    AlertDialog.Builder builder;
    ProgressDialog dialog;

    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;

    String broadcastUrl = App.AppInfo.APP_URL;
    static Bitmap adBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 4;
        setContentView(R.layout.activity_make_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = (Activity) context;
        dbOperations = new DBOperations(this);
        db = dbOperations.getWritableDatabase();
        galleryPhoto = new GalleryPhoto(this);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this);

        prefs = getSharedPreferences(App.AppInfo.PREF_AD_VALUES, Context.MODE_PRIVATE);
        prefUserInfo =  getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        arrayInterestCodes = new String[]{"optional","1a","1b","1c","1d","1e","1f","1g","1h","1i","1j","1k","1l","1m","1n","1o", "1p", "1q"};
        arrayBroadcastRange = new String[]{"Choose Range","Local","City","National"};
        arrayDuration = new String[]{"Choose Duration", "2 Days", "4 Days", "7 Days", "10 Days"};
        arrayCategoryNames= new String[]{"Choose Category","Amusement Park","Business","Clubbing","Concert","Exhibition","Festival","Band & Musicians","Music","Theatre",
                "Comedy","Retreat","Fashion","Recreational","Party","Outdoor", "Tech & Design", "Community"};

        initializeViews();
        initializeClicks();
        loadBitmap();
        getCityNames();
        checkLocation();
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

    private void initializeViews() {
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        ibPoster = (ImageButton) findViewById(R.id.ibPoster);
        ibVideo = (ImageButton) findViewById(R.id.ibVideo);
        ibDeletePoster = (ImageButton) findViewById(R.id.ibDeletePoster);
        ibDeleteVideo = (ImageButton) findViewById(R.id.ibDeleteVideo);
        llPoster = (LinearLayout) findViewById(R.id.llPoster);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etTitle = (EditText) findViewById(R.id.etTitle);
        tvBrandName = (TextView) findViewById(R.id.tvBrandName);
        etBrandName = (EditText) findViewById(R.id.etBrandName);
        tvDesc1 = (TextView) findViewById(R.id.tvDesc1);
        etDesc1 = (EditText) findViewById(R.id.etDesc1);
        tvDesc2 = (TextView) findViewById(R.id.tvDesc2);
        etDesc2 = (EditText) findViewById(R.id.etDesc2);
        tvDesc3 = (TextView) findViewById(R.id.tvDesc3);
        etDesc3 = (EditText) findViewById(R.id.etDesc3);
        tvDesc4 = (TextView) findViewById(R.id.tvDesc4);
        etDesc4 = (EditText) findViewById(R.id.etDesc4);
        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        spinBroadcastRange = (Spinner) findViewById(R.id.spinBroadcastRange);
        spinDuration = (Spinner) findViewById(R.id.spinDuration);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        llCity = (LinearLayout) findViewById(R.id.llCity);
        llSuburb = (LinearLayout) findViewById(R.id.llSuburb);
        llLocation = (LinearLayout) findViewById(R.id.llLocation);
        autoCity = (AutoCompleteTextView) findViewById(R.id.autoCity);
        autoSuburb = (AutoCompleteTextView) findViewById(R.id.autoSuburb);
        tvAudience = (TextView) findViewById(R.id.tvAudience);
        tvBroadcastCharge = (TextView) findViewById(R.id.tvBroadcastCharge);
        tvAgentCode = (TextView) findViewById(R.id.tvAgentCode);
        etConfirmationCode = (EditText) findViewById(R.id.etConfirmationCode);
        bPreviewAd = (Button) findViewById(R.id.bPreviewAd);
        bBroadcastAd = (Button) findViewById(R.id.bBroadcastAd);

        categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,arrayCategoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        broadcastRangeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,arrayBroadcastRange);
        broadcastRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,arrayDuration);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinCategory.setAdapter(categoryAdapter);
        spinBroadcastRange.setAdapter(broadcastRangeAdapter);
        spinDuration.setAdapter(durationAdapter);
        etBrandName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    private void initializeClicks() {
        editor = prefs.edit();
        ibPoster.setOnClickListener(this);
        ibVideo.setOnClickListener(this);
        ibDeletePoster.setOnClickListener(this);
        ibDeleteVideo.setOnClickListener(this);
        ivPoster.setOnClickListener(this);
        bBroadcastAd.setOnClickListener(this);
        bPreviewAd.setOnClickListener(this);
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
                String viewChange = prefs.getString("viewChange","");
                switch (String.valueOf(parent.getItemAtPosition(position))){
                    case "Local":
                        editor.putString("selectedBroadcastRange","Local");
                        editor.putString("viewChange","local");
                        if (!viewChange.equals("local")) {
                            //startActivity
                            editor.apply();
                            changeView();
                        }
                        editor.apply();
                        break;
                    case "City":
                        editor.putString("selectedBroadcastRange","City");
                        editor.putString("viewChange","city");
                        if (!viewChange.equals("city")) {
                            //startActivity
                            editor.apply();
                            changeView();
                        }
                        editor.apply();
                        break;
                    case "National":
                        editor.putString("selectedBroadcastRange","National");
                        editor.putString("broadcastRangeCode", "national");
                        editor.putString("viewChange","");
                        if (!viewChange.equals("")) {
                            //startActivity
                            editor.apply();
                            changeView();
                        }
                        editor.apply();
                        break;
                    case "Choose Range":
                        editor.putString("selectedBroadcastRange","");
                        editor.putString("broadcastRangeCode", "");
                        editor.putString("viewChange","");
                        if (!viewChange.equals("")) {
                            //startActivity
                            editor.apply();
                            changeView();
                        }
                        editor.apply();
                        break;
                }
                new GetAudience(context).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor = prefs.edit();
                editor.putString("adDuration", String.valueOf(parent.getItemAtPosition(position)));
                editor.apply();
                new GetAudience(context).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void changeView() {
        Intent i = new Intent(this,MakeAd.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
        overridePendingTransition(0, R.anim.splash_fade);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibPoster:
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                break;
            case R.id.ibVideo:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), VIDEO_REQUEST);
                break;
            case R.id.ibDeletePoster:
                deletePoster();
                break;
            case R.id.ibDeleteVideo:
                deleteVideo();
                break;
            case R.id.ivPoster:
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                break;
            case R.id.bPreviewAd:
                previewAd();
                break;
            case R.id.bBroadcastAd:
                broadcastAd();
                break;
        }
    }



    private void previewAd() {
        startActivity(new Intent(this, PreviewAd.class));
    }

    private void broadcastAd() {
        final String userId = prefUserInfo.getString("userId","");
        final String xInterestCode = prefs.getString("selectedCategory","");
        final String xBroadcastRangeCode = prefs.getString("selectedBroadcastRange","");
        final String interestCode = prefs.getString("categoryCode","");
        final String locationCode = prefs.getString("locationCode","");
        final String broadcastRangeCode = prefs.getString("broadcastRangeCode","");
        final String adDuration = prefs.getString("adDuration","");
        final String broadcastPrice = prefs.getString("broadcastPrice", "");
        final String broadcastCharge = prefs.getString("broadcastCharge","");
        final String logo = prefs.getString("imageAvail","");

        final String brandName, title, desc1, desc2, desc3, desc4, confCode;
        String confCode1;
        brandName = etBrandName.getText().toString();
        title = etTitle.getText().toString();
        desc1 = etDesc1.getText().toString();
        desc2 = etDesc2.getText().toString();
        desc3 = etDesc3.getText().toString();
        desc4 = etDesc4.getText().toString();
        confCode1 = etConfirmationCode.getText().toString();

        StringBuilder stringBuilder = new StringBuilder();
        if (broadcastPrice.equals("free")) {
            confCode1 = "its for free";
        }
        confCode = confCode1;

        if (logo.equals("") || brandName.equals("") || brandName.length() < 2 || title.equals("") || title.length() < 5 || confCode.equals("")
                || adDuration.equals("") || broadcastRangeCode.equals("") || interestCode.equals("")) {
            if (logo.equals("")) {
                stringBuilder.append("- Include Ad Poster or Brand Logo\n\n");
            }
            if (brandName.equals("") || brandName.length() < 2) {
                stringBuilder.append("- Brand Name must be +2 characters\n\n");
            }
            if (title.equals("") || title.length() < 5) {
                stringBuilder.append("- Ad Title must be +5 characters\n\n");
            }
            if (xInterestCode.equals("") || xInterestCode.equals("Choose Category")) {
                stringBuilder.append("- Choose your Ad Category\n\n");
            }
            if (xBroadcastRangeCode.equals("") || xBroadcastRangeCode.equals("Choose Range")) {
                stringBuilder.append("- Choose your Broadcast Range\n\n");
            }
            if (xBroadcastRangeCode.equals("City")) {
                if (locationCode.equals("")) {
                    stringBuilder.append("- Select Your Target City\n\n");
                }
            }
            if (xBroadcastRangeCode.equals("Local")) {
                if (locationCode.length() != 6) {
                    stringBuilder.append("- Select Your Target City and Suburb\n\n");
                }
            }
            if (adDuration.equals("") || adDuration.equals("Choose Duration")) {
                stringBuilder.append("- Choose your Broadcast duration\n\n");
            }
            if (confCode.equals("") || confCode.length() < 3) {
                stringBuilder.append("- Include the Confirmation Code you received from Ecocash\n\n");
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
                    .setMessage("Your Ad will be Reviewed and then Broadcast considering it is appropriate." +
                            "\n\nIf not you will be Refunded considering the broadcast was Charged." +
                            "\n\nKeep Your Network Connection open, broadcast may take a few seconds if a video is attached." +
                            "\n\nThank You !!!\n")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AirAdBroadcast airEventBroadcast = new AirAdBroadcast(context);
                            airEventBroadcast.execute(userId,interestCode,locationCode,broadcastRangeCode,broadcastCharge,
                                    brandName,title,desc1, desc2, desc3, desc4,adDuration);
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
                editor.putString("broadcastRangeCode",codeToPut);
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
        getLocationNames(cityCode);
        new GetAudience(context).execute();
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
                    editor.putString("locationCode",locationCode + codeToPut);
                    editor.putString("broadcastRangeCode",locationCode + codeToPut);
                } else {
                    String cityCode = locationCode.substring(0,3);
                    editor.putString("locationCode",cityCode + codeToPut);
                    editor.putString("broadcastRangeCode",cityCode + codeToPut);
                }
                editor.putString("selectedLocation", String.valueOf(parent.getItemAtPosition(position)));
                editor.putString("selectedLocationCode",arrayLocationId[locCode]);
                editor.apply();
            }
        }
        new GetAudience(context).execute();
    }













    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int ibDeleteScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,resources.getDisplayMetrics());
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            handlePosterResult(data, height, width,ibDeleteScale);
        }
        else if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String videoPath = getRealPathFromURI(this, uri);
            File file = new File(videoPath);
            int size = Integer.parseInt(String.valueOf((file.length() / 1024) / 1024));
            if (size > 6) {
                Toast.makeText(getApplicationContext(), "Video must be 6MB or Less", Toast.LENGTH_SHORT).show();
            } else {
                handleVideoResult(data,ibDeleteScale);
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
        File toPath = new File(posterDir,"pre_ad.JPEG");
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
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_ad.JPEG";
        adBitmap = BitmapFactory.decodeFile(imagePath);
        RoundedBitmapDrawable imagePoster = RoundedBitmapDrawableFactory.create(getResources(), adBitmap);
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
        File toPath = new File(posterDir,"pre_ad.mp4");
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
        String imagePath = "sdcard/MyEvents/PreBroadcast/pre_ad.JPEG";
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");

        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300,resources.getDisplayMetrics());
        int ibDeleteScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,resources.getDisplayMetrics());
        if(imageFile.exists()) {
            llPoster.setLayoutParams(new LinearLayout.LayoutParams(width,height));
            /*Glide.with(this)
                    .load(imagePath)
                    .crossFade()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_placeholder)
                    .into(ivPoster);*/
            if (adBitmap == null) {
                adBitmap = BitmapFactory.decodeFile(imagePath);
            }
            RoundedBitmapDrawable imagePoster;

            imagePoster = RoundedBitmapDrawableFactory.create(getResources(), adBitmap);
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

    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projections = { MediaStore.Video.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, projections, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void deletePoster() {
        editor = prefs.edit();
        File imageFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.JPEG");
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
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
        videoFile.delete();
        ibDeletePoster.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ibDeleteVideo.setImageResource(0);
        ibVideo.setImageResource(R.drawable.ib_video_gray);
        editor.putString("videoAvail","");
        editor.apply();
    }






    private void checkLocation() {
        Resources resources = getResources();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 165, resources.getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 165, resources.getDisplayMetrics());

        String range = prefs.getString("selectedBroadcastRange","");
        if (range.equals("City") || range.equals("Local")) {
            llLocation.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            llLocation.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            if (range.equals("City")) {
                tvLocation.setText("Select Your Target City");
                llCity.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llCity.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (range.equals("Local")){
                tvLocation.setText("Select Your Target City And Suburb");
                llCity.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llCity.getLayoutParams().width = width;
                llSuburb.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llSuburb.getLayoutParams().width = width;
            }
        }
    }

    private void retrieveValues(){
        etTitle.setText(prefs.getString("adTitle",""));
        if (!prefs.getString("adTitle","").equals("")){
            tvTitle.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvTitle.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvTitle.setText("ad title");
        }
        etBrandName.setText(prefs.getString("adBrandName",""));
        if (!prefs.getString("adBrandName","").equals("")){
            tvBrandName.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvBrandName.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvBrandName.setText("brand name");
        }
        etDesc1.setText(prefs.getString("desc1",""));
        tvDesc1.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvDesc1.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (!prefs.getString("desc1","").equals("")){
            tvDesc1.setText("description(1)");
        }
        etDesc2.setText(prefs.getString("desc2",""));
        tvDesc2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvDesc2.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (!prefs.getString("desc2","").equals("")){
            tvDesc2.setText("description(2)");
        }
        etDesc3.setText(prefs.getString("desc3",""));
        tvDesc3.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvDesc3.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (!prefs.getString("desc3","").equals("")){
            tvDesc3.setText("description(3)");
        }
        etDesc4.setText(prefs.getString("desc4",""));
        tvDesc4.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvDesc4.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (!prefs.getString("desc4","").equals("")){
            tvDesc4.setText("description(4)");
        }

        spinCategory.setSelection(categoryAdapter.getPosition(prefs.getString("selectedCategory","")));
        spinBroadcastRange.setSelection(broadcastRangeAdapter.getPosition(prefs.getString("selectedBroadcastRange","")));
        spinDuration.setSelection(durationAdapter.getPosition(prefs.getString("adDuration","")));

        if(!prefs.getString("selectedCity","").equals("")){
            autoCity.setText(prefs.getString("selectedCity",""));
        }
        else {
            autoCity.setText("");
            autoCity.setHint("Select City");
        }
        if(!prefs.getString("selectedLocation","").equals("")){
            autoSuburb.setText(prefs.getString("selectedLocation",""));
        }
        else {
            autoSuburb.setText("");
            autoSuburb.setHint("Select Suburb");
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
        etConfirmationCode.setText(prefs.getString("confirmationCode",""));
    }

    @Override
    public void onPause() {
        super.onPause();
        editor = prefs.edit();
        editor.putString("adTitle",etTitle.getText().toString());
        editor.putString("adBrandName",etBrandName.getText().toString());
        editor.putString("desc1",etDesc1.getText().toString());
        editor.putString("desc2",etDesc2.getText().toString());
        editor.putString("desc3",etDesc3.getText().toString());
        editor.putString("desc4",etDesc4.getText().toString());
        editor.putString("confirmationCode", etConfirmationCode.getText().toString());
        editor.apply();
    }







    class GetAudience extends AsyncTask<Void, Void, Void> {
        Context context;
        String charge, agentCode, audience;
        GetAudience(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String broadcastRangeCode = prefs.getString("broadcastRangeCode","");
            final String categoryCode = prefs.getString("categoryCode","");
            final String adDuration = prefs.getString("adDuration","");
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
                    params.put("action", "get_ad_audience");
                    params.put("ad_duration", adDuration);
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

    class AirAdBroadcast extends AsyncTask<String, Void, String> {
        Context context;
        String mediaName, brandName, adTitle, bitmapName;
        String video;

        AirAdBroadcast(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", "Processing Ad Details ...",true);
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

            File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
            if (videoFile.exists()){
                video = "yes";
            } else {
                video = "no";
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            adBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            final String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            String adDuration = getDuration(params[11]);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", params[0]);
                jsonObject.put("interest_code", params[1]);
                jsonObject.put("location_code", params[2]);
                jsonObject.put("broadcast_range_code", params[3]);
                jsonObject.put("brand_name", params[5]);
                jsonObject.put("title", params[6]);
                jsonObject.put("desc1", params[7]);
                jsonObject.put("desc2", params[8]);
                jsonObject.put("desc3", params[9]);
                jsonObject.put("desc4", params[10]);
                jsonObject.put("ad_duration", adDuration);
                jsonObject.put("broadcast_charge", params[4]);
                jsonObject.put("media_name", mediaName);
                jsonObject.put("video", video);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            brandName = params[5];
            adTitle = params[6];

            final String json = jsonObject.toString();
            try {
                URL url = new URL(broadcastUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("broadcast_ad", "UTF-8") + "&" +
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
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result.contains("yes")){
                bitmapName = result.replace("yes", "");
                notificationBuilder.setOngoing(false);
                notificationBuilder.setContentTitle("Ad Broadcast Successful")
                        .setContentText("Thank You for Choosing Our Service!!")
                        .setTicker("Ad Broadcast Confirmation...")
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationBuilder.setProgress(0,0,false);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
                notificationManager.notify(6238,notificationBuilder.build());

                etBrandName.setText("");
                etTitle.setText("");
                autoCity.setText("");
                autoSuburb.setText("");
                etDesc1.setText("");
                etDesc2.setText("");
                etDesc3.setText("");
                etDesc4.setText("");
                etConfirmationCode.setText("");

                File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_ad.mp4");
                String selectedVidPath = "sdcard/MyEvents/PreBroadcast/pre_ad.mp4";
                if (videoFile.exists()){
                    askToShare();
                    new UploadBroadcastVideo(context, prefs.getString("mediaName",""),
                            "Ad Video Uploaded","Uploading video to Broadcast Server",
                            "Uploading video to Broadcast Server","MyEvents",selectedVidPath,"ad").execute();
                } else {
                    askToShare();
                    Toast.makeText(getApplicationContext(), "Broadcast Successful", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Broadcast Failed try again in a moment!", Toast.LENGTH_SHORT).show();
                notificationBuilder.setOngoing(false);
                notificationBuilder.setContentTitle("MyEvents")
                        .setContentText("Ad Broadcast Failed! Try Again in a Moment")
                        .setTicker("Broadcast Failed")
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big));
                notificationBuilder.setProgress(0,0,false);
                notificationManager.notify(6236,notificationBuilder.build());
            }
        }

        private void askToShare() {
            new AlertDialog.Builder(context).setTitle("Share on Facebook")
                    .setMessage("Would you like to share your ad on Facebook")
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

        String getDuration(String duration) {
            long adDuration = 0;
            switch (duration) {
                case "2 Days":
                    //get current timestamp and add two days
                    adDuration = getCurrentTimestamp() + 172800000;
                    break;
                case "4 Days":
                    //add 4 days
                    adDuration = getCurrentTimestamp() + 345600000;
                    break;
                case "7 Days":
                    // add 7 days
                    adDuration = getCurrentTimestamp() + 604800000;
                    break;
                case "10 Days":
                    //add 10 days
                    adDuration = getCurrentTimestamp() + 864000000;
                    break;
            }
            return String.valueOf(adDuration);
        }

        long getCurrentTimestamp(){
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

        void deleteBroadcastValues() {
            editor = prefs.edit();
            editor.clear().apply();
            etBrandName.setText("");
            etTitle.setText("");
            autoCity.setText("");
            autoSuburb.setText("");
            etDesc1.setText("");
            etDesc2.setText("");
            etDesc3.setText("");
            etDesc4.setText("");
            etConfirmationCode.setText("");
            editor.putString("spotId","");
            editor.apply();
            deletePoster();
            deleteVideo();
            adBitmap = null;
        }

    }

}
