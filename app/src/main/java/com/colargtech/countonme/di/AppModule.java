package com.colargtech.countonme.di;

import android.content.Context;

import com.colargtech.countonme.CountOnMeApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
