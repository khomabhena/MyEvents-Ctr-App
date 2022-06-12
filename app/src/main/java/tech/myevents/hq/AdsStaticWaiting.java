package tech.myevents.hq;

import android.database.Cursor;

import java.util.List;

public class AdsStaticWaiting {

    static String adItems;

    static Cursor adsCursor;

    static int notificationCount = 0;

    static List<String> lViews, lLikes, lWhen, lConfCode;
    static List<Integer> lLiked, lViewed, lVideo, lPublish, lCharge;

    static AdsAdapterWaiting adsAdapter;

    static String adParam;

    static List adsList;

    static String[] iInterestCode, iBrandName, iTitle, iDesc1,
            iDesc2, iDesc3, iDesc4, iBitmapName, iViewed, iLiked, iDuration, iVideo, iWhen;

    static Integer[] iId, iAdId, iViews, iLikes;

    static Integer[] sViewed, sLiked, sVideo, sPublish, sCharge;

    static String[] sWhen, sLikes, sViews, sConfCode;

}
