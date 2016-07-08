package com.colargtech.countonme.di.home;

import com.colargtech.countonme.database.di.CountOnMeDatabaseModule;
import com.colargtech.countonme.di.AppModule;
import com.colargtech.countonme.ui.action.ActionsFragment;
import com.colargtech.countonme.ui.count.CountFragment;
import com.colargtech.countonme.ui.home.view.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author juancho.
 */
@Singleton
@Component(
        modules = {AppModule.class, CountOnMeDatabaseModule.class}
)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);

    void inject(ActionsFragment actionsFragment);

    void inject(CountFragment countFragment);
}
