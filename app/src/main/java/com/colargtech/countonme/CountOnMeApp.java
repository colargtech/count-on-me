package com.colargtech.countonme;

import android.app.Application;

import com.colargtech.countonme.db.model.CountOnMeOpenHelper;
import com.facebook.stetho.Stetho;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

/**
 * @author juancho.
 */
public class CountOnMeApp extends Application {

    SqlBrite sqlBrite;
    BriteDatabase db;

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        sqlBrite = SqlBrite.create();
        db = sqlBrite.wrapDatabaseHelper(new CountOnMeOpenHelper(getApplicationContext()), Schedulers.io());
    }

    public BriteDatabase getDb() {
        return db;
    }

}
