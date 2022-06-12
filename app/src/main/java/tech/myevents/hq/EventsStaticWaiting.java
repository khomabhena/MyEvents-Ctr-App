package tech.myevents.hq;

import android.database.Cursor;

import java.util.List;

class EventsStaticWaiting {

    static String eventItems;

    static List eventsList;

    static int notificationCount = 0;

    static List<String> lViews, lLikes, lDay, lLocation, lConfCode;
    static List<Integer> lLiked, lViewed, lVideo, lDate, lPlace, lPublish, lCharge;

    static Cursor eventsCursor;

    static EventsAdapterWaiting eventsAdapter;

    static String eventParam;

    static String[] iInterestCode, iLocationCode, iEventName, iEventVenue, iEventDetails,
            iMinPrice, iMaxPrice, iStartTimestamp, iEndTimestamp, iBitmapName,
            iViewed, iLiked, iVideo, iWhen;


    static Integer[] iId, iEventId, iViews, iLikes;

    static Integer[] sViewed, sLiked, sVideo, sPlace, sDate, sPublish, sCharge;

    static String[] sWhen, sLikes, sViews, sLocation, sDay, sDay2, sDaysToGo, sConfCode;

}
