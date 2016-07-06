package com.colargtech.countonme.database.di;

import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * @author juancho.
 */
@Module
public class CountOnMeDatabaseModule {

    @Provides
    @Singleton
    @Named("GroupCreate")
    public Subject<Group, Group> provideGroupCreateSubject() {
        return BehaviorSubject.create();
    }

    @Provides
    @Singleton
    @Named("ActionUpdate")
    public Subject<Action, Action> provideActionUpdate() {
        return BehaviorSubject.create();
    }
}
