package tech.myevents.hq;

final class App {

    public App() {
    }

    static abstract class AppInfo {
        static final String PREF_USER_INFO = "tech.myevents.hq.UserInfo";
        static final String PREF_BROADCAST_VALUES = "tech.myevents.hq.BroadcastValues";
        static final String PREF_AD_VALUES = "tech.myevents.hq.AdValues";
        static final String PREF_BROADCAST_UPDATES = "tech.myevents.hq.BroadcastUpdates";


        //static final String APP_URL = "http://mabcole.site88.net/androidTest/sign_up.php";
        //static final String VID_URL = "http://mabcole.site88.net/androidTest/me_video/";
        //static final String BITMAP_URL = "http://mabcole.site88.net/androidTest/me_bitmaps/";
        //static final String VID_UPLOAD_URL = "http://mabcole.site88.net/androidTest/upload_to_server.php";


        static final String APP_URL = "https://myevents-app-157108.appspot.com";
        static final String VID_URL = "https://myevents-app-vids.storage.googleapis.com/";
        static final String BITMAP_URL_EVENTS = "https://myevents-app-bitmaps-events.storage.googleapis.com/";
        static final String BITMAP_URL_ADS = "https://myevents-app-bitmaps-ads.storage.googleapis.com/";
        static final String VID_UPLOAD_URL = "https://myevents-app-157108.appspot.com";
    }

}
