package org.kwork4;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        boolean b = getSharedPreferences("prefs", Context.MODE_PRIVATE).getBoolean("night",false);
        AppCompatDelegate.setDefaultNightMode(b ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate();
        FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(this);
    }
}
