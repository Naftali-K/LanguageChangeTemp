package com.example.languagechangetemp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.Configuration;

import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public class LanguageChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "Test_code";

    private SharedPreferences sharedPreferences;
    private DisplayMetrics displayMetrics;
    private String language;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {

            sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
            language = sharedPreferences.getString(MainActivity.SHARED_PREFERENCES_LANGUAGE, "");

            Resources resources = context.getResources();
            displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            String localLang = configuration.locale.getLanguage();

            if (localLang.equals(language)){
                Log.d(TAG, "setLanguage: same language");
                return;
            } else {
                Log.d(TAG, "onReceive: Set new language, NOT SAME");
            }

            configuration.locale = new Locale(language);
            resources.updateConfiguration(configuration, displayMetrics);
//            onConfigurationChanged(configuration);

            Intent restartIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(restartIntent);

            if (context instanceof Activity) {
                ((Activity)context).finish();
            }
        }
    }
}
