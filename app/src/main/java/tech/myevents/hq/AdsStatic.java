package tech.myevents.hq;

import android.database.Cursor;

import java.util.List;

class AdsStatic {

    static String adItems;

    static Cursor adsCursor;

    static int notificationCount = 0;

    static List<String> lViews, lLikes, lWhen;
    static List<Integer> lLiked, lViewed, lVideo;

    static AdsAdapter adsAdapter;

    static String adParam;

    static List adsList;

    static String[] iInterestCode, iBrandName, iTitle, iDesc1,
            iDesc2, iDesc3, iDesc4, iBitmapName, iViewed, iLiked, iDuration, iVideo, iWhen;

    static Integer[] iId, iAdId, iViews, iLikes;

    static Integer[] sViewed, sLiked, sVideo;

    static String[] sWhen, sLikes, sViews;

}
