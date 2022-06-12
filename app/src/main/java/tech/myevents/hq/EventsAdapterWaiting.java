package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static tech.myevents.hq.EventsStaticWaiting.eventItems;
import static tech.myevents.hq.EventsStaticWaiting.eventsList;
import static tech.myevents.hq.EventsStaticWaiting.sCharge;
import static tech.myevents.hq.EventsStaticWaiting.sConfCode;
import static tech.myevents.hq.EventsStaticWaiting.sDate;
import static tech.myevents.hq.EventsStaticWaiting.sDay;
import static tech.myevents.hq.EventsStaticWaiting.sLocation;
import static tech.myevents.hq.EventsStaticWaiting.sPlace;
import static tech.myevents.hq.EventsStaticWaiting.sPublish;

class EventsAdapterWaiting extends ArrayAdapter {

    SharedPreferences prefsList;
    SharedPreferences.Editor editor;

    EventsAdapterWaiting(Context context, int resource) {
        super(context, resource);
    }

    public void add(EventsGetSetterWaiting object) {
        eventsList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final EventsHolder eventsHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.waiting_events_layout, parent, false);
            eventsHolder = new EventsHolder();
            String fontPathBC = "fonts/black_cry.TTF";
            Typeface typefaceBC = Typeface.createFromAsset(getContext().getAssets(), fontPathBC);

            eventsHolder.ibPlace = (ImageButton) row.findViewById(R.id.ibPlace);
            eventsHolder.ibDate = (ImageButton) row.findViewById(R.id.ibDate);
            eventsHolder.ibLike = (ImageButton) row.findViewById(R.id.ibLike);
            eventsHolder.ibView = (ImageButton) row.findViewById(R.id.ibView);
            eventsHolder.ibVideo = (ImageButton) row.findViewById(R.id.ibVideo);
            eventsHolder.ivBitmap = (ImageView) row.findViewById(R.id.ivBitmap);
            eventsHolder.tvName = (TextView) row.findViewById(R.id.tvName);
            eventsHolder.tvVenue = (TextView) row.findViewById(R.id.tvVenue);
            eventsHolder.tvLocation = (TextView) row.findViewById(R.id.tvLocation);
            eventsHolder.tvDate = (TextView) row.findViewById(R.id.tvDate);
            eventsHolder.tvLike = (TextView) row.findViewById(R.id.tvLike);
            eventsHolder.tvView = (TextView) row.findViewById(R.id.tvView);
            eventsHolder.llContent = (LinearLayout) row.findViewById(R.id.llContent);
            eventsHolder.llLikesViews = (LinearLayout) row.findViewById(R.id.llLikesViews);
            eventsHolder.ibPublish = (ImageButton) row.findViewById(R.id.ibPublish);
            eventsHolder.tvPublish = (TextView) row.findViewById(R.id.tvPublish);
            eventsHolder.ibCharge = (ImageButton) row.findViewById(R.id.ibCharge);
            eventsHolder.tvConfCode = (TextView) row.findViewById(R.id.tvConfCode);
            eventsHolder.position = position;

            row.setTag(eventsHolder);
        } else {
            eventsHolder = (EventsHolder) row.getTag();
        }


        Activity activity = (Activity) getContext();
        prefsList = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final EventsGetSetterWaiting eventsGetSetter = (EventsGetSetterWaiting) getItem(position);
        String posterPath = "sdcard/MyEvents/Events/MyEvents Posters/";

        Glide.with(getContext())
                .load(posterPath + eventsGetSetter.getBitmapName() + ".JPEG")
                .crossFade()
                .into(eventsHolder.ivBitmap);

        eventsHolder.tvName.setText(eventsGetSetter.getEventName());
        eventsHolder.tvVenue.setText(eventsGetSetter.getEventVenue());
        if (eventItems != null) {
            eventsHolder.ibPlace.setImageResource(sPlace[position]);
            eventsHolder.tvLocation.setText(sLocation[position]);
            eventsHolder.ibDate.setImageResource(sDate[position]);
            eventsHolder.tvDate.setText(sDay[position]);
            eventsHolder.ibPublish.setImageResource(sPublish[position]);
            eventsHolder.tvConfCode.setText(sConfCode[position]);
            eventsHolder.ibCharge.setImageResource(sCharge[position]);
        } else {
            eventsHolder.ibPlace.setImageResource(new EventsCalculations(getContext()).checkLocation(eventsGetSetter.getLocationCode()));
            eventsHolder.tvLocation.setText(new EventsCalculations(getContext()).getLocation(eventsGetSetter.getLocationCode()));
            eventsHolder.ibDate.setImageResource(new EventsCalculations(getContext()).checkDate(eventsGetSetter.getStartTimestamp()));
            eventsHolder.tvDate.setText(new EventsCalculations(getContext()).getDate(eventsGetSetter.getStartTimestamp()));
            eventsHolder.ibPublish.setImageResource(new EventsCalculations(getContext()).publish(eventsGetSetter.getStatus()));
            eventsHolder.tvConfCode.setText(new EventsCalculations(getContext()).confCode(eventsGetSetter.getConfCOde(), eventsGetSetter.getBroadcastCharge()));
        }
        eventsHolder.tvPublish.setText(eventsGetSetter.getStatus());
        eventsHolder.ivBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BitmapView.class);
                intent.putExtra("bitmapName", eventsGetSetter.getBitmapName());
                intent.putExtra("scrollAction","eventScrollPosWaiting");
                intent.putExtra("itemPosition", position);
                getContext().startActivity(intent);
            }
        });
        eventsHolder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventContent.class);
                intent.putExtra("from", "eventsWaiting");
                intent.putExtra("position",position);
                getContext().startActivity(intent);
            }
        });
        eventsHolder.ibPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(eventsGetSetter.getId());
                String eventId = String.valueOf(eventsGetSetter.getEventId());
                String pos = String.valueOf(position);
                new PublishEvent(getContext()).execute(eventId, id, pos);

            }
        });

        return row;

    }

    private static class EventsHolder {
        ImageButton ibPlace, ibDate, ibLike, ibView, ibVideo, ibPublish, ibCharge;
        ImageView ivBitmap;
        TextView tvName, tvVenue, tvLocation, tvDate, tvLike, tvView, tvPublish, tvConfCode;
        LinearLayout llContent, llLikesViews;
        int position;
    }

}
