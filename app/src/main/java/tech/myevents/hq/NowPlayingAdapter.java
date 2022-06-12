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

import static tech.myevents.hq.NowplayingStatic.npItems;
import static tech.myevents.hq.NowplayingStatic.npList;
import static tech.myevents.hq.NowplayingStatic.sLiked;
import static tech.myevents.hq.NowplayingStatic.sLikes;
import static tech.myevents.hq.NowplayingStatic.sLocation;
import static tech.myevents.hq.NowplayingStatic.sViewed;
import static tech.myevents.hq.NowplayingStatic.sViews;


class NowPlayingAdapter extends ArrayAdapter {

    SharedPreferences prefsList;
    SharedPreferences.Editor editor;

    NowPlayingAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(NowPlayingGetSetter object) {
        npList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return npList.size();
    }

    @Override
    public Object getItem(int position) {
        return npList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final Holder holder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.now_playing_layout, parent, false);
            holder = new Holder();
            String fontPathBC = "fonts/black_cry.TTF";
            Typeface typefaceBC = Typeface.createFromAsset(getContext().getAssets(), fontPathBC);

            holder.ibLike = (ImageButton) row.findViewById(R.id.ibLike);
            holder.ibView = (ImageButton) row.findViewById(R.id.ibView);
            holder.ivBitmap = (ImageView) row.findViewById(R.id.ivBitmap);
            holder.tvName = (TextView) row.findViewById(R.id.tvName);
            holder.tvVenue = (TextView) row.findViewById(R.id.tvVenue);
            holder.tvLocation = (TextView) row.findViewById(R.id.tvLocation);
            holder.tvPrice = (TextView) row.findViewById(R.id.tvPrice);
            holder.tvLike = (TextView) row.findViewById(R.id.tvLike);
            holder.tvView = (TextView) row.findViewById(R.id.tvView);
            holder.llContent = (LinearLayout) row.findViewById(R.id.llContent);
            holder.llLikesViews = (LinearLayout) row.findViewById(R.id.llLikesViews);
            holder.tvTimeLeft = (TextView) row.findViewById(R.id.tvTimeLeft);
            //eventsHolder.tvName.setTypeface(typefaceBC);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        prefsList = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final NowPlayingGetSetter getSetter = (NowPlayingGetSetter) getItem(position);

        String posterPath = "sdcard/MyEvents/Events/MyEvents Posters/";
        StringBuilder builderPrice = new StringBuilder();
        builderPrice.append("$").append(getSetter.getMinPrice()).append(".00 - ")
                .append("$").append(getSetter.getMaxPrice()).append(".00");

        Glide.with(getContext())
                .load(posterPath + getSetter.getBitmapName() + ".JPEG")
                .crossFade()
                .into(holder.ivBitmap);

        holder.tvName.setText(getSetter.getEventName());
        holder.tvVenue.setText(getSetter.getEventVenue());
        holder.tvPrice.setText(builderPrice);

        if (npItems != null) {
            holder.tvLocation.setText(sLocation[position]);
            holder.tvLike.setText(sLikes[position]);
            holder.tvView.setText(sViews[position]);
            holder.ibLike.setImageResource(sLiked[position]);
            holder.ibView.setImageResource(sViewed[position]);
        } else {
            holder.tvLocation.setText(new EventsCalculations(getContext()).getLocation(getSetter.getLocationCode()));
            holder.tvLike.setText(new EventsCalculations(getContext()).getLikes(getSetter.getLikes()));
            holder.tvView.setText(new EventsCalculations(getContext()).getViews(getSetter.getViews()));
            holder.ibLike.setImageResource(new EventsCalculations(getContext()).checkLike(getSetter.getLiked()));
            holder.ibView.setImageResource(new EventsCalculations(getContext()).checkView(getSetter.getViewed()));
        }
       holder.ivBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BitmapView.class);
                intent.putExtra("bitmapName", getSetter.getBitmapName());
                intent.putExtra("scrollAction","npScrollPos");
                intent.putExtra("itemPosition", position);
                getContext().startActivity(intent);
            }
        });
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventContent.class);
                if (getSetter.getViewed() == null) {
                    int viewPlus = getSetter.getViews() + 1;
                    holder.ibView.setImageResource(R.drawable.ib_view_teal);
                    getSetter.setViewed("yes");
                    getSetter.setViews(viewPlus);
                    holder.tvView.setText(new EventsCalculations(getContext()).getViews(viewPlus));
                    if (npItems != null) {
                        sViews[position] = new EventsCalculations(getContext()).getViews(viewPlus);
                        sViewed[position] = R.drawable.ib_view_teal;
                    }
                    editor = prefsList.edit();
                    editor.putLong("lastEventServerUpdate", 125000);
                    editor.apply();

                    new EventLikes(getContext()).execute("eventView", String.valueOf(getSetter.getId()),
                            String.valueOf(viewPlus), String.valueOf(getSetter.getEventId()));
                }
                intent.putExtra("from", "nowPlaying");
                intent.putExtra("position", position);
                getContext().startActivity(intent);
            }
        });
        holder.ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = prefsList.edit();
                editor.putLong("lastEventServerUpdate", 125000);
                editor.apply();

                String liked = getSetter.getLiked();
                String id = String.valueOf(getSetter.getId());
                String eventId = String.valueOf(getSetter.getEventId());
                int likePlus = getSetter.getLikes() + 1;
                int likeMinus = getSetter.getLikes() - 1;
                if (liked == null) {
                    new EventLikes(getContext()).execute("eventLike", id, "no", String.valueOf(likePlus), eventId);
                    holder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    holder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    getSetter.setLiked("yes");
                    getSetter.setLikes(likePlus);
                    if (npItems != null) {
                        sLiked[position] = R.drawable.ib_heart_teal;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                    }
                }
                else if(liked.equals("yes")){
                    new EventLikes(getContext()).execute("eventLike",id,liked,String.valueOf(likeMinus),eventId);

                    holder.ibLike.setImageResource(R.drawable.ib_heart_gray);
                    getSetter.setLiked("no");
                    getSetter.setLikes(likeMinus);
                    holder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likeMinus));
                    if (npItems != null) {
                        sLiked[position] = R.drawable.ib_heart_gray;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likeMinus);
                    }
                } else if(liked.equals("no")) {
                    new EventLikes(getContext()).execute("eventLike", id, liked, String.valueOf(likePlus), eventId);
                    holder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    getSetter.setLiked("yes");
                    getSetter.setLikes(likePlus);
                    holder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    if (npItems != null) {
                        sLiked[position] = R.drawable.ib_heart_teal;
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                    }
                }
            }
        });
        holder.tvTimeLeft.setText(new EventsCalculations(getContext()).calculateNPTime(getSetter.getEndTimestamp()));


        return row;
    }

    private static class Holder {
        ImageButton ibLike, ibView, ibVideo;
        ImageView ivBitmap;
        TextView tvName, tvVenue, tvLocation, tvLike, tvView, tvPrice, tvTimeLeft;
        LinearLayout llContent, llLikesViews;
        int position;
    }

}
