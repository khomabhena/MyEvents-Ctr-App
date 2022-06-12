package tech.myevents.hq;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences p = getSharedPreferences(App.AppInfo.PREF_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("token", token);
        editor.putString("tokenRefresh","yes");
        editor.apply();
    }

}
