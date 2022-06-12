package tech.myevents.hq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DBOperations extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myevents.db";

    public static final String QUERY_USER = "CREATE TABLE "+ DBContract.User.TABLE_NAME+"("+
            DBContract.User.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.User.COLUMN_USER_ID+" INTEGER, "+ DBContract.User.COLUMN_PHONE_NUMBER+" TEXT,"+
            DBContract.User.COLUMN_LOCATION_CODE+" TEXT, "+ DBContract.User.COLUMN_INTEREST_CODE+" TEXT, "+
            DBContract.User.COLUMN_EMAIL+" TEXT, "+ DBContract.User.COLUMN_APP_VERSION+" TEXT);";

    public static final String QUERY_INTEREST = "CREATE TABLE "+ DBContract.Interest.TABLE_NAME+"("+
            DBContract.Interest.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Interest.COLUMN_INTEREST_CODE+" TEXT, "+ DBContract.Interest.COLUMN_INTEREST_NAME+" TEXT,"+
            DBContract.Interest.COLUMN_BROADCAST_RANGE+" TEXT);";

    public static final String QUERY_EVENT = "CREATE TABLE "+ DBContract.Event.TABLE_NAME+"("+
            DBContract.Event.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Event.COLUMN_USER_ID+" INTEGER, "+
            DBContract.Event.COLUMN_EVENT_ID+" INTEGER, "+
            DBContract.Event.COLUMN_INTEREST_CODE+" TEXT, "+
            DBContract.Event.COLUMN_LOCATION_CODE+" TEXT, "+ DBContract.Event.COLUMN_EVENT_NAME+" TEXT, "+
            DBContract.Event.COLUMN_EVENT_VENUE+" TEXT, "+ DBContract.Event.COLUMN_EVENT_DETAILS+" TEXT, "+
            DBContract.Event.COLUMN_MIN_PRICE+" TEXT, "+ DBContract.Event.COLUMN_MAX_PRICE+" TEXT, "+
            DBContract.Event.COLUMN_START_TIMESTAMP+" TEXT, "+ DBContract.Event.COLUMN_END_TIMESTAMP+" TEXT, "+
            DBContract.Event.COLUMN_BITMAP_NAME+" TEXT, "+ DBContract.Event.COLUMN_POSTER_BITMAP+" BLOB, "+
            DBContract.Event.COLUMN_EVENT_STATUS+" TEXT, "+ DBContract.Event.COLUMN_EVENT_UPDATE+" TEXT, "+
            DBContract.Event.COLUMN_VIEWS+" TEXT, "+ DBContract.Event.COLUMN_LIKES+" TEXT, "+
            DBContract.Event.COLUMN_VIEWED+" TEXT, "+ DBContract.Event.COLUMN_LIKED+" TEXT, " +
            DBContract.Event.COLUMN_LIKE_STATUS + " TEXT, " + DBContract.Event.COLUMN_VIEW_STATUS + " TEXT, " +
            DBContract.Event.CONF_CODE + " TEXT, " +
            DBContract.Event.BROADCAST_CHARGE + " TEXT, " +
            DBContract.Event.COLUMN_WHEN + " TEXT, " +
            DBContract.Event.COLUMN_VIDEO + " TEXT);";

    public static final String QUERY_AD = "CREATE TABLE "+ DBContract.Ad.TABLE_NAME+"("+
            DBContract.Ad.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Ad.COLUMN_USER_ID+" INTEGER, "+ DBContract.Ad.COLUMN_AD_ID + " INTEGER, " +
            DBContract.Ad.COLUMN_INTEREST_CODE+" TEXT, " + DBContract.Ad.COLUMN_BRAND_NAME+" TEXT, " +
            DBContract.Ad.COLUMN_TITLE + " TEXT, " +
            DBContract.Ad.COLUMN_DESC_1+" TEXT, " + DBContract.Ad.COLUMN_DESC_2 + " TEXT, " +
            DBContract.Ad.COLUMN_DESC_3 + " TEXT, " + DBContract.Ad.COLUMN_DESC_4 + " TEXT, " +
            DBContract.Ad.COLUMN_BITMAP_NAME + " TEXT, " +
            DBContract.Ad.COLUMN_DURATION + " TEXT, " +
            DBContract.Ad.COLUMN_AD_UPDATE+" TEXT, " +
            DBContract.Ad.COLUMN_VIEWS+" TEXT, " + DBContract.Ad.COLUMN_LIKES+" TEXT, "+
            DBContract.Ad.COLUMN_VIEWED+" TEXT, " + DBContract.Ad.COLUMN_LIKED+" TEXT, " +
            DBContract.Ad.COLUMN_LIKE_STATUS + " TEXT, " + DBContract.Ad.COLUMN_VIEW_STATUS + " TEXT, " +
            DBContract.Ad.CONF_CODE + " TEXT, " +
            DBContract.Ad.BROADCAST_CHARGE + " TEXT, " +
            DBContract.Ad.COLUMN_WHEN + " TEXT, " + DBContract.Ad.COLUMN_VIDEO + " TEXT, " +

            DBContract.Ad.COLUMN_AD_STATUS+" TEXT);";

    public static final String QUERY_EVENT_STATS = "CREATE TABLE "+ DBContract.EventStats.TABLE_NAME+"("+
            DBContract.EventStats.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.EventStats.COLUMN_USER_ID+" INTEGER, "+ DBContract.EventStats.COLUMN_EVENT_ID+" INTEGER);";

    public static final String QUERY_AD_STATS = "CREATE TABLE "+ DBContract.AdStats.TABLE_NAME+"("+
            DBContract.AdStats.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.AdStats.COLUMN_USER_ID+" INTEGER, "+ DBContract.AdStats.COLUMN_AD_ID+" INTEGER);";

    public static final String QUERY_USER_PREF = "CREATE TABLE "+ DBContract.UserPref.TABLE_NAME+"("+
            DBContract.UserPref.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.UserPref.COLUMN_USER_ID+" INTEGER, "+ DBContract.UserPref.COLUMN_LOCATION_CODE+" TEXT, "+
            DBContract.UserPref.COLUMN_INTEREST_CODE+" TEXT);";

    public static final String QUERY_LOCATION = "CREATE TABLE "+ DBContract.Location.TABLE_NAME+"("+
            DBContract.Location.COLUMN_LOCATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Location.COLUMN_CITY_ID+" INTEGER, "+ DBContract.Location.COLUMN_LOCATION_NAME+" TEXT, "+
            DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE+" TEXT);";

    public static final String QUERY_CONTINENT = "CREATE TABLE "+ DBContract.Continent.TABLE_NAME+"("+
            DBContract.Continent.COLUMN_CONTINENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Continent.COLUMN_CONTINENT_NAME+" TEXT, "+ DBContract.Continent.COLUMN_CONTINENT_BROADCAST_RANGE+" TEXT);";

    public static final String QUERY_COUNTRY = "CREATE TABLE "+ DBContract.Country.TABLE_NAME+"("+
            DBContract.Country.COLUMN_COUNTRY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Country.COLUMN_CONTINENT_ID+" INTEGER, "+ DBContract.Country.COLUMN_COUNTRY_NAME+" TEXT, "+
            DBContract.Country.COLUMN_COUNTRY_BROADCAST_RANGE+" TEXT);";

    public static final String QUERY_PROVINCE = "CREATE TABLE "+ DBContract.Province.TABLE_NAME+"("+
            DBContract.Province.COLUMN_PROVINCE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.Province.COLUMN_COUNTRY_ID+" INTEGER, " + DBContract.Province.COLUMN_PROVINCE_NAME+" TEXT);";

    public static final String QUERY_CITY = "CREATE TABLE "+ DBContract.City.TABLE_NAME+"("+
            DBContract.City.COLUMN_CITY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.City.COLUMN_PROVINCE_ID+" INTEGER, "+ DBContract.City.COLUMN_COUNTRY_ID+" INTEGER, "+
            DBContract.City.COLUMN_CITY_NAME+" TEXT);";

    public static final String QUERY_PRE_EVENT = "CREATE TABLE "+ DBContract.PreEvent.TABLE_NAME+"("+
            DBContract.PreEvent.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DBContract.PreEvent.COLUMN_INTEREST_CODE+" TEXT, "+ DBContract.PreEvent.COLUMN_LOCATION_CODE+" TEXT, "+
            DBContract.PreEvent.COLUMN_NAME+" TEXT, "+ DBContract.PreEvent.COLUMN_VENUE+" TEXT, "+
            DBContract.PreEvent.COLUMN_DETAILS+" TEXT, "+ DBContract.PreEvent.COLUMN_PRICE+" TEXT, "+
            DBContract.PreEvent.COLUMN_START_TIMESTAMP+" TEXT, "+ DBContract.PreEvent.COLUMN_END_TIMESTAMP+" TEXT, "+
            DBContract.PreEvent.COLUMN_POSTER_NAME+" TEXT, "+ DBContract.PreEvent.COLUMN_POSTER_BITMAP+" BLOB, "+
            DBContract.PreEvent.COLUMN_BROADCAST_CODE+" TEXT, "+ DBContract.PreEvent.COLUMN_STATUS+" TEXT);";

    private static final String QUERY_MAX_IDS = "CREATE TABLE " + DBContract.MaxIds.TABLE_NAME + "(" +
            DBContract.MaxIds.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.MaxIds.LOCATION_CODE + " TEXT, " + DBContract.MaxIds.EVENT_INTEREST_CODE + " TEXT, " +
            DBContract.MaxIds.AD_INTEREST_CODE + " TEXT, " +
            DBContract.MaxIds.MAX_EVENT_ID + " TEXT, " + DBContract.MaxIds.MAX_AD_ID + " TEXT);";

    private static final String QUERY_NAT_MAX_IDS = "CREATE TABLE " + DBContract.NatMaxId.TABLE_NAME + "(" +
            DBContract.NatMaxId.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.NatMaxId.LOCATION_CODE + " TEXT, " +
            DBContract.NatMaxId.EVENT_INTEREST_CODE + " TEXT, " +
            DBContract.NatMaxId.AD_INTEREST_CODE + " TEXT, " +
            DBContract.NatMaxId.MAX_EVENT_ID + " TEXT, " + DBContract.NatMaxId.MAX_AD_ID + " TEXT);";

    private static final String QUERY_CITY_MAX_IDS = "CREATE TABLE " + DBContract.CityMaxId.TABLE_NAME + "(" +
            DBContract.CityMaxId.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.CityMaxId.LOCATION_CODE + " TEXT, " +
            DBContract.CityMaxId.EVENT_INTEREST_CODE + " TEXT, " +
            DBContract.CityMaxId.AD_INTEREST_CODE + " TEXT, " +
            DBContract.CityMaxId.MAX_EVENT_ID + " TEXT, " + DBContract.CityMaxId.MAX_AD_ID + " TEXT);";

    private static final String QUERY_LOC_MAX_IDS = "CREATE TABLE " + DBContract.LocMaxId.TABLE_NAME + "(" +
            DBContract.LocMaxId.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.LocMaxId.LOCATION_CODE + " TEXT, " +
            DBContract.LocMaxId.EVENT_INTEREST_CODE + " TEXT, " +
            DBContract.LocMaxId.AD_INTEREST_CODE + " TEXT, " +
            DBContract.LocMaxId.MAX_EVENT_ID + " TEXT, " + DBContract.LocMaxId.MAX_AD_ID + " TEXT);";

    private static final String QUERY_SIGN_UPS = "CREATE TABLE " + DBContract.SignUps.TABLE_NAME + "(" +
            DBContract.SignUps.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.SignUps.PHONE_NUMBER + " TEXT, " +
            DBContract.SignUps.CONF_CODE + " TEXT);";

    Context context;
    public DBOperations(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //if (getCurrentTimestamp() > 1485535375574L || getCurrentTimestamp() < 1486558832424L) {
            db.execSQL(QUERY_USER);
            db.execSQL(QUERY_INTEREST);
            db.execSQL(QUERY_EVENT);
            db.execSQL(QUERY_AD);
            db.execSQL(QUERY_EVENT_STATS);
            db.execSQL(QUERY_AD_STATS);
            db.execSQL(QUERY_USER_PREF);
            db.execSQL(QUERY_CONTINENT);
            db.execSQL(QUERY_COUNTRY);
            db.execSQL(QUERY_PROVINCE);
            db.execSQL(QUERY_CITY);
            db.execSQL(QUERY_LOCATION);
            db.execSQL(QUERY_PRE_EVENT);
            db.execSQL(QUERY_MAX_IDS);
            db.execSQL(QUERY_NAT_MAX_IDS);
            db.execSQL(QUERY_CITY_MAX_IDS);
            db.execSQL(QUERY_LOC_MAX_IDS);
            db.execSQL(QUERY_SIGN_UPS);
        //}
        /*DBBackgroundTask dbBackgroundTask = new DBBackgroundTask(context);
        dbBackgroundTask.execute("insertAllData");*/
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Ad.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.AdStats.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.City.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Continent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Country.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Event.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.EventStats.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Interest.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Location.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.PreEvent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Province.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.UserPref.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.MaxIds.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.NatMaxId.TABLE_NAME);

        onCreate(db);
    }

    void deleteTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Ad.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.AdStats.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.City.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Continent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Country.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Event.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.EventStats.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Interest.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Location.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.PreEvent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.Province.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.UserPref.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.MaxIds.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.NatMaxId.TABLE_NAME);
    }

    Cursor getUpdatesEvent(SQLiteDatabase db, int id) {
        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID, DBContract.Event.COLUMN_EVENT_ID, DBContract.Event.COLUMN_INTEREST_CODE,
                DBContract.Event.COLUMN_LOCATION_CODE,
                DBContract.Event.COLUMN_EVENT_NAME,
                DBContract.Event.COLUMN_EVENT_VENUE, DBContract.Event.COLUMN_EVENT_DETAILS, DBContract.Event.COLUMN_MIN_PRICE,
                DBContract.Event.COLUMN_MAX_PRICE, DBContract.Event.COLUMN_START_TIMESTAMP, DBContract.Event.COLUMN_END_TIMESTAMP, DBContract.Event.COLUMN_BITMAP_NAME,
                DBContract.Event.COLUMN_VIEWS, DBContract.Event.COLUMN_LIKES, DBContract.Event.COLUMN_VIEWED,
                DBContract.Event.COLUMN_LIKED, DBContract.Event.COLUMN_VIDEO, DBContract.Event.COLUMN_WHEN};
        cursor = db.query(DBContract.Event.TABLE_NAME, projections, DBContract.Event.COLUMN_EVENT_ID + "='"+ id +"'",
                null, null, null, null);

        return cursor;
    }

    Cursor getSmsSent(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {DBContract.SignUps.COLUMN_ID};
        cursor = db.query(DBContract.SignUps.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    Cursor getUpdatesAd(SQLiteDatabase db, int id) {
        Cursor cursor;
        String[] projections = {DBContract.Ad.COLUMN_ID, DBContract.Ad.COLUMN_AD_ID, DBContract.Ad.COLUMN_VIEWS, DBContract.Ad.COLUMN_LIKES,
                DBContract.Ad.COLUMN_INTEREST_CODE, DBContract.Ad.COLUMN_BRAND_NAME, DBContract.Ad.COLUMN_TITLE,
                DBContract.Ad.COLUMN_DESC_1, DBContract.Ad.COLUMN_DESC_2, DBContract.Ad.COLUMN_DESC_3, DBContract.Ad.COLUMN_DESC_4,
                DBContract.Ad.COLUMN_BITMAP_NAME, DBContract.Ad.COLUMN_VIEWED, DBContract.Ad.COLUMN_LIKED,
                DBContract.Ad.COLUMN_DURATION, DBContract.Ad.COLUMN_VIDEO, DBContract.Ad.COLUMN_WHEN};
        cursor = db.query(DBContract.Ad.TABLE_NAME, projections, DBContract.Ad.COLUMN_AD_ID + "='"+ id +"'",
                null, null, null, null);

        return cursor;
    }

    Cursor getEventLikesAndViews(SQLiteDatabase db) {

        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID, DBContract.Event.COLUMN_EVENT_ID, DBContract.Event.COLUMN_LIKE_STATUS,
                DBContract.Event.COLUMN_VIEW_STATUS};
        cursor = db.query(DBContract.Event.TABLE_NAME,projections,
                DBContract.Event.COLUMN_EVENT_STATUS + "='new' OR " + DBContract.Event.COLUMN_EVENT_STATUS + "='now_playing'",
                null,null,null,null);

        return cursor;

    }

    Cursor getAdLikesAndViews(SQLiteDatabase db) {

        Cursor cursor;
        String[] projections = {DBContract.Ad.COLUMN_ID, DBContract.Ad.COLUMN_AD_ID, DBContract.Ad.COLUMN_LIKE_STATUS,
                DBContract.Ad.COLUMN_VIEW_STATUS};
        cursor = db.query(DBContract.Ad.TABLE_NAME,projections,
                DBContract.Ad.COLUMN_AD_STATUS + "='new'",
                null,null,null,null);

        return cursor;

    }

    String getLikeStatus(SQLiteDatabase db, int id) {

        String likeStatus = "";
        String[] projections = {DBContract.Event.COLUMN_LIKE_STATUS};
        Cursor cursor = db.query(DBContract.Event.TABLE_NAME, projections, DBContract.Event.COLUMN_ID + "='" + id + "'",null,null,null,null);
        while (cursor.moveToNext()) {

            likeStatus = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_LIKE_STATUS));

        }
        cursor.close();

        return likeStatus;
    }

    String getAdLikeStatus(SQLiteDatabase db, int id) {

        String likeStatus = "";
        String[] projections = {DBContract.Ad.COLUMN_LIKE_STATUS};
        Cursor cursor = db.query(DBContract.Ad.TABLE_NAME, projections, DBContract.Ad.COLUMN_ID + "='" + id + "'",null,null,null,null);
        while (cursor.moveToNext()) {

            likeStatus = cursor.getString(cursor.getColumnIndex(DBContract.Ad.COLUMN_LIKE_STATUS));

        }
        cursor.close();

        return likeStatus;

    }

    public void updateInterests(SQLiteDatabase db, String userId, String locationCode, String interestCode){

        ContentValues values = new ContentValues();
        values.put(DBContract.UserPref.COLUMN_LOCATION_CODE,locationCode);
        values.put(DBContract.UserPref.COLUMN_INTEREST_CODE,interestCode);
        db.update(DBContract.UserPref.TABLE_NAME,values, DBContract.UserPref.COLUMN_USER_ID+"='"+userId+"'",null);

    }

    Cursor getCity(SQLiteDatabase db){

        String[] projections = {DBContract.City.COLUMN_CITY_NAME};
        Cursor cursor = db.query(DBContract.City.TABLE_NAME,projections,null,null,null,null,null);

        return cursor;
    }

    Cursor getNowPlaying(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID, DBContract.Event.COLUMN_EVENT_ID, DBContract.Event.COLUMN_INTEREST_CODE,
                DBContract.Event.COLUMN_LOCATION_CODE,
                DBContract.Event.COLUMN_EVENT_NAME,
                DBContract.Event.COLUMN_EVENT_VENUE, DBContract.Event.COLUMN_EVENT_DETAILS, DBContract.Event.COLUMN_MIN_PRICE,
                DBContract.Event.COLUMN_MAX_PRICE, DBContract.Event.COLUMN_START_TIMESTAMP, DBContract.Event.COLUMN_END_TIMESTAMP, DBContract.Event.COLUMN_BITMAP_NAME,
                DBContract.Event.COLUMN_VIEWS, DBContract.Event.COLUMN_LIKES, DBContract.Event.COLUMN_VIEWED,
                DBContract.Event.COLUMN_LIKED, DBContract.Event.COLUMN_VIDEO, DBContract.Event.COLUMN_WHEN};
        cursor = db.query(true, DBContract.Event.TABLE_NAME,projections, DBContract.Event.COLUMN_EVENT_STATUS+"='now_playing'",
                null, DBContract.Event.COLUMN_EVENT_ID,
                null, DBContract.Event.COLUMN_ID + " DESC", null);

        return cursor;
    }

    Cursor getAds(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {DBContract.Ad.COLUMN_ID, DBContract.Ad.COLUMN_AD_ID, DBContract.Ad.COLUMN_VIEWS, DBContract.Ad.COLUMN_LIKES,
                DBContract.Ad.COLUMN_INTEREST_CODE, DBContract.Ad.COLUMN_BRAND_NAME, DBContract.Ad.COLUMN_TITLE,
                DBContract.Ad.COLUMN_DESC_1, DBContract.Ad.COLUMN_DESC_2, DBContract.Ad.COLUMN_DESC_3, DBContract.Ad.COLUMN_DESC_4,
                DBContract.Ad.COLUMN_BITMAP_NAME, DBContract.Ad.COLUMN_VIEWED, DBContract.Ad.COLUMN_LIKED,
                DBContract.Ad.COLUMN_DURATION, DBContract.Ad.COLUMN_VIDEO, DBContract.Ad.COLUMN_WHEN};
        cursor = db.query(true, DBContract.Ad.TABLE_NAME, projections, DBContract.Ad.COLUMN_AD_STATUS+"='new'",
                null, DBContract.Ad.COLUMN_AD_ID,
                null, DBContract.Ad.COLUMN_ID + " DESC", null);

        return cursor;
    }

    Cursor getAdsWaiting(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {DBContract.Ad.COLUMN_ID, DBContract.Ad.COLUMN_AD_ID,
                DBContract.Ad.COLUMN_VIEWS, DBContract.Ad.COLUMN_LIKES,
                DBContract.Ad.COLUMN_INTEREST_CODE, DBContract.Ad.COLUMN_BRAND_NAME, DBContract.Ad.COLUMN_TITLE,
                DBContract.Ad.COLUMN_DESC_1, DBContract.Ad.COLUMN_DESC_2, DBContract.Ad.COLUMN_DESC_3, DBContract.Ad.COLUMN_DESC_4,
                DBContract.Ad.COLUMN_BITMAP_NAME, DBContract.Ad.COLUMN_VIEWED, DBContract.Ad.COLUMN_LIKED,
                DBContract.Ad.COLUMN_DURATION, DBContract.Ad.COLUMN_VIDEO,
                DBContract.Ad.CONF_CODE, DBContract.Ad.BROADCAST_CHARGE, DBContract.Ad.COLUMN_AD_STATUS,
                DBContract.Ad.COLUMN_WHEN};
        cursor = db.query(DBContract.Ad.TABLE_NAME, projections,
                DBContract.Ad.COLUMN_AD_STATUS+"='waiting' OR " + DBContract.Ad.COLUMN_AD_STATUS + "='published'",
                null,null,
                null, null);

        return cursor;
    }

    Cursor checkDatedAds(SQLiteDatabase db) {

        Cursor cursor;
        String[] projections = {DBContract.Ad.COLUMN_ID, DBContract.Ad.COLUMN_DURATION};
        cursor = db.query(DBContract.Ad.TABLE_NAME, projections,
                DBContract.Ad.COLUMN_AD_STATUS+"='new' OR " + DBContract.Ad.COLUMN_AD_STATUS + "='published'",
                null,null,null,null);

        return cursor;
    }

    Cursor getEvents(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID, DBContract.Event.COLUMN_EVENT_ID, DBContract.Event.COLUMN_INTEREST_CODE,
                DBContract.Event.COLUMN_LOCATION_CODE,
                DBContract.Event.COLUMN_EVENT_NAME,
                DBContract.Event.COLUMN_EVENT_VENUE, DBContract.Event.COLUMN_EVENT_DETAILS, DBContract.Event.COLUMN_MIN_PRICE,
                DBContract.Event.COLUMN_MAX_PRICE, DBContract.Event.COLUMN_START_TIMESTAMP, DBContract.Event.COLUMN_END_TIMESTAMP, DBContract.Event.COLUMN_BITMAP_NAME,
                DBContract.Event.COLUMN_VIEWS, DBContract.Event.COLUMN_LIKES, DBContract.Event.COLUMN_VIEWED,
                DBContract.Event.COLUMN_LIKED, DBContract.Event.COLUMN_VIDEO, DBContract.Event.COLUMN_WHEN};
        cursor = db.query(true, DBContract.Event.TABLE_NAME,projections, DBContract.Event.COLUMN_EVENT_STATUS+"='new'",
                null, DBContract.Event.COLUMN_EVENT_ID,
                null, DBContract.Event.COLUMN_ID + " DESC", null);

        return cursor;
    }

    Cursor getEventsWaiting(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID,DBContract.Event.COLUMN_EVENT_ID,DBContract.Event.COLUMN_INTEREST_CODE,
                DBContract.Event.COLUMN_LOCATION_CODE,
                DBContract.Event.COLUMN_EVENT_NAME,
                DBContract.Event.COLUMN_EVENT_VENUE,DBContract.Event.COLUMN_EVENT_DETAILS,DBContract.Event.COLUMN_MIN_PRICE,
                DBContract.Event.COLUMN_MAX_PRICE,DBContract.Event.COLUMN_START_TIMESTAMP,DBContract.Event.COLUMN_END_TIMESTAMP,DBContract.Event.COLUMN_BITMAP_NAME,
                DBContract.Event.COLUMN_VIEWS,DBContract.Event.COLUMN_LIKES,DBContract.Event.COLUMN_VIEWED,
                DBContract.Event.COLUMN_LIKED,DBContract.Event.COLUMN_VIDEO,
                DBContract.Event.CONF_CODE, DBContract.Event.BROADCAST_CHARGE,
                DBContract.Event.COLUMN_EVENT_STATUS, DBContract.Event.COLUMN_WHEN};
        cursor = db.query(DBContract.Event.TABLE_NAME,projections,
                DBContract.Event.COLUMN_EVENT_STATUS + "='waiting' OR " + DBContract.Event.COLUMN_EVENT_STATUS + "='published'",
                null,null,
                null, null);

        return cursor;
    }

    Cursor checkNowPlaying(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {DBContract.Event.COLUMN_ID, DBContract.Event.COLUMN_START_TIMESTAMP, DBContract.Event.COLUMN_END_TIMESTAMP};
        cursor = db.query(DBContract.Event.TABLE_NAME,projections,
                DBContract.Event.COLUMN_EVENT_STATUS+"='new' OR " +
                        DBContract.Event.COLUMN_EVENT_STATUS + "='now_playing' OR " +
                        DBContract.Event.COLUMN_EVENT_STATUS + "='published'",
                null,null,null,null);

        return cursor;
    }

    public Bitmap getEventBitmap(SQLiteDatabase db, String rowId){
        String[] projections = {DBContract.Event.COLUMN_POSTER_BITMAP};
        Bitmap bitmap = null;
        Cursor cursor = db.query(DBContract.Event.TABLE_NAME,projections, DBContract.Event.COLUMN_ID+"='"+rowId+"'",null,null,null,null);
        if(cursor.moveToNext()){
            byte[] byteImage = cursor.getBlob(cursor.getColumnIndex(DBContract.PreEvent.COLUMN_POSTER_BITMAP));
            bitmap = BitmapFactory.decodeByteArray(byteImage,0,byteImage.length);
        }
        cursor.close();
        return bitmap;
    }

    String getCity(SQLiteDatabase db, int id){

        String city = "";
        String[] projections = {DBContract.City.COLUMN_CITY_NAME};
        Cursor cursor = db.query(DBContract.City.TABLE_NAME,projections, DBContract.City.COLUMN_CITY_ID+"='"+id+"'",null,null,null,null);
        while (cursor.moveToNext()){
            city = cursor.getString(cursor.getColumnIndex(DBContract.City.COLUMN_CITY_NAME));
        }
        cursor.close();

        return city;
    }

    String getWhens(SQLiteDatabase db) {

        String[] projections = {DBContract.Event.COLUMN_WHEN};
        Cursor cursor = db.query(DBContract.Event.TABLE_NAME, projections, DBContract.Event.COLUMN_ID + "='200'", null, null, null, null);
        String whens = "";
        while (cursor.moveToNext()) {
            whens = cursor.getString(cursor.getColumnIndex(DBContract.Event.COLUMN_WHEN));
        }
        cursor.close();
        return whens;
    }

    String getSuburb(SQLiteDatabase db, int id){
        String location = "";
        String[] projections = {DBContract.Location.COLUMN_LOCATION_NAME};
        Cursor cursor = db.query(DBContract.Location.TABLE_NAME,projections, DBContract.Location.COLUMN_LOCATION_ID+"='"+id+"'",null,null,null,null);
        while (cursor.moveToNext()){
            location = cursor.getString(cursor.getColumnIndex(DBContract.Location.COLUMN_LOCATION_NAME));
        }
        cursor.close();

        return location;
    }


    public String[] getEventCities(SQLiteDatabase db){
        String[] arrayEventCities = new String[]{""};
        List<String> listCities = new LinkedList<String>(Arrays.asList(arrayEventCities));
        String[] projections = {DBContract.City.COLUMN_CITY_NAME};
        Cursor cursor = db.query(DBContract.City.TABLE_NAME,projections,null,null,null,null,null);
        while (cursor.moveToNext()){
            listCities.add(cursor.getString(cursor.getColumnIndex(DBContract.City.COLUMN_CITY_NAME)));
        }
        arrayEventCities = listCities.toArray(new String[listCities.size()]);
        cursor.close();
        return arrayEventCities;
    }

    public String[] getEventLocations(SQLiteDatabase db){
        String[] arrayLocations = new String[]{""};
        List<String> listLocations = new LinkedList<String>(Arrays.asList(arrayLocations));
        String[] projections = {DBContract.Ad.COLUMN_BRAND_NAME};

        Cursor cursor = db.query(DBContract.Ad.TABLE_NAME,projections,null,null,null,null,null);
        while (cursor.moveToNext()){
            listLocations.add(cursor.getString(cursor.getColumnIndex(DBContract.Ad.COLUMN_BRAND_NAME)));
        }

        arrayLocations = listLocations.toArray(new String[listLocations.size()]);
        cursor.close();
        return arrayLocations;
    }

    public Cursor getLocation(SQLiteDatabase db, String cityId){

        String[] projections = {DBContract.Location.COLUMN_LOCATION_ID, DBContract.Location.COLUMN_LOCATION_NAME, DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE};
        Cursor cursor = db.query(DBContract.Location.TABLE_NAME,projections, DBContract.Location.COLUMN_CITY_ID+"='"+cityId+"'",null,null,null,null);

        return cursor;
    }

    public void user(SQLiteDatabase db, String action, String user_id, String phoneNumber){
        if(action.equals("user_id_insertion")){
            ContentValues values = new ContentValues();
            ContentValues userPref = new ContentValues();
            values.put(DBContract.User.COLUMN_USER_ID,user_id);
            values.put(DBContract.User.COLUMN_PHONE_NUMBER,phoneNumber);
            values.put(DBContract.User.COLUMN_APP_VERSION,"");
            userPref.put(DBContract.UserPref.COLUMN_USER_ID,user_id);
            db.insert(DBContract.User.TABLE_NAME,null,values);
            db.insert(DBContract.UserPref.TABLE_NAME,null,userPref);
        }
    }
    public void interest(SQLiteDatabase db){}
    public void event(SQLiteDatabase db, String action){}
    public void ad(SQLiteDatabase db, String action){}
    public void event_stats(SQLiteDatabase db){}
    public void ad_stats(SQLiteDatabase db){}
    public void user_pref(SQLiteDatabase db, String action){}

    public void addInfo(SQLiteDatabase db){
        ContentValues continentAfrica = new ContentValues();
        continentAfrica.put(DBContract.Continent.COLUMN_CONTINENT_NAME, DBContract.Continent.VALUE_CONTINENT_NAME_AFRICA);
        continentAfrica.put(DBContract.Continent.COLUMN_CONTINENT_BROADCAST_RANGE, DBContract.Continent.VALUE_CONTINENT_BROADCAST_RANGE_AFRICA);
        db.insert(DBContract.Continent.TABLE_NAME,null,continentAfrica);

        ContentValues countryZimbabwe = new ContentValues();
        countryZimbabwe.put(DBContract.Country.COLUMN_CONTINENT_ID, DBContract.Country.VALUE_CONTINENT_ID_ZIMBABWE);
        countryZimbabwe.put(DBContract.Country.COLUMN_COUNTRY_NAME, DBContract.Country.VALUE_COUNTRY_NAME_ZIMBABWE);
        countryZimbabwe.put(DBContract.Country.COLUMN_COUNTRY_BROADCAST_RANGE, DBContract.Country.VALUE_BROADCAST_RANGE_ZIMBABWE);
        db.insert(DBContract.Country.TABLE_NAME,null,countryZimbabwe);

        ContentValues provinceBulawayo = new ContentValues();
        provinceBulawayo.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_BULAWAYO);
        provinceBulawayo.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_BULAWAYO);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceBulawayo);

        ContentValues provinceHarare = new ContentValues();
        provinceHarare.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_HARARE);
        provinceHarare.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_HARARE);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceHarare);

        ContentValues provinceManicaland = new ContentValues();
        provinceManicaland.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MANICALAND);
        provinceManicaland.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MANICALAND);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceManicaland);

        ContentValues provinceMashonalandCentral = new ContentValues();
        provinceMashonalandCentral.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MASHONALAND_CENTRAL);
        provinceMashonalandCentral.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MASHONALAND_CENTRAL);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMashonalandCentral);

        ContentValues provinceMashonalandEast = new ContentValues();
        provinceMashonalandEast.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MASHONALAND_EAST);
        provinceMashonalandEast.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MASHONALAND_EAST);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMashonalandEast);

        ContentValues provinceMashonalandWest = new ContentValues();
        provinceMashonalandWest.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MASHONALAND_WEST);
        provinceMashonalandWest.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MASHONALAND_WEST);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMashonalandWest);

        ContentValues provinceMasvingo = new ContentValues();
        provinceMasvingo.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MASVINGO);
        provinceMasvingo.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MASVINGO);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMasvingo);

        ContentValues provinceMatabelelandNorth = new ContentValues();
        provinceMatabelelandNorth.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MATABELELAND_NORTH);
        provinceMatabelelandNorth.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MATABELELAND_NORTH);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMatabelelandNorth);

        ContentValues provinceMatabelelandSouth = new ContentValues();
        provinceMatabelelandSouth.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MATABELELAND_SOUTH);
        provinceMatabelelandSouth.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MATABELELAND_SOUTH);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMatabelelandSouth);

        ContentValues provinceMidlands = new ContentValues();
        provinceMidlands.put(DBContract.Province.COLUMN_COUNTRY_ID, DBContract.Province.VALUE_COUNTRY_ID_MIDLANDS);
        provinceMidlands.put(DBContract.Province.COLUMN_PROVINCE_NAME, DBContract.Province.VALUE_PROVINCE_NAME_MIDLANDS);
        db.insert(DBContract.Province.TABLE_NAME,null,provinceMidlands);

        ContentValues cityBeitbridge = new ContentValues();
        cityBeitbridge.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_BEITBRIDGE);
        cityBeitbridge.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_BEITBRIDGE);
        cityBeitbridge.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_BEITBRIDGE);
        db.insert(DBContract.City.TABLE_NAME,null,cityBeitbridge);

        ContentValues cityBindura = new ContentValues();
        cityBindura.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_BINDURA);
        cityBindura.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_BINDURA);
        cityBindura.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_BINDURA);
        db.insert(DBContract.City.TABLE_NAME,null,cityBindura);

        ContentValues cityBulawayo = new ContentValues();
        cityBulawayo.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_BULAWAYO);
        cityBulawayo.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_BULAWAYO);
        cityBulawayo.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_BULAWAYO);
        db.insert(DBContract.City.TABLE_NAME,null,cityBulawayo);

        ContentValues cityChegutu = new ContentValues();
        cityChegutu.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_CHEGUTU);
        cityChegutu.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_CHEGUTU);
        cityChegutu.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_CHEGUTU);
        db.insert(DBContract.City.TABLE_NAME,null,cityChegutu);

        ContentValues cityChinhoyi = new ContentValues();
        cityChinhoyi.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_CHINHOYI);
        cityChinhoyi.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_CHINHOYI);
        cityChinhoyi.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_CHINHOYI);
        db.insert(DBContract.City.TABLE_NAME,null,cityChinhoyi);

        ContentValues cityChipinge = new ContentValues();
        cityChipinge.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_CHIPINGE);
        cityChipinge.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_CHIPINGE);
        cityChipinge.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_CHIPINGE);
        db.insert(DBContract.City.TABLE_NAME,null,cityChipinge);

        ContentValues cityChiredzi = new ContentValues();
        cityChiredzi.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_CHIREDZI);
        cityChiredzi.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_CHIREDZI);
        cityChiredzi.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_CHIREDZI);
        db.insert(DBContract.City.TABLE_NAME,null,cityChiredzi);

        ContentValues cityChitungwiza = new ContentValues();
        cityChitungwiza.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_CHITUNGWIZA);
        cityChitungwiza.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_CHITUNGWIZA);
        cityChitungwiza.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_CHITUNGWIZA);
        db.insert(DBContract.City.TABLE_NAME,null,cityChitungwiza);

        ContentValues cityGwanda = new ContentValues();
        cityGwanda.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_GWANDA);
        cityGwanda.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_GWANDA);
        cityGwanda.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_GWANDA);
        db.insert(DBContract.City.TABLE_NAME,null,cityGwanda);

        ContentValues cityGweru = new ContentValues();
        cityGweru.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_GWERU);
        cityGweru.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_GWERU);
        cityGweru.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_GWERU);
        db.insert(DBContract.City.TABLE_NAME,null,cityGweru);

        ContentValues cityHarare = new ContentValues();
        cityHarare.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_HARARE);
        cityHarare.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_HARARE);
        cityHarare.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_HARARE);
        db.insert(DBContract.City.TABLE_NAME,null,cityHarare);

        ContentValues cityKadoma = new ContentValues();
        cityKadoma.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_KADOMA);
        cityKadoma.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_KADOMA);
        cityKadoma.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_KADOMA);
        db.insert(DBContract.City.TABLE_NAME,null,cityKadoma);

        ContentValues cityKariba = new ContentValues();
        cityKariba.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_KARIBA);
        cityKariba.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_KARIBA);
        cityKariba.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_KARIBA);
        db.insert(DBContract.City.TABLE_NAME,null,cityKariba);

        ContentValues cityKaroi = new ContentValues();
        cityKaroi.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_KAROI);
        cityKaroi.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_KAROI);
        cityKaroi.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_KAROI);
        db.insert(DBContract.City.TABLE_NAME,null,cityKaroi);

        ContentValues cityKwekwe = new ContentValues();
        cityKwekwe.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_KWEKWE);
        cityKwekwe.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_KWEKWE);
        cityKwekwe.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_KWEKWE);
        db.insert(DBContract.City.TABLE_NAME,null,cityKwekwe);

        ContentValues cityMarondera = new ContentValues();
        cityMarondera.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_MARONDERA);
        cityMarondera.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_MARONDERA);
        cityMarondera.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_MARONDERA);
        db.insert(DBContract.City.TABLE_NAME,null,cityMarondera);

        ContentValues cityMasvingo = new ContentValues();
        cityMasvingo.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_MASVINGO);
        cityMasvingo.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_MASVINGO);
        cityMasvingo.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_MASVINGO);
        db.insert(DBContract.City.TABLE_NAME,null,cityMasvingo);

        ContentValues cityMutare = new ContentValues();
        cityMutare.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_MUTARE);
        cityMutare.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_MUTARE);
        cityMutare.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_MUTARE);
        db.insert(DBContract.City.TABLE_NAME,null,cityMutare);

        ContentValues cityNyanga = new ContentValues();
        cityNyanga.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_NYANGA);
        cityNyanga.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_NYANGA);
        cityNyanga.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_NYANGA);
        db.insert(DBContract.City.TABLE_NAME,null,cityNyanga);

        ContentValues cityPlumtree = new ContentValues();
        cityPlumtree.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_PLUMTREE);
        cityPlumtree.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_PLUMTREE);
        cityPlumtree.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_PLUMTREE);
        db.insert(DBContract.City.TABLE_NAME,null,cityPlumtree);

        ContentValues cityRusape = new ContentValues();
        cityRusape.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_RUSAPE);
        cityRusape.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_RUSAPE);
        cityRusape.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_RUSAPE);
        db.insert(DBContract.City.TABLE_NAME,null,cityRusape);

        ContentValues cityShurugwi = new ContentValues();
        cityShurugwi.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_SHURUGWI);
        cityShurugwi.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_SHURUGWI);
        cityShurugwi.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_SHURUGWI);
        db.insert(DBContract.City.TABLE_NAME,null,cityShurugwi);

        ContentValues cityVictoriaFalls = new ContentValues();
        cityVictoriaFalls.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_VICTORIA_FALLS);
        cityVictoriaFalls.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_VICTORIA_FALLS);
        cityVictoriaFalls.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_VICTORIA_FALLS);
        db.insert(DBContract.City.TABLE_NAME,null,cityVictoriaFalls);

        ContentValues cityZvishavane = new ContentValues();
        cityZvishavane.put(DBContract.City.COLUMN_PROVINCE_ID, DBContract.City.VALUE_PROVINCE_ID_ZVISHAVANE);
        cityZvishavane.put(DBContract.City.COLUMN_COUNTRY_ID, DBContract.City.VALUE_COUNTRY_ID_ZVISHAVANE);
        cityZvishavane.put(DBContract.City.COLUMN_CITY_NAME, DBContract.City.VALUE_CITY_NAME_ZVISHAVANE);
        db.insert(DBContract.City.TABLE_NAME,null,cityZvishavane);



        ContentValues locationBeitbridgeCBD = new ContentValues();
        locationBeitbridgeCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BEITBRIDGE_CITY_ID);
        locationBeitbridgeCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BEITBRIDGE_LOCATION_NAME_CBD);
        locationBeitbridgeCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BEITBRIDGE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBeitbridgeCBD);

        ContentValues locationBeitbridgeDulibadzimu = new ContentValues();
        locationBeitbridgeDulibadzimu.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BEITBRIDGE_CITY_ID);
        locationBeitbridgeDulibadzimu.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BEITBRIDGE_LOCATION_NAME_DULIBADZIMU);
        locationBeitbridgeDulibadzimu.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BEITBRIDGE_LOCATION_BROADCAST_RANGE_DULIBADZIMU);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBeitbridgeDulibadzimu);

        ContentValues locationBinduraAerodrome = new ContentValues();
        locationBinduraAerodrome.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraAerodrome.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_AERODROME);
        locationBinduraAerodrome.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_AERODROME);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraAerodrome);

        ContentValues locationBinduraSuburb = new ContentValues();
        locationBinduraSuburb.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraSuburb.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_BINDURA_SUBURB);
        locationBinduraSuburb.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_BINDURA_SUBURB);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraSuburb);

        ContentValues locationBinduraCBD = new ContentValues();
        locationBinduraCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_CBD);
        locationBinduraCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraCBD);

        ContentValues locationBinduraChipadze = new ContentValues();
        locationBinduraChipadze.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraChipadze.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_CHIPADZE);
        locationBinduraChipadze.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CHIPADZE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraChipadze);

        ContentValues locationBinduraChiwaridzo = new ContentValues();
        locationBinduraChiwaridzo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraChiwaridzo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_CHIWARIDZO);
        locationBinduraChiwaridzo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CHIWARIDZO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraChiwaridzo);

        ContentValues locationBinduraGarikai = new ContentValues();
        locationBinduraGarikai.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BINDURA_CITY_ID);
        locationBinduraGarikai.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BINDURA_LOCATION_NAME_GARIKAI);
        locationBinduraGarikai.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BINDURA_LOCATION_BROADCAST_RANGE_GARIKAI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBinduraGarikai);

        ContentValues locationBulawayoAscot = new ContentValues();
        locationBulawayoAscot.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoAscot.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ASCOT);
        locationBulawayoAscot.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ASCOT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoAscot);

        ContentValues locationBulawayoBarbourFields = new ContentValues();
        locationBulawayoBarbourFields.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBarbourFields.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BARBOUR_FIELDS);
        locationBulawayoBarbourFields.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BARBOUR_FIELDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBarbourFields);

        ContentValues locationBulawayoBarhamGreen = new ContentValues();
        locationBulawayoBarhamGreen.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBarhamGreen.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BARHAM_GREEN);
        locationBulawayoBarhamGreen.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BARHAM_GREEN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBarhamGreen);

        ContentValues locationBulawayoBeaconHill = new ContentValues();
        locationBulawayoBeaconHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBeaconHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BEACON_HILL);
        locationBulawayoBeaconHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BEACON_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBeaconHill);

        ContentValues locationBulawayoBellevue = new ContentValues();
        locationBulawayoBellevue.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBellevue.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BELLEVUE);
        locationBulawayoBellevue.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELLEVUE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBellevue);

        ContentValues locationBulawayoBelmont = new ContentValues();
        locationBulawayoBelmont.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBelmont.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BELMONT);
        locationBulawayoBelmont.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELMONT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBelmont);

        ContentValues locationBulawayoBelmontIndustrialArea = new ContentValues();
        locationBulawayoBelmontIndustrialArea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBelmontIndustrialArea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BELMONT_INDUSTRIAL_AREA);
        locationBulawayoBelmontIndustrialArea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELMONT_INDUSTRIAL_AREA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBelmontIndustrialArea);

        ContentValues locationBulawayoBradfield = new ContentValues();
        locationBulawayoBradfield.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBradfield.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BRADFIELD);
        locationBulawayoBradfield.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BRADFIELD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBradfield);

        ContentValues locationBulawayoBurnside = new ContentValues();
        locationBulawayoBurnside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoBurnside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_BURNSIDE);
        locationBulawayoBurnside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BURNSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoBurnside);

        ContentValues locationBulawayoCement = new ContentValues();
        locationBulawayoCement.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoCement.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_CEMENT);
        locationBulawayoCement.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_CEMENT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoCement);

        ContentValues locationBulawayoCityCenter = new ContentValues();
        locationBulawayoCityCenter.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoCityCenter.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_CITY_CENTER);
        locationBulawayoCityCenter.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_CITY_CENTER);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoCityCenter);

        ContentValues locationBulawayoCowdrayPark = new ContentValues();
        locationBulawayoCowdrayPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoCowdrayPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_COWDRAY_PARK);
        locationBulawayoCowdrayPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_COWDRAY_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoCowdrayPark);

        ContentValues locationBulawayoDonnington = new ContentValues();
        locationBulawayoDonnington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoDonnington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_DONNINGTON);
        locationBulawayoDonnington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DONNINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoDonnington);

        ContentValues locationBulawayoDonningtonWest = new ContentValues();
        locationBulawayoDonningtonWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoDonningtonWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_DONNINGTON_WEST);
        locationBulawayoDonningtonWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DONNINGTON_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoDonningtonWest);

        ContentValues locationBulawayoDouglasdale = new ContentValues();
        locationBulawayoDouglasdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoDouglasdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_DOUGLASDALE);
        locationBulawayoDouglasdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DOUGLASDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoDouglasdale);

        ContentValues locationBulawayoEloana = new ContentValues();
        locationBulawayoEloana.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEloana.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ELOANA);
        locationBulawayoEloana.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ELOANA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEloana);

        ContentValues locationBulawayoEmakhandeni = new ContentValues();
        locationBulawayoEmakhandeni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEmakhandeni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_EMAKHANDENI);
        locationBulawayoEmakhandeni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_EMAKHANDENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEmakhandeni);

        ContentValues locationBulawayoEmganwini = new ContentValues();
        locationBulawayoEmganwini.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEmganwini.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_EMGANWINI);
        locationBulawayoEmganwini.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_EMGANWINI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEmganwini);

        ContentValues locationBulawayoEnqameni = new ContentValues();
        locationBulawayoEnqameni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEnqameni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ENQAMENI);
        locationBulawayoEnqameni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENQAMENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEnqameni);

        ContentValues locationBulawayoEnqotsheni = new ContentValues();
        locationBulawayoEnqotsheni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEnqotsheni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ENQOTSHENI);
        locationBulawayoEnqotsheni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENQOTSHENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEnqotsheni);

        ContentValues locationBulawayoEntumbane = new ContentValues();
        locationBulawayoEntumbane.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoEntumbane.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ENTUMBANE);
        locationBulawayoEntumbane.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENTUMBANE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoEntumbane);

        ContentValues locationBulawayoFagadola = new ContentValues();
        locationBulawayoFagadola.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoFagadola.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_FAGADOLA);
        locationBulawayoFagadola.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FAGADOLA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoFagadola);

        ContentValues locationBulawayoFamona = new ContentValues();
        locationBulawayoFamona.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoFamona.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_FAMONA);
        locationBulawayoFamona.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FAMONA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoFamona);

        ContentValues locationBulawayoFortunesGate = new ContentValues();
        locationBulawayoFortunesGate.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoFortunesGate.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_FORTUNES_GATE);
        locationBulawayoFortunesGate.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FORTUNES_GATE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoFortunesGate);

        ContentValues locationBulawayoFourWinds = new ContentValues();
        locationBulawayoFourWinds.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoFourWinds.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_FOUR_WINDS);
        locationBulawayoFourWinds.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FOUR_WINDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoFourWinds);

        ContentValues locationBulawayoGlencoe = new ContentValues();
        locationBulawayoGlencoe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGlencoe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GLENCOE);
        locationBulawayoGlencoe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENCOE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGlencoe);

        ContentValues locationBulawayoGlengary = new ContentValues();
        locationBulawayoGlengary.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGlengary.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GLENGARY);
        locationBulawayoGlengary.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENGARY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGlengary);

        ContentValues locationBulawayoGlenville = new ContentValues();
        locationBulawayoGlenville.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGlenville.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GLENVILLE);
        locationBulawayoGlenville.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENVILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGlenville);

        ContentValues locationBulawayoGranitePark = new ContentValues();
        locationBulawayoGranitePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGranitePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GRANITE_PARK);
        locationBulawayoGranitePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GRANITE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGranitePark);

        ContentValues locationBulawayoGreenHill = new ContentValues();
        locationBulawayoGreenHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGreenHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GREEN_HILL);
        locationBulawayoGreenHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GREEN_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGreenHill);

        ContentValues locationBulawayoGwabalanda = new ContentValues();
        locationBulawayoGwabalanda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoGwabalanda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_GWABALANDA);
        locationBulawayoGwabalanda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GWABALANDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoGwabalanda);

        ContentValues locationBulawayoHarrisvale = new ContentValues();
        locationBulawayoHarrisvale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHarrisvale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HARRISVALE);
        locationBulawayoHarrisvale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HARRISVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHarrisvale);

        ContentValues locationBulawayoHelenvale = new ContentValues();
        locationBulawayoHelenvale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHelenvale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HELENVALE);
        locationBulawayoHelenvale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HELENVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHelenvale);

        ContentValues locationBulawayoHighmount = new ContentValues();
        locationBulawayoHighmount.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHighmount.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HIGHMOUNT);
        locationBulawayoHighmount.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HIGHMOUNT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHighmount);

        ContentValues locationBulawayoHillcrest = new ContentValues();
        locationBulawayoHillcrest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHillcrest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HILLCREST);
        locationBulawayoHillcrest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLCREST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHillcrest);

        ContentValues locationBulawayoHillside = new ContentValues();
        locationBulawayoHillside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHillside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HILLSIDE);
        locationBulawayoHillside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHillside);

        ContentValues locationBulawayoHillsideSouth = new ContentValues();
        locationBulawayoHillsideSouth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHillsideSouth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HILLSIDE_SOUTH);
        locationBulawayoHillsideSouth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLSIDE_SOUTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHillsideSouth);

        ContentValues locationBulawayoHumePark = new ContentValues();
        locationBulawayoHumePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHumePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HUME_PARK);
        locationBulawayoHumePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HUME_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHumePark);

        ContentValues locationBulawayoHydePark = new ContentValues();
        locationBulawayoHydePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoHydePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_HYDE_PARK);
        locationBulawayoHydePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HYDE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoHydePark);

        ContentValues locationBulawayoIlanda = new ContentValues();
        locationBulawayoIlanda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoIlanda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_Ilanda);
        locationBulawayoIlanda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_Ilanda);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoIlanda);

        ContentValues locationBulawayoIminyela = new ContentValues();
        locationBulawayoIminyela.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoIminyela.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_IMINYELA);
        locationBulawayoIminyela.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_IMINYELA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoIminyela);

        ContentValues locationBulawayoIntini = new ContentValues();
        locationBulawayoIntini.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoIntini.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_INTINI);
        locationBulawayoIntini.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_INTINI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoIntini);

        ContentValues locationBulawayoJacaranda = new ContentValues();
        locationBulawayoJacaranda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoJacaranda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_JACARANDA);
        locationBulawayoJacaranda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_JACARANDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoJacaranda);

        ContentValues locationBulawayoKelvin = new ContentValues();
        locationBulawayoKelvin.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKelvin.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KELVIN);
        locationBulawayoKelvin.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KELVIN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKelvin);

        ContentValues locationBulawayoKenilworth = new ContentValues();
        locationBulawayoKenilworth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKenilworth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KENILWORTH);
        locationBulawayoKenilworth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KENILWORTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKenilworth);

        ContentValues locationBulawayoKhumalo = new ContentValues();
        locationBulawayoKhumalo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKhumalo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KHUMALO);
        locationBulawayoKhumalo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KHUAMLO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKhumalo);

        ContentValues locationBulawayoKhumaloNorth = new ContentValues();
        locationBulawayoKhumaloNorth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKhumaloNorth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KHUMALO_NORTH);
        locationBulawayoKhumaloNorth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KHUMALO_NORTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKhumaloNorth);

        ContentValues locationBulawayoKilallo = new ContentValues();
        locationBulawayoKilallo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKilallo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KILALLO);
        locationBulawayoKilallo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KILALLO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKilallo);

        ContentValues locationBulawayoKillarney = new ContentValues();
        locationBulawayoKillarney.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKillarney.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KILLARNEY);
        locationBulawayoKillarney.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KILLARNEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKillarney);

        ContentValues locationBulawayoKingsdale = new ContentValues();
        locationBulawayoKingsdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoKingsdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_KINGSDALE);
        locationBulawayoKingsdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KINGSDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoKingsdale);

        ContentValues locationBulawayoLakeside = new ContentValues();
        locationBulawayoLakeside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoLakeside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_LAKESIDE);
        locationBulawayoLakeside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LAKESIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoLakeside);

        ContentValues locationBulawayoLobengula = new ContentValues();
        locationBulawayoLobengula.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoLobengula.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_LOBENGULA);
        locationBulawayoLobengula.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOBENGULA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoLobengula);

        ContentValues locationBulawayoLobenvale = new ContentValues();
        locationBulawayoLobenvale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoLobenvale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_LOBENVALE);
        locationBulawayoLobenvale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOBENVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoLobenvale);

        ContentValues locationBulawayoLochview = new ContentValues();
        locationBulawayoLochview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoLochview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_LOCHVIEW);
        locationBulawayoLochview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOCHVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoLochview);

        ContentValues locationBulawayoLuveve = new ContentValues();
        locationBulawayoLuveve.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoLuveve.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_LUVEVE);
        locationBulawayoLuveve.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LUVEVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoLuveve);

        ContentValues locationBulawayoMabuthweni = new ContentValues();
        locationBulawayoMabuthweni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMabuthweni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MABUTHWENI);
        locationBulawayoMabuthweni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MABUTHWENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMabuthweni);

        ContentValues locationBulawayoMagwegwe = new ContentValues();
        locationBulawayoMagwegwe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMagwegwe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE);
        locationBulawayoMagwegwe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMagwegwe);

        ContentValues locationBulawayoMagwegweNorth = new ContentValues();
        locationBulawayoMagwegweNorth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMagwegweNorth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE_NORTH);
        locationBulawayoMagwegweNorth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE_NORTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMagwegweNorth);

        ContentValues locationBulawayoMagwegweWest = new ContentValues();
        locationBulawayoMagwegweWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMagwegweWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE_WEST);
        locationBulawayoMagwegweWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMagwegweWest);

        ContentValues locationBulawayoMahatshula = new ContentValues();
        locationBulawayoMahatshula.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMahatshula.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MAHATSHULA);
        locationBulawayoMahatshula.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAHATSHULA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMahatshula);

        ContentValues locationBulawayoMakokoba = new ContentValues();
        locationBulawayoMakokoba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMakokoba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MAKOKOBA);
        locationBulawayoMakokoba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAKOKOBA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMakokoba);

        ContentValues locationBulawayoMalindela = new ContentValues();
        locationBulawayoMalindela.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMalindela.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MALINDELA);
        locationBulawayoMalindela.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MALINDELA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMalindela);

        ContentValues locationBulawayoManningdale = new ContentValues();
        locationBulawayoManningdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoManningdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MANNINGDALE);
        locationBulawayoManningdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MANNINGDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoManningdale);

        ContentValues locationBulawayoMarlands = new ContentValues();
        locationBulawayoMarlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMarlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MARLANDS);
        locationBulawayoMarlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MARLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMarlands);

        ContentValues locationBulawayoMatsheumhlophe = new ContentValues();
        locationBulawayoMatsheumhlophe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMatsheumhlophe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MATSHEUMHLOPHE);
        locationBulawayoMatsheumhlophe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MATSHEUMHLOPHE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMatsheumhlophe);

        ContentValues locationBulawayoMatshobana = new ContentValues();
        locationBulawayoMatshobana.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMatshobana.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MATSHOBANA);
        locationBulawayoMatshobana.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MATSHOBANA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMatshobana);

        ContentValues locationBulawayoMontgomery = new ContentValues();
        locationBulawayoMontgomery.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMontgomery.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MONTGOMERY);
        locationBulawayoMontgomery.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MONTGOMERY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMontgomery);

        ContentValues locationBulawayoMontrose = new ContentValues();
        locationBulawayoMontrose.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMontrose.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MONTROSE);
        locationBulawayoMontrose.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MONTROSE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMontrose);

        ContentValues locationBulawayoMorningside = new ContentValues();
        locationBulawayoMorningside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMorningside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MORNINGSIDE);
        locationBulawayoMorningside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MORNINGSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMorningside);

        ContentValues locationBulawayoMpopoma = new ContentValues();
        locationBulawayoMpopoma.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMpopoma.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MPOPOMA);
        locationBulawayoMpopoma.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MPOPOMA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMpopoma);

        ContentValues locationBulawayoMunda = new ContentValues();
        locationBulawayoMunda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMunda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MUNDA);
        locationBulawayoMunda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MUNDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMunda);

        ContentValues locationBulawayoMzilikazi = new ContentValues();
        locationBulawayoMzilikazi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoMzilikazi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_MZILIKAZI);
        locationBulawayoMzilikazi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MZILIKAZI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoMzilikazi);

        ContentValues locationBulawayoNewLuveve = new ContentValues();
        locationBulawayoNewLuveve.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNewLuveve.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NewLuveve);
        locationBulawayoNewLuveve.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NewLuveve);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNewLuveve);

        ContentValues locationBulawayoNewmansford = new ContentValues();
        locationBulawayoNewmansford.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNewmansford.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NEWSMANSFORD);
        locationBulawayoNewmansford.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWSMANSFORD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNewmansford);

        ContentValues locationBulawayoNewton = new ContentValues();
        locationBulawayoNewton.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNewton.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NEWTON);
        locationBulawayoNewton.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNewton);

        ContentValues locationBulawayoNewtonWest = new ContentValues();
        locationBulawayoNewtonWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNewtonWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NEWTON_WEST);
        locationBulawayoNewtonWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWTON_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNewtonWest);

        ContentValues locationBulawayoNguboyenja = new ContentValues();
        locationBulawayoNguboyenja.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNguboyenja.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NGUBOYENJA);
        locationBulawayoNguboyenja.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NGUBOYENJA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNguboyenja);

        ContentValues locationBulawayoNjube = new ContentValues();
        locationBulawayoNjube.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNjube.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NJUBE);
        locationBulawayoNjube.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NJUBE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNjube);

        ContentValues locationBulawayoNketa = new ContentValues();
        locationBulawayoNketa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNketa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NKETA);
        locationBulawayoNketa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NKETA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNketa);

        ContentValues locationBulawayoNkulumane = new ContentValues();
        locationBulawayoNkulumane.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNkulumane.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NKULUMANE);
        locationBulawayoNkulumane.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NKULUMANE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNkulumane);

        ContentValues locationBulawayoNorthEnd = new ContentValues();
        locationBulawayoNorthEnd.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNorthEnd.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NORTH_END);
        locationBulawayoNorthEnd.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_END);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNorthEnd);

        ContentValues locationBulawayoNorthLynne = new ContentValues();
        locationBulawayoNorthLynne.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNorthLynne.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NORTH_LYNNE);
        locationBulawayoNorthLynne.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_LYNNE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNorthLynne);

        ContentValues locationBulawayoNorthTrenance = new ContentValues();
        locationBulawayoNorthTrenance.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNorthTrenance.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NORTH_TRENANCE);
        locationBulawayoNorthTrenance.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_TRENANCE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNorthTrenance);

        ContentValues locationBulawayoNorthlea = new ContentValues();
        locationBulawayoNorthlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNorthlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NORTHLEA);
        locationBulawayoNorthlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTHLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNorthlea);

        ContentValues locationBulawayoNorthvale = new ContentValues();
        locationBulawayoNorthvale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNorthvale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NORTHVALE);
        locationBulawayoNorthvale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTHVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNorthvale);

        ContentValues locationBulawayoNtabaMoyo = new ContentValues();
        locationBulawayoNtabaMoyo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoNtabaMoyo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_NTABA_MOYO);
        locationBulawayoNtabaMoyo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NTABA_MOYO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoNtabaMoyo);

        ContentValues locationBulawayoOrangeGrove = new ContentValues();
        locationBulawayoOrangeGrove.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoOrangeGrove.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ORANGE_GROVE);
        locationBulawayoOrangeGrove.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ORANGE_GROVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoOrangeGrove);

        ContentValues locationBulawayoPaddonhurst = new ContentValues();
        locationBulawayoPaddonhurst.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoPaddonhurst.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PADDONHURST);
        locationBulawayoPaddonhurst.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PADDONHURST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoPaddonhurst);

        ContentValues locationBulawayoParkslands = new ContentValues();
        locationBulawayoParkslands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoParkslands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PARKLANDS);
        locationBulawayoParkslands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PARKLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoParkslands);

        ContentValues locationBulawayoParkview = new ContentValues();
        locationBulawayoParkview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoParkview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PARKVIEW);
        locationBulawayoParkview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PARKVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoParkview);

        ContentValues locationBulawayoPhelandaba = new ContentValues();
        locationBulawayoPhelandaba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoPhelandaba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PHELANDABA);
        locationBulawayoPhelandaba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PHELANDABA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoPhelandaba);

        ContentValues locationBulawayoPumula = new ContentValues();
        locationBulawayoPumula.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoPumula.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PUMULA);
        locationBulawayoPumula.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PUMULA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoPumula);

        ContentValues locationBulawayoPumulaSouth = new ContentValues();
        locationBulawayoPumulaSouth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoPumulaSouth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_PUMULA_SOUTH);
        locationBulawayoPumulaSouth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PUMULA_SOUTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoPumulaSouth);

        ContentValues locationBulawayoQueensPark = new ContentValues();
        locationBulawayoQueensPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoQueensPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK);
        locationBulawayoQueensPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoQueensPark);

        ContentValues locationBulawayoQueensParkEast = new ContentValues();
        locationBulawayoQueensParkEast.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoQueensParkEast.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK_EAST);
        locationBulawayoQueensParkEast.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK_EAST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoQueensParkEast);

        ContentValues locationBulawayoQueensParkWest = new ContentValues();
        locationBulawayoQueensParkWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoQueensParkWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK_WEST);
        locationBulawayoQueensParkWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoQueensParkWest);

        ContentValues locationBulawayoRangemore = new ContentValues();
        locationBulawayoRangemore.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoRangemore.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_RANGEMORE);
        locationBulawayoRangemore.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RANGEMORE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoRangemore);

        ContentValues locationBulawayoRaylton = new ContentValues();
        locationBulawayoRaylton.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoRaylton.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_RAYLTON);
        locationBulawayoRaylton.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RAYLTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoRaylton);

        ContentValues locationBulawayoRichmond = new ContentValues();
        locationBulawayoRichmond.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoRichmond.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_RICHMOND);
        locationBulawayoRichmond.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RICHMOND);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoRichmond);

        ContentValues locationBulawayoRiverside = new ContentValues();
        locationBulawayoRiverside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoRiverside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_RIVERSIDE);
        locationBulawayoRiverside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RIVERSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoRiverside);

        ContentValues locationBulawayoRomneyPark = new ContentValues();
        locationBulawayoRomneyPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoRomneyPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_ROMNEY_PARK);
        locationBulawayoRomneyPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ROMNEY_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoRomneyPark);

        ContentValues locationBulawayoSizinda = new ContentValues();
        locationBulawayoSizinda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSizinda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_SIZINDA);
        locationBulawayoSizinda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SIZINDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSizinda);

        ContentValues locationBulawayoSouthdale = new ContentValues();
        locationBulawayoSouthdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSouthdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_SOUTHDALE);
        locationBulawayoSouthdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SOUTHDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSouthdale);

        ContentValues locationBulawayoSouthwold = new ContentValues();
        locationBulawayoSouthwold.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSouthwold.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_SOUTHWOLD);
        locationBulawayoSouthwold.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SOUTHWOLD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSouthwold);

        ContentValues locationBulawayoSteeldale = new ContentValues();
        locationBulawayoSteeldale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSteeldale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_STEELDALE);
        locationBulawayoSteeldale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_STEELDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSteeldale);

        ContentValues locationBulawayoSunninghill = new ContentValues();
        locationBulawayoSunninghill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSunninghill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_SUNNINGHILL);
        locationBulawayoSunninghill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SUNNINGHILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSunninghill);

        ContentValues locationBulawayoSunnyside = new ContentValues();
        locationBulawayoSunnyside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoSunnyside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_SUNNYSIDE);
        locationBulawayoSunnyside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SUNNYSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoSunnyside);

        ContentValues locationBulawayoTegela = new ContentValues();
        locationBulawayoTegela.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoTegela.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_TEGELA);
        locationBulawayoTegela.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TEGELA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoTegela);

        ContentValues locationBulawayoTheJungle = new ContentValues();
        locationBulawayoTheJungle.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoTheJungle.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_THE_JUNGLE);
        locationBulawayoTheJungle.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_THE_JUNGLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoTheJungle);

        ContentValues locationBulawayoThorngrove = new ContentValues();
        locationBulawayoThorngrove.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoThorngrove.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_THORNGROVE);
        locationBulawayoThorngrove.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_THORNGROVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoThorngrove);

        ContentValues locationBulawayoTrenance = new ContentValues();
        locationBulawayoTrenance.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoTrenance.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_TRENANCE);
        locationBulawayoTrenance.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TRENANCE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoTrenance);

        ContentValues locationBulawayoTshabalala = new ContentValues();
        locationBulawayoTshabalala.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoTshabalala.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_TSHABALALA);
        locationBulawayoTshabalala.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TSHABALALA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoTshabalala);

        ContentValues locationBulawayoTshabalalaExtension = new ContentValues();
        locationBulawayoTshabalalaExtension.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoTshabalalaExtension.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_TSHABALALA_EXTENSION);
        locationBulawayoTshabalalaExtension.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TSHABALALA_EXTENSION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoTshabalalaExtension);

        ContentValues locationBulawayoUmguzaEstate = new ContentValues();
        locationBulawayoUmguzaEstate.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoUmguzaEstate.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_UMGUZA_ESTATE);
        locationBulawayoUmguzaEstate.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_UMGUZA_ESTATE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoUmguzaEstate);

        ContentValues locationBulawayoUpperRangemore = new ContentValues();
        locationBulawayoUpperRangemore.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoUpperRangemore.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_UPPER_RANGEMORE);
        locationBulawayoUpperRangemore.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_UPPER_RANGEMORE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoUpperRangemore);

        ContentValues locationBulawayoWaterford = new ContentValues();
        locationBulawayoWaterford.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWaterford.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WATERFORD);
        locationBulawayoWaterford.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WATERFORD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWaterford);

        ContentValues locationBulawayoWaterlea = new ContentValues();
        locationBulawayoWaterlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWaterlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WATERLEA);
        locationBulawayoWaterlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WATERLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWaterlea);

        ContentValues locationBulawayoWestSomerton = new ContentValues();
        locationBulawayoWestSomerton.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWestSomerton.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WEST_SOMERTON);
        locationBulawayoWestSomerton.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WEST_SOMERTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWestSomerton);

        ContentValues locationBulawayoWestgate = new ContentValues();
        locationBulawayoWestgate.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWestgate.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WESTGATE);
        locationBulawayoWestgate.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WESTGATE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWestgate);

        ContentValues locationBulawayoWestondale = new ContentValues();
        locationBulawayoWestondale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWestondale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WESONDALE);
        locationBulawayoWestondale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WESONDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWestondale);

        ContentValues locationBulawayoWilsgrove = new ContentValues();
        locationBulawayoWilsgrove.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWilsgrove.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WILSGROVE);
        locationBulawayoWilsgrove.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WILSGROVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWilsgrove);

        ContentValues locationBulawayoWindsorPark = new ContentValues();
        locationBulawayoWindsorPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWindsorPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WINDSOR_PARK);
        locationBulawayoWindsorPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WINDSOR_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWindsorPark);

        ContentValues locationBulawayoWoodlands = new ContentValues();
        locationBulawayoWoodlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWoodlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WOODLANDS);
        locationBulawayoWoodlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWoodlands);

        ContentValues locationBulawayoWoodville = new ContentValues();
        locationBulawayoWoodville.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWoodville.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WOODVILLE);
        locationBulawayoWoodville.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODVILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWoodville);

        ContentValues locationBulawayoWoodvillePark = new ContentValues();
        locationBulawayoWoodvillePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_BULAWAYO_CITY_ID);
        locationBulawayoWoodvillePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_NAME_WOODVILLE_PARK);
        locationBulawayoWoodvillePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODVILLE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationBulawayoWoodvillePark);

        ContentValues locationChegutuCBD = new ContentValues();
        locationChegutuCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_CBD);
        locationChegutuCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuCBD);

        ContentValues locationChegutuLocation = new ContentValues();
        locationChegutuLocation.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuLocation.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_CHEGUTU_LOCATION);
        locationChegutuLocation.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CHEGUTU_LOCATION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuLocation);

        ContentValues locationChegutuChestGate = new ContentValues();
        locationChegutuChestGate.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuChestGate.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_CHESTGATE);
        locationChegutuChestGate.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CHESTGATE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuChestGate);

        ContentValues locationChegutuHintonVille = new ContentValues();
        locationChegutuHintonVille.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuHintonVille.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_HINTON_VILLE);
        locationChegutuHintonVille.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_HINTON_VILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuHintonVille);

        ContentValues locationChegutuKaguvi = new ContentValues();
        locationChegutuKaguvi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuKaguvi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_KAGUVI);
        locationChegutuKaguvi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_KAGUVI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuKaguvi);

        ContentValues locationChegutuMvovo = new ContentValues();
        locationChegutuMvovo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuMvovo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_MVOVO);
        locationChegutuMvovo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_MVOVO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuMvovo);

        ContentValues locationChegutuPfupajena = new ContentValues();
        locationChegutuPfupajena.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuPfupajena.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_PFUPAJENA);
        locationChegutuPfupajena.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_PFUPAJENA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuPfupajena);

        ContentValues locationChegutuRifleRangeZMDC = new ContentValues();
        locationChegutuRifleRangeZMDC.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHEGUTU_CITY_ID);
        locationChegutuRifleRangeZMDC.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_NAME_RIFLE_RANGE_ZMDC);
        locationChegutuRifleRangeZMDC.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_RIFLE_RANGE_ZMDC);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChegutuRifleRangeZMDC);

        ContentValues locationChinhoyiBrundish = new ContentValues();
        locationChinhoyiBrundish.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiBrundish.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_BRUNDISH);
        locationChinhoyiBrundish.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_BRUNDISH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiBrundish);

        ContentValues locationChinhoyiCBD = new ContentValues();
        locationChinhoyiCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_CBD);
        locationChinhoyiCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiCBD);

        ContentValues locationChinhoyiChikonohono = new ContentValues();
        locationChinhoyiChikonohono.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiChikonohono.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_CHIKONOHONO);
        locationChinhoyiChikonohono.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_CHIKONOHONO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiChikonohono);

        ContentValues locationChinhoyiColdStream = new ContentValues();
        locationChinhoyiColdStream.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiColdStream.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_COLD_STREAM);
        locationChinhoyiColdStream.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_COLD_STREAM);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiColdStream);

        ContentValues locationChinhoyiGazema = new ContentValues();
        locationChinhoyiGazema.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiGazema.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_GAZEMA);
        locationChinhoyiGazema.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_GAZEMA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiGazema);

        ContentValues locationChinhoyiGunhill = new ContentValues();
        locationChinhoyiGunhill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiGunhill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_GUNHILL);
        locationChinhoyiGunhill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_GUNHILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiGunhill);

        ContentValues locationChinhoyiHunyani = new ContentValues();
        locationChinhoyiHunyani.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiHunyani.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_HUNYANI);
        locationChinhoyiHunyani.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_HUNYANI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiHunyani);

        ContentValues locationChinhoyiMutapaSection = new ContentValues();
        locationChinhoyiMutapaSection.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiMutapaSection.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_MUTAPA_SECTION);
        locationChinhoyiMutapaSection.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_MUTAPA_SECTION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiMutapaSection);

        ContentValues locationChinhoyiMuzare = new ContentValues();
        locationChinhoyiMuzare.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiMuzare.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_MUZARE);
        locationChinhoyiMuzare.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_MUZARE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiMuzare);

        ContentValues locationChinhoyiOrangeGroove = new ContentValues();
        locationChinhoyiOrangeGroove.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiOrangeGroove.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_ORANGE_GROOVE);
        locationChinhoyiOrangeGroove.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_ORANGE_GROOVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiOrangeGroove);

        ContentValues locationChinhoyiRuvimbo = new ContentValues();
        locationChinhoyiRuvimbo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHINHOYI_CITY_ID);
        locationChinhoyiRuvimbo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_NAME_RUVIMBO);
        locationChinhoyiRuvimbo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_RUVIMBO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChinhoyiRuvimbo);

        ContentValues locationChipingeCBD = new ContentValues();
        locationChipingeCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIPINGE_CITY_ID);
        locationChipingeCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_NAME_CBD);
        locationChipingeCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChipingeCBD);

        ContentValues locationChipingeGazaTownship = new ContentValues();
        locationChipingeGazaTownship.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIPINGE_CITY_ID);
        locationChipingeGazaTownship.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_NAME_GAZA_TOWNSHIP);
        locationChipingeGazaTownship.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_GAZA_TOWNSHIP);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChipingeGazaTownship);

        ContentValues locationChipingeLowDensity = new ContentValues();
        locationChipingeLowDensity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIPINGE_CITY_ID);
        locationChipingeLowDensity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_NAME_LOW_DENSITY);
        locationChipingeLowDensity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_LOW_DENSITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChipingeLowDensity);

        ContentValues locationChipingeMediumDensity = new ContentValues();
        locationChipingeMediumDensity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIPINGE_CITY_ID);
        locationChipingeMediumDensity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_NAME_MEDIUM_DENSITY);
        locationChipingeMediumDensity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_MEDIUM_DENSITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChipingeMediumDensity);

        ContentValues locationChipingeUsanga = new ContentValues();
        locationChipingeUsanga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIPINGE_CITY_ID);
        locationChipingeUsanga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_NAME_USANGA);
        locationChipingeUsanga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_USANGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChipingeUsanga);

        ContentValues locationChiredziBuffaloRange = new ContentValues();
        locationChiredziBuffaloRange.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziBuffaloRange.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_BUFFALO_RANGE);
        locationChiredziBuffaloRange.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_BUFFALO_RANGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziBuffaloRange);

        ContentValues locationChiredziCBD = new ContentValues();
        locationChiredziCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_CBD);
        locationChiredziCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziCBD);

        ContentValues locationChiredziHippoValley = new ContentValues();
        locationChiredziHippoValley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziHippoValley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_HIPPO_VALLEY);
        locationChiredziHippoValley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_HIPPO_VALLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziHippoValley);

        ContentValues locationChiredziMalilangwe = new ContentValues();
        locationChiredziMalilangwe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziMalilangwe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_MALILANGWE);
        locationChiredziMalilangwe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_MALILANGWE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziMalilangwe);

        ContentValues locationChiredziMukwasini = new ContentValues();
        locationChiredziMukwasini.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziMukwasini.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_MUKWASINI);
        locationChiredziMukwasini.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_MUKWASINI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziMukwasini);

        ContentValues locationChiredziNandi = new ContentValues();
        locationChiredziNandi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziNandi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_NANDI);
        locationChiredziNandi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_NANDI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziNandi);

        ContentValues locationChiredziTown = new ContentValues();
        locationChiredziTown.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziTown.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_TOWN);
        locationChiredziTown.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TOWN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziTown);

        ContentValues locationChiredziTriangle = new ContentValues();
        locationChiredziTriangle.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziTriangle.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_TRIANGLE);
        locationChiredziTriangle.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TRIANGLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziTriangle);

        ContentValues locationChiredziTshovani = new ContentValues();
        locationChiredziTshovani.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziTshovani.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_TSHOVANI);
        locationChiredziTshovani.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TSHOVANI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziTshovani);

        ContentValues locationChiredziZSA = new ContentValues();
        locationChiredziZSA.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHIREDZI_CITY_ID);
        locationChiredziZSA.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_NAME_ZSA);
        locationChiredziZSA.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_ZSA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChiredziZSA);

        ContentValues locationChitungwizaMakoni = new ContentValues();
        locationChitungwizaMakoni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaMakoni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_MAKONI);
        locationChitungwizaMakoni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MAKONI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaMakoni);

        ContentValues locationChitungwizaManyamepark = new ContentValues();
        locationChitungwizaManyamepark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaManyamepark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_MANYAME_PARK);
        locationChitungwizaManyamepark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MANYAME_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaManyamepark);

        ContentValues locationChitungwizaMayambara = new ContentValues();
        locationChitungwizaMayambara.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaMayambara.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_MAYAMBARA);
        locationChitungwizaMayambara.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MAYAMBARA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaMayambara);

        ContentValues locationChitungwizaNyatsime = new ContentValues();
        locationChitungwizaNyatsime.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaNyatsime.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_NYATSIME);
        locationChitungwizaNyatsime.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_NYATSIME);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaNyatsime);

        ContentValues locationChitungwizaRockview = new ContentValues();
        locationChitungwizaRockview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaRockview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_ROCKVIEW);
        locationChitungwizaRockview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ROCKVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaRockview);

        ContentValues locationChitungwizaSeke = new ContentValues();
        locationChitungwizaSeke.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaSeke.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_SEKE);
        locationChitungwizaSeke.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_SEKE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaSeke);

        ContentValues locationChitungwizaSekeRural = new ContentValues();
        locationChitungwizaSekeRural.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaSekeRural.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_SEKE_RURAL);
        locationChitungwizaSekeRural.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_SEKE_RURAL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaSekeRural);

        ContentValues locationChitungwizaStMarys = new ContentValues();
        locationChitungwizaStMarys.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaStMarys.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_ST_MARYS);
        locationChitungwizaStMarys.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ST_MARYS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaStMarys);

        ContentValues locationChitungwizaZengeza = new ContentValues();
        locationChitungwizaZengeza.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaZengeza.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_ZENGEZA);
        locationChitungwizaZengeza.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ZENGEZA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaZengeza);

        ContentValues locationChitungwizaZiko = new ContentValues();
        locationChitungwizaZiko.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_CHITUNGWIZA_CITY_ID);
        locationChitungwizaZiko.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_NAME_ZIKO);
        locationChitungwizaZiko.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ZIKO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationChitungwizaZiko);

        ContentValues locationGwandaCBD = new ContentValues();
        locationGwandaCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_CBD);
        locationGwandaCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaCBD);

        ContentValues locationGwandaGeneva = new ContentValues();
        locationGwandaGeneva.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaGeneva.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_GENEVA);
        locationGwandaGeneva.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_GENEVA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaGeneva);

        ContentValues locationGwandaJacaranda = new ContentValues();
        locationGwandaJacaranda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaJacaranda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_JACARANDA);
        locationGwandaJacaranda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_JACARANDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaJacaranda);

        ContentValues locationGwandaJahunda = new ContentValues();
        locationGwandaJahunda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaJahunda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_JAHUNDA);
        locationGwandaJahunda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_JAHUNDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaJahunda);

        ContentValues locationGwandaMarriage = new ContentValues();
        locationGwandaMarriage.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaMarriage.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_MARRIAGE);
        locationGwandaMarriage.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_MARRIAGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaMarriage);

        ContentValues locationGwandaPhakama = new ContentValues();
        locationGwandaPhakama.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaPhakama.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_PHAKAMA);
        locationGwandaPhakama.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_PHAKAMA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaPhakama);

        ContentValues locationGwandaSpitzkop = new ContentValues();
        locationGwandaSpitzkop.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaSpitzkop.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_SPITZKOP);
        locationGwandaSpitzkop.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_SPITZKOP);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaSpitzkop);

        ContentValues locationGwandaUltraHigh = new ContentValues();
        locationGwandaUltraHigh.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWANDA_CITY_ID);
        locationGwandaUltraHigh.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWANDA_LOCATION_NAME_ULTRA_HIGH);
        locationGwandaUltraHigh.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWANDA_LOCATION_BROADCAST_RANGE_ULTRA_HIGH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGwandaUltraHigh);

        ContentValues locationGweruAscot = new ContentValues();
        locationGweruAscot.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruAscot.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_ASCOT);
        locationGweruAscot.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_ASCOT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruAscot);

        ContentValues locationGweruAthlone = new ContentValues();
        locationGweruAthlone.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruAthlone.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_ATHLONE);
        locationGweruAthlone.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_ATHLONE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruAthlone);

        ContentValues locationGweruBrackenhurst = new ContentValues();
        locationGweruBrackenhurst.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruBrackenhurst.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_BRACKENHURST);
        locationGweruBrackenhurst.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_BRACKENHURST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruBrackenhurst);

        ContentValues locationGweruCBD = new ContentValues();
        locationGweruCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_CBD);
        locationGweruCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruCBD);

        ContentValues locationGweruChristmasGift = new ContentValues();
        locationGweruChristmasGift.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruChristmasGift.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_CHRISTMAS_GIFT);
        locationGweruChristmasGift.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_CHRISTMAS_GIFT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruChristmasGift);

        ContentValues locationGweruCliftonPark = new ContentValues();
        locationGweruCliftonPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruCliftonPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_CLIFTON_PARK);
        locationGweruCliftonPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_CLIFTON_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruCliftonPark);

        ContentValues locationGweruDaylesford = new ContentValues();
        locationGweruDaylesford.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruDaylesford.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_DAYLESFORD);
        locationGweruDaylesford.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_DAYLESFORD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruDaylesford);

        ContentValues locationGweruEnfield = new ContentValues();
        locationGweruEnfield.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruEnfield.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_ENFIELD);
        locationGweruEnfield.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_ENFIELD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruEnfield);

        ContentValues locationGweruGweruEast = new ContentValues();
        locationGweruGweruEast.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruGweruEast.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_GWERU_EAST);
        locationGweruGweruEast.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_GWERU_EAST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruGweruEast);

        ContentValues locationGweruHarbenPark = new ContentValues();
        locationGweruHarbenPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruHarbenPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_HARBEN_PARK);
        locationGweruHarbenPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_HARBEN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruHarbenPark);

        ContentValues locationGweruHertfordshire = new ContentValues();
        locationGweruHertfordshire.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruHertfordshire.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_HERTFORDSHIRE);
        locationGweruHertfordshire.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_HERTFORDSHIRE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruHertfordshire);

        ContentValues locationGweruIvene = new ContentValues();
        locationGweruIvene.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruIvene.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_IVENE);
        locationGweruIvene.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_IVENE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruIvene);

        ContentValues locationGweruKopje = new ContentValues();
        locationGweruKopje.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruKopje.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_KOPJE);
        locationGweruKopje.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_KOPJE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruKopje);

        ContentValues locationGweruLundiPark = new ContentValues();
        locationGweruLundiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruLundiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_LUNDI_PARK);
        locationGweruLundiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_LUNDI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruLundiPark);

        ContentValues locationGweruMambo= new ContentValues();
        locationGweruMambo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruMambo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_MAMBO);
        locationGweruMambo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_MAMBO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruMambo);

        ContentValues locationGweruMkoba = new ContentValues();
        locationGweruMkoba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruMkoba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_MKOBA);
        locationGweruMkoba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_MKOBA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruMkoba);

        ContentValues locationGweruMtapa = new ContentValues();
        locationGweruMtapa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruMtapa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_MTAPA);
        locationGweruMtapa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_MTAPA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruMtapa);

        ContentValues locationGweruMtausiPark = new ContentValues();
        locationGweruMtausiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruMtausiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_MTAUSI_PARK);
        locationGweruMtausiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_MTAUSI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruMtausiPark);

        ContentValues locationGweruNorthlea = new ContentValues();
        locationGweruNorthlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruNorthlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_NORTHLEA);
        locationGweruNorthlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_NORTHLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruNorthlea);

        ContentValues locationGweruRidgemond = new ContentValues();
        locationGweruRidgemond.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruRidgemond.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_RIDGEMOND);
        locationGweruRidgemond.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_RIDGEMOND);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruRidgemond);

        ContentValues locationGweruRiverside = new ContentValues();
        locationGweruRiverside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruRiverside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_RIVERSIDE);
        locationGweruRiverside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_RIVERSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruRiverside);

        ContentValues locationGweruRundolfPark = new ContentValues();
        locationGweruRundolfPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruRundolfPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_RUNDOLF_PARK);
        locationGweruRundolfPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_RUNDOLF_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruRundolfPark);

        ContentValues locationGweruSenga = new ContentValues();
        locationGweruSenga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruSenga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_SENGA);
        locationGweruSenga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_SENGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruSenga);

        ContentValues locationGweruNehosho = new ContentValues();
        locationGweruNehosho.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruNehosho.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_NEHOSHO);
        locationGweruNehosho.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_NEHOSHO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruNehosho);

        ContentValues locationGweruShamrockPark = new ContentValues();
        locationGweruShamrockPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruShamrockPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_SHAMROCK_PARK);
        locationGweruShamrockPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_SHAMROCK_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruShamrockPark);

        ContentValues locationGweruSithabilePark = new ContentValues();
        locationGweruSithabilePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruSithabilePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_SITHABILE_PARK);
        locationGweruSithabilePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_SITHABILE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruSithabilePark);

        ContentValues locationGweruSouthdowns = new ContentValues();
        locationGweruSouthdowns.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruSouthdowns.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_SOUTHDOWNS);
        locationGweruSouthdowns.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_SOUTHDOWNS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruSouthdowns);

        ContentValues locationGweruSouthview = new ContentValues();
        locationGweruSouthview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruSouthview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_SOUTHVIEW);
        locationGweruAscot.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_SOUTHVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruSouthview);

        ContentValues locationGweruStAnnesDrive = new ContentValues();
        locationGweruStAnnesDrive.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruStAnnesDrive.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_ST_ANNES_DRIVE);
        locationGweruStAnnesDrive.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_ST_ANNES_DRIVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruStAnnesDrive);

        ContentValues locationGweruWindsorPark = new ContentValues();
        locationGweruWindsorPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruWindsorPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_WINDSOR_PARK);
        locationGweruWindsorPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_WINDSOR_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruWindsorPark);

        ContentValues locationGweruWoodlands = new ContentValues();
        locationGweruWoodlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_GWERU_CITY_ID);
        locationGweruWoodlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_GWERU_LOCATION_NAME_WOODLANDS);
        locationGweruWoodlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_GWERU_LOCATION_BROADCAST_RANGE_WOODLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationGweruWoodlands);

        ContentValues locationHarareAdylinn = new ContentValues();
        locationHarareAdylinn.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAdylinn.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ADYLINN);
        locationHarareAdylinn.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ADYLINN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAdylinn);

        ContentValues locationHarareAlexandraPark = new ContentValues();
        locationHarareAlexandraPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAlexandraPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ALEXANDRA_PARK);
        locationHarareAlexandraPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ALEXANDRA_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAlexandraPark);

        ContentValues locationHarareAmby = new ContentValues();
        locationHarareAmby.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAmby.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_AMBY);
        locationHarareAmby.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_AMBY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAmby);

        ContentValues locationHarareArcadia = new ContentValues();
        locationHarareArcadia.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareArcadia.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ARCADIA);
        locationHarareArcadia.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARCADIA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareArcadia);

        ContentValues locationHarareArdbennie = new ContentValues();
        locationHarareArdbennie.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareArdbennie.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ARDBENNIE);
        locationHarareArdbennie.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARDBENNIE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareArdbennie);

        ContentValues locationHarareArlington = new ContentValues();
        locationHarareArlington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareArlington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ARLINGTON);
        locationHarareArlington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARLINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareArlington);

        ContentValues locationHarareAshbrittle = new ContentValues();
        locationHarareAshbrittle.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAshbrittle.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ASHBRITTLE);
        locationHarareAshbrittle.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASHBRITTLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAshbrittle);

        ContentValues locationHarareAshdownPark = new ContentValues();
        locationHarareAshdownPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAshdownPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ASHDOWN_PARK);
        locationHarareAshdownPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASHDOWN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAshdownPark);

        ContentValues locationHarareAspindalePark = new ContentValues();
        locationHarareAspindalePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAspindalePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ASPINDALE_PARK);
        locationHarareAspindalePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASPINDALE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAspindalePark);

        ContentValues locationHarareAthlone = new ContentValues();
        locationHarareAthlone.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAthlone.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ATHLONE);
        locationHarareAthlone.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ATHLONE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAthlone);

        ContentValues locationHarareAvenues = new ContentValues();
        locationHarareAvenues.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAvenues.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_AVENUES);
        locationHarareAvenues.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVENUES);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAvenues);

        ContentValues locationHarareAvondale = new ContentValues();
        locationHarareAvondale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAvondale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_AVONDALE);
        locationHarareAvondale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAvondale);

        ContentValues locationHarareAvondaleWest = new ContentValues();
        locationHarareAvondaleWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAvondaleWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_AVONDALE_WEST);
        locationHarareAvondaleWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONDALE_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAvondaleWest);

        ContentValues locationHarareAvonlea = new ContentValues();
        locationHarareAvonlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareAvonlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_AVONLEA);
        locationHarareAvonlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareAvonlea);

        ContentValues locationHarareBallantynePark = new ContentValues();
        locationHarareBallantynePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBallantynePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BALLANTYNE_PARK);
        locationHarareBallantynePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BALLANTYNE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBallantynePark);

        ContentValues locationHarareBelgravia = new ContentValues();
        locationHarareBelgravia.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBelgravia.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BELGRAVIA);
        locationHarareBelgravia.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BELGRAVIA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBelgravia);

        ContentValues locationHarareBelvedere = new ContentValues();
        locationHarareBelvedere.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBelvedere.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BELVEDERE);
        locationHarareBelvedere.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BELVEDERE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBelvedere);

        ContentValues locationHarareBeverley = new ContentValues();
        locationHarareBeverley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBeverley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BEVERLEY);
        locationHarareBeverley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BEVERLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBeverley);

        ContentValues locationHarareBeverleyWest = new ContentValues();
        locationHarareBeverleyWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBeverleyWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BEVERLEY_WEST);
        locationHarareBeverleyWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BEVERLEY_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBeverleyWest);

        ContentValues locationHarareBloomingdale = new ContentValues();
        locationHarareBloomingdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBloomingdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BLOOMINGDALE);
        locationHarareBloomingdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BLOOMINGDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBloomingdale);

        ContentValues locationHarareBluffHill = new ContentValues();
        locationHarareBluffHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBluffHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BLUFF_HILL);
        locationHarareBluffHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BLUFF_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBluffHill);

        ContentValues locationHarareBorrowdale = new ContentValues();
        locationHarareBorrowdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBorrowdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BORROWDALE);
        locationHarareBorrowdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBorrowdale);

        ContentValues locationHarareBorrowdaleBrooke = new ContentValues();
        locationHarareBorrowdaleBrooke.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBorrowdaleBrooke.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BORROWDALE_BROOKE);
        locationHarareBorrowdaleBrooke.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE_BROOKE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBorrowdaleBrooke);

        ContentValues locationHarareBorrowdaleBrookeWest = new ContentValues();
        locationHarareBorrowdaleBrookeWest.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBorrowdaleBrookeWest.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BORROWDALE_BROOKE_WEST);
        locationHarareBorrowdaleBrookeWest.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE_BROOKE_WEST);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBorrowdaleBrookeWest);

        ContentValues locationHarareBraeside = new ContentValues();
        locationHarareBraeside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBraeside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BRAESIDE);
        locationHarareBraeside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BRAESIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBraeside);

        ContentValues locationHarareBrookeRidge = new ContentValues();
        locationHarareBrookeRidge.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBrookeRidge.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BROOKE_RIDGE);
        locationHarareBrookeRidge.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BROOKE_RIDGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBrookeRidge);

        ContentValues locationHarareBudiriro = new ContentValues();
        locationHarareBudiriro.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareBudiriro.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_BUDIRIRO);
        locationHarareBudiriro.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_BUDIRIRO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareBudiriro);

        ContentValues locationHarareCarrickCreagh = new ContentValues();
        locationHarareCarrickCreagh.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCarrickCreagh.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CARRICK_CREAGH);
        locationHarareCarrickCreagh.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CARRICK_CREAGH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCarrickCreagh);

        ContentValues locationHarareChadcombe = new ContentValues();
        locationHarareChadcombe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChadcombe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHADCOMBE);
        locationHarareChadcombe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHADCOMBE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChadcombe);

        ContentValues locationHarareChikurubi = new ContentValues();
        locationHarareChikurubi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChikurubi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHIKURUBI);
        locationHarareChikurubi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIKURUBI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChikurubi);

        ContentValues locationHarareChipukutu = new ContentValues();
        locationHarareChipukutu.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChipukutu.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHIPUKUTU);
        locationHarareChipukutu.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIPUKUTU);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChipukutu);

        ContentValues locationHarareChirembaPark = new ContentValues();
        locationHarareChirembaPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChirembaPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHIREMBA_PARK);
        locationHarareChirembaPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIREMBA_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChirembaPark);

        ContentValues locationHarareChisipiti = new ContentValues();
        locationHarareChisipiti.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChisipiti.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHISIPITI);
        locationHarareChisipiti.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHISIPITI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChisipiti);

        ContentValues locationHarareChizhanje = new ContentValues();
        locationHarareChizhanje.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareChizhanje.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CHIZHANJE);
        locationHarareChizhanje.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIZHANJE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareChizhanje);

        ContentValues locationHarareCityCentre = new ContentValues();
        locationHarareCityCentre.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCityCentre.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CITY_CENTRE);
        locationHarareCityCentre.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CITY_CENTRE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCityCentre);

        ContentValues locationHarareCivicCentre = new ContentValues();
        locationHarareCivicCentre.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCivicCentre.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CIVIC_CENTRE);
        locationHarareCivicCentre.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CIVIC_CENTRE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCivicCentre);

        ContentValues locationHarareColdComfort = new ContentValues();
        locationHarareColdComfort.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareColdComfort.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_COLD_COMFORT);
        locationHarareColdComfort.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLD_COMFORT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareColdComfort);

        ContentValues locationHarareColneValley = new ContentValues();
        locationHarareColneValley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareColneValley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_COLNE_VALLEY);
        locationHarareColneValley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLNE_VALLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareColneValley);

        ContentValues locationHarareColray = new ContentValues();
        locationHarareColray.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareColray.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_COLRAY);
        locationHarareColray.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLRAY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareColray);

        ContentValues locationHarareCoronationPark = new ContentValues();
        locationHarareCoronationPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCoronationPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CORONATION_PARK);
        locationHarareCoronationPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CORONATION_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCoronationPark);

        ContentValues locationHarareCotswoldHills = new ContentValues();
        locationHarareCotswoldHills.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCotswoldHills.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_COTSWOLD_HILLS);
        locationHarareCotswoldHills.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_COTSWOLD_HILLS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCotswoldHills);

        ContentValues locationHarareCranbournePark = new ContentValues();
        locationHarareCranbournePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCranbournePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CRANBOURNE_PARK);
        locationHarareCranbournePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CRANBOURNE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCranbournePark);

        ContentValues locationHarareCrowborough = new ContentValues();
        locationHarareCrowborough.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareCrowborough.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_CROWBOROUGH);
        locationHarareCrowborough.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_CROWBOROUGH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareCrowborough);

        ContentValues locationHarareDamofalls = new ContentValues();
        locationHarareDamofalls.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareDamofalls.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_DAMOFALLS);
        locationHarareDamofalls.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_DAMOFALLS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareDamofalls);

        ContentValues locationHarareDawnHill = new ContentValues();
        locationHarareDawnHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareDawnHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_DAWN_HILL);
        locationHarareDawnHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_DAWN_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareDawnHill);

        ContentValues locationHarareDonnybrook = new ContentValues();
        locationHarareDonnybrook.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareDonnybrook.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_DONNYBROOK);
        locationHarareDonnybrook.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_DONNYBROOK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareDonnybrook);

        ContentValues locationHarareDzivarasekwa = new ContentValues();
        locationHarareDzivarasekwa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareDzivarasekwa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_DZIVARASEKWA);
        locationHarareDzivarasekwa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_DZIVARASEKWA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareDzivarasekwa);

        ContentValues locationHarareEastlea = new ContentValues();
        locationHarareEastlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEastlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EASTLEA);
        locationHarareEastlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEastlea);

        ContentValues locationHarareEastleaNorth = new ContentValues();
        locationHarareEastleaNorth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEastleaNorth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EASTLEA_NORTH);
        locationHarareEastleaNorth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA_NORTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEastleaNorth);

        ContentValues locationHarareEastleaSouth = new ContentValues();
        locationHarareEastleaSouth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEastleaSouth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EASTLEA_SOUTH);
        locationHarareEastleaSouth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA_SOUTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEastleaSouth);

        ContentValues locationHarareEastview = new ContentValues();
        locationHarareEastview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEastview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EASTVIEW);
        locationHarareEastview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEastview);

        ContentValues locationHarareEmeraldHill = new ContentValues();
        locationHarareEmeraldHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEmeraldHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EMERALD_HILL);
        locationHarareEmeraldHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EMERALD_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEmeraldHill);

        ContentValues locationHarareEpworth = new ContentValues();
        locationHarareEpworth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareEpworth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_EPWORTH);
        locationHarareEpworth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_EPWORTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareEpworth);

        ContentValues locationHarareGevsteinPark = new ContentValues();
        locationHarareGevsteinPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGevsteinPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GEVSTEIN_PARK);
        locationHarareGevsteinPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GEVSTEIN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGevsteinPark);

        ContentValues locationHarareGlaudina = new ContentValues();
        locationHarareGlaudina.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGlaudina.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GLAUDINA);
        locationHarareGlaudina.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLAUDINA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGlaudina);

        ContentValues locationHarareGlenLorne = new ContentValues();
        locationHarareGlenLorne.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGlenLorne.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GLEN_LORNE);
        locationHarareGlenLorne.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_LORNE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGlenLorne);

        ContentValues locationHarareGlenNorah = new ContentValues();
        locationHarareGlenNorah.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGlenNorah.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GLEN_NORAH);
        locationHarareGlenNorah.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_NORAH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGlenNorah);

        ContentValues locationHarareGlenView = new ContentValues();
        locationHarareGlenView.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGlenView.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GLEN_VIEW);
        locationHarareGlenView.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_VIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGlenView);

        ContentValues locationHarareGlenwood = new ContentValues();
        locationHarareGlenwood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGlenwood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GLENWOOD);
        locationHarareGlenwood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLENWOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGlenwood);

        ContentValues locationHarareGrange = new ContentValues();
        locationHarareGrange.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGrange.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GRANGE);
        locationHarareGrange.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GRANGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGrange);

        ContentValues locationHarareGraniteside = new ContentValues();
        locationHarareGraniteside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGraniteside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GRANITESIDE);
        locationHarareGraniteside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GRANITESIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGraniteside);

        ContentValues locationHarareGreenGrove = new ContentValues();
        locationHarareGreenGrove.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGreenGrove.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GREEN_GROVE);
        locationHarareGreenGrove.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREEN_GROVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGreenGrove);

        ContentValues locationHarareGreencroft = new ContentValues();
        locationHarareGreencroft.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGreencroft.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GREENCROFT);
        locationHarareGreencroft.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREENCROFT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGreencroft);

        ContentValues locationHarareGreendale = new ContentValues();
        locationHarareGreendale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGreendale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GREENDALE);
        locationHarareGreendale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREENDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGreendale);

        ContentValues locationHarareGreystonePark = new ContentValues();
        locationHarareGreystonePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGreystonePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GREYSTONE_PARK);
        locationHarareGreystonePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREYSTONE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGreystonePark);

        ContentValues locationHarareGrobbiePark = new ContentValues();
        locationHarareGrobbiePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGrobbiePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GROBBIE_PARK);
        locationHarareGrobbiePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GROBBIE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGrobbiePark);

        ContentValues locationHarareGroombridge = new ContentValues();
        locationHarareGroombridge.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGroombridge.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GROOMBRIDGE);
        locationHarareGroombridge.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GROOMBRIDGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGroombridge);

        ContentValues locationHarareGunHill = new ContentValues();
        locationHarareGunHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareGunHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_GUN_HILL);
        locationHarareGunHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_GUN_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareGunHill);

        ContentValues locationHarareHaigPark = new ContentValues();
        locationHarareHaigPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHaigPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HAIG_PARK);
        locationHarareHaigPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HAIG_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHaigPark);

        ContentValues locationHarareHotcliffe = new ContentValues();
        locationHarareHotcliffe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHotcliffe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HOTCLIFFE);
        locationHarareHotcliffe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOTCLIFFE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHotcliffe);

        ContentValues locationHarareHatfield = new ContentValues();
        locationHarareHatfield.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHatfield.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HATFIELD);
        locationHarareHatfield.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HATFIELD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHatfield);

        ContentValues locationHarareHelensvale = new ContentValues();
        locationHarareHelensvale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHelensvale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HELENSVALE);
        locationHarareHelensvale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HELENSVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHelensvale);

        ContentValues locationHarareHighfield = new ContentValues();
        locationHarareHighfield.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHighfield.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HIGHFIELD);
        locationHarareHighfield.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HIGHFIELD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHighfield);

        ContentValues locationHarareHighlands = new ContentValues();
        locationHarareHighlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHighlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HIGHLANDS);
        locationHarareHighlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HIGHLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHighlands);

        ContentValues locationHarareHillside = new ContentValues();
        locationHarareHillside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHillside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HILLSIDE);
        locationHarareHillside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HILLSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHillside);

        ContentValues locationHarareHogertyHill = new ContentValues();
        locationHarareHogertyHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHogertyHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HOGERTY_HILL);
        locationHarareHogertyHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOGERTY_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHogertyHill);

        ContentValues locationHarareHopley = new ContentValues();
        locationHarareHopley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHopley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HOPLEY);
        locationHarareHopley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOPLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHopley);

        ContentValues locationHarareHoughtonPark = new ContentValues();
        locationHarareHoughtonPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareHoughtonPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_HOUGHTON_PARK);
        locationHarareHoughtonPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOUGHTON_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareHoughtonPark);

        ContentValues locationHarareInduna = new ContentValues();
        locationHarareInduna.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareInduna.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_INDUNA);
        locationHarareInduna.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_INDUNA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareInduna);

        ContentValues locationHarareKambanje = new ContentValues();
        locationHarareKambanje.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKambanje.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KAMBANJE);
        locationHarareKambanje.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KAMBANJE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKambanje);

        ContentValues locationHarareKambuzuma = new ContentValues();
        locationHarareKambuzuma.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKambuzuma.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KAMBUZUMA);
        locationHarareKambuzuma.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KAMBUZUMA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKambuzuma);

        ContentValues locationHarareKensington = new ContentValues();
        locationHarareKensington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKensington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KENSINGTON);
        locationHarareKensington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KENSINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKensington);

        ContentValues locationHarareKopje = new ContentValues();
        locationHarareKopje.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKopje.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KOPJE);
        locationHarareKopje.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KOPJE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKopje);

        ContentValues locationHarareKutsaga = new ContentValues();
        locationHarareKutsaga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKutsaga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KUTSAGA);
        locationHarareKutsaga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KUTSAGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKutsaga);

        ContentValues locationHarareKuwadzana = new ContentValues();
        locationHarareKuwadzana.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareKuwadzana.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_KUWADZANA);
        locationHarareKuwadzana.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_KUWADZANA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareKuwadzana);

        ContentValues locationHarareLetomboPark = new ContentValues();
        locationHarareLetomboPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLetomboPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LETOMBO_PARK);
        locationHarareLetomboPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LETOMBO_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLetomboPark);

        ContentValues locationHarareLewisam = new ContentValues();
        locationHarareLewisam.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLewisam.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LEWISAM);
        locationHarareLewisam.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LEWISAM);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLewisam);

        ContentValues locationHarareLichendale = new ContentValues();
        locationHarareLichendale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLichendale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LICHENDALE);
        locationHarareLichendale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LICHENDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLichendale);

        ContentValues locationHarareLincolnGreen = new ContentValues();
        locationHarareLincolnGreen.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLincolnGreen.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LICOLN_GREEN);
        locationHarareLincolnGreen.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LICOLN_GREEN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLincolnGreen);

        ContentValues locationHarareLittleNorfolk = new ContentValues();
        locationHarareLittleNorfolk.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLittleNorfolk.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LITTLE_NORFOLK );
        locationHarareLittleNorfolk.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LITTLE_NORFOLK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLittleNorfolk);

        ContentValues locationHarareLochinvar = new ContentValues();
        locationHarareLochinvar.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLochinvar.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LOCHINVAR);
        locationHarareLochinvar.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LOCHINVAR);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLochinvar);

        ContentValues locationHarareLoganPark = new ContentValues();
        locationHarareLoganPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLoganPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LOGAN_PARK);
        locationHarareLoganPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LOGAN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLoganPark);

        ContentValues locationHarareLorelei = new ContentValues();
        locationHarareLorelei.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLorelei.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LORELEI);
        locationHarareLorelei.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LORELEI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLorelei);

        ContentValues locationHarareLuna = new ContentValues();
        locationHarareLuna.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareLuna.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_LUNA);
        locationHarareLuna.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_LUNA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareLuna);

        ContentValues locationHarareMabelreign = new ContentValues();
        locationHarareMabelreign.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMabelreign.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MABELREIGN);
        locationHarareMabelreign.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MABELREIGN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMabelreign);

        ContentValues locationHarareMabvuku = new ContentValues();
        locationHarareMabvuku.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMabvuku.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MABVUKU);
        locationHarareMabvuku.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MABVUKU);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMabvuku);

        ContentValues locationHarareMainwayMeadows = new ContentValues();
        locationHarareMainwayMeadows.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMainwayMeadows.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MAINWAY_MEADOWS);
        locationHarareMainwayMeadows.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MAINWAY_MEADOWS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMainwayMeadows);

        ContentValues locationHarareMalvern = new ContentValues();
        locationHarareMalvern.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMalvern.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MALVERN);
        locationHarareMalvern.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MALVERN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMalvern);

        ContentValues locationHarareMandara = new ContentValues();
        locationHarareMandara.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMandara.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MANDARA);
        locationHarareMandara.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANDARA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMandara);

        ContentValues locationHarareManidodaPark = new ContentValues();
        locationHarareManidodaPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareManidodaPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MANIDODA_PARK);
        locationHarareManidodaPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANIDODA_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareManidodaPark);

        ContentValues locationHarareManresa = new ContentValues();
        locationHarareManresa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareManresa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MANRESA);
        locationHarareManresa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANRESA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareManresa);

        ContentValues locationHarareMarimbaPark = new ContentValues();
        locationHarareMarimbaPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMarimbaPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MARIMBA_PARK);
        locationHarareMarimbaPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MARIMBA_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMarimbaPark);

        ContentValues locationHarareMarlborough = new ContentValues();
        locationHarareMarlborough.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMarlborough.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MARLBOROUGH);
        locationHarareMarlborough.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MARLBOROUGH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMarlborough);

        ContentValues locationHarareMayfieldPark = new ContentValues();
        locationHarareMayfieldPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMayfieldPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MAYFIELD_PARK);
        locationHarareMayfieldPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MAYFIELD_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMayfieldPark);

        ContentValues locationHarareMbare = new ContentValues();
        locationHarareMbare.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMbare.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MBARE);
        locationHarareMbare.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MBARE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMbare);

        ContentValues locationHarareMeyrickPark = new ContentValues();
        locationHarareMeyrickPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMeyrickPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MEYRICK_PARK);
        locationHarareMeyrickPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MEYRICK_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMeyrickPark);

        ContentValues locationHarareMidlands = new ContentValues();
        locationHarareMidlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMidlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MIDLANDS);
        locationHarareMidlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MIDLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMidlands);

        ContentValues locationHarareMiltonPark = new ContentValues();
        locationHarareMiltonPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMiltonPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MILTON_PARK);
        locationHarareMiltonPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MILTON_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMiltonPark);

        ContentValues locationHarareMondora = new ContentValues();
        locationHarareMondora.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMondora.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MONDORA);
        locationHarareMondora.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MONDORA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMondora);

        ContentValues locationHarareMonovale = new ContentValues();
        locationHarareMonovale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMonovale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MONOVALE);
        locationHarareMonovale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MONOVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMonovale);

        ContentValues locationHarareMountHampden = new ContentValues();
        locationHarareMountHampden.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMountHampden.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MOUNT_HAMPDEN);
        locationHarareMountHampden.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MOUNT_HAMPDEN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMountHampden);

        ContentValues locationHarareMountPleasant = new ContentValues();
        locationHarareMountPleasant.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMountPleasant.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MOUNT_PLEASANT);
        locationHarareMountPleasant.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MOUNT_PLEASANT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMountPleasant);

        ContentValues locationHarareMsasa = new ContentValues();
        locationHarareMsasa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMsasa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MSASA);
        locationHarareMsasa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MSASA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMsasa);

        ContentValues locationHarareMsasaPark = new ContentValues();
        locationHarareMsasaPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMsasaPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MSASA_PARK);
        locationHarareMsasaPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MSASA_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMsasaPark);

        ContentValues locationHarareMufakose = new ContentValues();
        locationHarareMufakose.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMufakose.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MUFAKOSE);
        locationHarareMufakose.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUFAKOSE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMufakose);

        ContentValues locationHarareMukuvisi = new ContentValues();
        locationHarareMukuvisi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMukuvisi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MUKUVISI);
        locationHarareMukuvisi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUKUVISI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMukuvisi);

        ContentValues locationHarareMukuvisiPark = new ContentValues();
        locationHarareMukuvisiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMukuvisiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MUKUVISI_PARK);
        locationHarareMukuvisiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUKUVISI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMukuvisiPark);

        ContentValues locationHarareNewRidgeview = new ContentValues();
        locationHarareNewRidgeview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareNewRidgeview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_NEW_RIDGEVIEW);
        locationHarareNewRidgeview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_NEW_RIDGEVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareNewRidgeview);

        ContentValues locationHarareNewlands = new ContentValues();
        locationHarareNewlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareNewlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_NEWLANDS);
        locationHarareNewlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_NEWLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareNewlands);

        ContentValues locationHarareNkwisiPark = new ContentValues();
        locationHarareNkwisiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareNkwisiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_NKWISI_PARK);
        locationHarareNkwisiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_NKWISI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareNkwisiPark);

        ContentValues locationHarareNorthwood = new ContentValues();
        locationHarareNorthwood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareNorthwood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_NORTHWOOD);
        locationHarareNorthwood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_NORTHWOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareNorthwood);

        ContentValues locationHarareOldForestPark = new ContentValues();
        locationHarareOldForestPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareOldForestPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_OLD_FOREST_PARK);
        locationHarareOldForestPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_OLD_FOREST_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareOldForestPark);

        ContentValues locationHarareMeadowlands = new ContentValues();
        locationHarareMeadowlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareMeadowlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_MEADOWLANDS);
        locationHarareMeadowlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_MEADOWLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareMeadowlands);

        ContentValues locationHarareParkton = new ContentValues();
        locationHarareParkton.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareParkton.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_PARKTON);
        locationHarareParkton.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_PARKTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareParkton);

        ContentValues locationHararePhiladelphia = new ContentValues();
        locationHararePhiladelphia.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHararePhiladelphia.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_PHILADELPHIA);
        locationHararePhiladelphia.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_PHILADELPHIA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHararePhiladelphia);

        ContentValues locationHararePamona = new ContentValues();
        locationHararePamona.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHararePamona.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_PAMONA);
        locationHararePamona.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_PAMONA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHararePamona);

        ContentValues locationHarareProspect = new ContentValues();
        locationHarareProspect.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareProspect.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_PROSPECT);
        locationHarareProspect.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_PROSPECT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareProspect);

        ContentValues locationHarareProspectPark = new ContentValues();
        locationHarareProspectPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareProspectPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_PROSPECT_PARK);
        locationHarareProspectPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_PROSPECT_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareProspectPark);

        ContentValues locationHarareQueensdale = new ContentValues();
        locationHarareQueensdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareQueensdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_QUEENSDALE);
        locationHarareQueensdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_QUEENSDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareQueensdale);

        ContentValues locationHarareQuinnington = new ContentValues();
        locationHarareQuinnington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareQuinnington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_QUINNINGTON);
        locationHarareQuinnington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_QUINNINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareQuinnington);

        ContentValues locationHarareRhodesville = new ContentValues();
        locationHarareRhodesville.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRhodesville.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RHODESVILLE);
        locationHarareRhodesville.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RHODESVILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRhodesville);

        ContentValues locationHarareRidgeview = new ContentValues();
        locationHarareRidgeview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRidgeview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RIDGEVIEW);
        locationHarareRidgeview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RIDGEVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRidgeview);

        ContentValues locationHarareReitfontein = new ContentValues();
        locationHarareReitfontein.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareReitfontein.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_REITFONTEIN);
        locationHarareReitfontein.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_REITFONTEIN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareReitfontein);

        ContentValues locationHarareRingley = new ContentValues();
        locationHarareRingley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRingley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RINGLEY);
        locationHarareRingley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RINGLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRingley);

        ContentValues locationHarareRolfValley = new ContentValues();
        locationHarareRolfValley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRolfValley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ROLF_VALLEY);
        locationHarareRolfValley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ROLF_VALLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRolfValley);

        ContentValues locationHarareRugare = new ContentValues();
        locationHarareRugare.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRugare.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RUGARE);
        locationHarareRugare.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RUGARE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRugare);

        ContentValues locationHarareRunniville = new ContentValues();
        locationHarareRunniville.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRunniville.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RUNNINGVILLE);
        locationHarareRunniville.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RUNNINGVILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRunniville);

        ContentValues locationHarareRyelands = new ContentValues();
        locationHarareRyelands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareRyelands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_RYELANDS);
        locationHarareRyelands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_RYELANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareRyelands);

        ContentValues locationHarareSangananiPark = new ContentValues();
        locationHarareSangananiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSangananiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SANGANANI_PARK);
        locationHarareSangananiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SANGANANI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSangananiPark);

        ContentValues locationHarareSciencePark = new ContentValues();
        locationHarareSciencePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSciencePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SCIENCE_PARK);
        locationHarareSciencePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SCIENCE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSciencePark);

        ContentValues locationHarareSentosa = new ContentValues();
        locationHarareSentosa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSentosa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SENTOSA);
        locationHarareSentosa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SENTOSA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSentosa);

        ContentValues locationHarareShawashaHills = new ContentValues();
        locationHarareShawashaHills.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareShawashaHills.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SHAWASHA_HILLS);
        locationHarareShawashaHills.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHAWASHA_HILLS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareShawashaHills);

        ContentValues locationHarareSherwoodPark = new ContentValues();
        locationHarareSherwoodPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSherwoodPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SHERWOOD_PARK);
        locationHarareSherwoodPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHERWOOD_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSherwoodPark);

        ContentValues locationHarareShartson = new ContentValues();
        locationHarareShartson.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareShartson.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SHARTSON);
        locationHarareShartson.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHARTSON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareShartson);

        ContentValues locationHarareSoutherton = new ContentValues();
        locationHarareSoutherton.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSoutherton.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SOUTHERTON);
        locationHarareSoutherton.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SOUTHERTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSoutherton);

        ContentValues locationHarareStAndrewsPark = new ContentValues();
        locationHarareStAndrewsPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareStAndrewsPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ST_ANDREWS_PARK);
        locationHarareStAndrewsPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ST_ANDREWS_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareStAndrewsPark);

        ContentValues locationHarareStMartins = new ContentValues();
        locationHarareStMartins.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareStMartins.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ST_MARTINS);
        locationHarareStMartins.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ST_MARTINS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareStMartins);

        ContentValues locationHarareStrathaven = new ContentValues();
        locationHarareStrathaven.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareStrathaven.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_STRATHAVEN);
        locationHarareStrathaven.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_STRATHAVEN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareStrathaven);

        ContentValues locationHarareSunningdale = new ContentValues();
        locationHarareSunningdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSunningdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SUNNINGDALE);
        locationHarareSunningdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNNINGDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSunningdale);

        ContentValues locationHarareSunridge = new ContentValues();
        locationHarareSunridge.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSunridge.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SUNRIDGE);
        locationHarareSunridge.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNRIDGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSunridge);

        ContentValues locationHarareSunrise = new ContentValues();
        locationHarareSunrise.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSunrise.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SUNRISE);
        locationHarareSunrise.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNRISE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSunrise);

        ContentValues locationHarareSunwayCity = new ContentValues();
        locationHarareSunwayCity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareSunwayCity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_SUNWAY_CITY);
        locationHarareSunwayCity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNWAY_CITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareSunwayCity);

        ContentValues locationHarareTafara = new ContentValues();
        locationHarareTafara.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareTafara.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_TAFARA);
        locationHarareTafara.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_TAFARA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareTafara);

        ContentValues locationHarareTheGrange = new ContentValues();
        locationHarareTheGrange.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareTheGrange.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_THE_GRANGE);
        locationHarareTheGrange.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_THE_GRANGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareTheGrange);

        ContentValues locationHarareTynwald = new ContentValues();
        locationHarareTynwald.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareTynwald.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_TYNWALD);
        locationHarareTynwald.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_TYNWALD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareTynwald);

        ContentValues locationHarareUmwinsidale = new ContentValues();
        locationHarareUmwinsidale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareUmwinsidale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_UMWINSIDALE);
        locationHarareUmwinsidale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_UMWINSIDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareUmwinsidale);

        ContentValues locationHarareUplands = new ContentValues();
        locationHarareUplands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareUplands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_UPLANDS);
        locationHarareUplands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_UPLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareUplands);

        ContentValues locationHarareVainona = new ContentValues();
        locationHarareVainona.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareVainona.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_VAINONA);
        locationHarareVainona.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_VAINONA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareVainona);

        ContentValues locationHarareValencedene = new ContentValues();
        locationHarareValencedene.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareValencedene.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_VALENCEDENE);
        locationHarareValencedene.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_VALENCEDENE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareValencedene);

        ContentValues locationHarareVentersburg = new ContentValues();
        locationHarareVentersburg.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareVentersburg.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_VENTERSBURG);
        locationHarareVentersburg.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_VENTERSBURG);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareVentersburg);

        ContentValues locationHarareWarrenPark = new ContentValues();
        locationHarareWarrenPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWarrenPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WARREN_PARK);
        locationHarareWarrenPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WARREN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWarrenPark);

        ContentValues locationHarareWaterfalls = new ContentValues();
        locationHarareWaterfalls.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWaterfalls.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WATERFALLS);
        locationHarareWaterfalls.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WATERFALLS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWaterfalls);

        ContentValues locationHarareWestgate = new ContentValues();
        locationHarareWestgate.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWestgate.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WESTGATE);
        locationHarareWestgate.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WESTGATE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWestgate);

        ContentValues locationHarareWestwood = new ContentValues();
        locationHarareWestwood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWestwood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WESTWOOD);
        locationHarareWestwood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WESTWOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWestwood);

        ContentValues locationHarareWillovale = new ContentValues();
        locationHarareWillovale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWillovale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WILLOVALE);
        locationHarareWillovale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WILLOVALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWillovale);

        ContentValues locationHarareWilmington = new ContentValues();
        locationHarareWilmington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWilmington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WILMINGTON);
        locationHarareWilmington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WILMINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWilmington);

        ContentValues locationHarareWorkington = new ContentValues();
        locationHarareWorkington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareWorkington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_WORKINGTON);
        locationHarareWorkington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_WORKINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareWorkington);

        ContentValues locationHarareZimrePark = new ContentValues();
        locationHarareZimrePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_HARARE_CITY_ID);
        locationHarareZimrePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_HARARE_LOCATION_NAME_ZIMRE_PARK);
        locationHarareZimrePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_HARARE_LOCATION_BROADCAST_RANGE_ZIMRE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationHarareZimrePark);

        ContentValues locationKadomaCBD = new ContentValues();
        locationKadomaCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_CBD);
        locationKadomaCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaCBD);

        ContentValues locationKadomaChakari = new ContentValues();
        locationKadomaChakari.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaChakari.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_CHAKARI);
        locationKadomaChakari.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_CHAKARI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaChakari);

        ContentValues locationKadomaCottonResearch = new ContentValues();
        locationKadomaCottonResearch.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaCottonResearch.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_COTTON_RESEARCH);
        locationKadomaCottonResearch.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_COTTON_RESEARCH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaCottonResearch);

        ContentValues locationKadomaEastview = new ContentValues();
        locationKadomaEastview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaEastview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_EASTVIEW);
        locationKadomaEastview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_EASTVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaEastview);

        ContentValues locationKadomaEiffelFlats = new ContentValues();
        locationKadomaEiffelFlats.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaEiffelFlats.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_EIFFEL_FLATS);
        locationKadomaEiffelFlats.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_EIFFEL_FLATS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaEiffelFlats);

        ContentValues locationKadomaIngezi = new ContentValues();
        locationKadomaIngezi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaIngezi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_INGEZI);
        locationKadomaIngezi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_INGEZI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaIngezi);

        ContentValues locationKadomaMornington = new ContentValues();
        locationKadomaMornington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaMornington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_MORNINGTON);
        locationKadomaMornington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_MORNINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaMornington);

        ContentValues locationKadomaPatchway = new ContentValues();
        locationKadomaPatchway.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaPatchway.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_PATCHWAY);
        locationKadomaPatchway.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_PATCHWAY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaPatchway);

        ContentValues locationKadomaRimuka = new ContentValues();
        locationKadomaRimuka.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaRimuka.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_RIMUKA);
        locationKadomaRimuka.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_RIMUKA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaRimuka);

        ContentValues locationKadomaRioTinto = new ContentValues();
        locationKadomaRioTinto.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaRioTinto.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_RIO_TINTO);
        locationKadomaRioTinto.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_RIO_TINTO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaRioTinto);

        ContentValues locationKadomaWaverly = new ContentValues();
        locationKadomaWaverly.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaWaverly.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_WAVERLY);
        locationKadomaWaverly.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_WAVERLY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaWaverly);

        ContentValues locationKadomaWestview = new ContentValues();
        locationKadomaWestview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KADOMA_CITY_ID);
        locationKadomaWestview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KADOMA_LOCATION_NAME_WESTVIEW);
        locationKadomaWestview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KADOMA_LOCATION_BROADCAST_RANGE_WESTVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKadomaWestview);

        ContentValues locationKaribaAerialHill = new ContentValues();
        locationKaribaAerialHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaAerialHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_AERIAL_HILL);
        locationKaribaAerialHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_AERIAL_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaAerialHill);

        ContentValues locationKaribaBaobabRidge = new ContentValues();
        locationKaribaBaobabRidge.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaBaobabRidge.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_BAOBAB_RIDGE);
        locationKaribaBaobabRidge.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BAOBAB_RIDGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaBaobabRidge);

        ContentValues locationKaribaBatonga = new ContentValues();
        locationKaribaBatonga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaBatonga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_BATONGA);
        locationKaribaBatonga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BATONGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaBatonga);

        ContentValues locationKaribaBoulderRidge = new ContentValues();
        locationKaribaBoulderRidge.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaBoulderRidge.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_BOULDER_RIDGE);
        locationKaribaBoulderRidge.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BOULDER_RIDGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaBoulderRidge);

        ContentValues locationKaribaCBD = new ContentValues();
        locationKaribaCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_CBD);
        locationKaribaCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaCBD);

        ContentValues locationKaribaHeights = new ContentValues();
        locationKaribaHeights.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaHeights.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_HEIGTHS);
        locationKaribaHeights.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_HEIGTHS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaHeights);

        ContentValues locationKaribaHospitalHill = new ContentValues();
        locationKaribaHospitalHill.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaHospitalHill.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_HOSPITAL_HILL);
        locationKaribaHospitalHill.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_HOSPITAL_HILL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaHospitalHill);

        ContentValues locationKaribaMicaPoint = new ContentValues();
        locationKaribaMicaPoint.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaMicaPoint.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_MICA_POINT);
        locationKaribaMicaPoint.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_MICA_POINT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaMicaPoint);

        ContentValues locationKaribaNyamhunga = new ContentValues();
        locationKaribaNyamhunga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KARIBA_CITY_ID);
        locationKaribaNyamhunga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KARIBA_LOCATION_NAME_NYAMHUNGA);
        locationKaribaNyamhunga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KARIBA_LOCATION_BROADCAST_RANGE_NYAMHUNGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaribaNyamhunga);

        ContentValues locationKaroiCBD = new ContentValues();
        locationKaroiCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KAROI_CITY_ID);
        locationKaroiCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KAROI_LOCATION_NAME_CBD);
        locationKaroiCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KAROI_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaroiCBD);

        ContentValues locationKaroiChiedza = new ContentValues();
        locationKaroiChiedza.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KAROI_CITY_ID);
        locationKaroiChiedza.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KAROI_LOCATION_NAME_CHIEDZA);
        locationKaroiChiedza.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KAROI_LOCATION_BROADCAST_RANGE_CHIEDZA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaroiChiedza);

        ContentValues locationKaroiChikangwe = new ContentValues();
        locationKaroiChikangwe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KAROI_CITY_ID);
        locationKaroiChikangwe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KAROI_LOCATION_NAME_CHIKANGWE);
        locationKaroiChikangwe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KAROI_LOCATION_BROADCAST_RANGE_CHIKANGWE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaroiChikangwe);

        ContentValues locationKaroiFlamboyant = new ContentValues();
        locationKaroiFlamboyant.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KAROI_CITY_ID);
        locationKaroiFlamboyant.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KAROI_LOCATION_NAME_FLAMBOYANT);
        locationKaroiFlamboyant.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KAROI_LOCATION_BROADCAST_RANGE_FLAMBOYANT);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKaroiFlamboyant);

        ContentValues locationKwekweAmaveni = new ContentValues();
        locationKwekweAmaveni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweAmaveni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_AMAVENI);
        locationKwekweAmaveni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_AMAVENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweAmaveni);

        ContentValues locationKwekweCBD = new ContentValues();
        locationKwekweCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_CBD);
        locationKwekweCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweCBD);

        ContentValues locationKwekweFitchley = new ContentValues();
        locationKwekweFitchley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweFitchley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_FITCHLEY);
        locationKwekweFitchley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_FITCHLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweFitchley);

        ContentValues locationKwekweGarikai = new ContentValues();
        locationKwekweGarikai.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweGarikai.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_GARIKAI);
        locationKwekweGarikai.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GARIKAI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweGarikai);

        ContentValues locationKwekweGlenhood = new ContentValues();
        locationKwekweGlenhood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweGlenhood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_GLENHOOD);
        locationKwekweGlenhood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GLENHOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweGlenhood);

        ContentValues locationKwekweGoldenAcres = new ContentValues();
        locationKwekweGoldenAcres.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweGoldenAcres.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_GOLDEN_ACRES);
        locationKwekweGoldenAcres.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GOLDEN_ACRES);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweGoldenAcres);

        ContentValues locationKwekweMasasa = new ContentValues();
        locationKwekweMasasa.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweMasasa.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_MASASA);
        locationKwekweMasasa.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_MASASA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweMasasa);

        ContentValues locationKwekweMbizo = new ContentValues();
        locationKwekweMbizo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweMbizo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_MBIZO);
        locationKwekweMbizo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_MBIZO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweMbizo);

        ContentValues locationKwekweNewTown = new ContentValues();
        locationKwekweNewTown.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweNewTown.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_NEW_TOWN);
        locationKwekweNewTown.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_NEW_TOWN);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweNewTown);

        ContentValues locationKwekweRedcliff = new ContentValues();
        locationKwekweRedcliff.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweRedcliff.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_REDCLIFF);
        locationKwekweRedcliff.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_REDCLIFF);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweRedcliff);

        ContentValues locationKwekweRutendo = new ContentValues();
        locationKwekweRutendo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweRutendo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_RUTENDO);
        locationKwekweRutendo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_RUTENDO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweRutendo);

        ContentValues locationKwekweTownhood = new ContentValues();
        locationKwekweTownhood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweTownhood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_TOWNHOOD);
        locationKwekweTownhood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_TOWNHOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweTownhood);

        ContentValues locationKwekweWestend = new ContentValues();
        locationKwekweWestend.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_KWEKWE_CITY_ID);
        locationKwekweWestend.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_KWEKWE_LOCATION_NAME_WESTEND);
        locationKwekweWestend.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_WESTEND);
        db.insert(DBContract.Location.TABLE_NAME,null,locationKwekweWestend);

        ContentValues locationMarondera1stStreet = new ContentValues();
        locationMarondera1stStreet.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMarondera1stStreet.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_1ST_STREET);
        locationMarondera1stStreet.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_1ST_STREET);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMarondera1stStreet);

        ContentValues locationMarondera2ndStreet = new ContentValues();
        locationMarondera2ndStreet.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMarondera2ndStreet.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_2ND_STREET);
        locationMarondera2ndStreet.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_2ND_STREET);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMarondera2ndStreet);

        ContentValues locationMarondera3rdStreet = new ContentValues();
        locationMarondera3rdStreet.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMarondera3rdStreet.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_3RD_STREET);
        locationMarondera3rdStreet.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_3RD_STREET);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMarondera3rdStreet);

        ContentValues locationMarondera4thStreet = new ContentValues();
        locationMarondera4thStreet.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMarondera4thStreet.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_4TH_STREET);
        locationMarondera4thStreet.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_4TH_STREET);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMarondera4thStreet);

        ContentValues locationMaronderaCBD = new ContentValues();
        locationMaronderaCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_CBD);
        locationMaronderaCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaCBD);

        ContentValues locationMaronderaCherimaRujeko = new ContentValues();
        locationMaronderaCherimaRujeko.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaCherimaRujeko.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_CHERIMA_RUJEKO);
        locationMaronderaCherimaRujeko.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CHERIMA_RUJEKO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaCherimaRujeko);

        ContentValues locationMaronderaCherutombo = new ContentValues();
        locationMaronderaCherutombo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaCherutombo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_CHERUTOMBO);
        locationMaronderaCherutombo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CHERUTOMBO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaCherutombo);

        ContentValues locationMaronderaDombotombo = new ContentValues();
        locationMaronderaDombotombo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaDombotombo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_DOMBOTOMBO);
        locationMaronderaDombotombo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_DOMBOTOMBO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaDombotombo);

        ContentValues locationMaronderaGarikaiElveshood = new ContentValues();
        locationMaronderaGarikaiElveshood.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaGarikaiElveshood.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_GARIKAI_ELVESHOOD);
        locationMaronderaGarikaiElveshood.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_GARIKAI_ELVESHOOD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaGarikaiElveshood);

        ContentValues locationMaronderaLendyPark = new ContentValues();
        locationMaronderaLendyPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaLendyPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_LENDY_PARK);
        locationMaronderaLendyPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_LENDY_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaLendyPark);

        ContentValues locationMaronderaMorningside = new ContentValues();
        locationMaronderaMorningside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaMorningside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_MORNINGSIDE);
        locationMaronderaMorningside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_MORNINGSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaMorningside);

        ContentValues locationMaronderaNyameni = new ContentValues();
        locationMaronderaNyameni.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaNyameni.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_NYAMENI);
        locationMaronderaNyameni.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_NYAMENI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaNyameni);

        ContentValues locationMaronderaParadise = new ContentValues();
        locationMaronderaParadise.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaParadise.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_PARADISE);
        locationMaronderaParadise.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_PARADISE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaParadise);

        ContentValues locationMaronderaRusike = new ContentValues();
        locationMaronderaRusike.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaRusike.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_RUSIKE);
        locationMaronderaRusike.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUSIKE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaRusike);

        ContentValues locationMaronderaRuvimboPark = new ContentValues();
        locationMaronderaRuvimboPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaRuvimboPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_RUVIMBO_PARK);
        locationMaronderaRuvimboPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUVIMBO_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaRuvimboPark);

        ContentValues locationMaronderaRuwarePark = new ContentValues();
        locationMaronderaRuwarePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaRuwarePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_RUWARE_PARK);
        locationMaronderaRuwarePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUWARE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaRuwarePark);

        ContentValues locationMaronderaRuzawiPark = new ContentValues();
        locationMaronderaRuzawiPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaRuzawiPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_RUZAWI_PARK);
        locationMaronderaRuzawiPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUZAWI_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaRuzawiPark);

        ContentValues locationMaronderaWistonPark = new ContentValues();
        locationMaronderaWistonPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaWistonPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_WISTON_PARK);
        locationMaronderaWistonPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_WISTON_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaWistonPark);

        ContentValues locationMaronderaYellowCity = new ContentValues();
        locationMaronderaYellowCity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MARONDERA_CITY_ID);
        locationMaronderaYellowCity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MARONDERA_LOCATION_NAME_YELLOW_CITY);
        locationMaronderaYellowCity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_YELLOW_CITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMaronderaYellowCity);

        ContentValues locationMasvingoBuffaloRange = new ContentValues();
        locationMasvingoBuffaloRange.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoBuffaloRange.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BUFFALO_RANGE);
        locationMasvingoBuffaloRange.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_BUFFALO_RANGE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoBuffaloRange);

        ContentValues locationMasvingoCBD = new ContentValues();
        locationMasvingoCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_CBD);
        locationMasvingoCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoCBD);

        ContentValues locationMasvingoClipstornPark = new ContentValues();
        locationMasvingoClipstornPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoClipstornPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_CLIPSTORN_PARK);
        locationMasvingoClipstornPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_CLIPSTORN_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoClipstornPark);

        ContentValues locationMasvingoEastville = new ContentValues();
        locationMasvingoEastville.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoEastville.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_EASTVILLE);
        locationMasvingoEastville.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_EASTVILLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoEastville);

        ContentValues locationMasvingoFourInfantryBattalion = new ContentValues();
        locationMasvingoFourInfantryBattalion.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoFourInfantryBattalion.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_FOUR_INFANTRY_BATTALION);
        locationMasvingoFourInfantryBattalion.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_FOUR_INFANTRY_BATTALION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoFourInfantryBattalion);

        ContentValues locationMasvingoMorningside = new ContentValues();
        locationMasvingoMorningside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoMorningside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_MORNINGSIDE);
        locationMasvingoMorningside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_MORNINGSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoMorningside);

        ContentValues locationMasvingoMucheke = new ContentValues();
        locationMasvingoMucheke.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoMucheke.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_MUCHEKE);
        locationMasvingoMucheke.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_MUCHEKE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoMucheke);

        ContentValues locationMasvingoRhodene = new ContentValues();
        locationMasvingoRhodene.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoRhodene.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_RHODENE);
        locationMasvingoRhodene.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RHODENE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoRhodene);

        ContentValues locationMasvingoRujeko = new ContentValues();
        locationMasvingoRujeko.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoRujeko.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_RUJEKO);
        locationMasvingoRujeko.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RUJEKO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoRujeko);

        ContentValues locationMasvingoRunyararo = new ContentValues();
        locationMasvingoRunyararo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MASVINGO_CITY_ID);
        locationMasvingoRunyararo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MASVINGO_LOCATION_RUNYARARO);
        locationMasvingoRunyararo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RUNYARARO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMasvingoRunyararo);

        ContentValues locationMutareAvenues = new ContentValues();
        locationMutareAvenues.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareAvenues.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_AVENUES);
        locationMutareAvenues.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_AVENUES);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareAvenues);

        ContentValues locationMutareBorderale = new ContentValues();
        locationMutareBorderale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareBorderale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_BORDERALE);
        locationMutareBorderale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_BORDERALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareBorderale);

        ContentValues locationMutareCBD = new ContentValues();
        locationMutareCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_CBD);
        locationMutareCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareCBD);

        ContentValues locationMutareChikanga = new ContentValues();
        locationMutareChikanga.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareChikanga.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_CHIKANGA);
        locationMutareChikanga.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CHIKANGA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareChikanga);

        ContentValues locationMutareChikangaExtension = new ContentValues();
        locationMutareChikangaExtension.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareChikangaExtension.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_CHIKANGA_EXTENSION);
        locationMutareChikangaExtension.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CHIKANGA_EXTENSION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareChikangaExtension);

        ContentValues locationMutareDangamvura = new ContentValues();
        locationMutareDangamvura.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareDangamvura.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_DANGAMVURA);
        locationMutareDangamvura.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_DANGAMVURA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareDangamvura);

        ContentValues locationMutareDarlington = new ContentValues();
        locationMutareDarlington.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareDarlington.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_DARLINGTON);
        locationMutareDarlington.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_DARLINGTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareDarlington);

        ContentValues locationMutareFairbridgePark = new ContentValues();
        locationMutareFairbridgePark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareFairbridgePark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_FAIRBRIDGE_PARK);
        locationMutareFairbridgePark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FAIRBRIDGE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareFairbridgePark);

        ContentValues locationMutareFernValley = new ContentValues();
        locationMutareFernValley.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareFernValley.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_FERN_VALLEY);
        locationMutareFernValley.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FERN_VALLEY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareFernValley);

        ContentValues locationMutareFlorida = new ContentValues();
        locationMutareFlorida.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareFlorida.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_FLORIDA);
        locationMutareFlorida.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FLORIDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareFlorida);

        ContentValues locationMutareGarikaiHlalaniKuhle = new ContentValues();
        locationMutareGarikaiHlalaniKuhle.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareGarikaiHlalaniKuhle.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_GARIKAI_HLALANI_KUHLE);
        locationMutareGarikaiHlalaniKuhle.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GARIKAI_HLALANI_KUHLE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareGarikaiHlalaniKuhle);

        ContentValues locationMutareGimboki = new ContentValues();
        locationMutareGimboki.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareGimboki.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_GIMBOKI);
        locationMutareGimboki.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GIMBOKI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareGimboki);

        ContentValues locationMutareGreenside = new ContentValues();
        locationMutareGreenside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareGreenside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_GREENSIDE);
        locationMutareGreenside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GREENSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareGreenside);

        ContentValues locationMutareGreensideExtension = new ContentValues();
        locationMutareGreensideExtension.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareGreensideExtension.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_GREENSIDE_EXTENSION);
        locationMutareGreensideExtension.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GREENSIDE_EXTENSION);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareGreensideExtension);

        ContentValues locationMutareHobhouse = new ContentValues();
        locationMutareHobhouse.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareHobhouse.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_HOBHOUSE);
        locationMutareHobhouse.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_HOBHOUSE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareHobhouse);

        ContentValues locationMutareMaiMaria = new ContentValues();
        locationMutareMaiMaria.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareMaiMaria.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_MAI_MARIA);
        locationMutareMaiMaria.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MAI_MARIA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareMaiMaria);

        ContentValues locationMutareMorningside = new ContentValues();
        locationMutareMorningside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareMorningside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_MORNINGSIDE);
        locationMutareMorningside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MORNINGSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareMorningside);

        ContentValues locationMutareMurambi = new ContentValues();
        locationMutareMurambi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareMurambi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_MURAMBI);
        locationMutareMurambi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MURAMBI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareMurambi);

        ContentValues locationMutareNatviewPark = new ContentValues();
        locationMutareNatviewPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareNatviewPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_NATVIEW_PARK);
        locationMutareNatviewPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_NATVIEW_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareNatviewPark);

        ContentValues locationMutarePalmerston = new ContentValues();
        locationMutarePalmerston.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutarePalmerston.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_PALMERSTON);
        locationMutarePalmerston.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_PALMERSTON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutarePalmerston);

        ContentValues locationMutareSakubva = new ContentValues();
        locationMutareSakubva.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareSakubva.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_SAKUBVA);
        locationMutareSakubva.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_SAKUBVA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareSakubva);

        ContentValues locationMutareStJosephs = new ContentValues();
        locationMutareStJosephs.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareStJosephs.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_ST_JOSEPHS);
        locationMutareStJosephs.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ST_JOSEPHS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareStJosephs);

        ContentValues locationMutareTigersKloof = new ContentValues();
        locationMutareTigersKloof.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareTigersKloof.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_TIGERS_KLOOF);
        locationMutareTigersKloof.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_TIGERS_KLOOF);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareTigersKloof);

        ContentValues locationMutareToronto = new ContentValues();
        locationMutareToronto.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareToronto.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_TORONTO);
        locationMutareToronto.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_TORONTO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareToronto);

        ContentValues locationMutareUtopia = new ContentValues();
        locationMutareUtopia.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareUtopia.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_UTOPIA);
        locationMutareUtopia.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_UTOPIA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareUtopia);

        ContentValues locationMutareWeirmouth = new ContentValues();
        locationMutareWeirmouth.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareWeirmouth.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_WEIRMOUTH);
        locationMutareWeirmouth.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_WEIRMOUTH);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareWeirmouth);

        ContentValues locationMutareWestlea = new ContentValues();
        locationMutareWestlea.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareWestlea.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_WESTLEA);
        locationMutareWestlea.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_WESTLEA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareWestlea);

        ContentValues locationMutareYeovil = new ContentValues();
        locationMutareYeovil.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareYeovil.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_YEOVIL);
        locationMutareYeovil.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_YEOVIL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareYeovil);

        ContentValues locationMutareZimta = new ContentValues();
        locationMutareZimta.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareZimta.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_ZIMTA);
        locationMutareZimta.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ZIMTA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareZimta);

        ContentValues locationMutareZimunya = new ContentValues();
        locationMutareZimunya.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_MUTARE_CITY_ID);
        locationMutareZimunya.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_MUTARE_LOCATION_NAME_ZIMUNYA);
        locationMutareZimunya.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ZIMUNYA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationMutareZimunya);

        ContentValues locationNyangaCBD = new ContentValues();
        locationNyangaCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_CBD);
        locationNyangaCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaCBD);

        ContentValues locationNyangaDepePark = new ContentValues();
        locationNyangaDepePark .put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaDepePark .put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_DEPE_PARK);
        locationNyangaDepePark .put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_DEPE_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaDepePark);

        ContentValues locationNyangaMangondoza = new ContentValues();
        locationNyangaMangondoza.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaMangondoza.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_MANGONDOZA);
        locationNyangaMangondoza.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_MANGONDOZA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaMangondoza);

        ContentValues locationNyangaNyamuka = new ContentValues();
        locationNyangaNyamuka.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaNyamuka.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_NYAMUKA);
        locationNyangaNyamuka.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_NYAMUKA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaNyamuka);

        ContentValues locationNyangaNyangani = new ContentValues();
        locationNyangaNyangani.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaNyangani.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_NYANGANI);
        locationNyangaNyangani.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_NYANGANI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaNyangani);

        ContentValues locationNyangaRochdale = new ContentValues();
        locationNyangaRochdale.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_NYANGA_CITY_ID);
        locationNyangaRochdale.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_NYANGA_LOCATION_NAME_ROCHDALE);
        locationNyangaRochdale.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_NYANGA_LOCATION_BROADCAST_RANGE_ROCHDALE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationNyangaRochdale);

        ContentValues locationPlumtreeCBD = new ContentValues();
        locationPlumtreeCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_CBD);
        locationPlumtreeCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeCBD);

        ContentValues locationPlumtreeDingumuzi = new ContentValues();
        locationPlumtreeDingumuzi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeDingumuzi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_DINGUMUZI);
        locationPlumtreeDingumuzi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_DINGUMUZI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeDingumuzi);

        ContentValues locationPlumtreeHebron = new ContentValues();
        locationPlumtreeHebron.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeHebron.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_HEBRON);
        locationPlumtreeHebron.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_HEBRON);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeHebron);

        ContentValues locationPlumtreeKariba = new ContentValues();
        locationPlumtreeKariba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeKariba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_KARIBA);
        locationPlumtreeKariba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_KARIBA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeKariba);

        ContentValues locationPlumtreeLakeview = new ContentValues();
        locationPlumtreeLakeview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeLakeview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_LAKEVIE);
        locationPlumtreeLakeview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_LAKEVIE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeLakeview);

        ContentValues locationPlumtreeMadubes = new ContentValues();
        locationPlumtreeMadubes.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeMadubes.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_MADUBES);
        locationPlumtreeMadubes.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MADUBES);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeMadubes);

        ContentValues locationPlumtreeMathendele = new ContentValues();
        locationPlumtreeMathendele.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeMathendele.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_MATHENDELE);
        locationPlumtreeMathendele.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MATHENDELE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeMathendele);

        ContentValues locationPlumtreeMatiwaza = new ContentValues();
        locationPlumtreeMatiwaza.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeMatiwaza.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_MATIWAZA);
        locationPlumtreeMatiwaza.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MATIWAZA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeMatiwaza);

        ContentValues locationPlumtreeMediumDensity = new ContentValues();
        locationPlumtreeMediumDensity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeMediumDensity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_MEDIUM_DENSITY);
        locationPlumtreeMediumDensity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MEDIUM_DENSITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeMediumDensity);

        ContentValues locationPlumtreeRangiore = new ContentValues();
        locationPlumtreeRangiore.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeRangiore.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_RANGIORE);
        locationPlumtreeRangiore.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_RANGIORE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeRangiore);

        ContentValues locationPlumtreeZBS = new ContentValues();
        locationPlumtreeZBS.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_PLUMTREE_CITY_ID);
        locationPlumtreeZBS.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_NAME_ZBS);
        locationPlumtreeZBS.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_ZBS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationPlumtreeZBS);

        ContentValues locationRusapeCastleBase = new ContentValues();
        locationRusapeCastleBase.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeCastleBase.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_CASTLE_BASE);
        locationRusapeCastleBase.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CASTLE_BASE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeCastleBase);

        ContentValues locationRusapeCBD = new ContentValues();
        locationRusapeCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_CBD);
        locationRusapeCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeCBD);

        ContentValues locationRusapeChingaira = new ContentValues();
        locationRusapeChingaira.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeChingaira.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_CHINGAIRA);
        locationRusapeChingaira.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CHINGAIRA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeChingaira);

        ContentValues locationRusapeCrocodile = new ContentValues();
        locationRusapeCrocodile.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeCrocodile.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_CROCODILE);
        locationRusapeCrocodile.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CROCODILE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeCrocodile);

        ContentValues locationRusapeGopal = new ContentValues();
        locationRusapeGopal.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeGopal.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_GOPAL);
        locationRusapeGopal.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_GOPAL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeGopal);

        ContentValues locationRusapeLisapi = new ContentValues();
        locationRusapeLisapi.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeLisapi.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_LISAPI);
        locationRusapeLisapi.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_LISAPI);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeLisapi);

        ContentValues locationRusapeMabvazuva = new ContentValues();
        locationRusapeMabvazuva.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeMabvazuva.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_MABVAZUVA);
        locationRusapeMabvazuva.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MABVAZUVA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeMabvazuva);

        ContentValues locationRusapeMagamba = new ContentValues();
        locationRusapeMagamba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeMagamba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_MAGAMBA);
        locationRusapeMagamba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MAGAMBA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeMagamba);

        ContentValues locationRusapeMbuyanehanda = new ContentValues();
        locationRusapeMbuyanehanda.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeMbuyanehanda.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_MBUYANEHANDA);
        locationRusapeMbuyanehanda.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MBUYANEHANDA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeMbuyanehanda);

        ContentValues locationRusapeNyangaDrive = new ContentValues();
        locationRusapeNyangaDrive.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeNyangaDrive.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_NYANGA_DRIVE);
        locationRusapeNyangaDrive.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_NYANGA_DRIVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeNyangaDrive);

        ContentValues locationRusapeOffNyangaDrive = new ContentValues();
        locationRusapeOffNyangaDrive.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeOffNyangaDrive.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_OFF_NYANGA_DRIVE);
        locationRusapeOffNyangaDrive.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_OFF_NYANGA_DRIVE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeOffNyangaDrive);

        ContentValues locationRusapeSilverpool = new ContentValues();
        locationRusapeSilverpool.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeSilverpool.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_SILVERPOOL);
        locationRusapeSilverpool.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_SILVERPOOL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeSilverpool);

        ContentValues locationRusapeTsanzaguru = new ContentValues();
        locationRusapeTsanzaguru.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeTsanzaguru.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_TSANZAGURU);
        locationRusapeTsanzaguru.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_TSANZAGURU);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeTsanzaguru);

        ContentValues locationRusapeVengere = new ContentValues();
        locationRusapeVengere.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_RUSAPE_CITY_ID);
        locationRusapeVengere.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_RUSAPE_LOCATION_NAME_VENGERE);
        locationRusapeVengere.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_VENGERE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationRusapeVengere);

        ContentValues locationShurugwiCBD = new ContentValues();
        locationShurugwiCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_CBD);
        locationShurugwiCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiCBD);

        ContentValues locationShurugwiDarkCity = new ContentValues();
        locationShurugwiDarkCity.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiDarkCity.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_DARK_CITY);
        locationShurugwiDarkCity.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_DARK_CITY);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiDarkCity);

        ContentValues locationShurugwiIronside = new ContentValues();
        locationShurugwiIronside.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiIronside.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_IRONSIDE);
        locationShurugwiIronside.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_IRONSIDE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiIronside);

        ContentValues locationShurugwiMakusha = new ContentValues();
        locationShurugwiMakusha.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiMakusha.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_MAKUSHA);
        locationShurugwiMakusha.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_MAKUSHA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiMakusha);

        ContentValues locationShurugwiPeakmine = new ContentValues();
        locationShurugwiPeakmine.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiPeakmine.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_PEAKMINE);
        locationShurugwiPeakmine.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_PEAKMINE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiPeakmine);

        ContentValues locationShurugwiRailwayBlock = new ContentValues();
        locationShurugwiRailwayBlock.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiRailwayBlock.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_RAILWAY_BLOCK);
        locationShurugwiRailwayBlock.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_RAILWAY_BLOCK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiRailwayBlock);

        ContentValues locationShurugwiTebekwe = new ContentValues();
        locationShurugwiTebekwe.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiTebekwe.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_TEBEKWE);
        locationShurugwiTebekwe.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_TEBEKWE);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiTebekwe);

        ContentValues locationShurugwiZBS = new ContentValues();
        locationShurugwiZBS.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_SHURUGWI_CITY_ID);
        locationShurugwiZBS.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_NAME_ZBS);
        locationShurugwiZBS.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_ZBS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationShurugwiZBS);

        ContentValues locationVictoriaFallsAerodrome = new ContentValues();
        locationVictoriaFallsAerodrome.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_VICTORIA_FALLS_CITY_ID);
        locationVictoriaFallsAerodrome.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_NAME_AERODROME);
        locationVictoriaFallsAerodrome.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_AERODROME);
        db.insert(DBContract.Location.TABLE_NAME,null,locationVictoriaFallsAerodrome);

        ContentValues locationVictoriaFallsCBD = new ContentValues();
        locationVictoriaFallsCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_VICTORIA_FALLS_CITY_ID);
        locationVictoriaFallsCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_NAME_CBD);
        locationVictoriaFallsCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationVictoriaFallsCBD);

        ContentValues locationVictoriaFallsChinotimba = new ContentValues();
        locationVictoriaFallsChinotimba.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_VICTORIA_FALLS_CITY_ID);
        locationVictoriaFallsChinotimba.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_NAME_CHINOTIMBA);
        locationVictoriaFallsChinotimba.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_CHINOTIMBA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationVictoriaFallsChinotimba);

        ContentValues locationVictoriaFallsMkhosana = new ContentValues();
        locationVictoriaFallsMkhosana.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_VICTORIA_FALLS_CITY_ID);
        locationVictoriaFallsMkhosana.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_NAME_MKHOSANA);
        locationVictoriaFallsMkhosana.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_MKHOSANA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationVictoriaFallsMkhosana);

        ContentValues locationVictoriaFallsSuburbs = new ContentValues();
        locationVictoriaFallsSuburbs.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_VICTORIA_FALLS_CITY_ID);
        locationVictoriaFallsSuburbs.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_NAME_SUBURBS);
        locationVictoriaFallsSuburbs.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_SUBURBS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationVictoriaFallsSuburbs);

        ContentValues locationZvishavaneCBD = new ContentValues();
        locationZvishavaneCBD.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneCBD.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_CBD);
        locationZvishavaneCBD.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_CBD);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneCBD);

        ContentValues locationZvishavaneEastview = new ContentValues();
        locationZvishavaneEastview.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneEastview.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_EASTVIEW);
        locationZvishavaneEastview.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_EASTVIEW);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneEastview);

        ContentValues locationZvishavaneHighlands = new ContentValues();
        locationZvishavaneHighlands.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneHighlands.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_HIGHLANDS);
        locationZvishavaneHighlands.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_HIGHLANDS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneHighlands);

        ContentValues locationZvishavaneKandodo = new ContentValues();
        locationZvishavaneKandodo.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneKandodo.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_KANDODO);
        locationZvishavaneKandodo.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_KANDODO);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneKandodo);

        ContentValues locationZvishavaneMabula = new ContentValues();
        locationZvishavaneMabula.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneMabula.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_MABULA);
        locationZvishavaneMabula.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MABULA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneMabula);

        ContentValues locationZvishavaneMaglas = new ContentValues();
        locationZvishavaneMaglas.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneMaglas.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_MAGLAS);
        locationZvishavaneMaglas.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MAGLAS);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneMaglas);

        ContentValues locationZvishavaneMakwasha = new ContentValues();
        locationZvishavaneMakwasha.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneMakwasha.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_MAKWASHA);
        locationZvishavaneMakwasha.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MAKWASHA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneMakwasha);

        ContentValues locationZvishavaneMandava = new ContentValues();
        locationZvishavaneMandava.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneMandava.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_MANDAVA);
        locationZvishavaneMandava.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MANDAVA);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneMandava);

        ContentValues locationZvishavaneNeil = new ContentValues();
        locationZvishavaneNeil.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneNeil.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_NEIL);
        locationZvishavaneNeil.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_NEIL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneNeil);

        ContentValues locationZvishavaneNovel = new ContentValues();
        locationZvishavaneNovel.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavaneNovel.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_NOVEL);
        locationZvishavaneNovel.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_NOVEL);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavaneNovel);

        ContentValues locationZvishavanePlatinumPark = new ContentValues();
        locationZvishavanePlatinumPark.put(DBContract.Location.COLUMN_CITY_ID,
                DBContract.Location.VALUE_ZVISHAVANE_CITY_ID);
        locationZvishavanePlatinumPark.put(DBContract.Location.COLUMN_LOCATION_NAME,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_NAME_PLATINUM_PARK);
        locationZvishavanePlatinumPark.put(DBContract.Location.COLUMN_LOCATION_BROADCAST_RANGE,
                DBContract.Location.VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_PLATINUM_PARK);
        db.insert(DBContract.Location.TABLE_NAME,null,locationZvishavanePlatinumPark);
    }

}