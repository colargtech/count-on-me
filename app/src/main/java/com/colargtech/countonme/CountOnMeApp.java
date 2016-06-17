package com.colargtech.countonme;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author juancho.
 */
public class CountOnMeApp extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
