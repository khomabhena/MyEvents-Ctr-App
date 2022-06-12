package tech.myevents.hq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static tech.myevents.hq.MainActivity.currentPage;

public class Profile extends AppCompatActivity {

    ImageView ivAmusementPark, ivBusiness, ivClubbing, ivConcert, ivExhibition, ivFestival, ivMusicalPerformance, ivMusic;
    ImageView ivTheatre, ivComedy, ivRetreat, ivFashion, ivRecreational, ivParty, ivOutdoor, ivTechAndDesign, ivCommunity;
    AutoCompleteTextView autoCity, autoSuburb;

    String[] arrayCityName = new String[]{};
    String[] arrayLocationName = new String[]{};
    String[] arrayLocationId = new String[]{};

    List<String> listCityName;
    List<String> listLocationName;
    List<String> listLocationId;

    Typeface typefaceGFR;
    Typeface typefaceBC;
    TextView tvCit, tvLoc, tvTap, tvComm, tvTech;
    TextView  tvAmuse, tvBus, tvClub, tvCon, tvExh, tvFes, tvMusP, tvMus, tvThe, tvCom, tvRet, tvFas, tvRec, tvPar, tvOut;

    DBOperations dbOperations;
    SQLiteDatabase db;
    SharedPreferences prefUserInfo;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 2;
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbOperations = new DBOperations(this);
        db = dbOperations.getWritableDatabase();
        prefUserInfo = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);

        autoCity = (AutoCompleteTextView) findViewById(R.id.autoCity);
        autoSuburb = (AutoCompleteTextView) findViewById(R.id.autoSuburb);
        String fontPathGFR = "fonts/goodfish_rg.ttf";
        String fontPathBC = "fonts/black_cry.TTF";

        typefaceGFR = Typeface.createFromAsset(getAssets(),fontPathGFR);
        typefaceBC = Typeface.createFromAsset(getAssets(),fontPathBC);

        initializeViews();
        initializeTexts();
        setTextTypeFaces();
        checkLocation();
        addInterestsImages();
        getCityNames();
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

    private void getCityNames() {
        String cityId = prefUserInfo.getString("prefCityCode","");
        arrayCityName = new String[]{""};
        listCityName = new LinkedList<String>(Arrays.asList(arrayCityName));

        Cursor cursor = dbOperations.getCity(db);
        while(cursor.moveToNext()){
            listCityName.add(cursor.getString(cursor.getColumnIndex(DBContract.City.COLUMN_CITY_NAME)));
        }
        cursor.close();
        arrayCityName = listCityName.toArray(new String[listCityName.size()]);
        if (!cityId.equals("")){
            getLocationNames(cityId);
        }
        searchForCity();
    }

    private void getLocationNames(String cityId){
        arrayLocationName = new String[]{""};
        arrayLocationId = new String[]{""};
        listLocationName = new LinkedList<String>(Arrays.asList(arrayLocationName));
        listLocationId = new LinkedList<String>(Arrays.asList(arrayLocationId));

        Cursor cursor = dbOperations.getLocation(db,cityId);
        while(cursor.moveToNext()) {
            listLocationName.add(cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_NAME)));
            listLocationId.add(cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_ID)));
        }
        cursor.close();
        arrayLocationName = listLocationName.toArray(new String[listLocationName.size()]);
        arrayLocationId = listLocationId.toArray(new String[listLocationId.size()]);
        searchForLocation();
    }

    private void searchForCity() {
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayCityName);
        autoCity.setAdapter(cityAdapter);
        autoCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityOperations(parent,position, arrayCityName);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    private void searchForLocation() {
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayLocationName);
        autoSuburb.setAdapter(locationAdapter);
        autoSuburb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationOperations(parent, position, arrayLocationName);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    private void cityOperations(AdapterView<?> parent, int position, String[] arrayCityName) {
        editor = prefUserInfo.edit();
        for (int x = 0; x < arrayCityName.length; x++) {
            if (arrayCityName[x].equals(String.valueOf(parent.getItemAtPosition(position)))) {
                editor.putLong("lastAdUpdate", 125000);
                editor.putLong("lastEventUpdate", 125000);
                String cityCode = prefUserInfo.getString("prefCityCode", "");
                if(!cityCode.equals(String.valueOf(x))) {
                    autoCity.setText(String.valueOf(parent.getItemAtPosition(position)));
                    autoSuburb.setText("");
                    autoSuburb.setHint("Not Selected");
                    editor.putString("prefCityName", String.valueOf(parent.getItemAtPosition(position)));
                    editor.putString("prefCityCode", String.valueOf(x));
                    String codeLen = String.valueOf(x);
                    String codeToPut = "";
                    switch (codeLen.length()) {
                        case 1:
                            codeToPut = "00" + x;
                            break;
                        case 2:
                            codeToPut = "0" + x;
                            break;
                        case 3:
                            codeToPut = String.valueOf(x);
                            break;
                    }
                    editor.putString("locationCode", codeToPut);
                    editor.putString("prefLocationCode","");
                    editor.putString("prefLocationName","");
                    editor.putString("interestsUpdate","yes");
                    editor.apply();
                    autoSuburb.setText("");
                    getLocationNames(String.valueOf(x));
                }
            }
        }
        editor.apply();
    }

    private void locationOperations(AdapterView<?> parent, int position, String[] arrayLocationName) {
        editor = prefUserInfo.edit();
        for (int x = 0; x < arrayLocationName.length; x++) {
            if (arrayLocationName[x].equals(String.valueOf(parent.getItemAtPosition(position)))) {
                editor.putLong("lastAdUpdate", 125000);
                editor.putLong("lastEventUpdate", 125000);
                String prefLocationCode = prefUserInfo.getString("prefLocationCode","");
                if(!prefLocationCode.equals(String.valueOf(x))) {
                    autoSuburb.setText(String.valueOf(parent.getItemAtPosition(position)));
                    editor.putString("prefLocationName", String.valueOf(parent.getItemAtPosition(position)));
                    editor.putString("prefLocationCode", arrayLocationId[x]);
                    editor.putString("interestsUpdate","yes");
                    String codeLen = arrayLocationId[x];
                    String codeToPut = "";
                    String locationCode = prefUserInfo.getString("locationCode","");

                    switch (codeLen.length()) {
                        case 1:
                            codeToPut = "00" + arrayLocationId[x];
                            break;
                        case 2:
                            codeToPut = "0" + arrayLocationId[x];
                            break;
                        case 3:
                            codeToPut = arrayLocationId[x];
                            break;
                    }

                    if (locationCode.length()==3){
                        editor.putString("locationCode",locationCode+codeToPut);
                    } else {
                        String cityCode = locationCode.substring(0,3);
                        editor.putString("locationCode",cityCode+codeToPut);
                    }
                    editor.apply();
                }
            }
        }
    }

    private void initializeViews() {
        ivRecreational = (ImageView) findViewById(R.id.ivRecreational);
        ivAmusementPark = (ImageView) findViewById(R.id.ivAmusementPark);
        ivBusiness = (ImageView) findViewById(R.id.ivBusiness);
        ivClubbing = (ImageView) findViewById(R.id.ivClubbing);
        ivConcert = (ImageView) findViewById(R.id.ivConcert);
        ivExhibition = (ImageView) findViewById(R.id.ivExhibition);
        ivFestival = (ImageView) findViewById(R.id.ivFestival);
        ivTheatre = (ImageView) findViewById(R.id.ivTheatre);
        ivMusicalPerformance = (ImageView) findViewById(R.id.ivMusicalPerformance);
        ivComedy = (ImageView) findViewById(R.id.ivComedy);
        ivRetreat = (ImageView) findViewById(R.id.ivRetreat);
        ivFashion = (ImageView) findViewById(R.id.ivFashion);
        ivParty = (ImageView) findViewById(R.id.ivParty);
        ivOutdoor = (ImageView) findViewById(R.id.ivOutdoor);
        ivMusic = (ImageView) findViewById(R.id.ivMusic);
        ivTechAndDesign = (ImageView) findViewById(R.id.ivTechAndDesign);
        ivCommunity = (ImageView) findViewById(R.id.ivCommunity);
    }

    private void initializeTexts() {
        tvCit = (TextView) findViewById(R.id.tvCit);
        tvLoc = (TextView) findViewById(R.id.tvLoc);
        tvTap = (TextView) findViewById(R.id.tvTap);
        tvAmuse = (TextView) findViewById(R.id.tvAmuse);
        tvBus = (TextView) findViewById(R.id.tvBus);
        tvClub = (TextView) findViewById(R.id.tvClub);
        tvCon = (TextView) findViewById(R.id.tvCon);
        tvExh = (TextView) findViewById(R.id.tvExh);
        tvFes = (TextView) findViewById(R.id.tvFes);
        tvMusP = (TextView) findViewById(R.id.tvMusP);
        tvMus = (TextView) findViewById(R.id.tvMus);
        tvThe = (TextView) findViewById(R.id.tvThe);
        tvCom = (TextView) findViewById(R.id.tvCom);
        tvRet = (TextView) findViewById(R.id.tvRet);
        tvFas = (TextView) findViewById(R.id.tvFas);
        tvRec = (TextView) findViewById(R.id.tvRec);
        tvPar = (TextView) findViewById(R.id.tvPar);
        tvOut = (TextView) findViewById(R.id.tvOut);
        tvTech = (TextView) findViewById(R.id.tvTech);
        tvComm = (TextView) findViewById(R.id.tvComm);
    }

    private void setTextTypeFaces() {
        tvCit.setTypeface(typefaceBC);
        tvLoc.setTypeface(typefaceBC);
        tvTap.setTypeface(typefaceBC);
        tvAmuse.setTypeface(typefaceBC);
        tvBus.setTypeface(typefaceBC);
        tvClub.setTypeface(typefaceBC);
        tvCon.setTypeface(typefaceBC);
        tvExh.setTypeface(typefaceBC);
        tvFes.setTypeface(typefaceBC);
        tvMusP.setTypeface(typefaceBC);
        tvMus.setTypeface(typefaceBC);
        tvThe.setTypeface(typefaceBC);
        tvCom.setTypeface(typefaceBC);
        tvRet.setTypeface(typefaceBC);
        tvFas.setTypeface(typefaceBC);
        tvRec.setTypeface(typefaceBC);
        tvPar.setTypeface(typefaceBC);
        tvOut.setTypeface(typefaceBC);
        tvTech.setTypeface(typefaceBC);
        tvComm.setTypeface(typefaceBC);
    }

    private void checkLocation() {
        SharedPreferences names = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        if(!names.getString("prefCityName","").equals("")){
            autoCity.setText(names.getString("prefCityName",""));
        }
        else {
            autoCity.setText("");
            autoCity.setHint("Not Selected");
        }
        if(!names.getString("prefLocationName","").equals("")){
            autoSuburb.setText(names.getString("prefLocationName",""));
        }
        else {
            autoSuburb.setText("");
            autoSuburb.setHint("Not Selected");
        }
    }

    private void addInterestsImages() {
        SharedPreferences ints = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        String prefInterests = ints.getString("interestsCode","");

        if(prefInterests.contains("1a")) {
            ivAmusementPark.setImageResource(R.drawable.int_amusement_park_teal);
        }
        else {
            ivAmusementPark.setImageResource(R.drawable.int_amusement_park_gray);
        }
        if(prefInterests.contains("1b")){
            ivBusiness.setImageResource(R.drawable.int_business_teal);
        }
        else {
            ivBusiness.setImageResource(R.drawable.int_business_gray);
        }
        if(prefInterests.contains("1c")){
            ivClubbing.setImageResource(R.drawable.int_clubbing_teal);
        }
        else {
            ivClubbing.setImageResource(R.drawable.int_clubbing_gray);
        }
        if(prefInterests.contains("1d")){
            ivConcert.setImageResource(R.drawable.int_concert_teal);
        }
        else {
            ivConcert.setImageResource(R.drawable.int_concert_gray);
        }
        if(prefInterests.contains("1e")){
            ivExhibition.setImageResource(R.drawable.int_exhibition_teal);
        }
        else {
            ivExhibition.setImageResource(R.drawable.int_exhibition_gray);
        }
        if(prefInterests.contains("1f")){
            ivFestival.setImageResource(R.drawable.int_festival_teal);
        }
        else {
            ivFestival.setImageResource(R.drawable.int_festival_gray);
        }
        if(prefInterests.contains("1g")){
            ivMusicalPerformance.setImageResource(R.drawable.int_musician_band_teal);
        }
        else {
            ivMusicalPerformance.setImageResource(R.drawable.int_musician_band_gray);
        }
        if(prefInterests.contains("1h")){
            ivMusic.setImageResource(R.drawable.int_music_teal);
        }
        else {
            ivMusic.setImageResource(R.drawable.int_music_gray);
        }
        if(prefInterests.contains("1i")){
            ivTheatre.setImageResource(R.drawable.int_theatre_teal);
        }
        else {
            ivTheatre.setImageResource(R.drawable.int_theatre_gray);
        }
        if(prefInterests.contains("1j")){
            ivComedy.setImageResource(R.drawable.int_comedy_teal);
        }
        else {
            ivComedy.setImageResource(R.drawable.int_comedy_gray);
        }
        if(prefInterests.contains("1k")){
            ivRetreat.setImageResource(R.drawable.int_retreat_teal);
        }
        else {
            ivRetreat.setImageResource(R.drawable.int_retreat_gray);
        }
        if(prefInterests.contains("1l")){
            ivFashion.setImageResource(R.drawable.int_fashion_teal);
        }
        else {
            ivFashion.setImageResource(R.drawable.int_fashion_gray);
        }
        if(prefInterests.contains("1m")){
            ivRecreational.setImageResource(R.drawable.int_recreational_teal);
        }
        else {
            ivRecreational.setImageResource(R.drawable.int_recreational_gray);
        }
        if(prefInterests.contains("1n")){
            ivParty.setImageResource(R.drawable.int_party_teal);
        }
        else {
            ivParty.setImageResource(R.drawable.int_party_gray);
        }
        if(prefInterests.contains("1o")){
            ivOutdoor.setImageResource(R.drawable.int_outdoor_teal);
        }
        else {
            ivOutdoor.setImageResource(R.drawable.int_outdoor_gray);
        }
        if (prefInterests.contains("1p")) {
            ivTechAndDesign.setImageResource(R.drawable.int_tech_and_design_teal);
        } else {
            ivTechAndDesign.setImageResource(R.drawable.int_tech_and_design_gray);
        }
        if (prefInterests.contains("1q")) {
            ivCommunity.setImageResource(R.drawable.int_community_teal);
        } else {
            ivCommunity.setImageResource(R.drawable.int_community_gray);
        }
    }

    public void interestChange(View view){
        editor = prefUserInfo.edit();
        String prefInterests = prefUserInfo.getString("interestsCode","");
        String eventInterests = prefUserInfo.getString("eventInterests", "");
        String adInterests = prefUserInfo.getString("adInterests", "");
        editor.putString("interestsUpdate","yes");
        editor.putLong("lastAdUpdate", 125000);
        editor.putLong("lastEventUpdate", 125000);
        editor.apply();

        switch (view.getId()){
            case R.id.llAmusementPark:
                if (prefInterests.contains("1a")) {
                    String toRep = prefInterests.replace("1a","");
                    String toRepSec = eventInterests.replace("1a","");
                    String toRep3 = adInterests.replace("1a","");
                    editor.putString("adInterests",toRep3);
                    editor.putString("interestsCode",toRep);
                    editor.putString("eventInterests", toRepSec);
                    editor.apply();
                    ivAmusementPark.setImageResource(R.drawable.int_amusement_park_gray);
                } else {
                    String toPutSec = eventInterests+"1a";
                    String toPut = prefInterests+"1a";
                    String toPut3 = adInterests + "1a";
                    editor.putString("adInterests", toPut3);
                    editor.putString("interestsCode",toPut);
                    editor.putString("eventInterests", toPutSec);
                    editor.apply();
                    ivAmusementPark.setImageResource(R.drawable.int_amusement_park_teal);
                }
                break;
            case R.id.llBusiness:
                if (prefInterests.contains("1b")){
                    String toRepSec = eventInterests.replace("1b","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1b","");
                    String toRep3 = adInterests.replace("1b","");
                    editor.putString("adInterests",toRep3);
                    editor.putString("interestsCode",toRep);
                    editor.apply();
                    ivBusiness.setImageResource(R.drawable.int_business_gray);
                } else {
                    String toPutSec = eventInterests+"1b";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1b";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1b";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivBusiness.setImageResource(R.drawable.int_business_teal);
                }
                break;
            case R.id.llClubbing:
                if (prefInterests.contains("1c")){
                    String toRepSec = eventInterests.replace("1c","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1c","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1c","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivClubbing.setImageResource(R.drawable.int_clubbing_gray);
                } else {
                    String toPutSec = eventInterests+"1c";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1c";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1c";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivClubbing.setImageResource(R.drawable.int_clubbing_teal);
                }
                break;
            case R.id.llConcert:
                if (prefInterests.contains("1d")){
                    String toRepSec = eventInterests.replace("1d","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1d","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1d","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivConcert.setImageResource(R.drawable.int_concert_gray);
                } else {
                    String toPutSec = eventInterests+"1d";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1d";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1d";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivConcert.setImageResource(R.drawable.int_concert_teal);
                }
                break;
            case R.id.llExhibition:
                if (prefInterests.contains("1e")){
                    String toRepSec = eventInterests.replace("1e","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1e","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1e","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivExhibition.setImageResource(R.drawable.int_exhibition_gray);
                } else {
                    String toPutSec = eventInterests+"1e";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1e";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1e";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivExhibition.setImageResource(R.drawable.int_exhibition_teal);
                }
                break;
            case R.id.llFestival:
                if (prefInterests.contains("1f")){
                    String toRepSec = eventInterests.replace("1f","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1f","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1f","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivFestival.setImageResource(R.drawable.int_festival_gray);
                } else {
                    String toPutSec = eventInterests+"1f";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1f";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1f";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivFestival.setImageResource(R.drawable.int_festival_teal);
                }
                break;
            case R.id.llMusicalPerformance:
                if (prefInterests.contains("1g")){
                    String toRepSec = eventInterests.replace("1g","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1g","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1g","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivMusicalPerformance.setImageResource(R.drawable.int_musician_band_gray);
                } else {
                    String toPutSec = eventInterests+"1g";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1g";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1g";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivMusicalPerformance.setImageResource(R.drawable.int_musician_band_teal);
                }
                break;
            case R.id.llMusic:
                if (prefInterests.contains("1h")){
                    String toRepSec = eventInterests.replace("1h","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1h","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1h","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivMusic.setImageResource(R.drawable.int_music_gray);
                } else {
                    String toPutSec = eventInterests+"1h";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1h";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1h";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivMusic.setImageResource(R.drawable.int_music_teal);
                }
                break;
            case R.id.llTheatre:
                if (prefInterests.contains("1i")){
                    String toRepSec = eventInterests.replace("1i","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1i","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1i","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivTheatre.setImageResource(R.drawable.int_theatre_gray);
                } else {
                    String toPutSec = eventInterests+"1i";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1i";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1i";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivTheatre.setImageResource(R.drawable.int_theatre_teal);
                }
                break;
            case R.id.llComedy:
                if (prefInterests.contains("1j")){
                    String toRepSec = eventInterests.replace("1j","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1j","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1j","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivComedy.setImageResource(R.drawable.int_comedy_gray);
                } else {
                    String toPutSec = eventInterests+"1j";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1j";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1j";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivComedy.setImageResource(R.drawable.int_comedy_teal);
                }
                break;
            case R.id.llRetreat:
                if (prefInterests.contains("1k")){
                    String toRepSec = eventInterests.replace("1k","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1k","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1k","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivRetreat.setImageResource(R.drawable.int_retreat_gray);
                } else {
                    String toPutSec = eventInterests+"1k";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1k";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1k";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivRetreat.setImageResource(R.drawable.int_retreat_teal);
                }
                break;
            case R.id.llFashion:
                if (prefInterests.contains("1l")){
                    String toRepSec = eventInterests.replace("1l","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1l","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1l","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivFashion.setImageResource(R.drawable.int_fashion_gray);
                } else { String toPut = prefInterests+"1l";
                    String toPutSec = eventInterests+"1l";
                    editor.putString("eventInterests", toPutSec);
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1l";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivFashion.setImageResource(R.drawable.int_fashion_teal);
                }
                break;
            case R.id.llRecreational:
                if (prefInterests.contains("1m")){
                    String toRepSec = eventInterests.replace("1m","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1m","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1m","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivRecreational.setImageResource(R.drawable.int_recreational_gray);
                } else {
                    String toPutSec = eventInterests+"1m";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1m";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1m";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivRecreational.setImageResource(R.drawable.int_recreational_teal);
                }
                break;
            case R.id.llParty:
                if (prefInterests.contains("1n")){
                    String toRepSec = eventInterests.replace("1n","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1n","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1n","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivParty.setImageResource(R.drawable.int_party_gray);
                } else {
                    String toPutSec = eventInterests+"1n";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1n";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1n";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivParty.setImageResource(R.drawable.int_party_teal);
                }
                break;
            case R.id.llOutdoor:
                if (prefInterests.contains("1o")){
                    String toRepSec = eventInterests.replace("1o","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1o","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1o","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivOutdoor.setImageResource(R.drawable.int_outdoor_gray);
                } else {
                    String toPutSec = eventInterests + "1o";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1o";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1o";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivOutdoor.setImageResource(R.drawable.int_outdoor_teal);
                }
                break;
            case R.id.llTechAndDesign:
                if (prefInterests.contains("1p")){
                    String toRepSec = eventInterests.replace("1p","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1p","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1p","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivTechAndDesign.setImageResource(R.drawable.int_tech_and_design_gray);
                } else {
                    String toPutSec = eventInterests + "1p";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1p";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1p";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivTechAndDesign.setImageResource(R.drawable.int_tech_and_design_teal);
                }
                break;
            case R.id.llCommunity:
                if (prefInterests.contains("1q")){
                    String toRepSec = eventInterests.replace("1q","");
                    editor.putString("eventInterests", toRepSec);
                    String toRep = prefInterests.replace("1q","");
                    editor.putString("interestsCode",toRep);
                    String toRep3 = adInterests.replace("1q","");
                    editor.putString("adInterests",toRep3);
                    editor.apply();
                    ivCommunity.setImageResource(R.drawable.int_community_gray);
                } else {
                    String toPutSec = eventInterests + "1q";
                    editor.putString("eventInterests", toPutSec);
                    String toPut = prefInterests+"1q";
                    editor.putString("interestsCode",toPut);
                    String toPut3 = adInterests + "1q";
                    editor.putString("adInterests", toPut3);
                    editor.apply();
                    ivCommunity.setImageResource(R.drawable.int_community_teal);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String update = prefUserInfo.getString("interestsUpdate", "");
        if (update.equals("yes")) {
            ContentValues values = new ContentValues();
            values.put(DBContract.User.COLUMN_LOCATION_CODE, prefUserInfo.getString("locationCode", ""));
            values.put(DBContract.User.COLUMN_INTEREST_CODE, prefUserInfo.getString("interestsCode", ""));
            db.update(DBContract.User.TABLE_NAME,
                    values, DBContract.User.COLUMN_ID + "='" + prefUserInfo.getString("userId", "") + "'", null);

            UpdateUser updateUser = new UpdateUser(this);
            updateUser.execute(prefUserInfo.getString("userId",""),
                    prefUserInfo.getString("locationCode",""), prefUserInfo.getString("interestsCode",""));
        }
    }

}
