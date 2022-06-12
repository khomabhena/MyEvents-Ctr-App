package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static tech.myevents.hq.EventsStatic.eventItems;
import static tech.myevents.hq.EventsStatic.eventsList;
import static tech.myevents.hq.EventsStatic.sDate;
import static tech.myevents.hq.EventsStatic.sDay;
import static tech.myevents.hq.EventsStatic.sLiked;
import static tech.myevents.hq.EventsStatic.sLikes;
import static tech.myevents.hq.EventsStatic.sLocation;
import static tech.myevents.hq.EventsStatic.sPlace;
import static tech.myevents.hq.EventsStatic.sVideo;
import static tech.myevents.hq.EventsStatic.sViewed;
import static tech.myevents.hq.EventsStatic.sViews;

class EventsAdapter extends ArrayAdapter {

    SharedPreferences prefsList;
    SharedPreferences.Editor editor;

    EventsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(EventsGetSetter object) {
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
            row = layoutInflater.inflate(R.layout.events_row_layout,parent,false);
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
            eventsHolder.position = position;
            //eventsHolder.tvName.setTypeface(typefaceBC);

            row.setTag(eventsHolder);
        } else {
            eventsHolder = (EventsHolder) row.getTag();
        }

        prefsList = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final EventsGetSetter eventsGetSetter = (EventsGetSetter) getItem(position);
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
            eventsHolder.tvLike.setText(sLikes[position]);
            eventsHolder.tvView.setText(sViews[position]);
            eventsHolder.ibLike.setImageResource(sLiked[position]);
            eventsHolder.ibView.setImageResource(sViewed[position]);
            eventsHolder.ibVideo.setImageResource(sVideo[position]);
        } else {
            eventsHolder.ibPlace.setImageResource(new EventsCalculations(getContext()).checkLocation(eventsGetSetter.getLocationCode()));
            eventsHolder.tvLocation.setText(new EventsCalculations(getContext()).getLocation(eventsGetSetter.getLocationCode()));
            eventsHolder.ibDate.setImageResource(new EventsCalculations(getContext()).checkDate(eventsGetSetter.getStartTimestamp()));
            eventsHolder.tvDate.setText(new EventsCalculations(getContext()).getDate(eventsGetSetter.getStartTimestamp()));
            eventsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(eventsGetSetter.getLikes()));
            eventsHolder.tvView.setText(new EventsCalculations(getContext()).getViews(eventsGetSetter.getViews()));
            eventsHolder.ibLike.setImageResource(new EventsCalculations(getContext()).checkLike(eventsGetSetter.getLiked()));
            eventsHolder.ibView.setImageResource(new EventsCalculations(getContext()).checkView(eventsGetSetter.getViewed()));
            eventsHolder.ibVideo.setImageResource(new EventsCalculations(getContext()).checkVideo(eventsGetSetter.getVideo()));
        }
        eventsHolder.ivBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BitmapView.class);
                intent.putExtra("bitmapName", eventsGetSetter.getBitmapName());
                intent.putExtra("scrollAction","eventScrollPos");
                intent.putExtra("itemPosition", position);
                getContext().startActivity(intent);
            }
        });
        eventsHolder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventContent.class);
                if (eventsGetSetter.getViewed() == null) {
                    int viewPlus = eventsGetSetter.getViews() + 1;
                    eventsHolder.ibView.setImageResource(R.drawable.ib_view_teal);
                    eventsGetSetter.setViewed("yes");
                    eventsGetSetter.setViews(viewPlus);
                    eventsHolder.tvView.setText(new EventsCalculations(getContext()).getViews(viewPlus));
                    if (eventItems != null) {
                        sViews[position] = new EventsCalculations(getContext()).getViews(viewPlus);
                        sViewed[position] = R.drawable.ib_view_teal;
                    }
                    editor = prefsList.edit();
                    editor.putLong("lastEventServerUpdate", 125000);
                    editor.apply();

                    new EventLikes(getContext()).execute("eventView", String.valueOf(eventsGetSetter.getId()),
                            String.valueOf(viewPlus), String.valueOf(eventsGetSetter.getEventId()));
                }
                intent.putExtra("from", "events");
                intent.putExtra("position",position);
                getContext().startActivity(intent);
            }
        });
        eventsHolder.ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = prefsList.edit();
                editor.putLong("lastEventServerUpdate", 125000);
                editor.apply();

                String liked = eventsGetSetter.getLiked();
                String id = String.valueOf(eventsGetSetter.getId());
                String eventId = String.valueOf(eventsGetSetter.getEventId());
                int likePlus = eventsGetSetter.getLikes() + 1;
                int likeMinus = eventsGetSetter.getLikes() - 1;
                if (liked == null) {
                    new EventLikes(getContext()).execute("eventLike", id, "no", String.valueOf(likePlus), eventId);
                    eventsHolder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    eventsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    eventsGetSetter.setLiked("yes");
                    eventsGetSetter.setLikes(likePlus);
                    if (eventItems != null) {
                        sLiked[position] = R.drawable.ib_heart_teal;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                    }
                }
                else if(liked.equals("yes")){
                    new EventLikes(getContext()).execute("eventLike",id,liked, String.valueOf(likeMinus),eventId);

                    eventsHolder.ibLike.setImageResource(R.drawable.ib_heart_gray);
                    eventsGetSetter.setLiked("no");
                    eventsGetSetter.setLikes(likeMinus);
                    eventsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likeMinus));
                    if (eventItems != null) {
                        sLiked[position] = R.drawable.ib_heart_gray;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likeMinus);
                    }
                } else if(liked.equals("no")) {
                    new EventLikes(getContext()).execute("eventLike", id, liked, String.valueOf(likePlus), eventId);
                    eventsHolder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    eventsGetSetter.setLiked("yes");
                    eventsGetSetter.setLikes(likePlus);
                    eventsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    if (eventItems != null) {
                        sLiked[position] = R.drawable.ib_heart_teal;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                    }
                }
            }
        });

        return row;

    }

    private static class EventsHolder {
        ImageButton ibPlace, ibDate, ibLike, ibView, ibVideo;
        ImageView ivBitmap;
        TextView tvName, tvVenue, tvLocation, tvDate, tvLike, tvView;
        LinearLayout llContent, llLikesViews;
        int position;
    }

}
