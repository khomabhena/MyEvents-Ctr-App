package tech.myevents.hq;

import android.database.Cursor;

import java.util.List;


class EventsStatic {

    static String eventItems;

    static List eventsList;

    static int notificationCount = 0;

    static List<String> lViews, lLikes, lDay, lLocation;
    static List<Integer> lLiked, lViewed, lVideo, lDate, lPlace;

    static Cursor eventsCursor;

    static EventsAdapter eventsAdapter;

    static String eventParam;

    static String[] iInterestCode, iLocationCode, iEventName, iEventVenue, iEventDetails,
            iMinPrice, iMaxPrice, iStartTimestamp, iEndTimestamp, iBitmapName,
            iViewed, iLiked, iVideo, iWhen;


    static Integer[] iId, iEventId, iViews, iLikes;

    static Integer[] sViewed, sLiked, sVideo, sPlace, sDate;

    static String[] sWhen, sLikes, sViews, sLocation, sDay, sDay2, sDaysToGo;

}