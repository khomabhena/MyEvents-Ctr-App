package tech.myevents.hq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static tech.myevents.hq.AdsStatic.adItems;
import static tech.myevents.hq.AdsStatic.adsList;
import static tech.myevents.hq.AdsStatic.sLiked;
import static tech.myevents.hq.AdsStatic.sLikes;
import static tech.myevents.hq.AdsStatic.sVideo;
import static tech.myevents.hq.AdsStatic.sViewed;
import static tech.myevents.hq.AdsStatic.sViews;
import static tech.myevents.hq.AdsStatic.sWhen;

class AdsAdapter extends ArrayAdapter {

    //List list = new ArrayList();
    private SharedPreferences prefsList;
    private SharedPreferences.Editor editor;

    AdsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(AdsGetSetter object) {
        adsList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return adsList.size();
    }

    @Override
    public Object getItem(int position) {
        return adsList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final AdsHolder adsHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.ads_layout, parent, false);
            adsHolder = new AdsHolder();

            adsHolder.ivLogo = (ImageView) row.findViewById(R.id.ivLogo);
            adsHolder.tvBrandName = (TextView) row.findViewById(R.id.tvBrandName);
            adsHolder.tvTitle = (TextView) row.findViewById(R.id.tvTitle);
            adsHolder.tvLike = (TextView) row.findViewById(R.id.tvLike);
            adsHolder.tvView = (TextView) row.findViewById(R.id.tvView);
            adsHolder.tvWhen = (TextView) row.findViewById(R.id.tvWhen);
            adsHolder.ibLike = (ImageButton) row.findViewById(R.id.ibLike);
            adsHolder.ibView = (ImageButton) row.findViewById(R.id.ibView);
            adsHolder.ibVideo = (ImageButton) row.findViewById(R.id.ibVideo);
            adsHolder.llContent = (LinearLayout) row.findViewById(R.id.llContent);
            adsHolder.llLikesViews = (LinearLayout) row.findViewById(R.id.llLikesViews);

            row.setTag(adsHolder);
        } else {
            adsHolder = (AdsHolder) row.getTag();
        }

        prefsList = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final AdsGetSetter adsGetSetter = (AdsGetSetter) getItem(position);
        final String posterPath = "sdcard/MyEvents/Ads/MyEvents Posters/";

        Glide.with(getContext())
                .load(posterPath + adsGetSetter.getBitmapName() + ".JPEG")
                .crossFade()
                .into(adsHolder.ivLogo);
        adsHolder.tvBrandName.setText(adsGetSetter.getBrandName());
        adsHolder.tvTitle.setText(adsGetSetter.getTitle());

        if (adItems != null) {
            adsHolder.tvWhen.setText(sWhen[position]);
            adsHolder.tvView.setText(sViews[position]);
            adsHolder.tvLike.setText(sLikes[position]);
            adsHolder.ibView.setImageResource(sViewed[position]);
            adsHolder.ibLike.setImageResource(sLiked[position]);
            adsHolder.ibVideo.setImageResource(sVideo[position]);
        } else {
            adsHolder.tvWhen.setText(new EventsCalculations(getContext()).calculateTimeReceived(adsGetSetter.getWhen()));
            adsHolder.tvView.setText(new EventsCalculations(getContext()).getLikes(adsGetSetter.getViews()));
            adsHolder.tvLike.setText(new EventsCalculations(getContext()).getViews(adsGetSetter.getLikes()));
            adsHolder.ibView.setImageResource(new EventsCalculations(getContext()).checkView(adsGetSetter.getViewed()));
            adsHolder.ibLike.setImageResource(new EventsCalculations(getContext()).checkLike(adsGetSetter.getLiked()));
            adsHolder.ibVideo.setImageResource(new EventsCalculations(getContext()).checkVideo(adsGetSetter.getVideo()));
        }

        adsHolder.ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogoView.class);
                intent.putExtra("itemPosition", position);
                intent.putExtra("bitmapName", adsGetSetter.getBitmapName());
                intent.putExtra("scrollAction","adScrollPosWaiting");
                getContext().startActivity(intent);
            }
        });
        adsHolder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adsGetSetter.getViewed() == null) {
                    int viewPlus = adsGetSetter.getViews() + 1;
                    adsHolder.ibView.setImageResource(R.drawable.ib_view_teal);
                    adsGetSetter.setViewed("yes");
                    adsGetSetter.setViews(viewPlus);
                    adsHolder.tvView.setText(new EventsCalculations(getContext()).getViews(viewPlus));
                    if (adItems != null) {
                        sViews[position] = new EventsCalculations(getContext()).getViews(viewPlus);
                        sViewed[position] = R.drawable.ib_view_teal;
                    }

                    editor = prefsList.edit();
                    editor.putLong("lastAdServerUpdate", 125000);
                    editor.apply();
                    new AdLikes(getContext()).execute("adView", String.valueOf(adsGetSetter.getId()),
                            String.valueOf(viewPlus), String.valueOf(adsGetSetter.getAdId()));
                }

                Intent intent = new Intent(getContext(), AdContent.class);
                intent.putExtra("position", position);
                intent.putExtra("from", "ads");
                getContext().startActivity(intent);
            }
        });
        adsHolder.ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = prefsList.edit();
                editor.putLong("lastAdServerUpdate", 125000);
                editor.apply();
                String liked = adsGetSetter.getLiked();
                String id = String.valueOf(adsGetSetter.getId());
                String adId = String.valueOf(adsGetSetter.getAdId());
                int likePlus = adsGetSetter.getLikes() + 1;
                int likeMinus = adsGetSetter.getLikes() - 1;
                if (liked == null) {
                    new AdLikes(getContext()).execute("adLike", id, "no", String.valueOf(likePlus), adId);
                    adsHolder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    adsGetSetter.setLiked("yes");
                    adsGetSetter.setLikes(likePlus);
                    adsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    if (adItems != null) {
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                        sLiked[position] = R.drawable.ib_heart_teal;
                    }
                }
                else if (liked.equals("yes")) {
                    new AdLikes(getContext()).execute("adLike", id, liked, String.valueOf(likeMinus), adId);
                    adsHolder.ibLike.setImageResource(R.drawable.ib_heart_gray);
                    adsGetSetter.setLiked("no");
                    adsGetSetter.setLikes(likeMinus);
                    adsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likeMinus));
                    if (adItems != null) {
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likeMinus);
                        sLiked[position] = R.drawable.ib_heart_gray;
                    }
                }
                else if (liked.equals("no")) {
                    new AdLikes(getContext()).execute("adLike", id, liked, String.valueOf(likePlus), adId);
                    adsHolder.ibLike.setImageResource(R.drawable.ib_heart_teal);
                    adsGetSetter.setLiked("yes");
                    adsGetSetter.setLikes(likePlus);
                    adsHolder.tvLike.setText(new EventsCalculations(getContext()).getLikes(likePlus));
                    if (adItems != null) {
                        sLikes[position] = new EventsCalculations(getContext()).getLikes(likePlus);
                        sLiked[position] = R.drawable.ib_heart_teal;
                    }
                }
            }
        });

        return row;
    }

    private static class AdsHolder {
        ImageView ivLogo;
        TextView tvBrandName, tvTitle, tvLike, tvView, tvWhen;
        ImageButton ibLike, ibView, ibVideo;
        LinearLayout llContent, llLikesViews;
    }

}