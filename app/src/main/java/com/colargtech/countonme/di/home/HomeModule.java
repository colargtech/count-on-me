package com.colargtech.countonme.di.home;

import com.colargtech.countonme.ui.home.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author juancho.
 */
@Module
public class HomeModule {

    @Provides
    @Singleton
    HomePresenter providesHomePresenter() {
        return new HomePresenter();
    }
}
