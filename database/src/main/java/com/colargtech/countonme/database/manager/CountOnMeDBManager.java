package com.colargtech.countonme.database.manager;

import com.colargtech.countonme.database.model.ActionDB;
import com.colargtech.countonme.database.model.CountDB;
import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;
import com.colargtech.rx_realm.ActionWithRealm;
import com.colargtech.rx_realm.RealmObservableUtils;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.Subject;

/**
 * @author gdfesta
 */
@Singleton
public class CountOnMeDBManager {
    private final RealmConfiguration configuration;
    private final Subject<Group, Group> groupCreateSubject;
    private final Subject<Action, Action> actionUpdateSubject;

    @Inject
    public CountOnMeDBManager(RealmConfiguration configuration, @Named("GroupCreate") Subject<Group, Group> groupCreateSubject,
                              @Named("ActionUpdate") Subject<Action, Action> actionSub) {
        this.configuration = configuration;
        this.groupCreateSubject = groupCreateSubject;
        this.actionUpdateSubject = actionSub;
    }

    public Observable<Group> getAllGroups() {
        return RealmObservableUtils
                .createRealObjectObservableFromRealmResultsWithRealm(new ActionWithRealm<RealmResults<GroupDB>>() {
                    @Override
                    public RealmResults<GroupDB> call(Realm realm) {
                        return realm.where(GroupDB.class).findAll();
                    }
                }, this.configuration)
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return groupDB.toGroup();
                    }
                });
    }

    public void createGroup(final String name) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<GroupDB>() {
                    @Override
                    public GroupDB call(Realm realm) {
                        GroupDB groupDB = new GroupDB();
                        groupDB.setId(UUID.randomUUID().toString());
                        groupDB.setName(name);
                        realm.copyToRealmOrUpdate(groupDB);
                        return groupDB;
                    }
                }, this.configuration)
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return groupDB.toGroup();
                    }
                })
                .subscribe(new Action1<Group>() {
                    @Override
                    public void call(Group group) {
                        groupCreateSubject.onNext(group);
                    }
                });
    }

    public void addCount(final String actionId, final Date date, final long counts) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        ActionDB actionDB = realm.where(ActionDB.class).equalTo("id", actionId).findFirst();
                        for (long count = 0; count < counts; count++) {
                            CountDB countDB = new CountDB();
                            countDB.setId(UUID.randomUUID().toString());
                            countDB.setDate(date);
                            actionDB.getCounts().add(countDB);
                        }
                        realm.copyToRealmOrUpdate(actionDB);
                        return actionDB;
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB actionDB) {
                        return actionDB.toAction();
                    }
                })
                .subscribe(new Action1<Action>() {
                    @Override
                    public void call(Action action) {
                        actionUpdateSubject.onNext(action);
                    }
                });
    }
}
