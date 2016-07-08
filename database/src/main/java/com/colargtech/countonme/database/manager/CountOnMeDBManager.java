package com.colargtech.countonme.database.manager;

import com.colargtech.countonme.database.model.ActionDB;
import com.colargtech.countonme.database.model.CountDB;
import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;
import com.colargtech.rx_realm.ActionWithRealm;
import com.colargtech.rx_realm.RealmObservableUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
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
    private final Subject<Action, Action> actionCreatedSubject;
    private final Subject<Action, Action> actionUpdateSubject;

    @Inject
    public CountOnMeDBManager(RealmConfiguration configuration, @Named("GroupCreate") Subject<Group, Group> groupCreateSubject,
                              @Named("ActionCreate") Subject<Action, Action> actionCreatedSubject, @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.configuration = configuration;
        this.groupCreateSubject = groupCreateSubject;
        this.actionUpdateSubject = actionUpdateSubject;
        this.actionCreatedSubject = actionCreatedSubject;
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

    public Observable<Action> getAllActions(final String groupID) {
        return RealmObservableUtils
                .createRealObjectObservableFromRealmResultsWithRealm(new ActionWithRealm<RealmResults<ActionDB>>() {
                    @Override
                    public RealmResults<ActionDB> call(Realm realm) {
                        return realm.where(GroupDB.class).equalTo("id", groupID).findFirst().getActions().sort("name");
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB actionDB) {
                        return actionDB.toAction();
                    }
                });
    }

    public Observable<Action> getAction(final String actionID) {
        return RealmObservableUtils
                .createObservableWithRealm(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        return realm.where(ActionDB.class).equalTo("id", actionID).findFirst();
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB actionDB) {
                        return actionDB.toAction();
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
                        groupDB.setActions(new RealmList<ActionDB>());
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

    public void createAction(final String groupId, final String name) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        GroupDB groupDB = realm.where(GroupDB.class).equalTo("id", groupId).findFirst();
                        ActionDB actionDB = new ActionDB();
                        actionDB.setId(UUID.randomUUID().toString());
                        actionDB.setName(name);
                        groupDB.addAction(actionDB);
                        realm.copyToRealmOrUpdate(groupDB);
                        return actionDB;
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB groupDB) {
                        return groupDB.toAction();
                    }
                })
                .subscribe(new Action1<Action>() {
                    @Override
                    public void call(Action action) {
                        actionCreatedSubject.onNext(action);
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

    public void addCustom(final String actionId, final Date date) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        ActionDB actionDB = realm.where(ActionDB.class).equalTo("id", actionId).findFirst();
                        long counts = actionDB.getIncrementBy();
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

    public void removeCounts(final String actionId, final Date date, final long counts) {
        //TODO
    }

    public void decreaseCustom(String actionId, Date date) {
        //TODO
    }
}
