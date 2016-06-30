package com.colargtech.countonme.database.manager;

import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.rx_realm.ActionWithRealm;
import com.colargtech.rx_realm.RealmObservableUtils;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.subjects.Subject;

/**
 * @author gdfesta
 */
@Singleton
public class CountOnMeDBManager {
    private final RealmConfiguration configuration;
    private final Subject<GroupDB, GroupDB> subject;

    @Inject
    public CountOnMeDBManager(RealmConfiguration configuration, Subject<GroupDB, GroupDB> subject) {
        this.configuration = configuration;
        this.subject = subject;
    }

    public Observable<GroupDB> getAllGroups() {
        return RealmObservableUtils
                .createRealObjectObservableFromRealmResultsWithRealm(new ActionWithRealm<RealmResults<GroupDB>>() {
                    @Override
                    public RealmResults<GroupDB> call(Realm realm) {
                        return realm.where(GroupDB.class).findAll();
                    }
                }, this.configuration);
    }

    public void createGroup(String name) {
        final Realm realm = Realm.getInstance(this.configuration);
        realm.beginTransaction();
        GroupDB groupDB = new GroupDB();
        groupDB.setId(UUID.randomUUID().toString());
        groupDB.setName(name);
        realm.copyToRealmOrUpdate(groupDB);
        realm.commitTransaction();
        realm.close();
        subject.onNext(groupDB);
    }
}
