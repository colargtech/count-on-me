package com.colargtech.countonme;

import android.app.Application;
import android.content.Context;

import com.colargtech.countonme.di.AppModule;
import com.colargtech.countonme.di.home.DaggerHomeComponent;
import com.colargtech.countonme.di.home.HomeComponent;
import com.colargtech.countonme.di.home.HomeModule;

/**
 * @author juancho.
 */
public class CountOnMeApp extends Application {

    private HomeComponent homeComponent;

    public HomeComponent getHomeComponent() {
        return homeComponent;
    }

    public static CountOnMeApp get(Context context) {
        return (CountOnMeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initDependencies();
    }

    private void initDependencies() {
        AppModule appModule = new AppModule(this);

        homeComponent = DaggerHomeComponent.builder()
                .appModule(appModule)
                .homeModule(new HomeModule())
                .build();
    }
}
