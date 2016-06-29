package com.colargtech.countonme.di;

import android.content.Context;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.database.CountOnMeDatabaseModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

/**
 * @author juancho.
 */
@Module
public class AppModule {
    private final CountOnMeApp app;

    public AppModule(CountOnMeApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    CountOnMeApp providesApplication() {
        return app;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return app;
    }

    @Provides
    @Singleton
    public RealmConfiguration provideAppRealmConfiguration() {
        return new RealmConfiguration
                .Builder(this.app)
                .name("countonme.app")
                .modules(new CountOnMeDatabaseModule())
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
