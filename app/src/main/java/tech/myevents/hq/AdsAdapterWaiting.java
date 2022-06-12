package tech.myevents.hq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static tech.myevents.hq.AdsStaticWaiting.adItems;
import static tech.myevents.hq.AdsStaticWaiting.adsList;
import static tech.myevents.hq.AdsStaticWaiting.sCharge;
import static tech.myevents.hq.AdsStaticWaiting.sConfCode;
import static tech.myevents.hq.AdsStaticWaiting.sPublish;
import static tech.myevents.hq.AdsStaticWaiting.sWhen;

class AdsAdapterWaiting extends ArrayAdapter {

    //List list = new ArrayList();
    private SharedPreferences prefsList;
    private SharedPreferences.Editor editor;

    AdsAdapterWaiting(Context context, int resource) {
        super(context, resource);
    }

    public void add(AdsGetSetterWaiting object) {
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
            row = layoutInflater.inflate(R.layout.waiting_ads_layout, parent, false);
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
            adsHolder.ibPublish = (ImageButton) row.findViewById(R.id.ibPublish);
            adsHolder.tvPublish = (TextView) row.findViewById(R.id.tvPublish);
            adsHolder.llLikesViews = (LinearLayout) row.findViewById(R.id.llLikesViews);
            adsHolder.tvConfCode = (TextView) row.findViewById(R.id.tvConfCode);
            adsHolder.ibCharge = (ImageButton) row.findViewById(R.id.ibCharge);

            row.setTag(adsHolder);
        } else {
            adsHolder = (AdsHolder) row.getTag();
        }

        Activity activity = (Activity) getContext();
        prefsList = getContext().getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        final AdsGetSetterWaiting adsGetSetter = (AdsGetSetterWaiting) getItem(position);
        final String posterPath = "sdcard/MyEvents/Ads/MyEvents Posters/";

        Glide.with(getContext())
                .load(posterPath + adsGetSetter.getBitmapName() + ".JPEG")
                .crossFade()
                .into(adsHolder.ivLogo);
        adsHolder.tvBrandName.setText(adsGetSetter.getBrandName());
        adsHolder.tvTitle.setText(adsGetSetter.getTitle());

        if (adItems != null) {
            adsHolder.tvWhen.setText(sWhen[position]);
            adsHolder.ibPublish.setImageResource(sPublish[position]);
            adsHolder.tvConfCode.setText(sConfCode[position]);
            adsHolder.ibCharge.setImageResource(sCharge[position]);
        } else {
            adsHolder.tvWhen.setText(new EventsCalculations(getContext()).calculateTimeReceived(adsGetSetter.getWhen()));
            adsHolder.ibPublish.setImageResource(new EventsCalculations(getContext()).publish(adsGetSetter.getStatus()));
            adsHolder.ibPublish.setImageResource(new EventsCalculations(getContext()).publish(adsGetSetter.getStatus()));
            adsHolder.tvConfCode.setText(new EventsCalculations(getContext()).confCode(adsGetSetter.getConfCOde(), adsGetSetter.getBroadcastCharge()));
        }
        adsHolder.tvPublish.setText(adsGetSetter.getStatus());
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
                Intent intent = new Intent(getContext(), AdContent.class);
                intent.putExtra("position", position);
                intent.putExtra("from", "adsWaiting");
                getContext().startActivity(intent);
            }
        });
        adsHolder.ibPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(adsGetSetter.getId());
                String eventId = String.valueOf(adsGetSetter.getAdId());
                String pos = String.valueOf(position);
                new PublishAd(getContext()).execute(eventId, id, pos);
            }
        });

        return row;
    }

    private static class AdsHolder {
        ImageView ivLogo;
        TextView tvBrandName, tvTitle, tvLike, tvView, tvWhen, tvPublish, tvConfCode;
        ImageButton ibLike, ibView, ibVideo, ibPublish, ibCharge;
        LinearLayout llContent, llLikesViews;
    }
}
