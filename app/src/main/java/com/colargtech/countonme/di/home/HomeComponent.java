package com.colargtech.countonme.di.home;

import com.colargtech.countonme.di.AppModule;
import com.colargtech.countonme.ui.home.view.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author juancho.
 */
@Singleton
@Component(
        modules = {AppModule.class, HomeModule.class}
)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);

}
